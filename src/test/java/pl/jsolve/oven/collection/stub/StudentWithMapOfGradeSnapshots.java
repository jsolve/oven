package pl.jsolve.oven.collection.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;

@MappableTo(StudentWithMapOfGrades.class)
public class StudentWithMapOfGradeSnapshots {

	@Map(to = "grades", keysAs = Exam.class, valuesAs = Grade.class)
	private java.util.Map<ExamSnapshot, GradeSnapshot> gradeSnapshots;

	public java.util.Map<ExamSnapshot, GradeSnapshot> getGradeSnapshots() {
		return gradeSnapshots;
	}

	public void setGradeSnapshots(java.util.Map<ExamSnapshot, GradeSnapshot> gradeSnapshots) {
		this.gradeSnapshots = gradeSnapshots;
	}
}