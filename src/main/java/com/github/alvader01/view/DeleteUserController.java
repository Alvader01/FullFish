package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteUserController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @FXML
    TableView<User> table;
    @FXML
    TableColumn<User, String> username;
    @FXML
    TableColumn<User, String> name;
    @FXML
    TableColumn<User, String> email;
    @FXML
    Button delete;
    @FXML
    ImageView info;
    @FXML
    TextField usernames;

    private ObservableList<User> users;


    @Override
    public void onOpen(Object input) throws IOException {
        UserDAO uDAO = new UserDAO();
        List<User> users = uDAO.findAll();
        this.users= FXCollections.observableArrayList(users);
        table.setItems(this.users);

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setEditable(true);
        username.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUsername()));
        name.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        email.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getEmail()));
        username.getTableView().refresh();
    }

    public User getValuesTextField() {
        User userdao = new User();
        String username = usernames.getText();
        userdao.setUsername(username);
        return userdao;
    }


    /**
     * Deletes the selected user from the database.
     * Displays success or error alerts based on the deletion status.
     *
     * @throws SQLException If a SQL exception occurs during the deletion process.
     * @throws IOException  If an I/O exception occurs while deleting the user.
     */
    public void deleteUser() throws SQLException, IOException {
        User userdao = getValuesTextField();
        UserDAO uDAO = new UserDAO();
        if (uDAO.findByUsername(userdao.getUsername()) != null) {
            uDAO.delete(userdao);
            AppController.ShowAlertsSuccessfullyDeleteUser();
            App.currentController.changeScene(Scenes.DELETEUSER,null);
        } else {
            AppController.ShowAlertsErrorDeleteUser();
        }
    }

    public void changeSceneToUserConfig() throws IOException{
        App.currentController.changeScene(Scenes.USERCONFIG,null);
    }


    public void ShowInfo(){
        AppController.ShowInformationDeleteUser();
    }


}
