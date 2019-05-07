package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DAO.Operation;
import model.DAO.OperationDAO;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/JFX/scene.fxml"));
        primaryStage.setTitle("Encrypt-Decrypt");
        primaryStage.setScene(new Scene(root, 1280, 800));
        Platform.runLater(root::requestFocus);
        primaryStage.setOnCloseRequest(e -> OperationDAO.close());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
