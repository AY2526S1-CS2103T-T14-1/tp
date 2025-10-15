package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Very small window that shows the schedule text produced by the {@code schedule} command.
 * <p>
 * This window is modal (owned by the primary stage) and simply displays a read-only TextArea.
 */
public class ScheduleWindow extends UiPart<AnchorPane> {
    private static final String FXML = "ScheduleWindow.fxml";

    private final Stage dialogStage;

    @javafx.fxml.FXML
    private TextArea contentArea;

    /**
     * Creates a new modal schedule window owned by the given {@code owner} stage.
     *
     * @param owner The primary stage to own this dialog.
     */
    public ScheduleWindow(Stage owner) {
        super(FXML);
        dialogStage = new Stage();
        dialogStage.setTitle("Weekly Schedule");
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(getRoot()));
        dialogStage.setResizable(true);
    }

    /**
     * Sets the schedule text content to display.
     *
     * @param content The text to show in the window.
     */
    public void setContentArea(String content) {
        contentArea.setText(content);
    }

    /**
     * Shows the window and brings it to the front.
     */
    public void show() {
        dialogStage.show();
        dialogStage.requestFocus();
    }

    /**
     * Brings the window to the front if it is already showing.
     */
    public void focus() {
        dialogStage.requestFocus();
    }

    /**
     * Returns whether the window is currently visible.
     *
     * @return true if showing, false otherwise.
     */
    public boolean isShowing() {
        return dialogStage.isShowing();
    }
}
