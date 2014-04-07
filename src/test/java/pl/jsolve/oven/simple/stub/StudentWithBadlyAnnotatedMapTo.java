package pl.jsolve.oven.simple.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.stub.person.StudentSnapshot;

@MappableTo(StudentSnapshot.class)
public class StudentWithBadlyAnnotatedMapTo {

	public static final String NOT_EXISTING_FIELD = "notExistingField";

	@Map(to = NOT_EXISTING_FIELD)
	private int semester;

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}
}