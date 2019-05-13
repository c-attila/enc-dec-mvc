package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DAO.OperationDAO;

public class Main extends Application {

    private static Stage pStage;

    /**
     * Starts the user interface.
     *
     * @param primaryStage the primary stage of the interface
     * @throws Exception
     */
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

    /**
     * Shows a popup window with the given text.
     *
     * @param text the given text to be shown in the window.
     */
    public static void showPopupWindow(String text) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getPrimaryStage());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(text));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
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
