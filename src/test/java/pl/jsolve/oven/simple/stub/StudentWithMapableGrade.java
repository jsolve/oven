package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.annotation.Mappings;

@MappableTo({ StudentWithMapableGradeSnapshot.class, StudentWithGradeAsInteger.class })
public class StudentWithMapableGrade {

	@Mappings({
		@Map(to = "gradeSnapshot", of = StudentWithMapableGradeSnapshot.class),
			@Map(of = StudentWithGradeAsInteger.class)
	})
	private Grade grade;

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
}