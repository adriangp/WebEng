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
@WebServlet(urlPatterns = { "/list" })
public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String id = req.getParameter("id");
		int idInt = 0;
		boolean numberError = false;
		try{
			idInt = Integer.parseInt(id);
		}catch (NumberFormatException e){
			numberError = true;
		}
		
		ToDoList list = null;
		ToDo todo = null;
		
		String table ="<table border=\"1\" align=\"center\" cellpadding=\"5\" "
				+ "id=\"table\">"
				+ "<tr><th><b> Task </b></th><th><b> Context </b></th>"
				+ "<th><b> Project </b></th><th><b> Priority </b></th></tr>";
		
		if (id == null || id.equals("")){
			// List all the tasks
			Client client = ClientBuilder.newClient();
			Response resp2 = client.target("http://localhost:8081/tasks")
					.request(MediaType.APPLICATION_JSON).get();
			resp.setStatus(resp2.getStatus());
			if (resp2.getStatus() == HttpServletResponse.SC_OK){
				list = resp2.readEntity(ToDoList.class);
				for (ToDo t : list.getList()){
					table = appendToDo(table, t);
				}
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head>"
						+ "<body><h1 align=\"center\">Tasks List</h1><br/>"
						+ table	+ "</table>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}else{
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">" 
						+ resp2.getStatus() + ": "
						+ resp2.getStatusInfo() + "</h1><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}
		}else{
			// List a single task
			if (numberError){
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("<html><head><title>The ultimate WebApp to manage your "
						+ "tasks</title></head><body><h1 align=\"center\">"
						+ "Error listing the task</h1><br/><h2 align=\"center\">"
						+ "Please, insert a numeric identifier</h2><br/>"
						+ "<input type=\"button\" value=\"Back\" "
						+ "onClick=\"location.href='./index.html'\"/>"
						+ "</body></html>");
			}else{
				Client client = ClientBuilder.newClient();
				Response resp2 = client.target("http://localhost:8081/tasks/todo/"+idInt)
						.request(MediaType.APPLICATION_JSON).get();
				resp.setStatus(resp2.getStatus());
				if (resp2.getStatus() == HttpServletResponse.SC_OK){
					todo = resp2.readEntity(ToDo.class);
					table = appendToDo(table, todo);
					out.println("<html><head><title>The ultimate WebApp to manage your "
							+ "tasks</title></head>"
							+ "<body><h1 align=\"center\">Tasks List</h1><br/>"
							+ table	+ "</table>"
							+ "<input type=\"button\" value=\"Back\" "
							+ "onClick=\"location.href='./index.html'\"/>"
							+ "</body></html>");
				}else{
					out.println("<html><head><title>The ultimate WebApp to manage your "
							+ "tasks</title></head><body><h1 align=\"center\">" 
							+ resp2.getStatus() + ": "
							+ resp2.getStatusInfo() + "</h1><br/>"
							+ "<input type=\"button\" value=\"Back\" "
							+ "onClick=\"location.href='./index.html'\"/>"
							+ "</body></html>");
				}
			}
		}
	}
	
	private String appendToDo(String table, ToDo todo){
		table += " <tr><td> " + todo.getTask() + " </td><td> " + todo.getContext() 
				+ " </td><td> " + todo.getProject() + " </td><td> " 
				+ todo.getPriority() + " </td></tr> ";
		return table;
	}
}