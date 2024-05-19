package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateFishTankController extends Controller implements Initializable {

    @FXML
    TextField name;
    @FXML
    TextField capacity;
    @FXML
    TextField lengthy;
    @FXML
    TextField width;
    @FXML
    TextField height;
    @FXML
    Button crear;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof User) {
            UserSession.login((User) input);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public FishTank getFishTankValues() {
        User currentUser = UserSession.getCurrentUser();
        FishTank fishTank = null;

        if(currentUser != null) {
            String names = name.getText();
            int capacitys = Integer.parseInt(capacity.getText());
            float lengthys = Float.parseFloat(lengthy.getText());
            float widths = Float.parseFloat(width.getText());
            float heights = Float.parseFloat(height.getText());

            fishTank = new FishTank(names, capacitys, lengthys, widths, heights);
        }

        return fishTank;
    }

    public void createFishTank() throws IOException {
        FishTank fishTank = getFishTankValues();

        if (fishTank == null || fishTank.getName().isEmpty() || fishTank.getCapacity() == 0 || fishTank.getLengthy() == 0.0 || fishTank.getWidth() == 0.0 || fishTank.getHeight() == 0.0) {
            AppController.ShowAlertsErrorCreatingTank();
        } else {
            FishTankDAO tankDAO = new FishTankDAO();

            if (tankDAO.exists(fishTank.getId())) {
                AppController.ShowAlertsTankAlreadyExists();
            } else {
                if (tankDAO.saveFishTank(fishTank, UserSession.getCurrentUser()) != null) {
                    AppController.ShowAlertsSuccessfullyCreateTank();
                } else {
                    AppController.ShowAlertsErrorCreatingTank();
                }
            }
        }
    }


    public void changeSceneToFishTankConfig() throws IOException{
        App.currentController.changeScene(Scenes.FISHTANKCONFIG,null);
    }


}
