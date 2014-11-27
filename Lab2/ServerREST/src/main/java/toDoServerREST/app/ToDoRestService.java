package toDoServerREST.app;

import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import toDoServerREST.common.ToDo;
import toDoServerREST.common.ToDoList;

@Path("/tasks")
public class ToDoRestService {

	public final static String DEFAULT_FILE_NAME = "todos_list.json";
	
	@Inject
	ToDoList toDoList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ToDoList getToDoList() {
		return toDoList;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToDo(@Context UriInfo info, ToDo toDo) {
		toDo.setId(toDoList.nextId());
		toDo.setHref(info.getAbsolutePathBuilder().path("todo/{id}").build(toDo.getId()));
		toDoList.addToDo(toDo);
		FileWriter output;
		Gson gson = new Gson();
		try {
			output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			return Response.serverError().build();
		}
		return Response.created(toDo.getHref()).entity(toDo).build();
	}

	@GET
	@Path("/todo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToDo(@PathParam("id") int id) {
		for (ToDo t : toDoList.getList()) {
			if (t.getId() == id) {
				return Response.ok(t).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	
	@PUT
	@Path("/todo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateToDo(@Context UriInfo info,
			@PathParam("id") int id, ToDo toDo) {
		for (int i = 0; i < toDoList.getList().size(); i++) {
			if (toDoList.getList().get(i).getId() == id) {
				toDo.setId(id);
				toDo.setHref(info.getAbsolutePath());
				toDoList.getList().set(i, toDo);
				return Response.ok(toDo).build();
			}
		}
		FileWriter output;
		Gson gson = new Gson();
		try {
			output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			return Response.serverError().build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Path("/todo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateToDo(@PathParam("id") int id) {
		for (int i = 0; i < toDoList.getList().size(); i++) {
			if (toDoList.getList().get(i).getId() == id) {
				toDoList.getList().remove(i);
				return Response.noContent().build();
			}
		}
		FileWriter output;
		Gson gson = new Gson();
		try {
			output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			return Response.serverError().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

}
