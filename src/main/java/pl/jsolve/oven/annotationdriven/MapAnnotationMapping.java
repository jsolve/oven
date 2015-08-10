package pl.jsolve.oven.annotationdriven;

import pl.jsolve.oven.annotationdriven.annotation.*;
import pl.jsolve.oven.annotationdriven.exception.MappingException;
import pl.jsolve.oven.builder.MapperBuilder;
import pl.jsolve.sweetener.collection.Collections;
import pl.jsolve.sweetener.core.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

class MapAnnotationMapping implements AnnotationMapping {

	private static final String NESTING_CHARACTER = ".";
	private static final Class<Map> MAP_ANNOTATION_CLASS = Map.class;
	private static final Class<MapToAlias> MAP_TO_ALIAS_ANNOTATION_CLASS = MapToAlias.class;
	private static final Class<Mappings> MAPPINGS_ANNOTATION_CLASS = Mappings.class;
	private static final Class<MappingsForAliases> MAPPINGS_FOR_ALIASES_ANNOTATION_CLASS = MappingsForAliases.class;
	private final AnnotationProvider annotationProvider = new AnnotationProvider();

	@Override
	public <S, T> void apply(S sourceObject, T targetObject) {
		applyOnFieldsAnnotatedByMap(sourceObject, targetObject);
		applyOnFieldsAnnotatedByMappings(sourceObject, targetObject);
	}

	private <S, T> void applyOnFieldsAnnotatedByMap(S sourceObject, T targetObject) {
		List<AnnotatedField> annotatedFieldsForMapAlias = annotationProvider.getFieldsAnnotatedBy(sourceObject, MAP_TO_ALIAS_ANNOTATION_CLASS);
		List<AnnotatedField> annotatedFieldsForMap = annotationProvider.getFieldsAnnotatedBy(sourceObject, MAP_ANNOTATION_CLASS);
		for (AnnotatedField field : annotatedFieldsForMap) {
			Map mapAnnotation = (Map) field.getAnnotation();
			applyOnFieldWithAnnotation(sourceObject, targetObject, field.get(), mapAnnotation);
		}
		for (AnnotatedField field : annotatedFieldsForMapAlias) {
			MapToAlias mapAnnotation = (MapToAlias) field.getAnnotation();
			applyOnFieldWithAnnotation(sourceObject, targetObject, field.get(), mapAnnotation);
		}
	}

	private <S, T> void applyOnFieldsAnnotatedByMappings(S sourceObject, T targetObject) {
		List<AnnotatedField> annotatedFields = annotationProvider.getFieldsAnnotatedBy(sourceObject, MAPPINGS_ANNOTATION_CLASS);
		for (AnnotatedField field : annotatedFields) {
			Map[] mapAnnotations = ((Mappings) field.getAnnotation()).value();
			applyOnFieldWithAnnotations(sourceObject, targetObject, field.get(), mapAnnotations);
		}
		annotatedFields = annotationProvider.getFieldsAnnotatedBy(sourceObject, MAPPINGS_FOR_ALIASES_ANNOTATION_CLASS);
		for (AnnotatedField field : annotatedFields) {
			MapToAlias[] mapAnnotations = ((MappingsForAliases) field.getAnnotation()).value();
			applyOnFieldWithAnnotations(sourceObject, targetObject, field.get(), mapAnnotations);
		}
	}

	private <S, T> void applyOnFieldWithAnnotations(S sourceObject, T targetObject, Field field, Map... mapAnnotations) {
		for (Map mapAnnotation : mapAnnotations) {
			applyOnFieldWithAnnotation(sourceObject, targetObject, field, mapAnnotation);
		}
	}

	private <S, T> void applyOnFieldWithAnnotations(S sourceObject, T targetObject, Field field, MapToAlias... mapAnnotations) {
		for (MapToAlias mapAnnotation : mapAnnotations) {
			applyOnFieldWithAnnotation(sourceObject, targetObject, field, mapAnnotation);
		}
	}

	private <S, T> void applyOnFieldWithAnnotation(S sourceObject, T targetObject, Field field, Map mapAnnotation) {
		if (isMappingIntendedForTargetObject(targetObject, mapAnnotation)) {
			String targetFieldName = getTargetFieldName(field, mapAnnotation);
			throwExceptionWhenFieldIsNotPresent(targetObject, targetFieldName);

			String sourceFieldName = getSourceFieldName(field, mapAnnotation);
			throwExceptionWhenFieldIsNotPresent(sourceObject, sourceFieldName);

			Class<?> targetFieldType = Reflections.getFieldType(targetObject, targetFieldName);
			Object sourceFieldValue = Reflections.getFieldValue(sourceObject, sourceFieldName);

			sourceFieldValue = mapObjectToTargetType(sourceFieldValue, targetFieldType, mapAnnotation);
			Reflections.setFieldValue(targetObject, targetFieldName, sourceFieldValue);
		}
	}

	private <S, T> void applyOnFieldWithAnnotation(S sourceObject, T targetObject, Field field, MapToAlias mapAnnotation) {
		if (isMappingIntendedForTargetObject(targetObject, mapAnnotation)) {
			String targetFieldName = getTargetFieldName(field, mapAnnotation);
			throwExceptionWhenFieldIsNotPresent(targetObject, targetFieldName);

			String sourceFieldName = getSourceFieldName(field, mapAnnotation);
			throwExceptionWhenFieldIsNotPresent(sourceObject, sourceFieldName);

			Class<?> targetFieldType = Reflections.getFieldType(targetObject, targetFieldName);
			Object sourceFieldValue = Reflections.getFieldValue(sourceObject, sourceFieldName);

			sourceFieldValue = mapObjectToTargetType(sourceFieldValue, targetFieldType, mapAnnotation);
			Reflections.setFieldValue(targetObject, targetFieldName, sourceFieldValue);
		}
	}

	private <T> boolean isMappingIntendedForTargetObject(T targetObject, Map mapAnnotation) {
		return Collections.containsAny(Reflections.getClasses(targetObject), mapAnnotation.of());
	}

	private <T> boolean isMappingIntendedForTargetObject(T targetObject, MapToAlias mapAnnotation) {
		if (targetObject.getClass().getAnnotation(Alias.class) == null) {
			return false;
		}

		return Arrays.asList(mapAnnotation.of()).contains(targetObject.getClass().getAnnotation(Alias.class).value());
	}

	private String getTargetFieldName(Field field, Map mapAnnotation) {
		if (mapAnnotation.to().isEmpty()) {
			return field.getName();
		}
		return mapAnnotation.to();
	}

	private String getTargetFieldName(Field field, MapToAlias mapAnnotation) {
		if (mapAnnotation.to().isEmpty()) {
			return field.getName();
		}
		return mapAnnotation.to();
	}

	private String getSourceFieldName(Field field, Map mapAnnotation) {
		if (mapAnnotation.fromNested().isEmpty()) {
			return field.getName();
		}
		return field.getName() + NESTING_CHARACTER + mapAnnotation.fromNested();
	}

	private String getSourceFieldName(Field field, MapToAlias mapAnnotation) {
		if (mapAnnotation.fromNested().isEmpty()) {
			return field.getName();
		}
		return field.getName() + NESTING_CHARACTER + mapAnnotation.fromNested();
	}

	private void throwExceptionWhenFieldIsNotPresent(Object object, String fieldName) {
		if (!Reflections.isFieldPresent(object, fieldName)) {
			throw new MappingException("%s does not contain field '%s'. Perhaps you have misspelled field name in @Map annotation?",
					object.getClass(), fieldName);
		}
	}

	private Object mapObjectToTargetType(Object object, Class<?> targetType, Map mapAnnotation) {
		return MapperBuilder.toType(targetType)
				.arrayElementsTo(mapAnnotation.elementsAs())
				.collectionElementsTo(mapAnnotation.elementsAs())
				.mapKeysAndValuesTo(mapAnnotation.keysAs(), mapAnnotation.valuesAs())
				.usingAnnotations()
				.usingTypeConvertion()
				.map(object);
	}

	private Object mapObjectToTargetType(Object object, Class<?> targetType, MapToAlias mapAnnotation) {
		return MapperBuilder.toType(targetType)
				.arrayElementsTo(mapAnnotation.elementsAs())
				.collectionElementsTo(mapAnnotation.elementsAs())
				.mapKeysAndValuesTo(mapAnnotation.keysAs(), mapAnnotation.valuesAs())
				.usingAnnotations()
				.usingTypeConvertion()
				.map(object);
	}
}
