package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Task;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private static MainWindowController instance;
    private ObservableList<Task> taskList;
    private ObservableList<CheckBox> checkBoxList;

    public static MainWindowController getInstance(){
        return null==instance?instance=new MainWindowController():instance;
    }

    @FXML
    private VBox vbox;

    @FXML
    private JFXCheckBox checkbox;

    @FXML
    private Label txtID;

    @FXML
    private JFXListView<CheckBox> listView=new JFXListView<>();


    @FXML
    void btnAddnewTaskOnAction(ActionEvent event) throws Exception{
        Stage stage=new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/add_new_task.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadTask();
    }

    @FXML
    void btnViewCompletedOnAction(ActionEvent event) {
        Stage stage=new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/view_task.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTask(){
        taskList = DBConnection.getInstance().getList();

        checkBoxList=FXCollections.observableArrayList();

        for (int i=0; i<taskList.size(); i++){
            JFXCheckBox checkBox=new JFXCheckBox(taskList.get(i).getTskTitle());
            checkBox.setCheckedColor(Color.GREEN);
            checkBox.setUnCheckedColor(Color.RED);
            checkBox.widthProperty().add(100);
            checkBox.setPrefSize(80,30);

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    String tskTitle=checkBox.getText();
                    checkBoxList.remove(checkBox);
                    System.out.println(tskTitle + " Checked");
                    DBConnection.getInstance().completedTask(tskTitle);
                }
            });

            checkBoxList.add(checkBox);
        }

        listView.setItems(checkBoxList);
    }

    @FXML
    void OnClicked(MouseEvent event) {
        loadTask();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTask();
    }
}
