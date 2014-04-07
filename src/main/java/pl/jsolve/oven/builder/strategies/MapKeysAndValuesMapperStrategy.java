package pl.jsolve.oven.builder.strategies;

import java.util.Map;

import pl.jsolve.oven.builder.MapperBuilder;
import pl.jsolve.sweetener.collection.Maps;

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
		Map<Object, Object> targetMap = Maps.newConcurrentMap();
		for (Object key : sourceMap.keySet()) {
			Object value = sourceMap.get(key);
			targetMap.put(keysMapper.map(key), valuesMapper.map(value));
		}
		return targetMap;
	}
}