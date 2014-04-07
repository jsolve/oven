package pl.jsolve.oven.annotationdriven;

public interface AnnotationMapping {

	<S, T> void apply(S sourceObject, T targetObject);
}