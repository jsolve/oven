package pl.jsolve.oven.performance;

import pl.jsolve.oven.performance.dto.ClassDto;
import pl.jsolve.oven.performance.dto.GradeDto;
import pl.jsolve.oven.performance.dto.StudentDto;
import pl.jsolve.sweetener.collection.Collections;
import pl.jsolve.sweetener.text.Strings;

import java.util.List;
import java.util.Random;

public class DtoDataPreparer {

	static Random random = new Random();

	public static ClassDto generateClass(int count) {
		List<StudentDto> students = Collections.newArrayList();

		for (int i = 0; i < count; i++) {
			StudentDto student = new StudentDto();
			student.setName(randomString());
			student.setLastName(randomString());
			student.setAddress(randomString());
			List<GradeDto> grades = Collections.newArrayList();
			for (int j = 0; j < random.nextInt(); j++) {
				GradeDto grade = new GradeDto();
				grade.setDescription(randomString());
				grade.setGrade(random.nextInt());
				grades.add(grade);
			}
			students.add(student);
		}

		ClassDto clazz = new ClassDto();
		clazz.setName(randomString());
		clazz.setStudents(students);
		return clazz;
	}

	private static String randomString() {
		return Strings.random((int) (random.nextFloat() * 10000));
	}
}
