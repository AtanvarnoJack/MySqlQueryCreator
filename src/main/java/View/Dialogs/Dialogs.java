package View.Dialogs;

import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by alco on 27/07/2015.
 */
public class Dialogs {
    private Stage stage;

    public Dialogs(Stage stage) {
        this.stage = stage;
    }

    public void dialogBadFileFormat() {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("Bad File")
                .masthead("Bad format file!")
                .message("Please select a file with a type format for SqlCreator!")
                .showError();
    }

    public void dialogNoData() {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("No Data")
                .masthead("No text to copy!")
                .message("There is nothing to copy!\n Please execute the program before!")
                .showError();
    }

    public void dialogBadNumber() {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("Bad Number")
                .masthead("Enter a valid version number!")
                .message("Please enter a version number! \n Between 6.0 and 0!")
                .showError();
    }

    public void dialogNoText() {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("No Text")
                .masthead("Execute Programs!")
                .message("There is nothing to export!")
                .showWarning();
    }

    public void dialogBadConnectionSql(SQLException sqlE) {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("Bad Connection")
                .masthead("Can not connect to Base!")
                .message("Please verify your url, User or password.\n" + sqlE)
                .showError();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
