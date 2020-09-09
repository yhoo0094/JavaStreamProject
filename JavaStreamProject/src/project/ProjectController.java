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
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
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
	String userId = null;
	Stage stageSell = new Stage(StageStyle.UTILITY);
	TableView<Item> buyTableView;
	
	Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 로그인 버튼
		btnLogin.setOnAction(e -> btnLoginAction());
		// 회원가입 버튼
		btnSignIn.setOnAction(e -> btnSignInAction());

	}// end of initialize

	public void btnLoginAction() {
		userId = txtID.getText();
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
			}
			;
			int a = 0;
			for (Member M : MList) {
				if (M.getName().equals(txtID.getText())) {
					a = 1;
					if (M.getPassword() == Integer.parseInt(txtPassword.getText())) {
						primaryStage.close();
						// 구매 및 판매 창 띄우기
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
					} else {
						Stage stage = new Stage(StageStyle.UTILITY);
						try {
							Parent parent = FXMLLoader.load(getClass().getResource("NoPassWord.fxml"));
							Scene scene = new Scene(parent);
							stage.setTitle("경고");
							stage.setScene(scene);
							stage.show();

							// 확인버튼
							Button btnBuy = (Button) parent.lookup("#btnOK");
							btnBuy.setOnAction(e -> stage.close());

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
			if (a == 0) {
				Stage stage = new Stage(StageStyle.UTILITY);
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("NoID.fxml"));
					Scene scene = new Scene(parent);
					stage.setTitle("경고");
					stage.setScene(scene);
					stage.show();

					// 확인버튼
					Button btnBuy = (Button) parent.lookup("#btnOK");
					btnBuy.setOnAction(e -> stage.close());

				} catch (IOException e) {
					e.printStackTrace();
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

			Button btnSignIn = (Button) parent.lookup("#btnSignIn");
			TextField txtID = (TextField) parent.lookup("#txtID");
			TextField txtPassword = (TextField) parent.lookup("#txtPassword");
			TextField txtPasswordChk = (TextField) parent.lookup("#txtPasswordChk");
			btnSignIn.setOnAction(a -> {
				if (txtPassword.getText().equals(txtPasswordChk.getText())) {
					ProjectDAO.btnSignIn(txtID.getText(), txtPassword.getText());
					stage.close();
				} else {
					Stage stage2 = new Stage(StageStyle.UTILITY);
					try {
						Parent parent2 = FXMLLoader.load(getClass().getResource("PasswordchkError.fxml"));
						Scene scene2 = new Scene(parent2);
						stage2.setTitle("경고");
						stage2.setScene(scene2);
						stage2.show();

						// 확인버튼
						Button btnBuy = (Button) parent2.lookup("#btnOK");
						btnBuy.setOnAction(e -> stage2.close());

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

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

			buyTableView = (TableView<Item>) parent.lookup("#buyTableView");

			TableColumn<Item, ?> tc = buyTableView.getColumns().get(0);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));

			tc = buyTableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));

			tc = buyTableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));

			buyTableView.setItems(ProjectDAO.listBuy());
			
			buyTableView.setOnMouseClicked(e -> {
				int buyPid = buyTableView.getSelectionModel().getSelectedItem().getPid();
			
			Button btnBuyEnd = (Button) parent.lookup("#btnBuyEnd");
			btnBuyEnd.setOnAction(a -> btnBuyEndAction(buyPid));
		});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void btnBuyEndAction(int buyPid) {
		ProjectDAO.buy(buyPid);
		buyTableView.setItems(ProjectDAO.listBuy());
	}
	TableView<Item> sellTableView;
	public void btnSellAction() {
		// 판매 창 띄우기
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Sell.fxml"));

			Scene scene = new Scene(parent);
			stageSell.setTitle("판매");
			stageSell.setScene(scene);
			stageSell.show();

			sellTableView = (TableView<Item>) parent.lookup("#sellTableView");

			TableColumn<Item, ?> tc = sellTableView.getColumns().get(0);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));

			tc = sellTableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));

			tc = sellTableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));

			IList = ProjectDAO.listSell(userId);
			sellTableView.setItems(IList);

			Button btnAdd = (Button) parent.lookup("#btnAdd");
			btnAdd.setOnAction(e -> btnAddAction());
			
			sellTableView.setOnMouseClicked(e -> {
				if(e.getClickCount() == 2) { //더블클릭이면
					int selectedPID = sellTableView.getSelectionModel().getSelectedItem().getPid();
					handleDoubleClickAction(selectedPID);
				}
			});

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
	
	public void handleDoubleClickAction(int selectedPID) {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Update.fxml"));
			
			Scene scene = new Scene(parent);
			stage.setTitle("물건수정");
			stage.setScene(scene);
			stage.show();
			
			TextField txtName = (TextField) parent.lookup("#txtName");
			TextField txtStatus = (TextField) parent.lookup("#txtStatus");
			TextField txtPrice = (TextField) parent.lookup("#txtPrice");

			//이름기준으로 국어, 수학, 영어 점수를 화면에 입력해주기
			for(Item item : IList) {
				if(Integer.valueOf(item.getPid()) == Integer.valueOf(selectedPID)) {
					txtName.setText(String.valueOf(item.getName()));
					txtStatus.setText(String.valueOf(item.getCondition()));
					txtPrice.setText(String.valueOf(item.getPrice()));
				}
			}
			
			Button btnModify = (Button) parent.lookup("#btnModify");
			btnModify.setOnAction(e -> {
				
				String name = txtName.getText();
				String status = txtStatus.getText();
				String price = txtPrice.getText();
				ProjectDAO.btnModifyAction(name, status, price, selectedPID);
				IList = ProjectDAO.listSell(userId);
				sellTableView.setItems(IList);
				stage.close();
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void btnAddAction() {
		// 물건 등록창
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("AddItem.fxml"));

			Scene scene = new Scene(parent);
			stage.setTitle("물건추가");
			stage.setScene(scene);
			stage.show();

			Button btnSellAdd = (Button) parent.lookup("#btnSellAdd");
			btnSellAdd.setOnAction(e -> {
				TextField txtName = (TextField) parent.lookup("#txtName");
				TextField txtStatus = (TextField) parent.lookup("#txtStatus");
				TextField txtPrice = (TextField) parent.lookup("#txtPrice");

				String name = txtName.getText();
				String status = txtStatus.getText();
				String price = txtPrice.getText();
				ProjectDAO.btnSellAddAction(userId, name, status, price);
				IList = ProjectDAO.listSell(userId);
				sellTableView.setItems(IList);
				stage.close();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}// end of Initializable
