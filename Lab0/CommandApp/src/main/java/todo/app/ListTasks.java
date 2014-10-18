package todo.app;

import java.io.FileReader;

import todo.common.ToDo;
import todo.common.ToDoList;

import com.google.gson.Gson;

public class ListTasks {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	static void Print(ToDoList todos){
		for (ToDo t : todos.getList()){
			System.out.println("\nTask name: " + t.getTask());
			System.out.println("Task's contenxt: " + t.getContext());
			System.out.println("Task is associated to the project: " + t.getProject());
			System.out.println("Task's priority: " + t.getPriority());
		}
	}
	
	public static void main(String[] args) throws Exception{
		Gson gson = new Gson();
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}
		ToDoList todos = gson.fromJson(new FileReader(filename),
				ToDoList.class);
		
		Print(todos);
	}

}
