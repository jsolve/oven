package pl.jsolve.oven.annotationdriven;

import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.sweetener.core.Reflections;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.synchronizedList;

public final class AnnotationDrivenMapper {

	private static final List<AnnotationMapping> mappings = synchronizedList(new LinkedList<AnnotationMapping>());

	private AnnotationDrivenMapper() {
		throw new AssertionError("Using constructor of this class is prohibited.");
	}

	static {
		registerAnnotationMapping(new MapAnnotationMapping());
	}

	public static void registerAnnotationMapping(AnnotationMapping annotationMapping) {
		if (annotationMapping != null) {
			mappings.add(annotationMapping);
		}
	}

	public static <T, V> V map(T sourceObject, Class<V> targetClass) {
		throwExceptionWhenIsNotMappableToTargetClass(sourceObject, targetClass);
		V targetObject = Reflections.tryToCreateNewInstance(targetClass);
		applyAllMappings(sourceObject, targetObject);
		return targetObject;
	}

	private static <T, V> void throwExceptionWhenIsNotMappableToTargetClass(T object, Class<V> targetClass) {
		if (!isMappableToTargetClass(object, targetClass)) {
			throw new MappingException(
					"%s is not mappable to %s. Perhaps you forgot to add @MappableTo(%s.class) annotation over %s class?",
					object.getClass(), targetClass, targetClass.getSimpleName(), object.getClass().getSimpleName());
		}
	}

	public static <T, V> boolean isMappableToTargetClass(T object, Class<V> targetClass) {
		if (object == null) {
			return false;
		}
		MappableTo mappableTo = object.getClass().getAnnotation(MappableTo.class);
		return mappableTo != null && Arrays.asList(mappableTo.value()).contains(targetClass);
	}

	private static <V, T> void applyAllMappings(T sourceObject, V targetObject) {
		for (AnnotationMapping mapping : mappings) {
			mapping.apply(sourceObject, targetObject);
		}
	}
}