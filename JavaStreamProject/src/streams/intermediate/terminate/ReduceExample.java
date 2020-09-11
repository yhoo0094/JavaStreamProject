package streams.intermediate.terminate;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import common.EmpDAO;
import common.Employee;

public class ReduceExample {
	public static void main(String[] args) {
		//사원의 평균 급여 계산
		List<Employee> empList = EmpDAO.getEmpList();
		Stream<Employee> stream = empList.stream();
		int sum = stream.map(Employee::getSalary).reduce((a, b)-> a+b).get();
		stream = empList.stream();
		double count = stream.count();
		double avg = sum/count;
		System.out.println("평균급여는" + avg + "입니다.");
		
		//사원의 평균 급여 계산2
		stream = empList.stream();
		OptionalDouble avg2 = stream.flatMapToInt(a -> IntStream.of(a.getSalary())).average();
		System.out.println("평균급여는" + avg2.getAsDouble() + "입니다.");
		
		stream = empList.stream();
		OptionalDouble avg3 = stream.filter(t -> t.getJobId()
				.equals("IT_PROG"))
				.flatMapToInt(a -> IntStream.of(a.getSalary()))
				.average();
		System.out.println("IT부서의 평균급여는 " + avg3.getAsDouble() + "입니다.");
	}
}
