package todo.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import todo.common.ToDo;
import todo.common.ToDoList;

import com.google.gson.Gson;

public class AddToDo {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	static ToDo PromptForAddress(BufferedReader stdin, PrintStream stdout)
			throws IOException {

		ToDo todo = new ToDo();
		
		stdout.print("Enter task name: ");
		todo.setTask(stdin.readLine());

		stdout.print("Enter the task's context: ");
		todo.setContext(stdin.readLine());

		stdout.print("Enter the project associated for the task: ");
		todo.setProject(stdin.readLine());
		
		stdout.print("Enter the task's priority: ");
		todo.setPriority(Integer.parseInt(stdin.readLine()));
		
		return todo;
	}
	
	public static void main(String args[]) throws Exception {
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) filename = args[0];
		
		ToDoList todolist = new ToDoList();
		Gson gson = new Gson();
		
		try {
			todolist = gson.fromJson(new FileReader(filename),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(filename
					+ ": File not found.  Creating a new file.");
		}
		
		todolist.addToDo(PromptForAddress(new BufferedReader(
				new InputStreamReader(System.in)), System.out));
		
		FileWriter output = new FileWriter(filename);
		output.write(gson.toJson(todolist));
		output.close();
	}
}
