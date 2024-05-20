package com.github.alvader01.view;

import com.github.alvader01.App;
import com.github.alvader01.Model.dao.FishTankDAO;
import com.github.alvader01.Model.entity.FishTank;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteFishTankController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @FXML
    TableView<FishTank> table;
    @FXML
    TableColumn<FishTank, Integer> Id;
    @FXML
    TableColumn<FishTank, String> name;
    @FXML
    TableColumn<FishTank, String> capacity;
    @FXML
    TableColumn<FishTank, String> lengthy;
    @FXML
    TableColumn<FishTank, String> width;
    @FXML
    TableColumn<FishTank, String> height;
    @FXML
    Button eliminar;
    @FXML
    ImageView info;
    @FXML
    TextField ids;

    private ObservableList<FishTank> fishTanks;

    @Override
    public void onOpen(Object input) throws IOException {
        FishTankDAO fDAO = new FishTankDAO();
        List<FishTank> fishTanks = fDAO.findAll();
        this.fishTanks = FXCollections.observableArrayList(fishTanks);
        table.setItems(this.fishTanks);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FishTankDAO fDAO = new FishTankDAO();
        table.setEditable(true);
        Id.setCellValueFactory(fishTank -> new SimpleIntegerProperty(fishTank.getValue().getId()).asObject());
        name.setCellValueFactory(fishTank -> new SimpleStringProperty(fishTank.getValue().getName()));
        capacity.setCellValueFactory(fishTank -> new SimpleIntegerProperty(fishTank.getValue().getCapacity()).asString());
        lengthy.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getLengthy()).asString());
        width.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getWidth()).asString());
        height.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getHeight()).asString());
        Id.getTableView().refresh();

    }

    public FishTank getValuesTextField() {
        FishTank fishTankdao = new FishTank();
        int id = Integer.parseInt(ids.getText());
        fishTankdao.setId(id);
        return fishTankdao;
    }


    /**
     * Deletes the selected fish tank from the database.
     * Displays success or error alerts based on the deletion status.
     *
     * @throws SQLException If a SQL exception occurs during the deletion process.
     * @throws IOException  If an I/O exception occurs while deleting the fish tank.
     */
    public void deleteFishTank() throws SQLException, IOException {
        FishTank fishTankdao = getValuesTextField();
        FishTankDAO fDAO = new FishTankDAO();
        if (fDAO.findById(fishTankdao.getId()) != null) {
            fDAO.delete(fishTankdao);
            AppController.ShowAlertsSuccessfullyDeleteFishTank();
            App.currentController.changeScene(Scenes.DELETEFISHTANK,null);
        } else {
            AppController.ShowAlertsErrorDeleteFishTank();
        }
    }

    public void changeSceneToFishTankConfig() throws IOException{
        App.currentController.changeScene(Scenes.FISHTANKCONFIG,null);
    }


    public void ShowInfo(){
        AppController.ShowInformationDeleteFishTank();
    }


}
