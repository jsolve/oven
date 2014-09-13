package pl.jsolve.oven.builder.strategies;

import java.util.Map;

import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.oven.builder.MapperBuilder;
import pl.jsolve.sweetener.collection.Collections;
import pl.jsolve.sweetener.collection.Maps;
import pl.jsolve.sweetener.exception.InstanceCreationException;

public class MapKeysAndValuesMapperStrategy implements MapperBuilderStrategy {

	private final MapperBuilder<?> keysMapper;
	private final MapperBuilder<?> valuesMapper;

	public MapKeysAndValuesMapperStrategy(Class<?> keysType, Class<?> valuesType) {
		keysMapper = MapperBuilder.toType(keysType).usingAnnotations().usingTypeConvertion();
		valuesMapper = MapperBuilder.toType(valuesType).usingAnnotations().usingTypeConvertion();
	}

	@Override
	public Object apply(Object object, Class<?> targetType) {
		if (isMap(object.getClass())) {
			return mapKeysAndValues((Map<?, ?>) object);
		}
		return object;
	}

	private boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}

	private Object mapKeysAndValues(Map<?, ?> sourceMap) {
		Map<Object, Object> targetMap = getTargetMapInstance(sourceMap);
		for (Object key : sourceMap.keySet()) {
			Object value = sourceMap.get(key);
			targetMap.put(keysMapper.map(key), valuesMapper.map(value));
		}
		return targetMap;
	}

	private Map getTargetMapInstance(Map<?, ?> sourceMap)  {
		try {
			return sourceMap.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new InstanceCreationException("Cannot create an instance of java.util.Map", e);
		} catch (IllegalAccessException e) {
			throw new InstanceCreationException("Cannot create an instance of java.util.Map", e);
		}
	}
}