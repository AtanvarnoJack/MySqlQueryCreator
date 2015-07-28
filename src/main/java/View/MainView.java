package View;

import Excel.Loader.LoaderKeywords;
import FileOut.FileSql;
import Request.MainRequest;
import Request.MySQL.MySqlRequest;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jxl.read.biff.BiffException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alco on 27/07/2015.
 * MainView is a class who contain all background method of the view.
 */
public class MainView {
    //App Constant
    private final static String APP_TITLE = "MySql Query Creator";
    private final static String ICON_SQL_CREATOR_PATH = "file:img/IconSqlCreator.png";
    private final static String HELP = "Hello!\n" +
            "You need to load a \".xls\" file that contains 4 columns named:\n" +
            "\"type\": Must contain \"Trigger\". Other types in development.\n" +
            "\"table\": Contains the name of your table in database.\n" +
            "\"champs\": Contains the name of the rows in your table.\n" +
            "\"condition\": Contains the condition for the trigger.\n" +
            "\n" +
            "Example:\n" +
            "type\t\ttable\t\t\tchamps\t\t\t\t\tcondition\n" +
            "Trigger\t\tPERSON\t\ttoto \t'Y' or 'N'\n" +
            "Trigger\t\tPERSON\t\ttata \t\t'V1' or 'V2' or 'V3'\n" +
            "Trigger\t\tPERSON\t\ttiti \t\t'Low' or 'Medium' or 'High'\n" +
            "\n" +
            // "The output are create in a SQL file named \"TriggerBase.sql\" on the Desktop!\n" +
            "\n" +
            "See the example \"ExempleFile.xls\" in application repository.\n" +
            "Use the template \"EmptyFile.xls\"  in application repository to create your own request.";
    //Variables:
    private static double mySqlVersion = 5.6;
    private Stage stage;
    private Dialogs dialogs;

    public MainView(Stage stage) {
        dialogs = new Dialogs(stage);
        this.stage = stage;
    }

    public static String getAppTitle() {
        return APP_TITLE;
    }

    public static String getHelp() {
        return HELP;
    }

    /**
     * initStage Init the stage of application with default params
     *
     * @param stage
     * @throws IOException
     */
    public void initStage(Stage stage) throws IOException {
        stage.setTitle(APP_TITLE);
        stage.setMinHeight(364);
        stage.setMinWidth(450);
        try {
            stage.getIcons().add(new Image(ICON_SQL_CREATOR_PATH));
        }catch (Exception e){}
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        Scene scene = new Scene(root);

        String css = "/texture.css";
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    /***
     * getExcelFile display a windows FileChooser for select a Excel File
     * @param stage
     * @return
     */
    public File getExcelFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel files 97-2003 (*.xls)", "*.xls"),
                new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"),
                new FileChooser.ExtensionFilter("All files (*.*)", "*.*")
        );

        fileChooser.setTitle(APP_TITLE + " : Open the source file");

        File defaultDirectory = new File("C:/");
        fileChooser.setInitialDirectory(defaultDirectory);

        return fileChooser.showOpenDialog(stage);
    }

    /***
     * copyToClipboard copy data in params to clipboard windows path
     * @param text
     */
    public void copyToClipboard(String text) throws IllegalArgumentException {
        if (!text.equals("")) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(text);
            clipboard.setContents(strSel, null);
        } else {
            throw new IllegalArgumentException("Can't copy a null text!");
        }

    }

    /**
     * export saved request generated in a sql file.
     *
     * @param allRequest
     */
    public void export(String allRequest) {
        if (!allRequest.equals("")){

            FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("SQL Files (*.sql)", "*.sql"),
                    new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
            );

            File defaultDirectory = new File("C:/Users/Public/Desktop");
            fileChooser.setInitialDirectory(defaultDirectory);
            fileChooser.setTitle(APP_TITLE + " : Save request");
            Date date = new Date();
            fileChooser.setInitialFileName(APP_TITLE + "_export_" + date.getDate() + "_" + (date.getMonth() + 1) + "_" + (date.getYear() + 1900) + "_" + date.getHours() + date.getMinutes() + date.getSeconds());

            File fileDirectory = fileChooser.showSaveDialog(stage);

            if (fileDirectory != null){
                FileSql fileSql = new FileSql(fileDirectory);
                fileSql.writeStringInSqlFile(allRequest);
            }
        }else {
            throw new IllegalArgumentException("Can't copy a null text!");
            //dialogs.dialogNoText();
        }
    }

    /**
     * procedureCreateRequest concat all and prepare data for createAllRequest method.
     *
     * @param file
     * @return
     * @throws IOException
     * @throws BiffException
     * @throws IllegalArgumentException
     */
    public String procedureCreateRequest(File file) throws IOException, BiffException, IllegalArgumentException {
        LoaderKeywords loaderKeywords = new LoaderKeywords();
        MainRequest mainRequest = new MySqlRequest(mySqlVersion);
        loaderKeywords.loadList(file);

        java.util.List<String> typeListSort = sortByType(loaderKeywords.getTypeList());

        java.util.List<String> typeList = loaderKeywords.getTypeList();
        java.util.List<String> tableList = loaderKeywords.getTableList();
        java.util.List<String> champsList = loaderKeywords.getChampsList();
        java.util.List<String> conditionList = loaderKeywords.getConditionList();

        java.util.List<String> allRequestList = mainRequest.createAllRequest(typeListSort, typeList, tableList, champsList, conditionList);
        Date date = new Date();
        String allRequest = "/**************************************************\n" +
                "*  Request created by " + APP_TITLE + "!\n" +
                "*  Date: " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + "\n" +
                "*  Author: " + System.getProperty("user.name") + "\n" +
                "**************************************************/";
        ;

        for (String request : allRequestList) {
            allRequest = allRequest + "\n\n" + request;
        }

        return allRequest;
    }


    /**
     * sortByType return a "distinct" of params list.
     *
     * @param typeList
     * @return
     */
    private java.util.List<String> sortByType(java.util.List<String> typeList) {
        java.util.List<String> allTypeInFile = new ArrayList<String>();
        for (String type : typeList) {
            String typeMod = type.trim().toUpperCase();
            Boolean existType = false;
            if (allTypeInFile.isEmpty()) {
                allTypeInFile.add(typeMod);
            } else {
                for (String typeInFile : allTypeInFile) {
                    if (typeInFile.equals(typeMod)) {
                        existType = true;
                    }
                }
                if (!existType) {
                    allTypeInFile.add(typeMod);
                }
            }
        }
        return allTypeInFile;
    }

    public double getMySqlVersion() {
        return mySqlVersion;
    }

    public void setMySqlVersion(double mySqlVersion) {
        this.mySqlVersion = mySqlVersion;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Dialogs getDialogs() {
        return dialogs;
    }

    public void setDialogs(Dialogs dialogs) {
        this.dialogs = dialogs;
    }
}
