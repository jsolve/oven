package pl.jsolve.oven.builder.strategies;

public interface MapperBuilderStrategy {

	Object apply(Object object, Class<?> targetType);
}