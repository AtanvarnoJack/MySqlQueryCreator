package View.Home;

import View.Dialogs.Dialogs;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import jxl.read.biff.BiffException;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by alco on 06/07/2015.3
 * View main for view HomePage/fxml
 */
public class HomeView extends Application implements Initializable {
    @FXML
    Label labelOutput;
    @FXML
    Label infoOutput;
    @FXML
    TextArea textAreaMysqlVersion;
    @FXML
    MenuBar menuBarView;

    @FXML
    MenuItem exitButton;

    private Dialogs dialogs;
    private Home home;
    private Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        home = new Home(stage);
        dialogs = new Dialogs(stage);
        home.initStage(stage);
        mainStage = stage;
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
                        dialogs.dialogBadNumber();
                    }
                    home.setMySqlVersion(parseDouble);
                } catch (NumberFormatException NFE) {
                    dialogs.dialogBadNumber();
                    labelOutput.setText("Please choose a valid mysql version number! \n\n" + NFE + "\n\nSee Help! (Button '?')");
                }
            }
        });
    }

    @FXML
    private void handleButtonActionOpenFile(ActionEvent event) throws IOException {
        dialogs = new Dialogs(mainStage);
        home = new Home(mainStage);
        // infoOutput.setText("Request!");

        File file = home.getExcelFile(mainStage);
        if (file != null) {
            try {
                String allRequest = home.procedureCreateRequest(file);
                labelOutput.setText(allRequest);
                infoOutput.setText("End! Find your request below!");

                // System.out.println("Request = " + allRequest);
            } catch (IllegalArgumentException IAE) {
                infoOutput.setText("Request aborted!");
                dialogs.dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \n\n" + IAE + "\n\nSee Help! (Button '?')");
            } catch (BiffException e) {
                infoOutput.setText("Request aborted!");
                dialogs.dialogBadFileFormat();
                labelOutput.setText("Please select a file with a type format for SqlCreator! \nSee Help! (Button '?')");
            }
        }
    }

    @FXML
    public void handleButtonActionExport(ActionEvent actionEvent) {
        dialogs = new Dialogs(mainStage);
        home = new Home(mainStage);
        try {
            home.export(labelOutput.getText());
            infoOutput.setText("Text has been exported in the file you specified!");
        } catch (IllegalArgumentException IAE) {
            dialogs.dialogNoData();
            infoOutput.setText("There is nothing to export...");
        }
    }

    @FXML
    private void handleButtonActionHelp(ActionEvent actionEvent) throws IOException {
        home = new Home(mainStage);
        infoOutput.setText("Help:");
        labelOutput.setText(home.getHelp());
    }

    @FXML
    private void handleButtonCopy(ActionEvent actionEvent) throws IOException {
        dialogs = new Dialogs(mainStage);
        home = new Home(mainStage);
        try {
            home.copyToClipboard(labelOutput.getText());
            infoOutput.setText("Text has been saved in clipboard!");
        } catch (IllegalArgumentException IAE) {
            dialogs.dialogNoData();
            infoOutput.setText("There is nothing to copy...");
        }
    }

    @FXML
    public void handleButtonCut(ActionEvent actionEvent) {
        dialogs = new Dialogs(mainStage);
        home = new Home(mainStage);
        try {
            home.copyToClipboard(labelOutput.getText());
            labelOutput.setText("");
            infoOutput.setText("Text has been saved in clipboard!");
        } catch (IllegalArgumentException IAE) {
            dialogs.dialogNoData();
            infoOutput.setText("There is nothing to copy...");
        }
    }

    @FXML
    public void handleButtonPast(ActionEvent actionEvent) {
        home = new Home(mainStage);
        try {
            labelOutput.setText(home.pastToClipboard());
            infoOutput.setText("Text has been Past!");
        } catch (UnsupportedFlavorException e) {
            dialogs.dialogBadFileFormat();
            infoOutput.setText("There is nothing to Past...");
        } catch (IOException e) {
            dialogs.dialogNoData();
            infoOutput.setText("There is nothing to Past...");
        }

    }

    @FXML
    private void handleButtonBaseConnectionMenu(ActionEvent event) {
        home = new Home(mainStage);
        try {
            home.showPopupBaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonVersion(ActionEvent actionEvent) {
        //TODO display version
    }
}
