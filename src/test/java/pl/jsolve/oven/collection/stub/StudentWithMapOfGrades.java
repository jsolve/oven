package pl.jsolve.oven.collection.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;

@MappableTo(StudentWithMapOfGradeSnapshots.class)
public class StudentWithMapOfGrades {

	@Map(to = "gradeSnapshots", keysAs = ExamSnapshot.class, valuesAs = GradeSnapshot.class)
	private java.util.Map<Exam, Grade> grades;

	public java.util.Map<Exam, Grade> getGrades() {
		return grades;
	}

	public void setGrades(java.util.Map<Exam, Grade> grades) {
		this.grades = grades;
	}
}