package pl.jsolve.oven.performance.mapper;

import java.util.List;

public class Subject {

	private String name;
	private List<Integer> grades;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getGrades() {
		return grades;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + ", grades=" + grades + "]";
	}
}
