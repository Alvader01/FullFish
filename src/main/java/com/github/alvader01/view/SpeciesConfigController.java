package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.dao.SpeciesDAO;
import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SpeciesConfigController extends Controller implements Initializable {
    @FXML
    Button crear;
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
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        dimension.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        longevity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Species species = event.getRowValue();
            species.setName(event.getNewValue());
            sDAO.update(species);
        });
        dimension.setOnEditCommit(event2 -> {
            if (event2.getNewValue().equals(event2.getOldValue())) {
                return;
            }
            Species species = event2.getRowValue();
            species.setDimension(Integer.parseInt(String.valueOf(event2.getNewValue())));
            sDAO.update(species);
        });
        longevity.setOnEditCommit(event3 -> {
            if (event3.getNewValue().equals(event3.getOldValue())) {
                return;
            }
            Species species = event3.getRowValue();
            species.setLongevity(Integer.parseInt(String.valueOf(event3.getNewValue())));
            sDAO.update(species);
        });
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }

    public void ShowInfo(){
        AppController.ShowInformationSpecies();
    }
    public void changeSceneToCreateSpecies() throws IOException{
        App.currentController.changeScene(Scenes.CREATESPECIES,null);
    }
    public void changeSceneToDeleteSpecies() throws IOException{
        App.currentController.changeScene(Scenes.DELETESPECIES,null);
    }
}