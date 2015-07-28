package View;

import ExcelReader.LoaderExcel;
import FileOut.FileSql;
import Request.SqlAlter;
import Request.SqlTrigger;
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
import java.util.Arrays;
import java.util.Date;

/**
 * Created by alco on 27/07/2015.
 * MainView is a class who contain all background method of the view.
 */
public class MainView {
    //App Constant
    private final static String SQL_CREATOR = "SqlCreator";
    private final static String ICON_SQL_CREATOR_PATH = "file:img/IconSqlCreator.png";
    private final static String HELP = "Hello!\n" +
            "You need to load a \".xls\" file who contain 4 column named:\n" +
            "\"type\": Contains value \"Trigger\". Other in development.\n" +
            "\"table\": Contain the name of yours Table in Base.\n" +
            "\"champs\":Contain the name of the rows in your table base.\n" +
            "\"condition\": Contain the condition for the trigger.\n" +
            "\n" +
            "Example:\n" +
            "type\t\ttable\t\t\tchamps\t\t\t\t\tcondition\n" +
            "Trigger\t\tPERSON\t\ttoto \t'Y' or 'N'\n" +
            "Trigger\t\tPERSON\t\ttata \t\t'V1' or 'V2' or 'V3'\n" +
            "Trigger\t\tPERSON\t\ttiti \t\t'Low' or 'Medium' or 'High'\n" +
            "\n" +
            "The output are create in a SQL file named \"TriggerBase.sql\" on the Desktop!\n" +
            "\n" +
            "For an Excel file example see \"ExempleFile.xls\" in application repository.\n" +
            "For an Empty file see\"EmptyFile.xls\" in application repository.";
    //Variables:
    private static double mySqlVersion = 5.6;
    private Stage stage;
    private Dialogs dialogs;

    public MainView(Stage stage) {
        dialogs = new Dialogs(stage);
        this.stage = stage;
    }

    public static String getSqlCreator() {
        return SQL_CREATOR;
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
        stage.setTitle(SQL_CREATOR);
        stage.setMinHeight(260);
        stage.setMinWidth(400);
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

        fileChooser.setTitle(SQL_CREATOR + " : Ouvrir le fichier source");

        File defaultDirectory = new File("C:/");
        fileChooser.setInitialDirectory(defaultDirectory);

        return fileChooser.showOpenDialog(stage);
    }

    /***
     * copyToClipboard copy data in params to clipboard windows path
     * @param text
     */
    public void copyToClipboard(String text) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(text);
        clipboard.setContents(strSel, null);
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
            fileChooser.setTitle(SQL_CREATOR + " : Save export");
            Date date = new Date();
            fileChooser.setInitialFileName(SQL_CREATOR + "_export_" + date.getDate() + "_" + (date.getMonth() + 1) + "_" + (date.getYear() + 1900) + "_" + date.getHours() + date.getMinutes() + date.getSeconds());

            File fileDirectory = fileChooser.showSaveDialog(stage);

            if (fileDirectory != null){
                FileSql fileSql = new FileSql(fileDirectory);
                fileSql.writeStringInSqlFile(allRequest);
            }
        }else {
            dialogs.dialogNoText();
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
        LoaderExcel loaderExcel = new LoaderExcel();
        loaderExcel.loadList(file);

        java.util.List<String> typeListSort = sortByType(loaderExcel.getTypeList());

        java.util.List<String> typeList = loaderExcel.getTypeList();
        java.util.List<String> tableList = loaderExcel.getTableList();
        java.util.List<String> champsList = loaderExcel.getChampsList();
        java.util.List<String> conditionList = loaderExcel.getConditionList();

        java.util.List<String> allRequestList = createAllRequest(typeListSort, typeList, tableList, champsList, conditionList);
        Date date = new Date();
        String allRequest = "/**************************************************\n" +
                "*  Request created by " + SQL_CREATOR + "!\n" +
                "*  Date: " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900) + "\n" +
                "*  Author: " + System.getProperty("user.name") + "\n" +
                "**************************************************/";
        ;

        for (String request : allRequestList) {
            allRequest = allRequest + "\n\n" + request;
        }

        return allRequest;
    }

    /***
     * createAllRequest sort all list an generate all request in a List<String>.
     * @param typeListSort
     * @param typeList
     * @param tableList
     * @param champsList
     * @param conditionList
     * @return
     */
    private java.util.List<String> createAllRequest(java.util.List<String> typeListSort, java.util.List<String> typeList, java.util.List<String> tableList, java.util.List<String> champsList, java.util.List<String> conditionList)
            throws IllegalArgumentException {
        java.util.List<String> tableListPrepare = new ArrayList<String>();
        java.util.List<String> champsListPrepare = new ArrayList<String>();
        java.util.List<String> conditionListPrepare = new ArrayList<String>();

        java.util.List<String> allRequestList = new ArrayList<String>();

        for(String type : typeListSort){
            tableListPrepare.clear();
            champsListPrepare.clear();
            conditionListPrepare.clear();
            for (int i = 0; i < typeList.size(); i++) {
                if (type.equals(typeList.get(i))) {
                    tableListPrepare.add(tableList.get(i));
                    champsListPrepare.add(champsList.get(i));
                    conditionListPrepare.add(conditionList.get(i));
                }
            }
            detectionType(tableListPrepare, champsListPrepare, conditionListPrepare, allRequestList, type);
        }
        return allRequestList;
    }

    /**
     * detectionType cast all type in different name and calling associated Request Creator
     *
     * @param tableListPrepare
     * @param champsListPrepare
     * @param conditionListPrepare
     * @param allRequestList
     * @param type
     */
    private void detectionType(java.util.List<String> tableListPrepare, java.util.List<String> champsListPrepare, java.util.List<String> conditionListPrepare, java.util.List<String> allRequestList, String type)
            throws IllegalArgumentException {
        String[] architectureType = type.trim().toUpperCase().split(":");

        if (architectureType.length != 0) {
            if (architectureType[0].equals(SqlTrigger.getTrigger())) {
                SqlTrigger sqlTrigger = new SqlTrigger();
                allRequestList.add(sqlTrigger.createListSqlDropIfExist(tableListPrepare));
                allRequestList.add(sqlTrigger.createSqlTriggerList(tableListPrepare, champsListPrepare, conditionListPrepare, mySqlVersion));
            } else if (architectureType[0].equals(SqlAlter.getAlter())) {
                if (architectureType.length == 3) {
                    if (architectureType[1].equals(SqlAlter.getEngine())) {
                        if (architectureType[2].equals(SqlAlter.getInnodb())) {
                            SqlAlter sqlAlter = new SqlAlter();
                            allRequestList.add(sqlAlter.getAllAlterTableEngine(tableListPrepare, SqlAlter.getInnodb()));
                        } else if (architectureType[2].equals(SqlAlter.getMyisam())) {
                            SqlAlter sqlAlter = new SqlAlter();
                            allRequestList.add(sqlAlter.getAllAlterTableEngine(tableListPrepare, SqlAlter.getMyisam()));
                        } else {
                            throw new IllegalArgumentException("Architecture Type is not correct!\n" +
                                    " Engine Name:" + architectureType[2] + " for " + Arrays.toString(architectureType));
                        }
                    } else {
                        throw new IllegalArgumentException("Architecture Type is not correct!\n" +
                                "Alter Type: " + architectureType[1] + " for " + Arrays.toString(architectureType));
                    }
                } else {
                    throw new IllegalArgumentException("Architecture Type is not correct!\n" +
                            "Type: " + architectureType[0] + " for " + Arrays.toString(architectureType));
                }
            } else {
                throw new IllegalArgumentException("Architecture Type is not correct!\n" +
                        "Rows: " + Arrays.toString(architectureType));
            }
        } else {
            throw new IllegalArgumentException("Type is not correct!\n" +
                    "Type: " + type);
        }
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
