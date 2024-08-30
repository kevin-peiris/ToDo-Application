package controller;

import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.conf.BooleanProperty;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Task;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddNewTaskController implements Initializable {

    @FXML
    private DatePicker tskDate;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXTextField txtId;


    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtTitle.getText().isEmpty() || txtDescription.getText().isEmpty() || tskDate.getValue()==null ){
            Alert alert=new Alert(Alert.AlertType.WARNING,"Empty Field or Fields");
            alert.show();
        } else {

            Task task = new Task(txtId.getText(), tskDate.getValue(),txtTitle.getText(),txtDescription.getText());
            System.out.println(task);

            DBConnection.getInstance().addNewTask(task);

            generateId();

            txtTitle.setText("");
            txtDescription.setText("");
            tskDate.setValue(null);



            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task Added");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
    }

    private void generateId(){
        int taskCount=1;


        try {
            String SQL = "SELECT * FROM ToDo";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                taskCount++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String SQL = "SELECT * FROM Completed";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                taskCount++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        String id=String.format("T%03d",taskCount);
        txtId.setText(id);



    }

}
