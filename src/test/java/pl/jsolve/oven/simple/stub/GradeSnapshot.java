package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(Grade.class)
public class GradeSnapshot {

	@Map
	private int grade;

	private GradeSnapshot(int grade) {
		this.grade = grade;
	}

	public static GradeSnapshot valueOf(int grade) {
		return new GradeSnapshot(grade);
	}

	public int getValue() {
		return grade;
	}
}