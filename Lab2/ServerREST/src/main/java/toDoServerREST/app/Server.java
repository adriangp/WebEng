package toDoServerREST.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import toDoServerREST.common.ToDo;
import toDoServerREST.common.ToDoList;

import com.google.gson.Gson;

public class Server {
	
	private static final Logger LOGGER = Grizzly.logger(Server.class);
	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		LOGGER.setLevel(Level.FINER);
		
		String filename = DEFAULT_FILE_NAME;
		Gson gson = new Gson();
 		
 		ToDoList todos = null;
		try {
			todos = gson.fromJson(new FileReader(filename), 
					ToDoList.class);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		
		// If the tasks haven't ID, now get de next ID
		for (ToDo t : todos.getList()){
			if (t.getId() == 0){
				t.setId(todos.nextId());
				t.setHref(URI.create("http://localhost:8081/tasks/todo/"+t.getId()));
			}
		}
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
				URI.create("http://localhost:8081/") , 
				new ApplicationConfig(todos));
		try {
			server.start();
			LOGGER.info("Press 's' to shutdown now the server...");
			while(true){
				int c = System.in.read();
				if (c == 's'){
					FileWriter output;
					gson = new Gson();
					try {
						output = new FileWriter(DEFAULT_FILE_NAME);
						output.write(gson.toJson(todos));
						output.close();
					} catch (IOException e) {
						LOGGER.log(Level.SEVERE, e.toString(), e);
					}
					break;
				}
			}
		} catch (IOException ioe) {
			LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
		} finally {
			server.stop();
			LOGGER.info("Server stopped");
		}
	}
}
