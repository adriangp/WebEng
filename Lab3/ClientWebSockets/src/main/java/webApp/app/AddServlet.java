package webApp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

import com.google.gson.Gson;

import webApp.common.ToDo;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/add" })
public class AddServlet extends HttpServlet {
	
	static CountDownLatch latch;
	static String toDo = "";
	static String status = "";
	static String response = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		latch = new CountDownLatch(1);
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
				Gson gson = new Gson();
				toDo = gson.toJson(todo);
				
				ClientManager client = ClientManager.createClient();
				try {
					client.connectToServer(AddEndPoint.class, new URI(
							"ws://localhost:8025/tasks/todo"));
					latch.await();
					resp.setStatus(HttpServletResponse.SC_CREATED);
					
				} catch (DeploymentException | URISyntaxException
						| InterruptedException | IOException e) {
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
				
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">" 
						+ status + "</h1><br/><h2 align=\"center\">"
						+ response + "</h2><br/>" 
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}
		}
	}
}