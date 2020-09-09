package streams.intermediate;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;


public class FlatMapExample {
	public static void main(String[] args) {
		Stream<Integer> stream = Stream.of(1,2,3,4,5);
//		stream.flatMap(new Function<Integer, Stream<Integer>>() {
//			
//			@Override
//			public Stream<Integer> apply(Integer t) {
//				return Stream.of(t*2);
//			}
//		}).forEach(System.out::println);
		
		//2배로 출력하기
//		stream.flatMap(t -> Stream.of(t*2))
//			  .forEach(System.out::println);
		
//		Employee.employees().stream().flatMapToDouble(new Function<Employee, DoubleStream>() {
//
//			@Override
//			public DoubleStream apply(Employee t) {
//				return DoubleStream.of(t.getIncome());
//			}
//			
//		});
		
		//income 출력하기
//		OptionalDouble result = Employee.employees()
//				.stream()
//				.flatMapToDouble(t -> DoubleStream.of(t.getIncome()))
//				.filter(n -> n > 2000).reduce(new DoubleBinaryOperator() {
//					
//					@Override
//					public double applyAsDouble(double left, double right) {
//						return left > right ? left : right;
//					}
//				});
//		
//		if(result.isPresent())
//			System.out.println("result: " + result);
//		else
//			System.out.println("결과 없음.");
		
		//ruduce로 income출력하기
		OptionalDouble result = Employee.employees()
				.stream()
				.flatMapToDouble(t -> DoubleStream.of(t.getIncome()))
				.reduce(new DoubleBinaryOperator() {
					
					@Override
					public double applyAsDouble(double left, double right) {//return값을 left에 넣고 계속 계산
						System.out.println(left + ", " + right);
						return left + right;
					}
				});
		
		if(result.isPresent())
			System.out.println("result: " + result);
		else
			System.out.println("결과 없음.");
		
//		Employee.employees().stream().flatMap(new Function<Employee, Stream<String>>() {
//
//			@Override
//			public Stream<String> apply(Employee t) {
//				return Stream.of(t.getName());
//			}
//		}).findFirst().ifPresent(System.out::println);
		
		//name출력하기
//		Employee.employees().stream()
//				.flatMap(t -> Stream.of(t.getName()))
//				.findFirst().ifPresent(System.out::println);
		
		Optional<LocalDate> result2 = Employee.employees().stream().flatMap(new Function<Employee, Stream<LocalDate>>() {

			@Override
			public Stream<LocalDate> apply(Employee t) {
				return Stream.of(t.getDateOfBirth());
			}
		}).reduce(new BinaryOperator<LocalDate>() {
			
			@Override
			public LocalDate apply(LocalDate t, LocalDate u) { 
				return t.isBefore(u)? t : u; //비교해서 작은 값을 리턴한다!
			}
		});
		System.out.println("min값: " + result2);
		
	}
}
