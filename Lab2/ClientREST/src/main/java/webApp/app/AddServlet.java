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

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/add" })
public class AddServlet extends HttpServlet {
	
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
		
		if (task == null || task.equals("") || context == null || 
				context.equals("") || project == null || project.equals("")){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head><body><h1 align=\"center\">"
					+ "Error adding the task</h1><br/><h2 align=\"center\">"
					+ "Please, complete all the fields</h2><br/>"
					+ "<input type=\"button\" value=\"Back\" "
					+ "onClick=\"location.href='./index.html'\"/>"
					+ "</body></html>");
		}else{
			if (numberError){
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">"
						+ "Error adding the task</h1><br/><h2 align=\"center\">"
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
				String path = "";
				Client client = ClientBuilder.newClient();
				Response resp2 = client.target("http://localhost:8081/tasks")
						.request(MediaType.APPLICATION_JSON).post(Entity.entity(
								todo, MediaType.APPLICATION_JSON));
				
				if (resp2.getStatus() == HttpServletResponse.SC_CREATED)
					path = resp2.getLocation().toString();
				resp.setStatus(resp2.getStatus());
				
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">" 
						+ resp2.getStatus() + ": "
						+ resp2.getStatusInfo() + "</h1><br/>"
						+ "<h2 align=\"center\">" +path + "</h2><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}
		}
	}
}