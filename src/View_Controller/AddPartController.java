/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class AddPartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private RadioButton inhouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private Label variableLabel;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partMinField;

    @FXML
    private TextField partMaxField;

    @FXML
    private TextField variableField;

    @FXML
    private Button addPartSaveBtn;

    @FXML
    private Button addPartCancelBtn;
    
    @FXML
    private Label errorLabel;
    
    public int partId;

    @FXML
    void addPartCancelBtnHandler(ActionEvent event) throws IOException {
        try {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("This part will not be added");
        alert.setContentText("Are you ok with this?");
        alert.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) addPartCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("OK PRESSED");
         } else {
            Stage stage;
            Parent root;
            stage = (Stage) addPartCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Errror");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    void toggleSwitch(ActionEvent event) {
        ToggleGroup group = new ToggleGroup();
        inhouseRadio.setToggleGroup(group);
        outsourcedRadio.setToggleGroup(group);
        
        inhouseRadio.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
           if(isSelected) {
               variableLabel.setText("Machine ID");
           } 
        });
        outsourcedRadio.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
           if(isSelected) {
               variableLabel.setText("Company Name");
           } 
        });
    }
         
    @FXML
    void addPartSave(ActionEvent event) throws IOException {
        
        //for confirming add part
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Add Part");
        confirm.setHeaderText("This part will be added");
        confirm.setContentText("Are you sure?");
        confirm.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> conf = confirm.showAndWait();
        
        Random random = new Random();
        int[] id = new int[100];
        for(int i = 0; i < id.length; i++) {
            id[i] = random.nextInt(1000);
        }
        String partName = partNameField.getText();
        double price = parseDouble(partPriceField.getText());
        int inventory = parseInt(partInvField.getText());
        int min = parseInt(partMinField.getText());
        int max = parseInt(partMaxField.getText());
        
        //alert dialog if exception
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Your entry is not valid");
        //alert.setContentText needs to be set
        
        if(inhouseRadio.isSelected()) {
            partId = id[random.nextInt(id.length)];
            if(max < min) {
                errorLabel.setText("max must be greater than min");
            }
            
            int machineID = parseInt(variableField.getText());
            
           Inhouse inhouse = new Inhouse(partId, partName, price, inventory, min, max, machineID);
           Inventory.addPart(inhouse);
           inhouse.getPartID();
           inhouse.getName();
           inhouse.getPrice();
           inhouse.getInStock();
           inhouse.getMin();
           inhouse.getMax();
           
           
        } else if (outsourcedRadio.isSelected()) {
            partId = id[random.nextInt(id.length)];
            if(max < min) {
                errorLabel.setText("max must be less than min");
            }
            String companyName = variableField.getText();
            Outsourced outsourced = new Outsourced(partId, partName, price, inventory, min, max, companyName);
            Inventory.addPart(outsourced);
            
        }
        //show confirmation dialog
        if(conf.isPresent() && conf.get() == ButtonType.OK) {
               Stage stage;
            Parent root;
            stage = (Stage) addPartSaveBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
