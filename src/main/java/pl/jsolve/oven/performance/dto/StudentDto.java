package pl.jsolve.oven.performance.dto;

import org.dozer.Mapping;
import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.performance.mapper.Student;

import java.util.List;

@MappableTo(Student.class)
public class StudentDto {

	@Mapping("firstName")
	@Map(to = "firstName")
	private String name;
	@Mapping("lastName")
	@Map(to = "lastName")
	private String lastName;
	@Mapping("address")
	@Map(to = "address")
	private String address;
	@Mapping("semester")
	@Map(to = "semester")
	private int semester;

//	@Mapping("subjects")
//	@Map(to = "subjects", elementsAs = Subject.class)
	private List<SubjectDto> subjects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public List<SubjectDto> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDto> subjects) {
		this.subjects = subjects;
	}

}
