package webApp.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todoserversoap.app.ToDo;
import todoserversoap.app.ToDoServer;
import todoserversoap.app.ToDoServerService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/add" })
public class AddServlet extends HttpServlet {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		ToDoServerService twss = new ToDoServerService();
		ToDoServer tws = twss.getToDoServerPort();
		
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
				String res = tws.addToDo(todo);
				resp.setStatus(HttpServletResponse.SC_OK);
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">" + res
						+ "</h1><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}
		}
	}
}