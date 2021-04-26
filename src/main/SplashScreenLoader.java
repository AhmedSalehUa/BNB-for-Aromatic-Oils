/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*; 
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author AHMED
 */
public class SplashScreenLoader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;
    @FXML
    private ProgressBar prog;
    @FXML
    private Label num;
    Preferences prefs;

    public SplashScreenLoader() {

    }

    @Override
    public void init() throws Exception {

        Parent root1 = FXMLLoader.load(getClass().getResource("splash.fxml"));
        scene = new Scene(root1);
        prefs = Preferences.userNodeForPackage(BNBForAromaticOils.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.preloaderStage = primaryStage;

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            String massege = "";
            double progress = ((ProgressNotification) info).getProgress();
            if (progress == 0) {
                massege = "connecting to local db";
            } else if (progress == 1) {
                massege = "successful";
            } else if (progress == 11) {
                massege = "faild";
                AlertDialogs.showError("failed to connect to local db");

            } else if (progress == 2) {
                massege = "connecting to local serever db";

            } else if (progress == 3) {
                massege = "successful";
            } else if (progress == 13) {
                massege = "faild";
                AlertDialogs.showError("failed to connect to serever db");
                TextInputDialog dialog = new TextInputDialog(prefs.get(DATABASE_IP, DEFAULT_DATABASE_IP));
                dialog.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/icons/dns.png"))));
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/assets/icons/logo.png").toString()));
                dialog.setTitle("Set Database Ip");
                dialog.setContentText("Please Enter Database IP:");
                dialog.setHeaderText("Database IP");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(ip -> prefs.put(DATABASE_IP, ip));

            } else if (progress == 33) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("no data base in " + prefs.get(DATABASE_LOCATION, DATABASE_LOCATION_DEFAULT));
                alert.showAndWait();
                System.exit(0);
            } else if (progress == 4) {
                massege = "check for update";
            } else if (progress == 5) {
                massege = "successful";
            } else if (progress == 100) {
                try {
                    Parent login = FXMLLoader.load(getClass().getResource("/screens/settings/Settings.fxml"));
                    System.err.println(prefs.get(THEME, DEFAULT_THEME));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                    Stage st = new Stage();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("BNB For Aromatic Oils");

                    double width = 350;
                    double height = 530;

                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    st.setX((screenBounds.getWidth() - width) / 2);
                    st.setY((screenBounds.getHeight() - height) / 2);

                    Scene scene = new Scene(login, width, height);
                    st.setScene(scene);
                    st.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
//            SplashScreenController.label.setText(massege);
        }

    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {

            case BEFORE_START:

                System.out.println("BEFORE_START");
                preloaderStage.hide();
                break;
        }

    }

}
