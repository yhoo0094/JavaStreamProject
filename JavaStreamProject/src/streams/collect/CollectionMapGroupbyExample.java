package streams.collect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionMapGroupbyExample {
	public static void main(String[] args) {
		//부서별 - 사원정보
		Student s1 = new Student("허성준", 25, Gender.MALE);
		Student s2 = new Student("최형준", 27, Gender.MALE);
		Student s3 = new Student("문선애", 29, Gender.FEMAIL);
		Student s4 = new Student("김다희", 31, Gender.FEMAIL);
		List<Student> students = Arrays.asList(s1, s2, s3, s4);
		
		Map<Gender, List<Student>> map;
		
//		List에 담기
		Map<Gender, List<Student>> gMap = students.stream()
				.collect(Collectors.groupingBy(new Function<Student, Gender>() {
					@Override
					public Gender apply(Student t) {
						return t.gender;
					}
		}));
		Set<Gender> set = gMap.keySet();
		for(Gender g : set) {
			System.out.println(g);
			List<Student> list = gMap.get(g);
			for(Student s : list) {
				System.out.println(s.name + ", " + s.age);
			}
		}
		
		//Set에 담기
//		Map<Gender, Set<Student>> gMap = students.stream()
//				.collect(Collectors.groupingBy(new Function<Student, Gender>() {
//					@Override
//					public Gender apply(Student t) {
//						return t.gender;
//					}
//		}, Collectors.toSet()));
		
		
		
		
	}
}
