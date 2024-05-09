package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends Controller implements Initializable {
    @FXML
    Button users;

    @FXML
    Button fishtanks;

    @FXML
    Button species;

    @FXML
    Button logout;

    @FXML
    ImageView boton;



    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Logout() throws IOException{
        UserSession.logout();
        App.currentController.changeScene(Scenes.LOGIN,null);
    }

    public void changeSceneToUsersConfig() throws IOException{
        App.currentController.changeScene(Scenes.USERCONFIG,null);
    }
    public void changeSceneToFishTanksConfig() throws IOException{
        App.currentController.changeScene(Scenes.FISHTANKCONFIG,null);
    }
    public void changeSceneToSpeciesConfig() throws IOException{
        App.currentController.changeScene(Scenes.SPECIESCONFIG,null);
    }

    public void changeSceneToLogin() throws IOException{
        App.currentController.changeScene(Scenes.LOGIN,null);
    }







}
