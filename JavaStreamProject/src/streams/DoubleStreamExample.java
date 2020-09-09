package streams;

import java.util.Arrays;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class DoubleStreamExample {
	public static void main(String[] args) {
		double[] dAry = {2.3, 4.5, 8.3, 5.5, 4.6};
		DoubleStream stream = Arrays.stream(dAry);
		double sum = Arrays.stream(dAry).filter(new DoublePredicate() {
			@Override
			public boolean test(double value) {
				return value < 5;
			}
		}).sum();
		System.out.println("sum: " + sum);
//		double sum = stream.filter(t -> t < 5)
//		.sum();
//		System.out.println(sum);
		
		LongStream lStream = Arrays.stream(new long[] {10, 29, 38});
		IntStream iStream =Arrays.stream(new int[] {1, 2, 3});
		DoubleStream dStream = Arrays.stream(new double[] {1.1, 2.2});
		Stream<Integer> inttream;
		Stream<String> sStream = Arrays.stream(new String[] {"Hong", "Park"});
		Stream<Student> tStream = Arrays.stream(new Student[] {new Student("이름1", 100), new Student("이름2", 90)});
	}
}
