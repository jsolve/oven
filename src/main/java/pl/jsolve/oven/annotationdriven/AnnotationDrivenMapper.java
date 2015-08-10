package pl.jsolve.oven.annotationdriven;

import pl.jsolve.oven.annotationdriven.annotation.Alias;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.annotation.MappableToAlias;
import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.sweetener.core.Reflections;
import pl.jsolve.typeconverter.TypeConverter;

import java.lang.reflect.Constructor;
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
		V targetObject = tryToCreateNewInstance(targetClass);
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
		MappableToAlias mappableToAlias = object.getClass().getAnnotation(MappableToAlias.class);

		boolean mappable =	mappableToAlias != null &&
				targetClass.getAnnotation(Alias.class) != null &&
				Arrays.asList(mappableToAlias.value()).contains(targetClass.getAnnotation(Alias.class).value());

		mappable = mappable || (mappableTo != null && Arrays.asList(mappableTo.value()).contains(targetClass)) ;

		return mappable;
	}

	private static <V, T> void applyAllMappings(T sourceObject, V targetObject) {
		for (AnnotationMapping mapping : mappings) {
			mapping.apply(sourceObject, targetObject);
		}
	}

	private static <T> T tryToCreateNewInstance(Class<T> clazz) {
		for (Constructor constructor : clazz.getDeclaredConstructors()) {
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length == 0) {
				return Reflections.tryToCreateNewInstance(clazz);
			}
			Object[] params = new Object[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				if (parameterTypes[i].isPrimitive()) {
					params[i] = TypeConverter.convert(0, parameterTypes[i]);
				} else {
					params[i] = null;
				}
			}
			return tryToCreateNewInstance(constructor, params);
		}
		throw new IllegalStateException(String.format("Given class %s has no constructors", clazz));
	}

	private static <T> T tryToCreateNewInstance(Constructor constructor, Object... params) {
		try {
			constructor.setAccessible(true);
			return (T) constructor.newInstance(params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			constructor.setAccessible(false);
		}
	}
}