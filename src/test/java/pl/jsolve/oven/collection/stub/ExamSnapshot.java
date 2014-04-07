package pl.jsolve.oven.collection.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(Exam.class)
public class ExamSnapshot {

	@Map
	private String name;

	public ExamSnapshot() {
	}

	public ExamSnapshot(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}