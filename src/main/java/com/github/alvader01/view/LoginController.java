package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.alvader01.view.AppController.alert;

public class LoginController extends Controller implements Initializable {
    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button button;

    @FXML
    Button buttonRegister;

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public User getValues(){
        User user = new User();
        String usernames = username.getText();
        String passwords = password.getText();
        if (!usernames.isEmpty() && !passwords.isEmpty()) {
            user.setUsername(usernames);
            user.setPassword(passwords);
            return user;
        } else {
            return null;
        }
    }

    public void Login() throws IOException{
        User user = getValues();
        UserDAO uDAO = new UserDAO();
        if (user==null) {
            AppController.ShowAlertsErrorLogin2();
        }
        else if (uDAO.findByUsername(user.getUsername()) != null) {
            UserSession.login(user);
            changeSceneToMainPage();
        }else{
            AppController.ShowAlertsErrorLogin();
        }
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }

    public void changeSceneToRegister() throws IOException{
        App.currentController.changeScene(Scenes.REGISTER,null);
    }


}
