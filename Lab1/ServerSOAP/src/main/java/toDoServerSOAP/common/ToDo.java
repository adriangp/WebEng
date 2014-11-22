package toDoServerSOAP.common;

public class ToDo {
	
	private String task;
	private String context;
	private String project;
	private int priority;
	
	public ToDo() {
	
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