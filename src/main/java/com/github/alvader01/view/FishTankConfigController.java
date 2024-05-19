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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FishTankConfigController extends Controller implements Initializable {
    @FXML
    Button crear;
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
    TableColumn<FishTank,String> width;
    @FXML
    TableColumn<FishTank,String> height;
    @FXML
    Button eliminar;
    @FXML
    ImageView info;
    @FXML
    Button anadir;


    private ObservableList<FishTank> fishTanks;

    @Override
    public void onOpen(Object input) throws IOException {
        FishTankDAO fDAO = new FishTankDAO();
        List<FishTank> fishTanks = fDAO.findAll();
        this.fishTanks= FXCollections.observableArrayList(fishTanks);
        table.setItems(this.fishTanks);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FishTankDAO fDAO = new FishTankDAO();
        table.setEditable(true);
        table.setRowFactory(tv -> {
            TableRow<FishTank> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                FishTank fishtank = table.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 3 && ! row.isEmpty() && fishtank != null ) {
                    FishTank fishTank = row.getItem();
                    try {
                        App.currentController.changeScene(Scenes.ADDSPECIESTOTANK,fishTank.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            return row ;
        });
        Id.setCellValueFactory(fishTank -> new SimpleIntegerProperty(fishTank.getValue().getId()).asObject());
        name.setCellValueFactory(fishTank -> new SimpleStringProperty(fishTank.getValue().getName()));
        capacity.setCellValueFactory(fishTank -> new SimpleIntegerProperty(fishTank.getValue().getCapacity()).asString());
        lengthy.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getLengthy()).asString());
        width.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getWidth()).asString());
        height.setCellValueFactory(fishTank -> new SimpleFloatProperty(fishTank.getValue().getHeight()).asString());
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        capacity.setCellFactory(TextFieldTableCell.forTableColumn());
        lengthy.setCellFactory(TextFieldTableCell.forTableColumn());
        width.setCellFactory(TextFieldTableCell.forTableColumn());
        height.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            FishTank fishTank = event.getRowValue();
            fishTank.setName(event.getNewValue());
            fDAO.update(fishTank);
        });
        capacity.setOnEditCommit(event2 -> {
            if (event2.getNewValue().equals(event2.getOldValue())) {
                return;
            }
            FishTank fishTank = event2.getRowValue();
            fishTank.setCapacity(Integer.parseInt(event2.getNewValue()));
            fDAO.update(fishTank);
        });
        lengthy.setOnEditCommit(event3 -> {
            if (event3.getNewValue().equals(event3.getOldValue())) {
                return;
            }
            FishTank fishTank = event3.getRowValue();
            fishTank.setLengthy(Float.parseFloat(event3.getNewValue()));
            fDAO.update(fishTank);
        });
        width.setOnEditCommit(event4 -> {
            if (event4.getNewValue().equals(event4.getOldValue())) {
                return;
            }
            FishTank fishTank = event4.getRowValue();
            fishTank.setLengthy(Float.parseFloat(event4.getNewValue()));
            fDAO.update(fishTank);
        });
        height.setOnEditCommit(event5 -> {
            if (event5.getNewValue().equals(event5.getOldValue())) {
                return;
            }
            FishTank fishTank = event5.getRowValue();
            fishTank.setLengthy(Float.parseFloat(event5.getNewValue()));
            fDAO.update(fishTank);
        });
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }

    public void ShowInfo(){
        AppController.ShowInformationFishTank();
    }
    public void changeSceneToCreateFishTank() throws IOException{
        App.currentController.changeScene(Scenes.CREATEFISHTANK,null);
    }
    public void changeSceneToAddSpeciesFishTank() throws IOException{
        App.currentController.changeScene(Scenes.ADDSPECIESTOTANK,null);
    }
    public void changeSceneToDeleteFishTank() throws IOException{
        App.currentController.changeScene(Scenes.DELETEFISHTANK,null);
    }
}
