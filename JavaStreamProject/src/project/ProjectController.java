package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProjectController implements Initializable {
	@FXML
	Button btnLogin, btnSignIn, btnBuy;
	@FXML
	TextField txtID, txtPassword;

	ObservableList<Member> MList = FXCollections.observableArrayList();
	ObservableList<Item> IList = FXCollections.observableArrayList();
	Connection conn = ConnectionDB.getDB();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 로그인 버튼
		btnLogin.setOnAction(e -> btnLoginAction());
		// 회원가입 버튼
		btnSignIn.setOnAction(e -> btnSignInAction());

	}// end of initialize

	public void btnLoginAction() {
		try {
			Parent p = FXMLLoader.load(getClass().getResource("login.fxml"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String sql = "select * from MEMBER";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Member mem = new Member(rs.getString("ID"), rs.getInt("PASSWORD"));
				MList.add(mem);
			};
			for (Member M : MList) {
				if (M.getName().equals(txtID.getText())) {
					if(M.getPassword()==Integer.parseInt(txtPassword.getText())) {
						// 구매및 판매 창 띄우기
						Stage stage = new Stage(StageStyle.UTILITY);
						try {
							Parent parent = FXMLLoader.load(getClass().getResource("BuyorSell.fxml"));

							Scene scene = new Scene(parent);
							stage.setTitle("구매/판매");
							stage.setScene(scene);
							stage.show();

							// 구매버튼
							Button btnBuy = (Button) parent.lookup("#btnBuy");
							btnBuy.setOnAction(e -> btnBuyAction());

							// 판매버튼
							Button btnSell = (Button) parent.lookup("#btnSell");
							btnSell.setOnAction(e -> btnSellAction());
							
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} 
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void btnSignInAction() {
		// 회원가입 창 띄우기
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SignIn.fxml"));

			Scene scene = new Scene(parent);
			stage.setTitle("회원가입");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void btnBuyAction() {
		// 구매 창 띄우기
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Buy.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("구매");
			stage.setScene(scene);
			stage.show();
			
			TableView<Item> tableView = (TableView<Item>) parent.lookup("#buyTableView");
			
			TableColumn<Item, ?> tc = tableView.getColumns().get(0);//첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));
			
			tc = tableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));
			
			tc = tableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			tableView.setItems(ProjectDAO.listBuy());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void btnSellAction() {
		// 판매 창 띄우기
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Sell.fxml"));

			Scene scene = new Scene(parent);
			stage.setTitle("판매");
			stage.setScene(scene);
			stage.show();
			
			TableView<Item> tableView = (TableView<Item>) parent.lookup("#sellTableView");
			
			TableColumn<Item, ?> tc = tableView.getColumns().get(0);//첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));
			
			tc = tableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));
			
			tc = tableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			tableView.setItems(ProjectDAO.listSell());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
//		TableView<Item> sellView = (TableView) parent.lookup("#sellView");
//		sellView.setOnMouseClicked(event -> {
//			
//			if(event.getClickCount() == 2) { //더블클릭이면
//				String selectedName = tableView.getSelectionModel().getSelectedItem().getName();
//				handleDoubleClickAction(selectedName);
//			}
//	});
	}
}// end of Initializable
