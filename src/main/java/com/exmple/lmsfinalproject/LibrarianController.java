package com.exmple.lmsfinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LibrarianController {
    // this will take librarian to book management section
    public void gotoBookManagement(ActionEvent event){
            HelloApplication.changeScene("bookmanagementsection");
    }
    public void gotoDashboard(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }

    // this part is to view students profile
    // it is going to take id as input from user and show details
    @FXML
    private TextField studentIDField;
    @FXML
    private Label nameL;
    @FXML
    private Label departmentL;
    @FXML
    private Label idL;
    @FXML
    private Label batchL;


    public void onClickShowStudentDetails(ActionEvent event){
        int id = Integer.parseInt(studentIDField.getText());
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM memberinfo WHERE id ='" + id + "';");
            while (resultSet.next()) {
                String name = resultSet.getString("studentName");
                String department = resultSet.getString("department");
                String batch = resultSet.getString("Batch");
/*
                LocalDate localDate = LocalDate.now();
                System.out.println(localDate);
                LocalDate dt = LocalDate.parse("2024-01-16");  // localdate converter
                System.out.println(dt);*/


                nameL.setText("Student name: " +name);
                idL.setText("ID: " + String.valueOf(id));
                departmentL.setText("Department: " + department);
                batchL.setText("Batch: " + batch);

                System.out.println(name + " " + department + " " + batch);
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }
    @FXML
    private Label issueLabel;
    @FXML
    private Label issueLabel11;

    /// issue books section
    @FXML private TextField idStudentField;
    @FXML private TextField idBookField;
    @FXML private TextField dayCountField;
    public void issueBook(ActionEvent event){
        int id = Integer.parseInt(idStudentField.getText());
        int bookid = Integer.parseInt(idBookField.getText());
        int dayCount = Integer.parseInt(dayCountField.getText());

        String studentName = null;
        String bookName = null;
        String bookauthor = null;

        // adding n with localdate and converting it to string so that we can put it into the mysql database
        LocalDate localDate = LocalDate.now();
        String currentDate = localDate.toString();

        LocalDate returnDate = localDate.plusDays(dayCount);
        String dateOfReturn = returnDate.toString();

        // this section is to retrieve data from the database
        // and assign them into those variables declared up above
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM memberinfo WHERE id ='" + id + "';");
            while (resultSet.next()) {
                studentName = resultSet.getString("studentName");
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

        // same here. just book data retrieve!
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bookmanagement WHERE id ='" + bookid + "';");
            while (resultSet.next()) {
                bookName = resultSet.getString("name");
                bookauthor = resultSet.getString("author");
            }
        }

        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
        System.out.println(studentName + " " + id  + " " + bookName + " " +bookauthor + " " +currentDate  + " " + dateOfReturn);

        // inseting those data into database
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "Insert into issuebook values(" + id + ",'" + studentName + "'," + bookid + ",'" + bookName+  "','" + bookauthor + "','" + currentDate + "','" + dateOfReturn+ "'," + "0);";
            System.out.println(query);
            statement.execute(query);

            // error message display
            if(studentName == null && bookName == null){
                issueLabel.setText("Both student ID and Book ID are incorrect are incorrect. Enter correct details");
                issueLabel11.setText("");
            }
            else if(studentName == null){
                issueLabel.setText("Enter correct student ID");
                issueLabel11.setText("");
            }
            else if(bookName == null){
                issueLabel.setText("Enter correct Book ID");
                issueLabel11.setText("");
            }
            else {
                issueLabel11.setText( bookName +"(" +bookid +")" + " assigned to " + studentName + "(" + id + ")" + ". Return date " + dateOfReturn);
                issueLabel.setText("");
                query = "Insert into issuebookhist values(" + id + ",'" + studentName + "'," + bookid + ",'" + bookName+  "','" + bookauthor + "','" + currentDate + "','" + dateOfReturn+ "');";
                statement.execute(query);
                System.out.println(query);
            }
            statement.execute(query);
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }


    }

    //


}
