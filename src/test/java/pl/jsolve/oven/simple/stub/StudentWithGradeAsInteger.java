package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(StudentWithGradeAsString.class)
public class StudentWithGradeAsInteger {

	@Map(to = "grade", of = StudentWithGradeAsString.class)
	private int grade;

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
