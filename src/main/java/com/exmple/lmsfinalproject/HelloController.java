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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML
    private TextField bookidField;
    @FXML
    private TextField bookNameField;
    @FXML
    private TextField bookAuthorField;
    @FXML
    private TextField bookQuantityField;
    @FXML
    private TextField bookLocationField;
    @FXML
    private TableColumn<Book, Number> idCol;
    @FXML
    private TableColumn<Book, Number> quantityCol;
    @FXML
    private TableColumn<Book, String> nameCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> locationCol;
    @FXML
    private TableView<Book> bookTableView;



    ObservableList<Book> bookObservableList;

    public void addBook(ActionEvent event){
        int id= Integer.parseInt(bookidField.getText());
        String name = bookNameField.getText();
        String author = bookAuthorField.getText();
        int quantity = Integer.parseInt(bookQuantityField.getText());
        String location = bookLocationField.getText();

        Book book = new Book(id, name, author, quantity, location);
        bookObservableList.add(book);

        //This line is to put data into database
        insertBook(book);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory( c-> new SimpleIntegerProperty(c.getValue().getId()));
        quantityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
        nameCol.setCellValueFactory( c-> new SimpleStringProperty(c.getValue().getName()));
        authorCol.setCellValueFactory( c-> new SimpleStringProperty(c.getValue().getAuthor()));
        locationCol.setCellValueFactory( c-> new SimpleStringProperty(c.getValue().getLocation()));

        bookObservableList = FXCollections.observableArrayList();
        bookTableView.setItems(bookObservableList);
    }


    // Section to insert data (book management section) into database
    // refer to practise jan 4-24 for any doubt

    public void insertBook (Book book){
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
        System.out.println("Connection established");
        Statement statement = connection.createStatement();
        String query = "insert into bookmanagement values(" + book.getId() + ",'" + book.getName() + "','" + book.getAuthor() + "'," + book.getQuantity() + ",'" + book.getLocation() + "');" ;
        statement.execute(query);
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }

    // This section is to delete books
    // the command to delete is 'delete from bookmanagement where id = 1;'
    // delete from table where id = 1;
    @FXML
    private Label outputLabel;
    @FXML
    private TextField idFieldDelete;
    @FXML
    private Button deleteButton;
    public void setDeleteButton(ActionEvent event){
        int target = Integer.parseInt(idFieldDelete.getText());
        deleteBook(target);
        System.out.println(target);
    }

    public void deleteBook (int id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "delete from bookmanagement where id =" + id +";";
            statement.execute(query);
            System.out.println("Deleted book");
            outputLabel.setText("Book deleted with id "+ id);

        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }

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
            System.out.println("insert into memberinfo values('" + name + "'," + id + ",'" + department +"'," + batch +",'" + password +"');" );
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
    public void onLibraianLogin(ActionEvent event){  // librarian login trigger button
        String mail = employeeID.getText();
        String password = emppassword.getText();
        System.out.println("test" + mail + " " + password);

        /* Write code here to get employee email and password from database
        and verify it
        if passed change the scene to librarian scene
         */
        HelloApplication.changeScene("librarian");  // passing it into change scene section


    }

    // this section will take the user into dashboard section
    public void gotoDashboard(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }


    // will take the librarian into book management section
    public void gotoBookManagement(ActionEvent event){
        HelloApplication.changeScene("bookmanagementsection");
    }

}