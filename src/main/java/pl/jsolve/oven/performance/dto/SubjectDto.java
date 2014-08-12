package pl.jsolve.oven.performance.dto;

import org.dozer.Mapping;
import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(SubjectDto.class)
public class SubjectDto {

	@Mapping("name")
	@Map(to = "name")
	private String subject;
	@Mapping("grades")
	private GradeDto[] grades;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public GradeDto[] getGrades() {
		return grades;
	}

	public void setGrades(GradeDto[] grades) {
		this.grades = grades;
	}

}
