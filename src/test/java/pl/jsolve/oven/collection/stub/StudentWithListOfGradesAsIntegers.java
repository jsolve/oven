package pl.jsolve.oven.collection.stub;

import java.util.List;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.annotation.Mappings;

@MappableTo({ StudentWithSetOfGradesAsStrings.class, StudentWithArrayOfGradesAsStrings.class, StudentWithListOfIntegers.class })
public class StudentWithListOfGradesAsIntegers {

	@Mappings({
			@Map(to = "grades", elementsAs = String.class, of = { StudentWithSetOfGradesAsStrings.class,
					StudentWithArrayOfGradesAsStrings.class }),
			@Map(to = "integers", of = StudentWithListOfIntegers.class)
	})
	private List<Integer> grades;

	public List<Integer> getGrades() {
		return grades;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}
}