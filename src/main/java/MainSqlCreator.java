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
 * Created by alco on 06/07/2015.3
 * View main for view HomePage/fxml
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
        // infoOutput.setText("Request!");

        File file = mainView.getExcelFile(stage);
        if (file != null) {
            try {
                String allRequest = mainView.procedureCreateRequest(file);
                labelOutput.setText(allRequest);
                infoOutput.setText("End! Find your request below!");

                // System.out.println("Request = " + allRequest);
            }catch (IllegalArgumentException IAE){
                infoOutput.setText("Request aborted!");
                mainView.getDialogs().dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \n\n" + IAE + "\n\nSee Help! (Button '?')");
            }catch (BiffException e) {
                infoOutput.setText("Request aborted!");
                mainView.getDialogs().dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \nSee Help! (Button '?')");
            }
        }
    }

    @FXML
    public void handleButtonActionExport(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainView = new MainView(stage);
        try {
            mainView.export(labelOutput.getText());
            infoOutput.setText("Text has been exported in the file you specified!");
        } catch (IllegalArgumentException IAE) {
            mainView.getDialogs().dialogNoData();
            infoOutput.setText("There is nothing to export...");
        }
    }

    @FXML
    private void handleButtonActionHelp(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainView = new MainView(stage);
        infoOutput.setText("Help:");
        labelOutput.setText(mainView.getHelp());
    }

    @FXML
    private void handleButtonCopy(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainView = new MainView(stage);
        try {
            mainView.copyToClipboard(labelOutput.getText());
            infoOutput.setText("Text has been saved in clipboard!");
        } catch (IllegalArgumentException IAE) {
            mainView.getDialogs().dialogNoData();
            infoOutput.setText("There is nothing to copy...");
        }

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
                    labelOutput.setText("Please choose a valid mysql version number! \n\n" + NFE + "\n\nSee Help! (Button '?')");
                }
            }
        });
    }
}
