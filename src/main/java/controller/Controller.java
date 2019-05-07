package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.Model;

/**
 * Controls the GUI and the business logic.
 */
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
    private void encryptAction(javafx.event.ActionEvent actionEvent) {
        model.encrypt(encPath.getText(), encPass.getText());
    }

    @FXML
    private void decryptAction(javafx.event.ActionEvent actionEvent) {
        model.decrypt(decPath.getText(), decPass.getText());
    }

    @FXML
    private void generateAction(javafx.event.ActionEvent actionEvent) {
        keyField.setText(model.generate());
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
        model = new Model();
    }

}
