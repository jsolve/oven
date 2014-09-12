package pl.jsolve.oven.annotationdriven;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AnnotationCache {

	public static final int INITIAL_CAPACITY = 10000;
	private Map<String, List<AnnotatedField>> storage = new HashMap(INITIAL_CAPACITY);

	public boolean isCached(Object object, Class<? extends Annotation> annotation) {
		return storage.containsKey(createKey(object, annotation));
	}

	public List<AnnotatedField> retrieve(Object object, Class<? extends Annotation> annotation) {
		return storage.get(createKey(object, annotation));
	}

	public void store(Object object, Class<? extends Annotation> annotation, List<AnnotatedField> fields) {
		storage.put(createKey(object, annotation), fields);
	}

	private String createKey(Object object, Class<? extends Annotation> annotation) {
		return object.getClass() + ":" + annotation;
	}
}
