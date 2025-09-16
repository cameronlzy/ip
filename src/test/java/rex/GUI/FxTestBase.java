package rex.GUI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;

/**
 * This base class was included in the GPT generation process
 * since it is not directly linked to a specific GUI class.
 *
 * It initializes the JavaFX runtime before any tests are run,
 * allowing JavaFX components to be created in unit tests.
 */
public class FxTestBase {
    /**
     * Initializes the JavaFX toolkit once before all tests.
     * Uses JFXPanel to bootstrap the runtime in a headless test environment.
     */
    @BeforeAll
    static void initToolkit() {
        // This forces JavaFX runtime to initialize
        new JFXPanel();
        Platform.setImplicitExit(false);
    }
}
