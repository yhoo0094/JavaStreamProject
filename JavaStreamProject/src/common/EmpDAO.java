package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmpDAO {
	
	public static List<Employee> getEmpList(){
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from EMPLOYEES";
		ObservableList<Employee> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
				 Employee employees = new Employee(
					 rs.getInt("EMPLOYEE_ID"), 
					 rs.getString("FIRST_NAME"), 
			  	   	 rs.getString("LAST_NAME"), 
			  	   	 rs.getString("EMAIL"), 
			  	   	 rs.getDate("HIRE_DATE").toLocalDate(),
			  	   	 rs.getString("JOB_ID"), 
			  	   	 rs.getInt("SALARY"));
	             list.add(employees);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Employee> getEmpList2(){
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from EMPLOYEES where JOB_ID = 'IT_PROG'";
		ObservableList<Employee> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
				 Employee employees = new Employee(
					 rs.getInt("EMPLOYEE_ID"), 
					 rs.getString("FIRST_NAME"), 
			  	   	 rs.getString("LAST_NAME"), 
			  	   	 rs.getString("EMAIL"), 
			  	   	 rs.getDate("HIRE_DATE").toLocalDate(),
			  	   	 rs.getString("JOB_ID"), 
			  	   	 rs.getInt("SALARY"));
	             list.add(employees);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
