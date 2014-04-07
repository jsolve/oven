package pl.jsolve.oven.collection.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(StudentWithListOfGradesAsIntegers.class)
public class StudentWithArrayOfGradesAsStrings {

	@Map(elementsAs = Integer.class)
	private String[] grades;

	public String[] getGrades() {
		return grades;
	}

	public void setGrades(String[] grades) {
		this.grades = grades;
	}
}