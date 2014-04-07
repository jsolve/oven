package pl.jsolve.oven.collection.stub;

import java.util.List;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.annotation.Mappings;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;

@MappableTo({ StudentWithListOfGradeSnapshots.class, StudentWithArrayOfGradeSnapshots.class, StudentWithArrayOfGrades.class })
public class StudentWithListOfGrades {

	@Mappings({
			@Map(to = "gradeSnapshots", elementsAs = GradeSnapshot.class, of = { StudentWithListOfGradeSnapshots.class,
					StudentWithArrayOfGradeSnapshots.class }),
			@Map(to = "grades", elementsAs = Grade.class, of = StudentWithArrayOfGrades.class)
	})
	private List<Grade> grades;

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}
