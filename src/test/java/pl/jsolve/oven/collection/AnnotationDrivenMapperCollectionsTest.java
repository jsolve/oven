package pl.jsolve.oven.collection;

import static org.fest.assertions.Assertions.assertThat;
import static pl.jsolve.sweetener.collection.Collections.newArrayList;
import static pl.jsolve.sweetener.collection.Collections.newHashSet;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper;
import pl.jsolve.oven.collection.stub.Exam;
import pl.jsolve.oven.collection.stub.ExamSnapshot;
import pl.jsolve.oven.collection.stub.StudentWithArrayOfGradeSnapshots;
import pl.jsolve.oven.collection.stub.StudentWithArrayOfGrades;
import pl.jsolve.oven.collection.stub.StudentWithArrayOfGradesAsStrings;
import pl.jsolve.oven.collection.stub.StudentWithListOfGradeSnapshots;
import pl.jsolve.oven.collection.stub.StudentWithListOfGrades;
import pl.jsolve.oven.collection.stub.StudentWithListOfGradesAsIntegers;
import pl.jsolve.oven.collection.stub.StudentWithListOfIntegers;
import pl.jsolve.oven.collection.stub.StudentWithMapOfGradeSnapshots;
import pl.jsolve.oven.collection.stub.StudentWithMapOfGrades;
import pl.jsolve.oven.collection.stub.StudentWithSetOfGradesAsStrings;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;
import pl.jsolve.sweetener.collection.Maps;

public class AnnotationDrivenMapperCollectionsTest {

	private static final String THEORY_OF_COMPUTER_SCIENCE = "Theory of Computer Science";
	private static final String NUMERICAL_METHODS = "Numerical Methods";
	private static final String EXAM_NAME_PROPERTY = "name";
	private static final String CLASS = "class";
	private static final String GRADE_VALUE_PROPERTY = "value";

	@Test
	public void shouldMapStudentWithListOfGradesToStudentWithListOfGradeSnapshots() {
		// given
		StudentWithListOfGrades studentWithGrades = new StudentWithListOfGrades();
		studentWithGrades.setGrades(newArrayList(Grade.valueOf(3), Grade.valueOf(2), Grade.valueOf(4), Grade.valueOf(5)));

		// when
		StudentWithListOfGradeSnapshots studentWithGradeSnapshots = AnnotationDrivenMapper
				.map(studentWithGrades, StudentWithListOfGradeSnapshots.class);

		// then
		assertCollectionHasElementsWithType(studentWithGradeSnapshots.getGradeSnapshots(), GradeSnapshot.class);
		assertThat(studentWithGradeSnapshots.getGradeSnapshots()).onProperty(GRADE_VALUE_PROPERTY).containsExactly(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithListOfGradeSnapshotsToStudentWithListOfGrades() {
		// given
		StudentWithListOfGradeSnapshots studentWithGradeSnapshots = new StudentWithListOfGradeSnapshots();
		studentWithGradeSnapshots.setGradeSnapshots(newArrayList(GradeSnapshot.valueOf(3), GradeSnapshot.valueOf(2),
				GradeSnapshot.valueOf(4), GradeSnapshot.valueOf(5)));

		// when
		StudentWithListOfGrades studentWithGrades = AnnotationDrivenMapper.map(studentWithGradeSnapshots, StudentWithListOfGrades.class);

		// then
		assertCollectionHasElementsWithType(studentWithGrades.getGrades(), Grade.class);
		assertThat(studentWithGrades.getGrades()).onProperty(GRADE_VALUE_PROPERTY).containsExactly(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithListOfGradesAsIntegersToStudentWithSetOfGradesAsStrings() {
		// given
		StudentWithListOfGradesAsIntegers studentWithGradesAsIntegers = new StudentWithListOfGradesAsIntegers();
		studentWithGradesAsIntegers.setGrades(newArrayList(3, 2, 4, 5));

		// when
		StudentWithSetOfGradesAsStrings studentWithGradesAsStrings = AnnotationDrivenMapper.map(studentWithGradesAsIntegers,
				StudentWithSetOfGradesAsStrings.class);

		// then
		assertThat(studentWithGradesAsStrings.getGrades()).containsOnly("3", "2", "4", "5");
	}

	@Test
	public void shouldMapStudentWithSetOfGradesAsStringsToStudentWithListOfGradesAsIntegers() {
		// given
		StudentWithSetOfGradesAsStrings studentWithGradesAsStrings = new StudentWithSetOfGradesAsStrings();
		studentWithGradesAsStrings.setGrades(newHashSet("3", "2", "4", "5"));

		// when
		StudentWithListOfGradesAsIntegers studentWithGradesAsIntegers = AnnotationDrivenMapper.map(studentWithGradesAsStrings,
				StudentWithListOfGradesAsIntegers.class);

		// then
		assertThat(studentWithGradesAsIntegers.getGrades()).containsOnly(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithListOfGradesToStudentWithArrayOfGrades() {
		// given
		StudentWithListOfGrades studentWithListOfGrades = new StudentWithListOfGrades();
		studentWithListOfGrades.setGrades(newArrayList(Grade.valueOf(3), Grade.valueOf(2), Grade.valueOf(4), Grade.valueOf(5)));

		// when
		StudentWithArrayOfGrades studentWithArrayOfGrades = AnnotationDrivenMapper.map(studentWithListOfGrades,
				StudentWithArrayOfGrades.class);

		// then
		assertArrayHasElementsWithType(studentWithArrayOfGrades.getGrades(), Grade.class);
		assertThat(studentWithArrayOfGrades.getGrades()).onProperty(GRADE_VALUE_PROPERTY).contains(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithListOfGradesToStudentWithArrayOfGradeSnapshots() {
		// given
		StudentWithListOfGrades studentWithListOfGrades = new StudentWithListOfGrades();
		studentWithListOfGrades.setGrades(newArrayList(Grade.valueOf(3), Grade.valueOf(2), Grade.valueOf(4), Grade.valueOf(5)));

		// when
		StudentWithArrayOfGradeSnapshots studentWithArrayOfGradeSnapshots = AnnotationDrivenMapper.map(studentWithListOfGrades,
				StudentWithArrayOfGradeSnapshots.class);

		// then
		assertArrayHasElementsWithType(studentWithArrayOfGradeSnapshots.getGradeSnapshots(), GradeSnapshot.class);
		assertThat(studentWithArrayOfGradeSnapshots.getGradeSnapshots()).onProperty(GRADE_VALUE_PROPERTY).contains(3, 2, 4, 5);
	}

	private <T> void assertArrayHasElementsWithType(T[] array, Class<?> clazz) {
		assertThat(array).onProperty(CLASS).containsOnly(clazz);
	}

	@Test
	public void shouldMapStudentWithListOfGradesAsIntegersToStudentWithArrayOfGradesAsStrings() {
		// given
		StudentWithListOfGradesAsIntegers studentWithIntegers = new StudentWithListOfGradesAsIntegers();
		studentWithIntegers.setGrades(newArrayList(3, 2, 4, 5));

		// when
		StudentWithArrayOfGradesAsStrings studentWithStrings = AnnotationDrivenMapper.map(studentWithIntegers,
				StudentWithArrayOfGradesAsStrings.class);

		// then
		assertThat(studentWithStrings.getGrades()).contains("3", "2", "4", "5");
	}

	@Test
	public void shouldStudentWithArrayOfGradesAsStringsToMapStudentWithListOfGradesAsIntegers() {
		// given
		StudentWithArrayOfGradesAsStrings studentWithStrings = new StudentWithArrayOfGradesAsStrings();
		studentWithStrings.setGrades(new String[] { "3", "2", "4", "5" });

		// when
		StudentWithListOfGradesAsIntegers studentWithIntegers = AnnotationDrivenMapper.map(studentWithStrings,
				StudentWithListOfGradesAsIntegers.class);

		// then
		assertThat(studentWithIntegers.getGrades()).contains(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithListOfGradesAsIntegersToStudentWithListOfIntegers() {
		// given
		StudentWithListOfGradesAsIntegers studentWithGradesAsIntegers = new StudentWithListOfGradesAsIntegers();
		studentWithGradesAsIntegers.setGrades(newArrayList(3, 2, 4, 5));

		// when
		StudentWithListOfIntegers studentWithIntegers = AnnotationDrivenMapper.map(studentWithGradesAsIntegers,
				StudentWithListOfIntegers.class);

		// then
		assertThat(studentWithIntegers.getIntegers()).contains(3, 2, 4, 5);
	}

	@Test
	public void shouldMapStudentWithMapOfGradesToStudentWithMapOfGradeSnapshots() {
		// given
		StudentWithMapOfGrades studentWithGrades = new StudentWithMapOfGrades();
		HashMap<Exam, Grade> grades = Maps.newHashMap();
		grades.put(new Exam(NUMERICAL_METHODS), Grade.valueOf(4));
		grades.put(new Exam(THEORY_OF_COMPUTER_SCIENCE), Grade.valueOf(5));
		studentWithGrades.setGrades(grades);

		// when
		StudentWithMapOfGradeSnapshots studentWithGradeSnapshots = AnnotationDrivenMapper.map(studentWithGrades,
				StudentWithMapOfGradeSnapshots.class);

		// then
		assertCollectionHasElementsWithType(studentWithGradeSnapshots.getGradeSnapshots().keySet(), ExamSnapshot.class);
		assertThat(studentWithGradeSnapshots.getGradeSnapshots().keySet()).onProperty(EXAM_NAME_PROPERTY)
				.containsOnly(NUMERICAL_METHODS, THEORY_OF_COMPUTER_SCIENCE);
		assertCollectionHasElementsWithType(studentWithGradeSnapshots.getGradeSnapshots().values(), GradeSnapshot.class);
		assertThat(studentWithGradeSnapshots.getGradeSnapshots().values()).onProperty(GRADE_VALUE_PROPERTY).containsOnly(4, 5);
	}

	@Test
	public void shouldMapStudentWithMapOfGradeSnapshotsToStudentWithMapOfGrades() {
		// given
		StudentWithMapOfGradeSnapshots studentWithGradeSnapshots = new StudentWithMapOfGradeSnapshots();
		HashMap<ExamSnapshot, GradeSnapshot> gradeSnapshots = Maps.newHashMap();
		gradeSnapshots.put(new ExamSnapshot(NUMERICAL_METHODS), GradeSnapshot.valueOf(4));
		gradeSnapshots.put(new ExamSnapshot(THEORY_OF_COMPUTER_SCIENCE), GradeSnapshot.valueOf(5));
		studentWithGradeSnapshots.setGradeSnapshots(gradeSnapshots);

		// when
		StudentWithMapOfGrades studentWithGrades = AnnotationDrivenMapper.map(studentWithGradeSnapshots, StudentWithMapOfGrades.class);

		// then
		assertCollectionHasElementsWithType(studentWithGrades.getGrades().keySet(), Exam.class);
		assertThat(studentWithGrades.getGrades().keySet()).onProperty(EXAM_NAME_PROPERTY).containsOnly(NUMERICAL_METHODS,
				THEORY_OF_COMPUTER_SCIENCE);
		assertCollectionHasElementsWithType(studentWithGrades.getGrades().values(), Grade.class);
		assertThat(studentWithGrades.getGrades().values()).onProperty(GRADE_VALUE_PROPERTY).containsOnly(4, 5);
	}

	private void assertCollectionHasElementsWithType(Collection<?> collection, Class<?> clazz) {
		assertThat(collection).onProperty(CLASS).containsOnly(clazz);
	}
}