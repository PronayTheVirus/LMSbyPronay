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

public class BookManagementController implements Initializable {
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

        /*From this line the below codes would be
        to put data into database*/
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
    public void gotoDashboard(ActionEvent event){
        HelloApplication.changeScene("dashboard");
    }



    // Write code here to update book data
}
