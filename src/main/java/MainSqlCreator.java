import View.MainView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by alco on 06/07/2015.
 */
public class MainSqlCreator extends Application implements Initializable {
    MainView mainView;

    @FXML
    Label labelOutput;
    @FXML
    Label infoOutput;
    @FXML
    TextArea textAreaMysqlVersion;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainView = new MainView(stage);
        mainView.initStage(stage);
    }

    @FXML
    private void handleButtonActionOpenFile(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainView = new MainView(stage);
        infoOutput.setText("Request!");

        File file = mainView.getExcelFile(stage);
        if (file != null) {
            try {
                String allRequest = mainView.procedureCreateRequest(file);
                labelOutput.setText(allRequest);
                infoOutput.setText("End! File Request.sql created on desktop!");

                System.out.println("Request = " + allRequest);
            }catch (IllegalArgumentException IAE){
                infoOutput.setText("Request aborded!");
                mainView.getDialogs().dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \nSee Help! (Button '?')");
            }catch (BiffException e) {
                infoOutput.setText("Request abord!");
                mainView.getDialogs().dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \nSee Help! (Button '?')");
            }
        }
    }

    @FXML
    public void handleButtonActionExport(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainView = new MainView(stage);
        mainView.export(labelOutput.getText());
        infoOutput.setText("File Exported!");

    }

    @FXML
    private void handleButtonActionHelp(ActionEvent event) throws IOException {
        infoOutput.setText("Help:");
        labelOutput.setText(mainView.getHelp());
    }

    @FXML
    private void handleButtonCopy(ActionEvent event) throws IOException {
        infoOutput.setText("Text has been saved in clipboard!");
        mainView.copyToClipboard(labelOutput.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreaMysqlVersion.setText("5.6");
        textAreaMysqlVersion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    double parseDouble = Double.parseDouble(newValue);
                    if (parseDouble > 6.0 || parseDouble <= 0) {
                        mainView.getDialogs().dialogBadNumber();
                    }
                    mainView.setMySqlVersion(parseDouble);
                } catch (NumberFormatException NFE) {
                    mainView.getDialogs().dialogBadNumber();
                }
            }
        });
    }
}
