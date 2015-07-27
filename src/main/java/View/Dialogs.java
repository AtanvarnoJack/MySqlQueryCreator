package View;

import javafx.stage.Stage;

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
                .masthead("Bad Format File!")
                .message("Please select a file with a type format for SqlCreator!")
                .showError();
    }

    public void dialogBadNumber() {
        org.controlsfx.dialog.Dialogs.create()
                .owner(stage)
                .title("Bad Number")
                .masthead("Enter a Version Number!")
                .message("Please enter a Version Number! \n Before 6.0 and after 0!")
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
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
