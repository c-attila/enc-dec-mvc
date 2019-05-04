package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.Model;

public class Controller implements Initializable {

    private Model model;

    @FXML
    private TextField encPath;

    @FXML
    private TextField decPath;

    @FXML
    private PasswordField encPass;

    @FXML
    private PasswordField decPass;

    @FXML
    private TextArea keyField;

    @FXML
    private void encryptAction(ActionEvent event) {

    }

    @FXML
    private void decryptAction(ActionEvent event) {

    }

    @FXML
    private void generateAction(ActionEvent event) {

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}