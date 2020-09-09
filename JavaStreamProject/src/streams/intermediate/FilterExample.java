package streams.intermediate;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.Predicate;

public class FilterExample {
	public static void main(String[] args) {
		List<Employee> list = Employee.employees();
//		list.stream().filter(new Predicate<Employee>() {
//			@Override
//			public boolean test(Employee t) {
//				return t.isMale();
//			}
//		}).peek(s -> {
//			System.out.println("원래 수익: " + s.getIncome());;
//			s.setIncome(s.getIncome()*0.9);
//		}).forEach(System.out::println);
		
		System.out.println("-------------수입 5000 이상-------------");
		list = Employee.employees();
		list.stream().filter(s -> s.getIncome() >= 5000.0)
					 .forEach(System.out::println);
		
		System.out.println("---------93년 보다 일찍 태어난 사람---------");
		list = Employee.employees();
		list.stream()
			.filter(t -> t.getDateOfBirth().isBefore(LocalDate.of(1993, Month.JANUARY, 1)))
			.forEach(System.out::println);
		
	}
}
