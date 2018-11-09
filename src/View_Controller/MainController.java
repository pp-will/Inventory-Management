/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label appTitle;

    @FXML
    private Label partsLabel;

    @FXML
    private Button partsSearchBtn;

    @FXML
    private TextField partsText;

    @FXML
    private TableView<Part> partsTable;//allParts

    @FXML
    private TableColumn<Part, Integer> partIDColumn; //reference get methods

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> invLevelColumn;

    @FXML
    private TableColumn<Part, Double> priceColumn;

    @FXML
    private Button productSearchBtn;

    @FXML
    private Button partsAddBtn;

    @FXML
    private Button partsModifyBtn;

    @FXML
    private Button partsDeleteBtn;

    @FXML
    private Button productsAddBtn;

    @FXML
    private Button productsModifyBtn;

    @FXML
    private Button productsDeleteBtn;

    @FXML
    private Button exitBtn;
    
    static boolean active;
    
    //reference tables
    
    @FXML
    private void onExitBtnHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void partsAddBtnHandler(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) partsAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AddPartController controller = loader.getController();
        
    }
    
    @FXML
    void partsModifyBtnHandler(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root;       
        stage=(Stage) partsModifyBtn.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ModifyPartController controller = loader.getController();
        Part part = partsTable.getSelectionModel().getSelectedItem();
        controller.setPart(part);
        delete();
       }
    
    @FXML
    void partsDeleteBtnHandler(ActionEvent event) {
        delete();
    }
    
    @FXML
    void partsSearchBtnHandler(ActionEvent event) {
        ObservableList<Part> data = partsTable.getItems();
        boolean present = false;
        String partString = partsText.getText();
        try {
            int partNum = Integer.parseInt(partString);
            for(Part p : data) {
                if(p.getPartID() == partNum) {
                    System.out.println("found " + p.getName());
                    partsTable.getSelectionModel().select(p);
                    present = true;
                }   
            }
            
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("The part you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
            
        } catch (NumberFormatException e) {
            for(Part p : data) {
                partString = partString.toLowerCase();
                String partName = p.getName().toLowerCase();
                if(partName.equals(partString)) {
                    partsTable.getSelectionModel().select(p);
                    present = true;
                }
            }
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("The part you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!active) {
        Inhouse inhouse1 = new Inhouse(0, "Part 1", 1.99, 5, 1, 8, 123);
        Inhouse inhouse2 = new Inhouse(1, "Part 2", 2.50, 7, 2, 6, 1232);
        Outsourced outsourced1 = new Outsourced(2, "Part 3", 4.99, 3, 1, 9, "Parts Inc.");
        Inventory.allParts.add(0, inhouse1);
        Inventory.allParts.add(1, inhouse2);
        Inventory.allParts.add(2, outsourced1);
        active = true;
        }
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        
        
    }    
    
    public void delete() {
        ObservableList<Part> data = partsTable.getItems();
        data.remove(partsTable.getSelectionModel().getSelectedItem());
            partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
            partsTable.setItems(data);
    }
    
}
