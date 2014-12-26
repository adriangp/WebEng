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
import webApp.common.ToDoList;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/list" })
public class ListServlet extends HttpServlet {

	static CountDownLatch latch;
	static String list = "";
	static String status = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		latch = new CountDownLatch(1);
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String table ="<table border=\"1\" align=\"center\" cellpadding=\"5\" "
				+ "id=\"table\">"
				+ "<tr><th><b> Task </b></th><th><b> Context </b></th>"
				+ "<th><b> Project </b></th><th><b> Priority </b></th></tr>";
		
		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(ListEndPoint.class, new URI(
					"ws://localhost:8025/tasks/todo"));
			latch.await();
			resp.setStatus(HttpServletResponse.SC_OK);
			
			Gson gson = new Gson();
			ToDoList todos = gson.fromJson(list, ToDoList.class);
			for (ToDo t : todos.getList()){
				table = appendToDo(table, t);
			}
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head>"
					+ "<body><h1 align=\"center\">Tasks List</h1><br/>"
					+ table	+ "</table>"
					+ "<input type=\"button\" value=\"Back\" "
					+ "onClick=\"location.href='./index.html'\"/>"
					+ "</body></html>");
			
		} catch (DeploymentException | URISyntaxException
				| InterruptedException | IOException e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("<html><head><title>The ultimate WebApp to manage your "
					+ "tasks</title></head><body><h1 align=\"center\">" 
					+ status + "</h1><br/>"
					+ "<input type=\"button\" value=\"Back\" "
					+ "onClick=\"location.href='./index.html'\"/>"
					+ "</body></html>");
		}
	}
	
	private String appendToDo(String table, ToDo todo){
		table += " <tr><td> " + todo.getTask() + " </td><td> " + todo.getContext() 
				+ " </td><td> " + todo.getProject() + " </td><td> " 
				+ todo.getPriority() + " </td></tr> ";
		return table;
	}
}