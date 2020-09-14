package common.excel;

import java.util.ArrayList;
import java.util.List;

import common.EmpDAO;
import common.Employee;

public class ExcelExample2 {
	public static void main(String[] args) {
//		EmployeeExcelWriter2 writer = new EmployeeExcelWriter2();
//		List<Employee> list = EmpDAO.getEmpList2();
//		writer.xlsWriter(list);
//		System.out.println("엑셀 완료2.");
		
		EmployeeExcelWriter2 writer = new EmployeeExcelWriter2();
		List<Employee> list = EmpDAO.getEmpList();
		List<Employee> list2 = new ArrayList<Employee>();
		list.stream().filter(e -> e.getJobId().equals("IT_PROG")).forEach(e -> list2.add(e));
		list2.stream().forEach(System.out::println);
		writer.xlsWriter(list2);
		System.out.println("엑셀 완료2.");
	}
}
