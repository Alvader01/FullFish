package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.dao.SpeciesDAO;
import com.github.alvader01.Model.dao.SpeciesInTankDAO;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddSpeciesInTankController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> FishTanksComboBox;
    @FXML
    private ComboBox<String> SpeciessComboBox;
    @FXML
    private ComboBox<String> DeleteSpeciesComboBox;
    @FXML
    private TableView<Species> SpeciesTableView;
    @FXML
    private TableColumn<Species, String> SpeciesNameColumn;
    @FXML
    private TableColumn<Species, Integer> SpeciesDimensionColumn;
    @FXML
    private TableColumn<Species, Integer> SpeciesLongevityColumn;


    private int currentFishTankId;
    private Map<String, Integer> fishTankNameToIdMap;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            currentFishTankId = (Integer) input;
            loadSpeciesInTank(currentFishTankId);
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<FishTank> fishTanks = FishTankDAO.build().findAll();

        fishTankNameToIdMap = new HashMap<>();
        for (FishTank tank : fishTanks) {
            fishTankNameToIdMap.put(tank.getName(), tank.getId());
        }

        List<String> fishTankNames = fishTanks.stream().map(FishTank::getName).collect(Collectors.toList());
        FishTanksComboBox.setItems(FXCollections.observableArrayList(fishTankNames));

        List<Species> species = SpeciesDAO.build().findAll();
        List<String> speciesNames = species.stream().map(Species::getName).collect(Collectors.toList());
        SpeciessComboBox.setItems(FXCollections.observableArrayList(speciesNames));

        FishTanksComboBox.setOnAction(event -> {
            String selectedTankName = FishTanksComboBox.getValue();
            if (selectedTankName != null) {
                try {
                    int selectedTankId = fishTankNameToIdMap.get(selectedTankName);
                    onOpen(selectedTankId);
                    loadSpeciesInTank(selectedTankId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        SpeciesNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpeciesDimensionColumn.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        SpeciesLongevityColumn.setCellValueFactory(new PropertyValueFactory<>("longevity"));
    }

    /**
     * Loads the species associated with the given tank ID into the corresponding GUI components.
     *
     * @param tankId The ID of the tank whose associated species are to be loaded.
     */
    private void loadSpeciesInTank(int tankId) {
        SpeciesInTankDAO speciesInTankDAO = new SpeciesInTankDAO();
        List<Species> speciesInTank = speciesInTankDAO.findAllSpeciesInTank(tankId);
        if (!speciesInTank.isEmpty()) {
            List<String> speciesNames = speciesInTank.stream().map(Species::getName).collect(Collectors.toList());
            DeleteSpeciesComboBox.setItems(FXCollections.observableArrayList(speciesNames));
            SpeciesTableView.setItems(FXCollections.observableArrayList(speciesInTank));
        } else {
            DeleteSpeciesComboBox.getItems().clear();
            SpeciesTableView.getItems().clear();
        }
    }


    /**
     * Adds the selected species to the selected fish tank.
     */
    public void addSpeciesToTank() {
        String selectedTankName = FishTanksComboBox.getValue();
        String speciesName = SpeciessComboBox.getValue();
        FishTankDAO fishTankDAO = new FishTankDAO();

        if (fishTankNameToIdMap.containsKey(selectedTankName)) {
            int fishTankId = fishTankNameToIdMap.get(selectedTankName);
            FishTank fishTank = fishTankDAO.findById(fishTankId);
            SpeciesDAO speciesDAO = new SpeciesDAO();
            Species species = speciesDAO.findSpeciesByName(speciesName);

            if (fishTank != null && species != null) {
                SpeciesInTankDAO speciesInTankDAO = SpeciesInTankDAO.build();
                if (speciesInTankDAO.isSpeciesInTank(fishTankId, species.getId())) {;
                    AppController.ShowAlertsSpeciesAlreadyExistsInTank();
                } else {
                    speciesInTankDAO.addSpeciesToTank(fishTankId, species.getId());
                    AppController.ShowAlertsSuccessfullyAddSpeciesToTank();
                }
                loadSpeciesInTank(fishTankId);
            }
        }
    }


    /**
     * Deletes the selected species from the selected fish tank.
     */
    public void deleteSpeciesFromTank() {
        String selectedTankName = FishTanksComboBox.getValue();
        String speciesName = DeleteSpeciesComboBox.getValue();
        FishTankDAO fishTankDAO = new FishTankDAO();

        if (fishTankNameToIdMap.containsKey(selectedTankName)) {
            int fishTankId = fishTankNameToIdMap.get(selectedTankName);
            FishTank fishTank = fishTankDAO.findById(fishTankId);
            SpeciesDAO speciesDAO = new SpeciesDAO();
            Species species = speciesDAO.findSpeciesByName(speciesName);

            if (fishTank != null && species != null) {
                SpeciesInTankDAO.build().removeSpeciesFromTank(fishTankId, species.getId());
                loadSpeciesInTank(fishTankId);
            }
        }
    }


    public void changeSceneToFishTankConfig() throws IOException {
        App.currentController.changeScene(Scenes.FISHTANKCONFIG, null);
    }

    public void ShowInfo() {
        AppController.ShowInformationAddSpecies();
    }
}
