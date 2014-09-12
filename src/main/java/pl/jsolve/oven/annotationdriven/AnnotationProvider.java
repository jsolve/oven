package pl.jsolve.oven.annotationdriven;

import pl.jsolve.sweetener.core.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class AnnotationProvider {

	private AnnotationCache annotationCache = new AnnotationCache();

	public <T extends Annotation> List<AnnotatedField> getFieldsAnnotatedBy(Object sourceObject, Class<T> annotation) {
		if(annotationCache.isCached(sourceObject, annotation)) {
			return annotationCache.retrieve(sourceObject, annotation);
		}
		List<AnnotatedField> annotatedFields = new ArrayList<AnnotatedField>();
		for(Field field : Reflections.getFieldsAnnotatedBy(sourceObject, annotation)) {
			annotatedFields.add(new AnnotatedField(field, field.getAnnotation(annotation)));
		}
		annotationCache.store(sourceObject, annotation, annotatedFields);
		return annotatedFields;
	}
}
