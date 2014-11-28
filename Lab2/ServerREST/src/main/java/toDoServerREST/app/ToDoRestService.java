package toDoServerREST.app;

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

import toDoServerREST.common.ToDo;
import toDoServerREST.common.ToDoList;

@Path("/tasks")
public class ToDoRestService {
	
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
		return Response.status(Status.NOT_FOUND).build();
	}

}
