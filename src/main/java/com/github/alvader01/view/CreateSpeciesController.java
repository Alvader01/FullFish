package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.Singleton.UserSession;
import com.github.alvader01.Model.dao.SpeciesDAO;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateSpeciesController extends Controller implements Initializable {
    @FXML
    TextField name;
    @FXML
    TextField dimension;
    @FXML
    TextField longevity;
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

    public Species getSpeciesValues() {
        User currentUser = UserSession.getCurrentUser();
        Species species = null;

        if(currentUser != null) {
            String names = name.getText();
            int dimensions = Integer.parseInt(dimension.getText());
            int longevities = Integer.parseInt(longevity.getText());;

            species = new Species(names, dimensions, longevities);
        }

        return species;
    }


    /**
     * Creates a new species with the provided values.
     * Displays error alerts if the provided values are invalid or if a species with the same ID already exists.
     *
     * @throws IOException If an I/O exception occurs while creating the species.
     */public void createSpecies() throws IOException {
        Species species = getSpeciesValues();

        if (species == null || species.getName().isEmpty() || species.getDimension() == 0 || species.getLongevity() == 0) {
            AppController.ShowAlertsErrorCreatingSpecies();
        } else {
            SpeciesDAO speciesDAO = new SpeciesDAO();

            if (speciesDAO.exists(species.getId())) {
                AppController.ShowAlertsSpeciesAlreadyExists();
            } else {
                if (speciesDAO.saveSpecies(species, UserSession.getCurrentUser()) != null) {
                    AppController.ShowAlertsSuccessfullyCreateSpecies();
                } else {
                    AppController.ShowAlertsErrorCreatingSpecies();
                }
            }
        }
    }


    public void changeSceneToSpeciesConfig() throws IOException{
        App.currentController.changeScene(Scenes.SPECIESCONFIG,null);
    }
}

