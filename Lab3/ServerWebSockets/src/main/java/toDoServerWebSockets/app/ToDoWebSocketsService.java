package toDoServerWebSockets.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import toDoServerWebSockets.common.ToDo;
import toDoServerWebSockets.common.ToDoList;


@ServerEndpoint(value = "/todo")
public class ToDoWebSocketsService {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		String op = message.split(": ")[0];
		Gson gson = new Gson();
		boolean status = false;
		String response = op + ": ";
		
		switch (op) {
		case "quit":
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
						"Service ended"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		case "add":
			try{
				ToDo todo = gson.fromJson(message.split(": ")[1], ToDo.class);
				status = addToDo(todo);
				if (status) response += "Task added succesfully";
				else response += "Error adding the task";
			} catch (Exception e){
				response += op + "Error during the operation";
			}
			break;
		case "remove":
			try{
				ToDo todo = gson.fromJson(message.split(": ")[1], ToDo.class);
			    status = deleteToDo(todo);
			    if (status) response += "Task deleted succesfully";
			    else response += "Error deleting the task";
			} catch (Exception e){
				response += "Error during the operation";
			}
			break;
		case "list":
			try{
				response += listToDos();
			} catch (Exception e){
				response += "Error during the operation";
			}
			break;
		}
		session.getAsyncRemote().sendText(response);
		return response;
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
	
	@OnError
	public void onError(Session session, Throwable t){
		logger.info(String.format("Session %s closed because an error",
				session.getId()));
	}
	
	private ToDoList getTasks(){
		ToDoList todos = null;
		Gson gson = new Gson();
		try {
			todos = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), 
					ToDoList.class);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return todos;
	}
	
	private boolean setTasks(ToDoList todos){
		FileWriter output;
		Gson gson = new Gson();
		try {
			output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(todos));
			output.close();
			return true;
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}
	
	private boolean addToDo(ToDo todo){
		try{
			ToDoList todos = getTasks();
			todos.addToDo(todo);
			setTasks(todos);
			return true;
		} catch (Exception e){
			logger.log(Level.SEVERE, e.toString(), e);
			return false;
		}
	}
	
	private boolean deleteToDo(ToDo t){
		boolean find = false;
		try{
			ToDoList todos = getTasks();
			find = todos.deleteToDo(t);
			setTasks(todos);
			return find;
		} catch (Exception e){
			logger.log(Level.SEVERE, e.toString(), e);
			return find;
		}
	}
	
	private String listToDos(){
		try{
			ToDoList todos = getTasks();
			Gson gson = new Gson();
			return gson.toJson(todos);
		}catch (Exception e){
			logger.log(Level.SEVERE, e.toString(), e);
			return "";
		}
	}
}