package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StreamMapExample {
	public static void main(String[] args) {
		List<Employee> employees = EmpDAO.getEmpList();
		//salary가 10000이상인 사원들 출력
		LocalDate date = LocalDate.of(2020, 5, 1);//데이트타입 -> 문자열
//		System.out.println(date.format(DateTimeFormatter.ISO_DATE));
//		LocalDate.parse("2020-05-05",DateTimeFormatter.ISO_DATE);//문자열 -> 데이트타입
//		employees.stream().filter(a -> a.getSalary() >= 10000)
//		.forEach(System.out::println);
//		.forEach(s -> System.out.println(s.getLastName() + " : " + s.getSalary()));
		
		//reduce응용
//		employees = EmpDAO.getEmpList();
//		Employee emp = employees.stream().reduce((a, b) -> a.getSalary() > b.getSalary()? a : b).get();
//		System.out.println("최고 연봉자: " + emp.getLastName() + " 연봉 " + emp.getSalary());
		
		//map사용하기
//		employees = EmpDAO.getEmpList();
//		int salary = employees.stream().map(Employee::getSalary).reduce((a, b) -> a > b? a : b).get();
//		System.out.println("최고 연봉: " + salary);
		
		LocalDate dd = LocalDate.parse("1991-01-01",DateTimeFormatter.ISO_DATE);
		employees = EmpDAO.getEmpList();
		employees.stream().filter(a -> a.getHireDate().isAfter(dd)).forEach(System.out::println);
		
		
	}
}
