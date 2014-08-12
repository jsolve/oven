package pl.jsolve.oven.performance;

import com.google.common.base.Stopwatch;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import pl.jsolve.oven.performance.dozer.DozerClass;
import pl.jsolve.oven.performance.dto.ClassDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class TestMain {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Generating test data ...");
		Stopwatch stopwatch = Stopwatch.createStarted();
		ClassDto clazz = DtoDataPreparer.generateClass(10000);
		stopwatch.stop();
		System.out.println("TIME: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

		System.out.println("Start mapping ? ");
		br.readLine();

		System.out.println("DOZER ...");
		Stopwatch dozerStopwatch = Stopwatch.createStarted();
		Mapper mapper = new DozerBeanMapper();
		DozerClass dozerClass = mapper.map(clazz, DozerClass.class);
		// System.out.println(dozerClass);
		dozerStopwatch.stop();
		System.out.println("TIME: " + dozerStopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

//		System.out.println("MAPPER ...");
//		Stopwatch mapperStopwatch = Stopwatch.createStarted();
//		MapperClass map = AnnotationDrivenMapper.map(clazz, MapperClass.class);
//		// System.out.println(map);
//		mapperStopwatch.stop();
//		System.out.println("TIME: " + mapperStopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");


		System.out.println("Exit ? ");
		br.readLine();
	}

}
