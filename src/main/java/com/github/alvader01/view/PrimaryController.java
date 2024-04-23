package com.github.alvader01.view;

import java.io.IOException;

import com.github.alvader01.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
