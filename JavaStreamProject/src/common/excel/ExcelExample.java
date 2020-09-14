package common.excel;

import java.util.List;

import common.EmpDAO;
import common.Employee;

public class ExcelExample {
	public static void main(String[] args) {
		EmployeeExcelWriter writer = new EmployeeExcelWriter();
		List<Employee> list = EmpDAO.getEmpList();
		writer.xlsWriter(list);
		System.out.println("엑셀 완료.");
	}
}
