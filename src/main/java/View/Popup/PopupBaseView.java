package View.Popup;

import BDD.IdConnection;
import View.Dialogs.Dialogs;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    PopupBase popupBase;

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
        popupBase = new PopupBase();
        boolean connection;
        connection = popupBase.btnTestConnection(textFieldURL.getText(), textFieldUser.getText(), textFieldPassword.getText());
        if (connection) {
            labelStateConnect.setText("Connected");
            circleStateConnection.setFill(Color.GREEN);
        } else {
            labelStateConnect.setText("Not Connected");
            circleStateConnection.setFill(Color.RED);
        }
    }

    @FXML
    private void handleButtonSave(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupBase = new PopupBase();
        try {
            popupBase.btnSaveConnection(textFieldURL.getText(), textFieldUser.getText(), textFieldPassword.getText());
            labelStateConnect.setText("Connected");
            circleStateConnection.setFill(Color.GREEN);
            stage.close();
        } catch (SQLException SqlE) {
            Dialogs dialogs = new Dialogs(stage);
            dialogs.dialogBadConnectionSql(SqlE);
            labelStateConnect.setText("Not Connected");
            circleStateConnection.setFill(Color.RED);
        }
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        popupBase = new PopupBase();
        popupBase.initPopup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (IdConnection.getURL() == null) {
            textFieldURL.setText("jdbc:mysql://hostname:port/basename");
            circleStateConnection.setFill(Color.ORANGE);
        } else {
            circleStateConnection.setFill(Color.GREEN);
            textFieldURL.setText(IdConnection.getURL());
            textFieldUser.setText(IdConnection.getUser());
            textFieldPassword.setText(IdConnection.getPassword());
        }
    }
}