package todoWebApp.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import todoWebApp.common.ToDo;
import todoWebApp.common.ToDoList;


@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/todos" })
public class ToDosServlet extends HttpServlet {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		String filter = req.getParameter("filter");
		String text = req.getParameter("text");
		
		PrintWriter out = resp.getWriter();
 		Gson gson = new Gson();
		String table ="<table border=\"1\" align=\"center\" cellpadding=\"5\" "
				+ "id=\"table\">"
				+ "<tr><th><b> Task </b></th><th><b> Context </b></th>"
				+ "<th><b> Project </b></th><th><b> Priority </b></th></tr>";
		
		try{
			ToDoList todos = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), 
					ToDoList.class);
			
			resp.setStatus(HttpServletResponse.SC_OK);
			if (filter != null && text != null){
				for (ToDo t : todos.getList()){
					switch (filter){
					case "task":
						if (t.getTask().contains(text))
							table = appendToDo(table, t);
						break;
					case "context":
						if (t.getContext().contains(text))
							table = appendToDo(table, t);
						break;
					case "project":
						if (t.getProject().contains(text))
							table = appendToDo(table, t);
						break;
					case "priority":
						if (String.valueOf(t.getPriority()).equals(text))
							table = appendToDo(table, t);
					}	
				}
			}
			
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head>"
					+ "<body><h1>Tasks List</h1><br/>"
					+ table
					+ "</table></body></html>");
		} catch(FileNotFoundException e){
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head>"
					+ "<body><h1>Error: "
					+ "File " + DEFAULT_FILE_NAME + " does not exist."
					+ "</h1></body></html>");
		}
	}
	
	private String appendToDo(String table, ToDo todo){
		table += " <tr><td> " + todo.getTask() + " </td><td> " + todo.getContext() 
				+ " </td><td> " + todo.getProject() + " </td><td> " 
				+ todo.getPriority() + " </td></tr> ";
		return table;
	}
}