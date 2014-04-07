package pl.jsolve.oven.builder;

import java.util.List;

import pl.jsolve.oven.builder.strategies.AnnotationDrivenMapperStrategy;
import pl.jsolve.oven.builder.strategies.ArrayElementsMapperStrategy;
import pl.jsolve.oven.builder.strategies.CollectionElementsMapperStrategy;
import pl.jsolve.oven.builder.strategies.MapKeysAndValuesMapperStrategy;
import pl.jsolve.oven.builder.strategies.MapperBuilderStrategy;
import pl.jsolve.oven.builder.strategies.TypeConverterStrategy;
import pl.jsolve.sweetener.collection.Collections;

public class MapperBuilder<T> {

	private final List<MapperBuilderStrategy> strategies = Collections.newLinkedList();
	private final Class<T> targetType;

	private MapperBuilder(Class<T> targetType) {
		this.targetType = targetType;
	}

	public static <T> MapperBuilder<T> toType(Class<T> targetType) {
		return new MapperBuilder<>(targetType);
	}

	public MapperBuilder<T> usingAnnotations() {
		strategies.add(new AnnotationDrivenMapperStrategy());
		return this;
	}

	public MapperBuilder<T> usingTypeConvertion() {
		strategies.add(new TypeConverterStrategy());
		return this;
	}

	public MapperBuilder<T> arrayElementsTo(Class<?> elementsType) {
		strategies.add(new ArrayElementsMapperStrategy(elementsType));
		return this;
	}

	public MapperBuilder<T> collectionElementsTo(Class<?> elementsType) {
		strategies.add(new CollectionElementsMapperStrategy(elementsType));
		return this;
	}

	public MapperBuilder<T> mapKeysAndValuesTo(Class<?> keysType, Class<?> valuesType) {
		strategies.add(new MapKeysAndValuesMapperStrategy(keysType, valuesType));
		return this;
	}

	@SuppressWarnings("unchecked")
	public T map(Object object) {
		if (object != null) {
			for (MapperBuilderStrategy strategy : strategies) {
				object = strategy.apply(object, targetType);
			}
		}
		return (T) object;
	}
}