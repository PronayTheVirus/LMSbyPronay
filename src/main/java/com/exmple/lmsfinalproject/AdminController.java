package com.exmple.lmsfinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class AdminController {

    /* This section right here is to insert librarian data into
    the database . an admin can add librarians in here
     */

    @FXML
    private TextField getLibMail;
    @FXML
    private TextField getLibpass;

    public void addLib(ActionEvent event){
        String mail = getLibMail.getText();
        String pass = getLibpass.getText();

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "insert into librarian values('"  + mail + "','" + pass + "');";
            statement.execute(query);
            System.out.println("Librarian DATA INSERTED");
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }


    // this section right here is to delete a librarian
    // this program right here will get email and the admin password from
    // the librarian and verify it,
    // if the verification is successful then it's going to delete the data from the database

    @FXML
    private TextField mailField;
    @FXML
    private TextField passField;
    public void deleteLib(ActionEvent event){
        System.out.println("reached deletelib");
                String mail = mailField.getText();
                String pass = passField.getText();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin WHERE email ='" + pass + "';");

            while (resultSet.next()) {
                String password = resultSet.getString("pass");

                /* this section is to verify the data with user input */
                System.out.println("test 1");

                if(Objects.equals(pass, password)){
                    if(deleteLibrarianFunction(mail)){
                        System.out.println("Deleted librarian");
                    }
                }
                else {
                    System.out.println("Didn't match Data, try again!");
                }
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }

    // librarian delete function
    public boolean deleteLibrarianFunction( String target){
        System.out.println("test2");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
//            delete from librarian where email = '1'
            String query = "delete from librarian where email = '" + target+ "';";
            System.out.println("delete from librarian where email = '" + target+ "';");
//            System.out.println("insert into memberinfo values('" + name + "'," + id + ",'" + department +"'," + batch +",'" + password +"');" );
            statement.execute(query);
            System.out.println("Librarian Delted");
            return true;
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
            return false;
        }
    }
}
