package project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Stream;

import common.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
	TableView<Item> sellTableView;
	TableView<Item> buyListTableView;
	TableView<Item> sellListTableView;
	TableView<Massage> massageListTableView;
	Label txtMassage;

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

						// 구매 및 판매 창 띄우기
						Stage stage = new Stage(StageStyle.UTILITY);
						stage.initModality(Modality.WINDOW_MODAL);
						stage.initOwner(primaryStage);
						try {
							Parent parent = FXMLLoader.load(getClass().getResource("BuyorSell.fxml"));

							Scene scene = new Scene(parent);
							primaryStage.setTitle("구매/판매");
							primaryStage.setScene(scene);
							primaryStage.show();

							// 환영문구
							Label textWelcome = (Label) parent.lookup("#textWelcome");
							textWelcome.setText(userId + "님 환영합니다!");
							
							//쪽지 갯수 나타내기
							int massageCount = ProjectDAO.selectNotReadMassage(userId).size();
							txtMassage = (Label)parent.lookup("#txtMassage");
							txtMassage.setText("쪽지(" + massageCount + ")");
							
							// 쪽지확인 버튼
							Button MassageChk = (Button) parent.lookup("#MassageChk");
							MassageChk.setOnAction(e -> MassageChkAction());

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

	// 받은 메세지 창
	public void MassageChkAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("MassageList.fxml"));

			Scene scene = new Scene(parent);
			stage.setTitle("받은 메세지");
			stage.setScene(scene);
			stage.show();

			massageListTableView = (TableView<Massage>) parent.lookup("#massageListTableView");

			TableColumn<Massage, ?> tc = massageListTableView.getColumns().get(0);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("chk"));
			
			tc = massageListTableView.getColumns().get(1);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("fromId"));

			tc = massageListTableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("title"));

			massageListTableView.setItems(ProjectDAO.selectMassage(userId));
			
			//메세지 내용 보기
			massageListTableView.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2) { // 더블클릭이면
					int selectedMId = massageListTableView.getSelectionModel().getSelectedItem().getMid();
					CheckMsg(selectedMId);
					ProjectDAO.UpdateChk(selectedMId);
					massageListTableView.setItems(ProjectDAO.selectMassage(userId));
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// 받은 메세지 내용보기
	public void CheckMsg(int selectedMId) {
		Massage massage = ProjectDAO.selectMassage(selectedMId);

		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("CheckMassage.fxml"));

			Scene scene = new Scene(parent);
			stage.setTitle("받은 메세지");
			stage.setScene(scene);
			stage.show();

			TextField MassageTitle = (TextField) parent.lookup("#MassageTitle");
			TextField Massage = (TextField) parent.lookup("#Massage");

			MassageTitle.setText(String.valueOf(massage.getTitle()));
			Massage.setText(String.valueOf(massage.getMassage()));

			// 답장 버튼
			String fromId = massage.getFromId();
			Button btnSendMassage = (Button) parent.lookup("#btnSendMassage");
			btnSendMassage.setOnAction(e -> {
				stage.close();
				answerMassageAction(fromId);
			});
			
			//삭제 버튼
			Button btnDeleteMassage = (Button) parent.lookup("#btnDeleteMassage");
			btnDeleteMassage.setOnAction(e -> {
				ProjectDAO.btnDeleteMassageAction(selectedMId);
				massageListTableView.setItems(ProjectDAO.selectMassage(userId));
				stage.close();
			});

			// 취소 버튼
			Button btnMassageEnd = (Button) parent.lookup("#btnMassageEnd");
			btnMassageEnd.setOnAction(e -> stage.close());
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 답장 보내기
	public void answerMassageAction(String fromId) {
		// 쪽지창
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SendMassage.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("쪽지");
			stage.setScene(scene);
			stage.show();

			TextField MassageTitle = (TextField) parent.lookup("#MassageTitle");
			TextField Massage = (TextField) parent.lookup("#Massage");

			// 보내기 버튼
			Button btnSendMassage = (Button) parent.lookup("#btnSendMassage");
			btnSendMassage.setOnAction(e -> {
				ProjectDAO.btnSendMassageAction(userId, fromId, MassageTitle.getText(), Massage.getText());
				stage.close();
			});

			// 취소 버튼
			Button btnMassageEnd = (Button) parent.lookup("#btnMassageEnd");
			btnMassageEnd.setOnAction(e -> stage.close());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void btnSignInAction() {
		// 회원가입 창 띄우기
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
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

			// 회원가입 버튼
			btnSignIn.setOnAction(a -> {
				List<Member> mem = ProjectDAO.selectMember();

//				mem.stream().filter(t -> t.)

//				mem.stream().filter(new Predicate<Member>() {
//
//					@Override
//					public boolean test(Member t) {
//						if(txtID.getText().equals(t.getName())) {
//							
//						}
//						return ;
//					}
//				});

				// 중복 ID확인하기
				int chkNum = 0;
				for (Member m : mem) {
					if (txtID.getText().equals(m.getName())) {
						Stage stage2 = new Stage(StageStyle.UTILITY);
						stage2.initModality(Modality.WINDOW_MODAL);
						stage2.initOwner(stage);
						try {
							Parent parent2 = FXMLLoader.load(getClass().getResource("SameID.fxml"));

							Scene scene2 = new Scene(parent2);
							stage2.setTitle("동일 아이디 경고");
							stage2.setScene(scene2);
							stage2.show();

							// 확인버튼
							Button btnBuy = (Button) parent2.lookup("#btnOK");
							btnBuy.setOnAction(e -> stage2.close());
						} catch (IOException e) {
							e.printStackTrace();
						}
						chkNum = 1;
						break;
					}
				}
				// PASSWORDchk 확인
				if (chkNum == 0 && txtPassword.getText().equals(txtPasswordChk.getText())) {
					ProjectDAO.btnSignIn(txtID.getText(), txtPassword.getText());
					stage.close();
				} else if (chkNum == 0) {
					Stage stage2 = new Stage(StageStyle.UTILITY);
					try {
						Parent parent2 = FXMLLoader.load(getClass().getResource("PasswordchkError.fxml"));
						Scene scene2 = new Scene(parent2);
						stage2.setTitle("비밀번호 확인 경고");
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

			tc = buyTableView.getColumns().get(3);
			tc.setCellValueFactory(new PropertyValueFactory<>("pid"));

			buyTableView.setItems(ProjectDAO.listOfBuy(userId));

			// 구매하기버튼
			buyTableView.setOnMouseClicked(e -> {
				int buyPid = buyTableView.getSelectionModel().getSelectedItem().getPid();
				Button btnBuyEnd = (Button) parent.lookup("#btnBuyEnd");
				btnBuyEnd.setOnAction(a -> {
					ProjectDAO.buy(buyPid, userId);
					buyTableView.setItems(ProjectDAO.listOfBuy(userId));
				});
				// 메세지 보내기
				if (e.getClickCount() == 2) { // 더블클릭이면
					int selectedPId = buyTableView.getSelectionModel().getSelectedItem().getPid();
					doubleClickAction(selectedPId);
				}
			});

			// 구매내역버튼
			Button btnBuyList = (Button) parent.lookup("#btnBuyList");
			btnBuyList.setOnAction(e -> btnBuyListAction());

			// 검색버튼
			TextField buySearch = (TextField) parent.lookup("#buySearch");
			Button btnSearch = (Button) parent.lookup("#btnSearch");
			btnSearch.setOnAction(e -> buyTableView.setItems(ProjectDAO.btnSearchAction(buySearch.getText())));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 구매창에서 더블클릭해서 쪽지보내기
	public void doubleClickAction(int selectedPId) {
		Item item = ProjectDAO.selectIdOfPId(selectedPId);
		String sellingId = item.getId();

		// 쪽지창
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SendMassage.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("쪽지");
			stage.setScene(scene);
			stage.show();

			TextField MassageTitle = (TextField) parent.lookup("#MassageTitle");
			TextField Massage = (TextField) parent.lookup("#Massage");

			// 보내기 버튼
			Button btnSendMassage = (Button) parent.lookup("#btnSendMassage");
			btnSendMassage.setOnAction(e -> {
				ProjectDAO.btnSendMassageAction(userId, sellingId, MassageTitle.getText(), Massage.getText());
				stage.close();
			});

			// 취소 버튼
			Button btnMassageEnd = (Button) parent.lookup("#btnMassageEnd");
			btnMassageEnd.setOnAction(e -> stage.close());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 구매내역 창
	public void btnBuyListAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("BuyList.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("구매내역");
			stage.setScene(scene);
			stage.show();

			buyListTableView = (TableView<Item>) parent.lookup("#buyListTableView");

			TableColumn<Item, ?> tc = buyListTableView.getColumns().get(0);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));

			tc = buyListTableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));

			tc = buyListTableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));

			buyListTableView.setItems(ProjectDAO.listBuy(userId));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 판매내역 창
	public void btnSellListAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("SellList.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("판매내역");
			stage.setScene(scene);
			stage.show();

			sellListTableView = (TableView<Item>) parent.lookup("#sellListTableView");

			TableColumn<Item, ?> tc = sellListTableView.getColumns().get(0);// 첫번째칼럼
			tc.setCellValueFactory(new PropertyValueFactory<>("name"));

			tc = sellListTableView.getColumns().get(1);
			tc.setCellValueFactory(new PropertyValueFactory<>("condition"));

			tc = sellListTableView.getColumns().get(2);
			tc.setCellValueFactory(new PropertyValueFactory<>("price"));

			sellListTableView.setItems(ProjectDAO.listSell(userId));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

			IList = ProjectDAO.listSelling(userId);
			sellTableView.setItems(IList);

			Button btnAdd = (Button) parent.lookup("#btnAdd");
			btnAdd.setOnAction(e -> btnAddAction());

			// 검색버튼
			TextField sellSearch = (TextField) parent.lookup("#sellSearch");
			Button btnSearch = (Button) parent.lookup("#btnSearch");
			btnSearch.setOnAction(e -> sellTableView.setItems(ProjectDAO.btnSearchAction(sellSearch.getText())));

			// 수정하기
			sellTableView.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2) { // 더블클릭이면
					int selectedPID = sellTableView.getSelectionModel().getSelectedItem().getPid();
					handleDoubleClickAction(selectedPID);
				}
			});

			// 판매내역
			Button btnSellList = (Button) parent.lookup("#btnSellList");
			btnSellList.setOnAction(e -> btnSellListAction());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

			for (Item item : IList) {
				if (Integer.valueOf(item.getPid()) == Integer.valueOf(selectedPID)) {
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
				IList = ProjectDAO.listSelling(userId);
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
				IList = ProjectDAO.listSelling(userId);
				sellTableView.setItems(IList);
				stage.close();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}// end of Initializable
