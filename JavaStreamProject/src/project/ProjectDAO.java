package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectDAO {
	
	//일련번호로 메세지 가져오기
	static Massage selectMassage(int selectedMId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from MASSAGE where MID =" + selectedMId;
		ObservableList<Massage> list = FXCollections.observableArrayList();
		Massage msg = null;
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
				 msg = new Massage(rs.getString("FROMID"), rs.getString("TOID"), rs.getString("TITLE"), rs.getString("MASSAGE"), rs.getInt("MID"));
	               list.add(msg);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	//내가 보낸게 아닌 메세지 가져오기
	static ObservableList<Massage> selectMassage(String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from MASSAGE where FROMID !='" + userId + "'";
		ObservableList<Massage> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
				 Massage msg = new Massage(rs.getString("FROMID"), rs.getString("TOID"), rs.getString("TITLE"), rs.getString("MASSAGE"), rs.getInt("MID"));
	               list.add(msg);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//구매창에서 더블클릭해서 PId값에 따른 ID가져오기
	static Item selectIdOfPId(int selectedPId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM where PID =" + selectedPId;
		ObservableList<Item> list = FXCollections.observableArrayList();
		Item item = null;
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			item = new Item(rs.getString("ID"), rs.getString("NAME"), 
	            		   rs.getString("CONDITION"), rs.getInt("PRICE"), rs.getInt("PID"));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;	
	}
	
	static ObservableList<Member> selectMember() {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from MEMBER";
		ObservableList<Member> list = FXCollections.observableArrayList();
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
	               Member mem = new Member(rs.getString("ID"), rs.getInt("PASSWORD"));
	               list.add(mem);
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	static ObservableList<Item> listOfBuy(String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM where ID != '" + userId + "'";
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
	
	public static ObservableList<Item> listSelling(String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM where ID = '" + userId + "'";
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
	
	public static ObservableList<Item> listBuy(String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from BUYITEM where BUYID = '" + userId + "'";
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
	
	public static ObservableList<Item> listSell(String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from BUYITEM where ID = '" + userId + "'";
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
	
	static void btnSendMassageAction(String userId, String sellingId, String MassageTitle , String Massage) {
		Connection conn = ConnectionDB.getDB();
		String sql = "insert into MASSAGE values(?, ?, ?, ?, MassageSequence.nextval)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1,	userId);
			pstmt.setNString(2,	sellingId);
			pstmt.setNString(3,	Massage);
			pstmt.setNString(4,	MassageTitle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void btnSignIn(String txtID, String txtPassword) {
		Connection conn = ConnectionDB.getDB();
		String sql = "insert into MEMBER values(?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1,	txtID);
			pstmt.setNString(2,	txtPassword);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void btnSellAddAction(String userId, String name, String status, String price) {
		Connection conn = ConnectionDB.getDB();
		String sql = "insert into SELLITEM values(?,?,?,?, sequence1.nextval)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,	userId);
			pstmt.setString(2,	name);
			pstmt.setString(3,	status);
			pstmt.setString(4,	price);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void btnModifyAction(String name, String status, String price, int selectedPID) {
		Connection conn = ConnectionDB.getDB();
		String sql = "UPDATE SELLITEM SET NAME = ?, CONDITION = ?, PRICE = ? WHERE PID = "+ selectedPID;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,	name);
			pstmt.setString(2,	status);
			pstmt.setString(3,	price);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void buy(int buyPid, String userId) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM where PID = " + buyPid;
		Item item = null;
		try {
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			 while(rs.next()) {
				 item = new Item(rs.getString("ID"), rs.getString("NAME"), 
	            		   rs.getString("CONDITION"), rs.getInt("PRICE"), rs.getInt("PID"));
	            };
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql2 = "delete SELLITEM where PID = "+ buyPid;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql2);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql3 = "insert into BUYITEM values(?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1,	item.getId());
			pstmt.setString(2,	item.getName());
			pstmt.setString(3,	item.getCondition());
			pstmt.setString(4,	String.valueOf(item.getPrice()));
			pstmt.setString(5,	String.valueOf(buyPid));
			pstmt.setString(6,	String.valueOf(userId));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static ObservableList<Item> btnSearchAction(String buySearch) {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from SELLITEM where NAME like '%" + buySearch + "%'";
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
