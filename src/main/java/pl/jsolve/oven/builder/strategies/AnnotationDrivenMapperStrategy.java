package pl.jsolve.oven.builder.strategies;

import pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper;

public class AnnotationDrivenMapperStrategy implements MapperBuilderStrategy {

	@Override
	public Object apply(Object object, Class<?> targetType) {
		if (AnnotationDrivenMapper.isMappableToTargetClass(object, targetType)) {
			return AnnotationDrivenMapper.map(object, targetType);
		}
		return object;
	}
}