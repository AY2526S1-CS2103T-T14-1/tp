package seedu.address.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Very small window that shows the text produced by the {@code schedule} and {@code payments} commands.
 * <p>
 * This window is modal (owned by the primary stage) and simply displays a read-only TextArea.
 */
public class PopupWindow extends UiPart<AnchorPane> {
    private static final String FXML = "PopupWindow.fxml";

    private final Stage dialogStage;

    @FXML
    private TextArea contentArea;

    /**
     * Creates a new modal window owned by the given {@code owner} stage.
     *
     * @param owner The primary stage to own this dialog.
     */
    public PopupWindow(Stage owner) {
        super(FXML);
        dialogStage = new Stage();
        dialogStage.setTitle("CommandOutputWindow");
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/info_icon.png"))));
        dialogStage.setScene(new Scene(getRoot()));
        dialogStage.setResizable(true);
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    @FXML
    private void handleCopyContent() {
        String text = contentArea.getText();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * Sets the text content to display.
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
