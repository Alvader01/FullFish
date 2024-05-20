package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.dao.SpeciesDAO;
import com.github.alvader01.Model.entity.Species;
import javafx.beans.property.SimpleIntegerProperty;
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

public class DeleteSpeciesController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @FXML
    TableView<Species> table;
    @FXML
    TableColumn<Species, Integer> Id;
    @FXML
    TableColumn<Species, String> name;
    @FXML
    TableColumn<Species, Integer> dimension;
    @FXML
    TableColumn<Species, Integer> longevity;
    @FXML
    Button eliminar;
    @FXML
    ImageView info;
    @FXML
    TextField ids;

    private ObservableList<Species> speciess;

    @Override
    public void onOpen(Object input) throws IOException {
        SpeciesDAO sDAO = new SpeciesDAO();
        List<Species> speciess = sDAO.findAll();
        this.speciess= FXCollections.observableArrayList(speciess);
        table.setItems(this.speciess);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpeciesDAO sDAO = new SpeciesDAO();
        table.setEditable(true);
        Id.setCellValueFactory(species -> new SimpleIntegerProperty(species.getValue().getId()).asObject());
        name.setCellValueFactory(species -> new SimpleStringProperty(species.getValue().getName()));
        dimension.setCellValueFactory(species -> new SimpleIntegerProperty(species.getValue().getDimension()).asObject());
        longevity.setCellValueFactory(species -> new SimpleIntegerProperty(species.getValue().getLongevity()).asObject());
    }

    public Species getValuesTextField() {
        Species speciesDAO = new Species();
        int id = Integer.parseInt(ids.getText());
        speciesDAO.setId(id);
        return speciesDAO;
    }


    /**
     * Deletes the selected species from the database.
     * Displays success or error alerts based on the deletion status.
     *
     * @throws SQLException If a SQL exception occurs during the deletion process.
     * @throws IOException  If an I/O exception occurs while deleting the species.
     */
    public void deleteSpecies() throws SQLException, IOException {
        Species speciesDAO = getValuesTextField();
        SpeciesDAO sDAO = new SpeciesDAO();
        if (sDAO.findById(speciesDAO.getId()) != null) {
            sDAO.delete(speciesDAO);
            AppController.ShowAlertsSuccessfullyDeleteSpecies();
            App.currentController.changeScene(Scenes.DELETESPECIES,null);
        } else {
            AppController.ShowAlertsErrorDeleteSpecies();
        }
    }

    public void changeSceneToSpeciesConfig() throws IOException{
        App.currentController.changeScene(Scenes.SPECIESCONFIG,null);
    }


    public void ShowInfo(){
        AppController.ShowInformationDeleteSpecies();
    }


}


