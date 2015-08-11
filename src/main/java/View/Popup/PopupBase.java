package View.Popup;

import Base.Connection.BaseConnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by alco on 29/07/2015.
 */
public class PopupBase {
    protected void initPopup() throws IOException {
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

    protected boolean btnTestConnection(String url, String user, String password) {
        BaseConnect baseConnectConnection = new BaseConnect();
        return baseConnectConnection.tryConnection(url, user, password);
    }

    protected void btnSaveConnection(String url, String user, String password) throws SQLException {
        BaseConnect baseConnectConnection = new BaseConnect(url, user, password);
    }
}
