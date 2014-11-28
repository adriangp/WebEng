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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webApp.common.ToDo;
import webApp.common.ToDoList;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/remove" })
public class RemoveServlet extends HttpServlet {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		String task = req.getParameter("task");
		String context = req.getParameter("context");
		String project = req.getParameter("project");
		int priority = 0;
		boolean numberError = false;
		try{
			priority = Integer.parseInt(req.getParameter("priority"));
		} catch (NumberFormatException e){
			numberError = true;
		}
		
		if (task.equals("") || task == null || context.equals("") || 
				context == null || project.equals("") || project == null){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head><body><h1 align=\"center\">"
					+ "Error removing the task</h1><br/><h2 align=\"center\">"
					+ "Please, complete all the fields</h2><br/>"
					+ "<input type=\"button\" value=\"Back\" "
					+ "onClick=\"location.href='./index.html'\"/>"
					+ "</body></html>");
		}else{
			if (numberError){
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">"
						+ "Error removing the task</h1><br/><h2 align=\"center\">"
						+ "Please, insert a numeric priority</h2><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}else{
				ToDo todo = new ToDo();
				todo.setTask(task);
				todo.setContext(context);
				todo.setProject(project);
				todo.setPriority(priority);

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
						if (t.equals(todo)){
							// If the task is found, it will be deleted
							find = true;
							Response resp3 = client.target(t.getHref())
									.request(MediaType.APPLICATION_JSON).delete();
							
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