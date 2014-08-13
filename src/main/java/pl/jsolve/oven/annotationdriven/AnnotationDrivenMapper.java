package pl.jsolve.oven.annotationdriven;

import static java.util.Collections.synchronizedList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.sweetener.core.Reflections;

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
		//TODO: process case when targetClass has no default constructoir
		//V targetObject = Reflections.tryToCreateNewInstance(targetClass);
		V targetObject = createAnInstance(targetClass);
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

	private static <T> T createAnInstance(java.lang.Class<T> clazz){
		Constructor[] allConstructors = clazz.getDeclaredConstructors();
		for (Constructor constructor : allConstructors) {
			Class<?>[] types  = constructor.getParameterTypes();

			if(types.length == 0){
				return Reflections.tryToCreateNewInstance(clazz);
			}
			else{
				Object[] params = new Object[types.length];

				for (int i = 0; i < types.length; i++) {

					String typeName = types[i].getName();

					if (typeName.equals("byte")||
							typeName.equals("short")||
							typeName.equals("int")) {
						params[i] = 0;
					}
					else if (typeName.equals("long")) {
						params[i] = 0L;
					}
					else if (typeName.equals("float")) {
						params[i] = 0.0;
					}
					else if (typeName.equals("double")) {
						params[i] = 0.0;
					}
					else if (typeName.equals("boolean")) {
						params[i] = true;
					}
					else if (typeName.equals("char")) {
						params[i] = ' ';
					}
					else{
							params[i] = null;
					}
				}
				try {
					return (T)constructor.newInstance(params);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	return null;
	}
}