package pl.jsolve.oven.builder.strategies;

import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.typeconverter.ConversionException;
import pl.jsolve.typeconverter.TypeConverter;

public class TypeConverterStrategy implements MapperBuilderStrategy {

	@Override
	public Object apply(Object object, Class<?> targetType) {
		if (!object.getClass().equals(targetType)) {
			return tryToConvertType(object, targetType);
		}
		return object;
	}

	private Object tryToConvertType(Object object, Class<?> targetType) {
		try {
			return TypeConverter.convert(object, targetType);
		} catch (ConversionException ce) {
			throw new MappingException(ce, "Type conversion between fields of type %s and %s failed.", object.getClass(), targetType);
		}
	}
}