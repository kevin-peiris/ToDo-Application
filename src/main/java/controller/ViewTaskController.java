package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Task;

import java.sql.*;

public class ViewTaskController {

    @FXML
    private TableColumn<?, ?> TaskId;

    @FXML
    private TableColumn<?, ?> colTaskDate;

    @FXML
    private TableColumn<?, ?> colTaskDesc;

    @FXML
    private TableColumn<?, ?> colTaskTitle;

    @FXML
    private TableView<Task> tblTasks;


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        TaskId.setCellValueFactory(new PropertyValueFactory<>("tskId"));
        colTaskDate.setCellValueFactory(new PropertyValueFactory<>("tskDate"));
        colTaskTitle.setCellValueFactory(new PropertyValueFactory<>("tskTitle"));
        colTaskDesc.setCellValueFactory(new PropertyValueFactory<>("tskDescription"));

        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();

        try {
            String SQL = "SELECT * FROM Completed";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                Task task=new Task(
                        resultSet.getString("TaskId"),
                        resultSet.getDate("TaskDate").toLocalDate(),
                        resultSet.getString("TaskTitle"),
                        resultSet.getString("TaskDescription")
                );


                taskObservableList.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblTasks.setItems(taskObservableList);
    }

}
