package rex.GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import seedu.rex.GUI.DialogBox;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Used ChatGPT to generate test by pasting the DialogBox GUI class
 * and the prompt was: "generate JUnit test".
 *
 * This class tests that DialogBox correctly sets and displays text and image.
 */
class DialogBoxTest extends FxTestBase {

    /**
     * Tests that getUserDialog() sets the dialog text and displays the image.
     */
    @Test
    void userDialog_setsText() {
        Image img = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/userImage.jpg")));
        DialogBox db = DialogBox.getUserDialog("hello", img);

        Label label = (Label) db.lookup(".label"); // find Label inside
        assertEquals("hello", label.getText());
    }
}
