package com.exmple.lmsfinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
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

}
