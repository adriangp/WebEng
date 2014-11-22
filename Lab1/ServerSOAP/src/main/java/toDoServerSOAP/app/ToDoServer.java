package toDoServerSOAP.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import toDoServerSOAP.common.ToDo;
import toDoServerSOAP.common.ToDoList;

import com.google.gson.Gson;

@WebService
public class ToDoServer {
	
	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	@WebMethod()
	public String addToDo(ToDo todo) {
		String filename = DEFAULT_FILE_NAME;
 		String response = "";
		Gson gson = new Gson();
 		
 		ToDoList todos = null;
		try {
			todos = gson.fromJson(new FileReader(filename), 
					ToDoList.class);
		} catch (FileNotFoundException e) {
			response = "Error: File not found.";
		}
		
		todos.addToDo(todo);
		
		FileWriter output;
		try {
			output = new FileWriter(filename);
			output.write(gson.toJson(todos));
			output.close();
			response = "Task save successfully!.";
		} catch (IOException e) {
			response = "Error: Can't save the task.";
		}
		
		return response;
	}
	
	@WebMethod()
	public String removeToDo(ToDo todo){
		String filename = DEFAULT_FILE_NAME;
 		String response = "";
 		boolean success = false;
		Gson gson = new Gson();
 		
 		ToDoList todos = null;
		try {
			todos = gson.fromJson(new FileReader(filename), 
					ToDoList.class);
		} catch (FileNotFoundException e) {
			response = "Error: File not found.";
		}
		
		if (todos.deleteToDo(todo)){
			success = true;
		}
		
		FileWriter output;
		try {
			output = new FileWriter(filename);
			output.write(gson.toJson(todos));
			output.close();
			
			// Response successfully if the changes are saved in the file
			if (success)
				response = "Task delete successfully!.";
			else 
				response = "Error: Task not found.";
		} catch (IOException e) {
			response = "Error: Can't delete the task.";
		}
		return response;
	}
	
	@WebMethod()
	public List<ToDo> listToDos(){
		String filename = DEFAULT_FILE_NAME;
		Gson gson = new Gson();
 		
 		ToDoList todos = null;
		try {
			todos = gson.fromJson(new FileReader(filename), 
					ToDoList.class);
		} catch (FileNotFoundException e) {
			return null;
		}
		return todos.getList();
	}
}