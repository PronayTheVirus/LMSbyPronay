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
public class StudentController {
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

    /* this section is for requesting book from the librarian*/
    @FXML TextField bookIdField;

    public void onClickSendBookLendReq(ActionEvent event){
        int bookid = Integer.parseInt(bookIdField.getText());

       // to put data into lendreq
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "insert into lendreq values(" + studentID + "," + bookid + ");" ;
            statement.execute(query);
            System.out.println("DATA INSERTED");
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }


    }

}
