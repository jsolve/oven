package pl.jsolve.oven.collection;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.jsolve.oven.builder.strategies.MapKeysAndValuesMapperStrategy;
import pl.jsolve.oven.simple.stub.Grade;
import pl.jsolve.oven.simple.stub.GradeSnapshot;
import pl.jsolve.sweetener.collection.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MapKeysAndValuesMapperStrategyTest {

	@Test
	@Parameters(method = "differentMapImplementations")
	public void shouldMapReturningHashMap(Map<Integer, Grade> mapOfGrades) {
		// given
		MapKeysAndValuesMapperStrategy mapKeysAndValuesMapperStrategy =
				new MapKeysAndValuesMapperStrategy(Integer.class, GradeSnapshot.class);
		mapOfGrades.put(3, Grade.valueOf(3));
		mapOfGrades.put(4, Grade.valueOf(4));

		// when
		Object result = mapKeysAndValuesMapperStrategy.apply(mapOfGrades, GradeSnapshot.class);

		// then
		assertThat(result).isInstanceOf(mapOfGrades.getClass());
		assertThat((Map) result).isNotEmpty();
	}

	private Object[] differentMapImplementations() {
		return $(Maps.newConcurrentMap(), Maps.newHashMap(), Maps.newLinkedHashMap(), Maps.newTreeMap());
	}
}
