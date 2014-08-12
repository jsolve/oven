package pl.jsolve.oven.performance.mapper;

import java.util.List;

public class MapperClass {

	private String name;
	private List<Student> students;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "MapperClass [name=" + name + ", students=" + students + "]";
	}

}
