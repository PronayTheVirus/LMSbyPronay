package com.exmple.lmsfinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public Label bookLabel;


    // Dashboard create account section and inserting those data into database
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentID;
    @FXML
    private TextField studentDepartment;
    @FXML
    private TextField studentBatch;
    @FXML
    private TextField studentPassword;
    @FXML
    private Button createAccountButton;

    public void onCreateAccountClick(ActionEvent event){
        String name = studentName.getText();
        long id = Integer.parseInt(studentID.getText());
        String department = studentDepartment.getText();
        int batch = Integer.parseInt(studentBatch.getText());
        String password = studentPassword.getText();

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "insert into memberinfo values('" + name + "'," + id + ",'" + department +"'," + batch +",'" + password +"');" ;
//            System.out.println("insert into memberinfo values('" + name + "'," + id + ",'" + department +"'," + batch +",'" + password +"');" );
            statement.execute(query);
            System.out.println("DATA INSERTED");
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }


    //Dashboard librarian login section
    // this will take librarian data from a librarian and verify them
    // upon verified it will redirect them to the librarian view
    @FXML
    private TextField employeeID;
    @FXML
    private TextField emppassword;
    @FXML
    private Label buildLog;

    @FXML
    public void onLibraianLogin(ActionEvent event){  // librarian login trigger button
        String mail = employeeID.getText();
        String password = emppassword.getText();
        System.out.println("test" + mail + " " + password);

        // this section is to retrive a specific colum form the database
        // such as getting the password based on email or else
        // in this case we are getting data from librarian
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM librarian WHERE email ='" + mail + "';");
            while (resultSet.next()) {
                String id = resultSet.getString("loginpass");

                /* this section is to verify the data with user input */

                if(Objects.equals(id, password)){
                    System.out.println("Verified librarian");
//                    bookLabel.setText("TEST");
                    HelloApplication.changeScene("librarian");  // passing it into change scene section
                }
                else {
                    System.out.println("Didn't match");
                    buildLog.setText("Password didn't match! TRY AGAIN");
                }
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

    }

    // this section will take the user into dashboard section
    public void gotoDashboard(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }


    // will take the librarian into book management section
    public void gotoBookManagement(ActionEvent event){
        HelloApplication.changeScene("bookmanagementsection");
    }
    // safe delete it, check it before doing so


//    // delete this section right here till ===========
//    public void addBook(ActionEvent event) {
//    }
//    public void setDeleteButton(ActionEvent event){
//
//    }
////    ======================================= till here


    /* student login section
    After verifying it will send the user to student section
     */
    @FXML
    private TextField studentIDfield;
    @FXML
    private TextField passSTDfield;
    public void isStudent(ActionEvent event){
        int stdId = Integer.parseInt(studentIDfield.getText());
        String stdPass = passSTDfield.getText();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM memberinfo WHERE id ='" + stdId + "';");
            while (resultSet.next()) {
                String id = resultSet.getString("loginpassword");

                /* this section is to verify the data with user input */
                if(Objects.equals(id, stdPass)){
                    System.out.println("Verified Student");

                    HelloApplication.changeScene("isStudent");
                    StudentController.passID(stdId);  ;// passing it into change scene section
                }
                else {
                    System.out.println("Didn't match");
                    buildLog.setText("client: student, Password didn't match! TRY AGAIN");
                }
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }


    }

// to view book count upon start of the project
    @FXML private Label memberLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) as id from bookmanagement;");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                bookLabel.setText(id + " Books");

            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }



        // this section is to show membercount upon starting
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) as id from memberinfo;");

            while (resultSet.next()) {
                String id = resultSet.getString("id");

                memberLabel.setText(id + " members");
                System.out.println(id);

            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

    }

    /// admin login section part final
    @FXML
    TextField adminuserF;
    @FXML
    TextField adminpassF;
    public void adminLogin(ActionEvent event){

        String mail = adminuserF.getText();
        String pass  = adminpassF.getText();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin WHERE email ='" + mail + "';");
            while (resultSet.next()) {
                String adpass = resultSet.getString("pass");

                /* this section is to verify the data with user input */
                if(Objects.equals(adpass, pass)){
                    System.out.println("Verified admin");

                    // passing admin mail for future use
                    AdminController.SET_MAIL(mail);
                    HelloApplication.changeScene("admin");
                }
                else {
                    System.out.println("Didn't match");
                }
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }


    }


}

