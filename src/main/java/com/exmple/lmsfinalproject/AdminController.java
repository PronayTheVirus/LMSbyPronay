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
    static String mail;

    public static void SET_MAIL(String smail) {
            mail =smail;
    }
    @FXML
    private Label output;

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

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "delete from librarian where email = '" + target+ "';";
            System.out.println("delete from librarian where email = '" + target+ "';");
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


    // Database wipe out function
    @FXML
    private TextField pasfield;

    public void admpass(ActionEvent event){
        String pass = pasfield.getText();
            try{
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM admin WHERE email ='" + mail + "';");

                while (resultSet.next()) {
                    String password = resultSet.getString("pass");

                    output.setText("Didn't match Data, try again!");
                    // This section right here will verify user input
                    if(Objects.equals(pass, password)){
                        wipeOutTheDatabase();
                    }
                    else {
                        output.setText("Didn't match Data, try again!");
                    }
                }
            }
            catch (SQLException e){
                System.out.println("SQL exception occured");
            }
        }




    // This function right here is to wipe out the full database
    public void wipeOutTheDatabase(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "delete from librarian;";
            statement.execute(query);
            query = "delete from bookmanagement;";
            statement.execute(query);
            query = "delete from memberinfo;";
            statement.execute(query);
            query = "delete from requestBook;";
            statement.execute(query);
            query = "delete from lendreq;";
            statement.execute(query);
//            System.out.println("delete from librarian where email = '" + target+ "';");

            output.setText("Wiped out everything from the database.");
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }

    // section to change password of admin
    // after verifying , it's going to wipe out and rewrite the DB
    @FXML
    private TextField oldPassField;
    @FXML
    private TextField newPassField;
    public void setNewPass(ActionEvent event){
        String oldPass = oldPassField.getText();
        String newPass = newPassField.getText();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin WHERE email ='" + mail + "';");

            while (resultSet.next()) {
                String password = resultSet.getString("pass");

                output.setText("Didn't match Data, try again!");
                // This section right here will verify user input
                if(Objects.equals(oldPass, password)){
                    adminRewrite(mail,newPass);
                    output.setText("Password changed");
                }
                else {
                    output.setText("Didn't match Data, try again!");
                }
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

    }

    // this function is going to rewrite data into DB and wipe upon calling
    public void adminRewrite(String mail, String pass){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "delete from admin";
            statement.execute(query);
            query = "insert into admin values('" + mail + "','" + pass +"');";
            statement.execute(query);
//            System.out.println("delete from librarian where email = '" + target+ "';");

            output.setText("Password changed successfully");
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }

    public void gotoDashboard(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }

}
