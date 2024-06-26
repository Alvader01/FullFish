package com.github.alvader01.view;

import com.github.alvader01.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @FXML
    static Alert alert= new Alert(Alert.AlertType.ERROR);

    @FXML
    static Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    static Alert alertInfoRegister= new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void onOpen(Object input) throws IOException {
        //Al abrir este controlador que cargue main en el centro
        changeScene(Scenes.LOGIN,null);
    }

    public void changeScene(Scenes scene,Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scene, String title,Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();

    }


    @Override
    public void onClose(Object output) {
        //nothing to do
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }

    public static void ShowAlertsErrorLogin(){
        alert.setTitle("Error");
        alert.setContentText("Usuario o contraseña incorrectos");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLoginPassword(){
        alert.setTitle("Error");
        alert.setContentText("La contraseña es incorrecta");
        alert.showAndWait();
    }
    public static void ShowAlertsInvalidEmail(){
        alert.setTitle("Error");
        alert.setContentText("El email tiene que ser valido, por ejemplo: 1rT8j@example.com");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorRegister(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLogin2(){
        alert.setTitle("Error");
        alert.setContentText("Necesitas introducir los campos de usuario y contraseña");
        alert.showAndWait();

    }
    public static void ShowAlertsSuccessfullyRegister(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario registrado con exito");
        alert2.showAndWait();
    }


    public static void ShowAlertsUserAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El usuario ya existe");
        alert.showAndWait();
    }
    public static void  ShowInformation(){
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setContentText("No puedes crear usuarios con el mismo usuario");
        alertInfoRegister.showAndWait();

    }

    public static void  ShowInformationUser(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar una acuario haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();

    }
    public static void  ShowInformationFishTank(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar una acuario haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();
    }
    public static void  ShowInformationAddSpecies(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para añadir una especie a un acuario selecciona que especie deseas añadir" +
                "y a que acuario deseas añadirla. Para eliminarla lo mismo pero pulsando el boton eliminar");
        alertInfoRegister.showAndWait();
    }
    public static void  ShowInformationSpecies(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar una especie haz doble click en el campo que quieras editar");
        alertInfoRegister.showAndWait();
    }

    public static void ShowAlertsTankAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El acuario ya existe");
        alert.showAndWait();
    }
    public static void ShowAlertsSpeciesAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("La especie ya existe");
        alert.showAndWait();
    }
    public static void ShowAlertsSpeciesAlreadyExistsInTank(){
        alert.setTitle("Error");
        alert.setContentText("La especie ya esta en el acuario");
        alert.showAndWait();
    }
    public static void ShowAlertsSuccessfullyAddSpeciesToTank(){
        alert2.setTitle("Exito");
        alert2.setContentText("Especie añadida con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsSuccessfullyCreateTank(){
        alert2.setTitle("Exito");
        alert2.setContentText("Acuario registrado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyCreateSpecies(){
        alert2.setTitle("Exito");
        alert2.setContentText("Especie registrada con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsErrorCreatingTank(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el acuario");
        alert.showAndWait();
    }


    public static void ShowAlertsErrorCreatingSpecies(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar la especie");
        alert.showAndWait();
    }
    public static void  ShowInformationDeleteUser(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el usuario que deaseas introduce su username." +
                "Aqui no podras actualizar los datos de usuario");
        alertInfoRegister.showAndWait();

    }

    public static void ShowAlertsSuccessfullyDeleteUser(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario eliminado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteFishTank(){
        alert2.setTitle("Exito");
        alert2.setContentText("Acuario eliminado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteSpecies(){
        alert2.setTitle("Exito");
        alert2.setContentText("Especie eliminada con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsErrorDeleteUser(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteFishTank(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el acuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteSpecies(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar la especie");
        alert.showAndWait();
    }

    public static void  ShowInformationDeleteFishTank(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el acuario que deaseas introduce su id." +
                "Aqui no podras actualizar los datos de acuario");
        alertInfoRegister.showAndWait();

    }
    public static void  ShowInformationDeleteSpecies(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar la especie que deaseas introduce su id." +
                "Aqui no podras actualizar los datos de la especie");
        alertInfoRegister.showAndWait();

    }

    @FXML
    private void closeApp(){
        System.exit(0);
    }


}

