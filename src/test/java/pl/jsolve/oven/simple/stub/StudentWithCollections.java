package pl.jsolve.oven.simple.stub;

import java.util.List;
import java.util.Set;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(StudentWithArrays.class)
public class StudentWithCollections {

	@Map(elementsAs = Integer.class)
	private List<Integer> grades;
	@Map(elementsAs = String.class)
	private Set<String> subjects;

	public List<Integer> getGrades() {
		return grades;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}

	public Set<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<String> subjects) {
		this.subjects = subjects;
	}
}