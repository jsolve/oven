package pl.jsolve.oven.annotationdriven;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotatedField {

	private Field self;
	private Annotation annotation;

	public AnnotatedField(Field self, Annotation annotation) {
		this.self = self;
		this.annotation = annotation;
	}

	public Field get() {
		return self;
	}

	public Annotation getAnnotation() {
		return annotation;
	}
}
