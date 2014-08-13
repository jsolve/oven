package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(GradeSnapshot.class)
public class Grade {

	@Map
	private int grade;
/*
	public Grade() {
	}
*/
	public Grade(int grade) {
		this.grade = grade;
	}

	public static Grade valueOf(int grade) {
		return new Grade(grade);
	}

	public int getValue() {
		return grade;
	}
}