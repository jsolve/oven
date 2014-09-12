package pl.jsolve.oven.cache;

import org.junit.Test;
import pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;

import static org.fest.assertions.Assertions.assertThat;

public class AnnotationDrivenMapperAnnotationCacheTest {

	@Test
	public void shouldMapObjectTwice() {
		// given
		Grade grade = Grade.valueOf(3);
		GradeSnapshot firstResult = AnnotationDrivenMapper.map(grade, GradeSnapshot.class);

		// when
		GradeSnapshot secondResult = AnnotationDrivenMapper.map(grade, GradeSnapshot.class);

		// then
		assertThat(firstResult.getValue()).isEqualTo(grade.getValue());
		assertThat(secondResult.getValue()).isEqualTo(grade.getValue());
	}
}
