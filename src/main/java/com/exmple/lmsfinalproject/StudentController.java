package com.exmple.lmsfinalproject;

import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;
public class StudentController implements Initializable {
    @FXML
    private Button gotobutton;
    public void gotoDash(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }

    // to pass student ID into this class
    static int studentID;
    public static void passID(int id){
        studentID =id;
        System.out.println(studentID);
    }


//        LibrarianController.finesCalculator(studentID);
    // student view section, current books, lend date, return date etc

    @FXML
    private Label name;
    @FXML
    private Label author;
    @FXML
    private Label ldate;
    @FXML
    private Label rdate;
//    @FXML
//    private Label name;
    @FXML
    private Label finesL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("entered");
        // will show things on startup
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM issuebook WHERE id ='" + studentID + "';");
            while (resultSet.next()) {
                String bookName = resultSet.getString("bookname");
                String authorL = resultSet.getString("author");
                String lenddate = resultSet.getString("lenddate");
                String returnDate = resultSet.getString("returnDate");
                LibrarianController librarianController = new LibrarianController();
                long fines = librarianController.finesCalculator(studentID);

                System.out.println(bookName + " " + authorL + " " + lenddate + " " + returnDate +" " + fines);
                System.out.println(studentID);

                // to show data
                name.setText("Book name: " + bookName);
                author.setText("Author: " + authorL);
                ldate.setText("Lend date: " + lenddate);
                rdate.setText("Return date: " +returnDate);
                finesL.setText("Fine amount: " + String.valueOf(fines));
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }

    // send note to librarian
    // to put data into database
    @FXML
    private TextArea note;
    @FXML
    private Button send;

    public void clickSend(ActionEvent event){
        String text = note.getText();
        int id = studentID;
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "insert into note values(" + id + ",'" + text + "');";
            statement.execute(query);
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }
    SearchController searchController = new SearchController();
    public void gotoSearch(ActionEvent event){
        searchController.isStudent(1);
        HelloApplication.changeScene("searchBookSection");
    }
}


