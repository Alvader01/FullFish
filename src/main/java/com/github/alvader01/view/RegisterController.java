package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {

    @FXML
    TextField username;
    @FXML
    TextField name;
    @FXML
    PasswordField password;
    @FXML
    TextField email;
    @FXML
    Button registrarse;


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
        String usernames = username.getText();
        String names = name.getText();
        String passwords = password.getText();
        String emails = email.getText();
        User user = new User(usernames,names,passwords,emails);
        return user;
    }

    public void Register() throws IOException{
        User user = getValues();
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            AppController.ShowAlertsErrorRegister();
        } else {
            UserDAO uDAO = new UserDAO();
            if (uDAO.exists(user.getUsername())) {
                AppController.ShowAlertsUserAlreadyExists();
            } else {
                if (uDAO.save(user) != null) {
                    UserSession.login(user);
                    changeSceneToLoginPage();
                    AppController.ShowAlertsSuccessfullyRegister();
                } else {
                    AppController.ShowAlertsErrorRegister();
                }
            }
        }
    }

    public void changeSceneToLoginPage() throws IOException{
        App.currentController.changeScene(Scenes.LOGIN,null);
    }


}
