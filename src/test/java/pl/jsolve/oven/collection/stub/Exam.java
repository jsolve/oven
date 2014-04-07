package pl.jsolve.oven.collection.stub;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;

@MappableTo(ExamSnapshot.class)
public class Exam {

	@Map
	private String name;

	public Exam() {
	}

	public Exam(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}