package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(StudentWithCollections.class)
public class StudentWithArrays {

	@Map(elementsAs = Integer.class)
	private Integer[] grades;
	@Map(elementsAs = String.class)
	private String[] subjects;

	public StudentWithArrays(Integer[] grades, String[] subjects) {
		this.grades = grades;
		this.subjects = subjects;
	}

	public Integer[] getGrades() {
		return grades;
	}

	public void setGrades(Integer[] grades) {
		this.grades = grades;
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
}