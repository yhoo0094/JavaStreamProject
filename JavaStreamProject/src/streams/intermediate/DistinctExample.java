package streams.intermediate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DistinctExample {
	public static void main(String[] args) {
		String[] strAry = {"Hong", "Hong", "Park", "Park", "Choi", "Choi"};
		
		Stream<String> sStream = Arrays.stream(strAry);
//		sStream.distinct().forEach(System.out::println);
		
		sStream = Arrays.stream(strAry);
//		sStream.sorted().forEach(System.out::println);
				
		Student[] students = {
							  new Student("이화진", 50),
							  new Student("이화진", 50),
							  new Student("이화진", 55),
							  new Student("동광희", 60),
							  new Student("동광희", 60),
							  new Student("우청일", 70),
							  new Student("우청일", 70),
							  new Student("김도은", 70),
							  new Student("김도은", 70)
		};
		Stream<Student> tStream = Arrays.stream(students);
//		tStream.distinct().forEach(System.out::println);
//		tStream.sorted().forEach(System.out::println);
//		tStream.sorted(new Comparator<Student>(){
//			@Override
//			public int compare(Student o1, Student o2) {
//				return o2.score - o1.score;
//			}
//		}).forEach(System.out::println);
		tStream.sorted((o1, o2) -> {
				return o2.score - o1.score;
		}).forEach(System.out::println);
		
	}
}
