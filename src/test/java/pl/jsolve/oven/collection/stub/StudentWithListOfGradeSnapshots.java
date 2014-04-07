package pl.jsolve.oven.collection.stub;

import java.util.List;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;

@MappableTo(StudentWithListOfGrades.class)
public class StudentWithListOfGradeSnapshots {

	@Map(to = "grades", elementsAs = Grade.class)
	private List<GradeSnapshot> gradeSnapshots;

	public List<GradeSnapshot> getGradeSnapshots() {
		return gradeSnapshots;
	}

	public void setGradeSnapshots(List<GradeSnapshot> gradeSnapshots) {
		this.gradeSnapshots = gradeSnapshots;
	}
}