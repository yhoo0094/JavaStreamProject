package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class Student {
	String name;
	int score;
	public Student(String name, int score) {this.name = name;this.score = score;}
	public String getName() {return name;}
	public int getScore() {return score;}
	
	
}
public class StreamExample2 {
	public static void main(String[] args) {
//		List<Student> list = Arrays.asList(new Student("최재영", 90), new Student("민해주", 88), new Student("김상민", 83), new Student("최형준", 86));
//		Stream<Student> students = list.stream();
//		long iCnt = students.filter(t -> t.score >=85)
//				.peek(t -> System.out.println(t.name + ", " + t.score))
//				.count();
//		System.out.println("총 인원: " + iCnt);
//		
////		//람다식 X
////		long iCnt = students.filter(new Predicate<Student>() {
////			@Override
////			public boolean test(Student t) {
////				return t.score >=85 ;
////			}
////		}).peek(new Consumer<Student>() {
////			@Override
////			public void accept(Student t) {
////				System.out.println(t.name + ", " + t.score);
////			}
////		}).count();
////		System.out.println("총 인원: " + iCnt);
		
		List<Student> list = Arrays.asList(new Student("최재영", 90), 
										   new Student("민해주", 88), 
										   new Student("김상민", 83), 
										   new Student("최형준", 86));
		Stream<Student> students = list.stream();
		long iCnt = 0; 
		students.filter(t -> t.score >=85)
				.peek(t -> System.out.println("peek => " + t.name + ", " + t.score))
				.filter(t -> t.name.startsWith("최"))
				.forEach(t -> System.out.println("forEach =>" + t.name + ", " + t.score));
		System.out.println("총 인원: " + iCnt);
		
	}
}
