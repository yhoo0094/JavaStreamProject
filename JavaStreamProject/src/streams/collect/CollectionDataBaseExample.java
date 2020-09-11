package streams.collect;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import common.EmpDAO;
import common.Employee;

public class CollectionDataBaseExample {
	public static void main(String[] args) {
		//employee테이블에서 1990년대에 입사한 사람들을 List컬렉션에 저장하기
		List<Employee> empList = EmpDAO.getEmpList();
		Stream<Employee> stream = empList.stream();
		LocalDate start = LocalDate.parse("1989-12-31",DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse("2000-01-01",DateTimeFormatter.ISO_DATE);
//		stream.filter(a -> a.getHireDate().isAfter(start)).filter(a->a.getHireDate().isBefore(end))
//		.forEach(a -> System.out.println("이름: " + a.getLastName()+ "\t" +"입사일: " + a.getHireDate()));

		//job -> ST_CLERK인 사람들의 이름과 급여를 컬렉션에 저장하기
//		System.out.println("-------------이름과 급여-------------");
//		stream = empList.stream();
//		Map<String, Integer> map = stream.filter(a -> a.getJobId().equals("ST_CLERK"))
//		.collect(Collectors.toMap(new Function<Employee, String>() {
//			@Override
//			public String apply(Employee t) {
//				return t.getLastName();
//			}			
//		}, new Function<Employee, Integer>() {
//			@Override
//			public Integer apply(Employee t) {
//				return t.getSalary();
//			}
//		}));
//		Set<String> keys = map.keySet();
//		for(String s : keys) {
//			System.out.println("이름: " + s + ", 급여: " + map.get(s));
//		}
//		System.out.println();
		
		//job -> ST_CLERK인 사람들의 이름과 급여를 컬렉션에 저장하기<람다!>
		System.out.println("-------------이름과 급여-------------");
		stream = empList.stream();
		Map<String, Integer> map = stream.filter(a -> a.getJobId().equals("ST_CLERK"))
		.collect(Collectors.toMap(a -> a.getLastName(), b -> b.getSalary()));
		Set<String> keys = map.keySet();
		for(String s : keys) {
			System.out.println("이름: " + s + "\t" + " 급여: " + map.get(s));
		}
		System.out.println();
		
		System.out.println("-------------이름과 부서-------------");
		stream = empList.stream();
		stream.filter(a -> a.getJobId().equals("ST_CLERK"))
		.forEach(a -> System.out.println("이름: " + a.getLastName() + "\t" +  "부서: " + a.getJobId()));
	}
}
