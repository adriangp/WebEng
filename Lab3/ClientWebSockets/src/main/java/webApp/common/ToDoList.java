package webApp.common;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

	private List<ToDo> list = new ArrayList<ToDo>();
	private int nextId = 1;
	
	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public List<ToDo> getList() {
		return list;
	}

	public void setList(List<ToDo> list) {
		this.list = list;
	}
	
	public void addToDo (ToDo t){
		if (!list.contains(t))
			list.add(t);
	}
	
	public boolean deleteToDo (ToDo t){
		if (list.contains(t)){
			list.remove(t);
			return true;
		}
		return false;
	}
	
	public int nextId() {
		int oldValue = nextId;
		nextId++;
		return oldValue;
	}
}