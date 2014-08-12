package pl.jsolve.oven.performance.mapper;

import java.util.List;

public class Student extends Person {

	private StudyCourse studyCourse;
	private int semester;
	private List<Subject> subjects;

	public StudyCourse getStudyCourse() {
		return studyCourse;
	}

	public void setStudyCourse(StudyCourse studyCourse) {
		this.studyCourse = studyCourse;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "Student [studyCourse=" + studyCourse + ", semester=" + semester + ", subjects=" + subjects + "]";
	}
}
