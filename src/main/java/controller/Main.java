package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DAO.Operation;
import model.DAO.OperationDAO;

public class Main extends Application {

    private static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/JFX/scene.fxml"));
        primaryStage.setTitle("Encrypt-Decrypt");
        primaryStage.setScene(new Scene(root, 1280, 800));
        Platform.runLater(root::requestFocus);
        primaryStage.setOnCloseRequest(e -> OperationDAO.close());
        primaryStage.show();
        pStage = primaryStage;
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    public static void setPrimaryStage(Stage pStage) {
        Main.pStage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
