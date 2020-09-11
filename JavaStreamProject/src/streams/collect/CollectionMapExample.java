package streams.collect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionMapExample {
	public static void main(String[] args) {
		Student s1 = new Student("허성준", 25, Gender.MALE);
		Student s2 = new Student("최형준", 27, Gender.MALE);
		Student s3 = new Student("문선애", 29, Gender.FEMAIL);
		List<Student> students = Arrays.asList(s1, s2, s3);
		
		Map<String, Integer> map = students.stream().filter(s -> s.age > 20)
			.collect(Collectors.toMap(new Function<Student, String>() {
				@Override
				public String apply(Student t) {
					return t.name;
				}			
			}, new Function<Student, Integer>() {
				@Override
				public Integer apply(Student t) {
					return t.age;
				}
			}));
		Set<String> keys = map.keySet();
		for(String s : keys) {
			System.out.println("이름: " + s + ", 나이: " + map.get(s));
		}
	}
}
