package com.yedam.book;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookController implements Initializable {
	@FXML
	TableView<Book> tableView;
	@FXML
	Button btnAdd;

	ObservableList<Book> list;

	Stage priStage;

	public void setPriStage(Stage priStage) {
		this.priStage = priStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
// 책정보
		// 테이블뷰의 칼럼 가져오기
		TableColumn<Book, ?> tc = tableView.getColumns().get(0); // 첫번째 칼럼을 가져오자
		tc.setCellValueFactory(new PropertyValueFactory<>("name"));

		tc = tableView.getColumns().get(1);
		tc.setCellValueFactory(new PropertyValueFactory<>("writer"));

		tc = tableView.getColumns().get(2);
		tc.setCellValueFactory(new PropertyValueFactory<>("publisher"));

		tc = tableView.getColumns().get(3);
		tc.setCellValueFactory(new PropertyValueFactory<>("price"));
		// 저장하는 리스트 생성
		list = FXCollections.observableArrayList();
		// 테이블뷰에 넣기
		tableView.setItems(list);

// 도서등록버튼
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleBtnAddAction();
			}
		});
// 마우스 더블클릭으로 수정
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) { // 클릭을 2번하는 이벤트가 발생하면(즉, 더블클릭하면)
					String selectedBook = tableView.getSelectionModel().getSelectedItem().getName();
					handleDoubleClikAction(selectedBook); //
				}
			}
		});

	}

// 수정 메소드
	public void handleDoubleClikAction(String name) {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnAdd.getScene().getWindow());
		
		
		try {
			Parent updateParent = FXMLLoader.load(getClass().getResource("BookUpdate.fxml"));
			
			Scene sc = new Scene(updateParent);
			stage.setScene(sc);
			stage.show();
			
			TextField tName = (TextField) updateParent.lookup("#tName");
			TextField tWriter = (TextField) updateParent.lookup("#tWriter");
			TextField tPublisher = (TextField) updateParent.lookup("#tPublisher");
			TextField tPrice = (TextField) updateParent.lookup("#tPrice");
			
			Button updateSave = (Button) updateParent.lookup("#updateSave");

			updateSave.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getName().equals(name)) {
							Book book = new Book(name, tWriter.getText(), tPublisher.getText(),
									Integer.parseInt(tPrice.getText())
							);
							list.set(i, book);
						}
					}
					stage.close();
				}
			});

			for (Book bk : list) {
				if (bk.getName().equals(name)) {
					tName.setText(bk.getName());
					tWriter.setText(bk.getWriter());
					tPublisher.setText(bk.getPublisher());
					tPrice.setText(String.valueOf(bk.getPrice()));
				}
			}

// 창닫기 버튼
			Button updateClose = (Button) updateParent.lookup("#updateClose");
			updateClose.setOnAction(e -> {
				stage.close();

			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

// 등록 메소드
	public void handleBtnAddAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(priStage);
		try {
			Parent addParent = FXMLLoader.load(getClass().getResource("BookAdd.fxml"));
			Scene sc = new Scene(addParent);
			stage.setScene(sc);
			stage.show();
			// 등록 저장버튼
			Button addSave = (Button) addParent.lookup("#addSave");
			addSave.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					TextField txtName = (TextField) addParent.lookup("#txtName");
					TextField txtWriter = (TextField) addParent.lookup("#txtWriter");
					TextField txtPublisher = (TextField) addParent.lookup("#txtPublisher");
					TextField txtPrice = (TextField) addParent.lookup("#txtPrice");

					Book book = new Book(txtName.getText(), txtWriter.getText(), txtPublisher.getText(),
							Integer.parseInt(txtPrice.getText()));
					list.add(book);
					stage.close();
				}
			});
			// 창닫기 버튼
			Button addClose = (Button) addParent.lookup("#addClose");
			addClose.setOnAction(e -> stage.close());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
