package webApp.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webApp.common.ToDo;
import webApp.common.ToDoList;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/update" })
public class UpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		String taskOld = req.getParameter("taskOld");
		String taskNew = req.getParameter("taskNew");
		String contextOld = req.getParameter("contextOld");
		String contextNew = req.getParameter("contextNew");
		String projectOld = req.getParameter("projectOld");
		String projectNew = req.getParameter("projectNew");
		
		int priorityOld = 0;
		int priorityNew = 0;
		boolean numberError = false;
		try{
			priorityOld = Integer.parseInt(req.getParameter("priorityOld"));
		} catch (NumberFormatException e){
			numberError = true;
		}
		try{
			priorityNew = Integer.parseInt(req.getParameter("priorityNew"));
		} catch (NumberFormatException e){
			priorityNew = -1;
		}
		
		if (taskOld == null || taskOld.equals("") || contextOld == null || 
				contextOld.equals("") || projectOld == null || projectOld.equals("")){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head><body><h1 align=\"center\">"
					+ "Error updatind the task</h1><br/><h2 align=\"center\">"
					+ "Please, complete all the fields</h2><br/>"
					+ "<input type=\"button\" value=\"Back\" "
					+ "onClick=\"location.href='./index.html'\"/>"
					+ "</body></html>");
		}else{
			if (numberError){
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">"
						+ "Error updating the task</h1><br/><h2 align=\"center\">"
						+ "Please, insert a numeric priority</h2><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}else{
				ToDo todoOld = new ToDo();
				todoOld.setTask(taskOld);
				todoOld.setContext(contextOld);
				todoOld.setProject(projectOld);
				todoOld.setPriority(priorityOld);

				String status = "";
				String info = "";
				boolean find = false;
				
				// Get all the tasks
				Client client = ClientBuilder.newClient();
				Response resp2 = client.target("http://localhost:8081/tasks")
						.request(MediaType.APPLICATION_JSON).get();
						
				if (resp2.getStatus() == HttpServletResponse.SC_OK){
					ToDoList list = resp2.readEntity(ToDoList.class);
					
					// Search the current task
					for (ToDo t : list.getList()){
						if (t.equals(todoOld)){
							// If the task is found, it will be updated with 
							// the new fields
							find = true;
							ToDo todoNew = new ToDo();
							
							if (taskNew != null && !taskNew.equals(""))
								todoNew.setTask(taskNew);
							else
								todoNew.setTask(taskOld);
							
							if (contextNew != null && !contextNew.equals(""))
								todoNew.setContext(contextNew);
							else
								todoNew.setContext(contextOld);
							
							if (projectNew != null && !projectNew.equals(""))
								todoNew.setProject(projectNew);
							else
								todoNew.setProject(projectOld);
							
							if (priorityNew != -1)
								todoNew.setPriority(priorityNew);
							else
								todoNew.setPriority(priorityOld);
							
							todoNew.setHref(todoOld.getHref());
							todoNew.setId(todoOld.getId());
							
							Response resp3 = client.target(t.getHref())
									.request(MediaType.APPLICATION_JSON)
									.put(Entity.entity(todoNew, MediaType.APPLICATION_JSON));
							
							status += resp3.getStatus();
							info += resp3.getStatusInfo();
							resp.setStatus(resp3.getStatus());
						}
					}
					if (!find){
						status += HttpServletResponse.SC_NOT_FOUND;
						info += "Not Found";
					}
				}else{
					status += resp2.getStatus();
					info += resp2.getStatusInfo();
				}
				
				resp.setStatus(resp2.getStatus());
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">" 
						+ status + ": "
						+ info + "</h1><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}
		}
	}
}