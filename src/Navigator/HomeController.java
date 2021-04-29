package Navigator;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import db.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.BNBForAromaticOils;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button Accounts;
    @FXML
    private Button mainData;
    @FXML
    private Button Store;

    Preferences prefs;
    @FXML
    private MenuButton userNode;
    @FXML
    private Label userName;
    @FXML
    private Button Hr;
    @FXML
    private Button Invoice;
    @FXML
    private Button Sales;
    @FXML
    private ImageView bnbLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(BNBForAromaticOils.class);
        new ZoomInLeft(Hr).play();
        new ZoomInRight(mainData).play();
        new ZoomInLeft(Accounts).play();
        new ZoomInRight(Invoice).play();
        new ZoomInLeft(Store).play();
        new ZoomInRight(Sales).play();
        userName.setText(prefs.get(USER_NAME, USERNAME_DEFAULT).toUpperCase());
        MenuItem logout = new MenuItem("Log Out");
        MenuItem changePassword = new MenuItem("change password");
        MenuItem settings = new MenuItem("settings");
        userNode.getItems().add(logout);
        userNode.getItems().add(changePassword);
        userNode.getItems().add(settings);

        try {
             bnbLogo.setImage(new Image(getClass().getResourceAsStream("/assets/icons/logo2.png")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //bnbLogo
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Parent login = FXMLLoader.load(getClass().getResource(Login));
                    System.err.println(prefs.get(THEME, DEFAULT_THEME));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                    Stage st = (Stage) mainData.getScene().getWindow();
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert dialod = new Alert(AlertType.ERROR);
                    dialod.setContentText(ex.getMessage());
                    dialod.show();
                }
            }
        });

        changePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Change Password");
                dialog.setHeaderText("You Are Abouy Changing Your Password");

                dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/icons8_password_200px_3.png").toString()));

                ButtonType loginButtonType = new ButtonType("Change", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                PasswordField username = new PasswordField();
                username.setPromptText("Password");
                PasswordField password = new PasswordField();
                password.setPromptText("Confirm Password");

                grid.add(new Label("Password:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Confirm Password:"), 0, 1);
                grid.add(password, 1, 1);

                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

                password.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(!password.getText().equals(username.getText()));
                });
                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(!password.getText().equals(username.getText()));
                });

                dialog.getDialogPane().setContent(grid);

                Platform.runLater(() -> username.requestFocus());

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(username.getText(), password.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    User us = new User();
                    us.setId(Integer.parseInt(prefs.get(USER_ID, "0")));
                    us.setPassword(usernamePassword.getKey());

                    if (us.changePassword()) {
                        AlertDialogs.showmessage("تم");
                    } else {
                        AlertDialogs.showError("حدث خطا");
                    }
                });
            }
        });

        Hr.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            //Background work                       
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(() -> {
                                try {
                                    try {
                                        Parent mainData = FXMLLoader.load(getClass().getResource(HrScreen));
                                        mainData.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
                                        Scene sc = new Scene(mainData);
                                        Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
                                        st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                                        st.setTitle("BNB For Aromatic Oils (شؤون الموظفين)");
                                        st.setScene(sc);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                        AlertDialogs.showErrors(ex);
                                    }
                                } finally {
                                    latch.countDown();
                                }
                            });
                            latch.await();

                            return null;
                        }
                    };
                }

                @Override
                protected void succeeded() {

                    super.succeeded();
                }
            };
            service.start();

        });

        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Parent login = FXMLLoader.load(getClass().getResource("/screens/settings/Settings.fxml"));
                    System.err.println(prefs.get(THEME, DEFAULT_THEME));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

                    Stage st = (Stage) mainData.getScene().getWindow();
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

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert dialod = new Alert(AlertType.ERROR);
                    dialod.setContentText(ex.getMessage());
                    dialod.show();
                }
            }
        });

    }

}
