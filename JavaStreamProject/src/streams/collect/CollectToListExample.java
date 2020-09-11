package streams.collect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

enum Gender {
	MALE, 
	FEMAIL;
}
class Student {
	String name;
	int age;
	Gender gender;
	public Student(String name, int age, Gender gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
}

public class CollectToListExample {
	public static void main(String[] args) {
		Student s1 = new Student("허성준", 25, Gender.MALE);
		Student s2 = new Student("최형준", 27, Gender.MALE);
		Student s3 = new Student("문선애", 29, Gender.FEMAIL);
		List<Student> students = Arrays.asList(s1, s2, s3);
		
//		students.stream().filter(s -> s.gender == Gender.MALE)
//		.forEach(s -> System.out.println(s.name + ": " + s.gender));
		
		Collector<Student, ?, List<Student>> collector = Collectors.toList();
		List<Student> newList = students.stream()
				.filter(s -> s.gender == Gender.MALE)
				.collect(collector);
//		.collect(Collectors.toList()); => 바로 담아도 ㄱㅊ
		newList.stream().forEach(s->System.out.println(s.name + ", " + s.age));
		
		//나이가 25살 넘는 사람들을 Set에 담기.
		System.out.println("25살이 넘는 사람");
//		Set<Student> setStr = new HashSet<Student>();
//		setStr.add(s1);
//		setStr.add(s2);
//		setStr.add(s3);
//		Set<Student> newSet = setStr.stream()
		Set<Student> newSet = students.stream()
				.filter(s -> s.age > 25)
				.collect(Collectors.toSet());
		Iterator<Student> iter = newSet.iterator();
		while (iter.hasNext()) {
			Student s = iter.next();
			System.out.println(s.name + ": " + s.age);
		}
	}
}
