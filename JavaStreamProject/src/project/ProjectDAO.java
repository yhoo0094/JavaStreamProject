package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectDAO {
	static ObservableList<Item> listBuy() {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from BUYITEM";
		ObservableList<Item> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
	               Item item = new Item(rs.getString("ID"), rs.getString("NAME"), 
	            		   rs.getString("CONDITION"), rs.getInt("PRICE"), rs.getInt("PID"));
	               list.add(item);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	static ObservableList<Item> listSell() {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM";
		ObservableList<Item> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
	               Item item = new Item(rs.getString("ID"), rs.getString("NAME"), 
	            		   rs.getString("CONDITION"), rs.getInt("PRICE"), rs.getInt("PID"));
	               list.add(item);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
