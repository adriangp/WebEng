package toDoServerWebSockets.common;

import java.net.URI;

public class ToDo {
	
	private int id;
	private URI href;
	
	private String task;
	private String context;
	private String project;
	private int priority;
	
	public ToDo() {
		this.id = 0;
	}

	public String getTask() {
		return task;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public String getProject() {
		return project;
	}
	
	public void setProject(String project) {
		this.project = project;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	@Override
	public boolean equals (Object t){
		if (t instanceof ToDo)
			return  this.task.equals(((ToDo) t).getTask()) &&
					this.context.equals(((ToDo) t).getContext()) && 
					this.project.equals(((ToDo) t).getProject()) &&
					this.priority == ((ToDo) t).getPriority();
			
		else return false; 
	}
}