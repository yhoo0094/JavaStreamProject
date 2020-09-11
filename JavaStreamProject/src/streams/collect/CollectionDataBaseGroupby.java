package streams.collect;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import common.EmpDAO;
import common.Employee;

public class CollectionDataBaseGroupby {
	public static void main(String[] args) {
		// 직무별 사원이름 출력하기
		List<Employee> empList = EmpDAO.getEmpList();
		Map<String, List<Employee>> jMap = empList.stream().collect(Collectors.groupingBy(t -> t.getJobId()));

		empList = EmpDAO.getEmpList();
		Map<String, List<Employee>> nMap = empList.stream()
				.collect(Collectors.groupingBy(e -> e.getJobId()));

//		Map<String, List<Employee>> jMap = empList.stream().collect(Collectors.groupingBy(new Function<Employee, String>() {
//
//			@Override
//			public String apply(Employee t) {
//				return t.getJobId();
//			}
//		}));

		Set<String> set = jMap.keySet();
		for (String g : set) {
			System.out.println("부서: " + g);
			List<Employee> list = jMap.get(g);
			for (Employee s : list) {
				System.out.print(s.getLastName() + ", ");
			}
			System.out.println();
		}
	}
}
