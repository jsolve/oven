package pl.jsolve.oven.performance.dto;

import org.dozer.Mapping;
import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.performance.mapper.MapperClass;
import pl.jsolve.oven.performance.mapper.Student;

import java.util.List;

@MappableTo(MapperClass.class)
public class ClassDto {

	@Mapping("name")
	@Map(to = "name")
	private String name;
	@Mapping("students")
	@Map(to = "students", elementsAs = Student.class)
	private List<StudentDto> students;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StudentDto> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDto> students) {
		this.students = students;
	}

}
