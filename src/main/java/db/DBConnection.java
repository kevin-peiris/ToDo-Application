package db;

import controller.MainWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Task;

import java.sql.*;
import java.util.List;

public class DBConnection {
    private ObservableList<Task> list;
    private static DBConnection instance;
    private Task task;

    DBConnection(){
         list= FXCollections.observableArrayList();
    }

    public static DBConnection getInstance(){
        return null==instance?instance=new DBConnection():instance;
    }

    public ObservableList<Task> getList(){
        ObservableList<Task> list = FXCollections.observableArrayList();

        try {
            String SQL = "SELECT * FROM ToDo";
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
                System.out.println(task);

                list.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }



    public void addNewTask(Task task){
        try {
            String SQL = "INSERT INTO ToDo VALUES('"+
                    task.getTskId()+"','"+
                    task.getTskDate()+"','"+
                    task.getTskTitle()+"','"+
                    task.getTskDescription()+"')";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);

            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Task search(String tskTitle){
        Task task=null;
        try {
            String SQL = "SELECT * FROM ToDo";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                task=new Task(
                        resultSet.getString("TaskId"),
                        resultSet.getDate("TaskDate").toLocalDate(),
                        resultSet.getString("TaskTitle"),
                        resultSet.getString("TaskDescription")
                );
                if (task.getTskTitle().equals(tskTitle)) {
                    System.out.println(task + "  Found");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }


    public void completedTask(String tskTitle) {
        Task task=search(tskTitle);

        try {
            String SQL = "INSERT INTO Completed VALUES('"+
                    task.getTskId()+"','"+
                    task.getTskDate()+"','"+
                    task.getTskTitle()+"','"+
                    task.getTskDescription()+"')";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);

            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String SQL = "DELETE FROM ToDo WHERE TaskTitle ='"+task.getTskTitle()+"'";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist", "root", "12345");
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(tskTitle+" Deleted");

    }
}
