package com.exmple.lmsfinalproject;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.nio.file.attribute.AttributeView;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class LibrarianController implements Initializable {
    AdminController adminController = new AdminController();
    int fines_per_day = adminController.setFineAmount();

    public int getFines_per_day() {
        return fines_per_day;
    }

    public void setFines_per_day(int fines_per_day) {
        this.fines_per_day = fines_per_day;
    }

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
    @FXML
    private Label fineShow;

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

    public long daysBetweenInclusive(LocalDate ld1, LocalDate ld2) {
        return Math.abs(ChronoUnit.DAYS.between(ld1, ld2)) + 1;
    }

    // Fines calculator function
    public long finesCalculator(int id){
        long fines = 0;

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM issuebook WHERE id ='" + id + "';");
            while (resultSet.next()) {
                String localdateStr = resultSet.getString("returndate");


                // converting string date to localtime
                LocalDate localDate1 = LocalDate.now();
                LocalDate localDate = LocalDate.parse(localdateStr);  // localdate converter

                // to find days in between
                 long finesDay = DAYS.between(localDate1,localDate) ;
                System.out.println(finesDay);

                // to check for past dates only
                if(localDate1.isAfter(localDate)){
                    fines = fines_per_day*finesDay*(-1);
                }
                else {
                    fines = 0;
                }


                System.out.println("FINE AMMount " + fines);

            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

        return fines;
    }


    // view fines and receive book section

    @FXML
    private TextField sID;
    int fineID;
    public void viewFines(ActionEvent event) {
        int id = Integer.parseInt(sID.getText());
        fineID = id;

        // in case the student doesn't exist
        fineShow.setText("Couldn't found any match with target");
        long tk = finesCalculator(id); // fine calculator call
        fineShow.setText("Fine amount: " + String.valueOf(tk) + " TK");  // to show fine ammount in label

    }


    //receive book after collecting fines

    @FXML private Label l;
    @FXML
    public void receivedBook(ActionEvent event){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "delete from issuebook where id = " + fineID + ";";
            System.out.println(query);
            statement.execute(query);
//            System.out.println("delete from librarian where email = '" + target+ "';");

            l.setText("Collected book from " + fineID);
        }
        catch (SQLException e){
            l.setText("Something went wrong");
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }

    }


    // From here this one is to extend days
    @FXML
    private TextField idField;
    @FXML
    private TextField dayfield;
    public void extendFines(ActionEvent event){
        int id = Integer.parseInt(idField.getText());
        int days = Integer.parseInt(dayfield.getText());

//        update issuebook set fines = 2 where bookid =5
        // to get the return date
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM issuebook WHERE id ='" + id + "';");
            while (resultSet.next()) {
                String localdateStr = resultSet.getString("returndate");
                System.out.println("Current date " + localdateStr);


                // converting string date to localtime
                LocalDate localDate = LocalDate.parse(localdateStr);  // String to localdate converter
                System.out.println("After parsing " + localDate);
                LocalDate local = localDate.plusDays(days);
                System.out.println("After adding " + local);
                String target = local.toString();
                System.out.println("Before passing it into section "+ target);
                extendDate(id, target);
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

    }
    public void extendDate(int id, String localdate){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            String query = "update issuebook set returndate ='" + localdate +"' where id =" + id  + ";";
            System.out.println(query);
            statement.execute(query);
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
            e.printStackTrace();
        }
    }

    /// this is to load person ids who sent messages
    // and show the librarian
    @FXML
    private TextArea textField;

    String str = "";
    public void onLoadClick(ActionEvent event){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM note;");
            while (resultSet.next()) {
                str = str + resultSet.getString("id");
                str = str + ", ";
                System.out.println(str);
            }
            textField.setText("You have unread messages from " + str);
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }
    }

    // this is to show the messages to the librarian, librarian will search by id
    @FXML
    private TextField ids;
    public void showNOTE(ActionEvent event){
        int id = Integer.parseInt(ids.getText());
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            System.out.println("Connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM note WHERE id ='" + id + "';");
            while (resultSet.next()) {
                String note = resultSet.getString("note");
                str = "";
                textField.setText("Message from " + id + "\n" + note);
                deleteNote(id);
            }
        }
        catch (SQLException e){
            System.out.println("SQL exception occured");
        }

    }

    // this section is to delete notes after the librarian reads it
    // this function will take an int value which is id, and delete it from the database

 public void deleteNote ( int id){
     try{
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
         System.out.println("Connection established");
         Statement statement = connection.createStatement();
         String query = " delete  from note where id = " + id + ";";
         System.out.println(query);
         System.out.println("note deleted with id " + id);
         statement.execute(query);
     }
     catch (SQLException e){
         System.out.println("SQL exception occured");
         e.printStackTrace();
     }
 }


    @FXML
    private TableColumn<Lent, Number> idCol;
    @FXML
    private TableColumn<Lent, String> studentCol;
    @FXML
    private TableColumn<Lent, String> bookNameCol;
    @FXML
    private TableColumn<Lent, Number> bookidCol;
    @FXML
    private TableColumn<Lent, String> lendDateCol;
    @FXML
    private TableColumn<Lent, String> returnDateCol;
    @FXML
    private TableColumn<Lent, Number> finesCol;
    @FXML
    private TableView<Lent> tableView;


    ObservableList<Lent> lentObservableList;
    // this is to show who lent books in a table
    // view issued book section
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Lent> lentList = list();
        idCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getStudentID()));
        studentCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        returnDateCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getReturnDate()));
        lendDateCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getLendDate()));
        finesCol.setCellValueFactory(c-> new SimpleLongProperty(c.getValue().getFines()));
        bookidCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getBookid()));
        bookNameCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getBookName()));

        lentObservableList = FXCollections.observableArrayList();
        lentObservableList.addAll(lentList);
        tableView.setItems(lentObservableList);
    }

    // to get data from the Database (above section)

    private List<Lent> list() {
        List <Lent> lentList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms", "root", "password");
            Statement statement = connection.createStatement();
            String query ="Select * from issuebook";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("studentName");
                String bookName = resultSet.getString("bookname");
                int bookid = resultSet.getInt("bookid");
                String lendDate = resultSet.getString("lenddate");
                String returnDate = resultSet.getString("returndate");
                long fines = finesCalculator(id);

                Lent lent = new Lent(id, studentName, bookName, bookid, lendDate, returnDate, fines);
                lentList.add(lent);
            }
        }
        catch (SQLException exception){
            System.out.println("Failed to get data from database");
        }
        return lentList;
    }
}
