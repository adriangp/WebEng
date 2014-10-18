package todo.common;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

	private List<ToDo> list = new ArrayList<ToDo>();
	
	public List<ToDo> getList() {
		return list;
	}

	public void setList(List<ToDo> list) {
		this.list = list;
	}
	
	public void addToDo (ToDo t){
		list.add(t);
	}
}
