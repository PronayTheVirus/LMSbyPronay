package com.exmple.lmsfinalproject;

import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.xml.stream.Location;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private Label buildLog1;

    @FXML
    private TableColumn<Book, Number> idColum;

    @FXML
    private TableColumn<Book, String> locationCol;

    @FXML
    private TableColumn<Book, String> nameCol;

    @FXML
    private TableColumn<Book, Number> quantityCol;
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TextField search;
    ObservableList<Book> bookObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Book> bookList = list();

        idColum.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
        nameCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        authorCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getAuthor()));
        quantityCol.setCellValueFactory(c-> new SimpleDoubleProperty(c.getValue().getQuantity()));
        locationCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getLocation()));

        bookObservableList = FXCollections.observableArrayList();
        bookObservableList.addAll(bookList);
        bookTableView.setItems(bookObservableList);


    }

    // this is to show all the books by default
    private List<Book> list() {
        List <Book> bookList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            String query ="Select * from bookmanagement";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               String author = resultSet.getString("author");
               int quantity = resultSet.getInt("quantity");
               String location = resultSet.getString("location");

                Book book = new Book(id, name, author, quantity, location);
                bookList.add(book);
            }
        }
        catch (SQLException exception){
            System.out.println("Failed to get data from database");
        }
        return bookList;
    }


    // this is to insert book into the table

}
