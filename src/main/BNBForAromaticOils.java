/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static assets.classes.statics.*;
import com.sun.javafx.application.LauncherImpl;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author AHMED
 */
public class BNBForAromaticOils extends Application {

    Preferences prefs;
    int state = 1;

    @Override
    public void start(Stage stage) throws Exception {
        if (state == 1) {
            prefs = Preferences.userNodeForPackage(BNBForAromaticOils.class);
            String propertyValue = prefs.get(THEME, DEFAULT_THEME);
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            Scene scene = new Scene(root);

            root.getStylesheets().add(getClass().getResource("/assets/styles/" + propertyValue + ".css").toExternalForm());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            stage.setTitle("BNB For Aromatic Oils");
            stage.setScene(scene);
            stage.toFront();
            stage.show();
        }
    }

    @Override
    public void init() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(BNBForAromaticOils.class);
        String get = prefs.get(CONFIG, CONFIG_DEFAULT);
        if (get.equals("no")) {
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(100));
            state = 0;
        } else {
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(0));

            Thread.sleep(100);
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(2));
            String get1 = prefs.get(DATABASE_TYPE, DATABASE_TYPE_DEFAULT);
            if (get1.equals(DATABASE_TYPE_DEFAULT)) {
                if (db.get.canCon()) {
                    LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(3));
                } else {
                    LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(13));

                }
            } else {
                if (db.lite.setConnection()) {
                    LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(3));
                } else {
                    
                    LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(33));
                }
            }

        }
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(BNBForAromaticOils.class, SplashScreenLoader.class, args);
    }

}
