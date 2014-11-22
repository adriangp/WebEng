package webApp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todoserversoap.app.ToDo;
import todoserversoap.app.ToDoServer;
import todoserversoap.app.ToDoServerService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/list" })
public class ListServlet extends HttpServlet {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		ToDoServerService twss = new ToDoServerService();
		ToDoServer tws = twss.getToDoServerPort();
		
		List<ToDo> list = tws.listToDos();
		
		String table ="<table border=\"1\" align=\"center\" cellpadding=\"5\" "
				+ "id=\"table\">"
				+ "<tr><th><b> Task </b></th><th><b> Context </b></th>"
				+ "<th><b> Project </b></th><th><b> Priority </b></th></tr>";
		
		for (ToDo t : list){
			table = appendToDo(table, t);
		}
		
		out.println("<html><head><title>The ultimate WebApp to manage your "
				+ "tasks</title></head>"
				+ "<body><h1 align=\"center\">Tasks List</h1><br/>"
				+ table	+ "</table>"
				+ "<input type=\"button\" value=\"Back\" "
				+ "onClick=\"location.href='./index.html'\"/>"
				+ "</body></html>");
	}
	
	private String appendToDo(String table, ToDo todo){
		table += " <tr><td> " + todo.getTask() + " </td><td> " + todo.getContext() 
				+ " </td><td> " + todo.getProject() + " </td><td> " 
				+ todo.getPriority() + " </td></tr> ";
		return table;
	}
}