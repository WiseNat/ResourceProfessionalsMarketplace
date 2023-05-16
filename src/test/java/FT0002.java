import com.wise.resource.professionals.marketplace.application.StageHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class FT0002 {

    @InjectMocks
    private StageHandler stageHandler;

    @Mock
    private TextField emailField;

    @Mock
    private PasswordField passwordField;

    @Mock
    private ChoiceBox<String> accountTypeField;

    @Test
    public void testSceneSwapWhenAuthenticated() {
        assertFalse(true);
    }

    @Test
    public void testNoSceneSwapWhenNotAuthenticated() {
        assertFalse(true);
    }
}
