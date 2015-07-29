package View.Popup;

import BDD.Base;
import BDD.IdConnection;
import View.Dialogs.Dialogs;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by alco on 29/07/2015.
 */
public class PopupBaseView extends Application implements Initializable {

    @FXML
    Label labelStateConnect;
    @FXML
    TextField textFieldURL;
    @FXML
    TextField textFieldUser;
    @FXML
    PasswordField textFieldPassword;
    @FXML
    Circle circleStateConnection;

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        labelStateConnect.setText("Cancel!");
        stage.close();
    }

    @FXML
    private void handleButtonTest(ActionEvent event) {
        Base baseConnection = new Base();
        boolean connection = baseConnection.tryConnection(textFieldURL.getText(), textFieldUser.getText(), textFieldPassword.getText());
        if (connection) {
            labelStateConnect.setText("Connected");
            circleStateConnection.setRadius(8);
            circleStateConnection.setFill(Color.GREEN);
        } else {
            labelStateConnect.setText("Not Connected");
            circleStateConnection.setRadius(8);
            circleStateConnection.setFill(Color.RED);
        }
    }

    @FXML
    private void handleButtonSave(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Base baseConnection = new Base(textFieldURL.getText(), textFieldUser.getText(), textFieldPassword.getText());
            labelStateConnect.setText("Connected");
            circleStateConnection.setRadius(8);
            circleStateConnection.setFill(Color.GREEN);
            stage.close();
        } catch (SQLException SqlE) {
            Dialogs dialogs = new Dialogs(stage);
            dialogs.dialogBadConnectionSql(SqlE);
            labelStateConnect.setText("Not Connected");
            circleStateConnection.setRadius(8);
            circleStateConnection.setFill(Color.RED);
        }
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/PopupBaseConnection.fxml"));
        Scene scene = new Scene(parent);

        String css = "/texture.css";
        scene.getStylesheets().add(css);

        Stage stage = new Stage();
        stage.setMinWidth(300);
        stage.setMinHeight(250);
        stage.setMaxWidth(300);
        stage.setMaxHeight(250);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (IdConnection.getURL() == null) {
            textFieldURL.setText("jdbc:mysql://hostname:port/basename");
        } else {
            textFieldURL.setText(IdConnection.getURL());
            textFieldUser.setText(IdConnection.getUser());
            textFieldPassword.setText(IdConnection.getPassword());
        }
    }
}