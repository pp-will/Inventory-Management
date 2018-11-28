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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Toggle;
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
    static boolean active = true;
    public int partId;
    private String partName;
    private double price;
    private int inventory, min, max, machineID;
    

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
        
      if(inhouseRadio.isSelected()) {
          variableLabel.setText("Machine ID");
      } else if (outsourcedRadio.isSelected()) {
          variableLabel.setText("Company Name");
      }
        /*inhouseRadio.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
           if(isSelected) {
               variableLabel.setText("Machine ID");
               System.out.println("machine");
           } 
        });
        outsourcedRadio.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
           if(isSelected) {
               variableLabel.setText("Company Name");
               System.out.println("company");
           } 
        });*/
    }
         
    @FXML
    void addPartSave(ActionEvent event) throws IOException {
        
        //for confirming add part
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Add Part");
        confirm.setHeaderText("This part will be added");
        confirm.setContentText("Press OK to save");
        confirm.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> conf = confirm.showAndWait();
        
        Random random = new Random();
        int[] id = new int[100];
        for(int i = 0; i < id.length; i++) {
            id[i] = random.nextInt(1000);
        }
        
        //alert dialog if exception
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        
        partName = partNameField.getText();
        try {
            price = parseDouble(partPriceField.getText());
        } catch (NumberFormatException e) {
            alert.setHeaderText("Price must be in the following format: X.XX");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        try {
            inventory = parseInt(partInvField.getText());
        } catch (NumberFormatException e) {
            alert.setHeaderText("Inventory must be an integer");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        try {
            min = parseInt(partMinField.getText());
        } catch (NumberFormatException e) {
            alert.setHeaderText("Min must be an integer");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        try {
            max = parseInt(partMaxField.getText());
        } catch (NumberFormatException e) {
            alert.setHeaderText("Max must be an integer");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        if(inhouseRadio.isSelected()) {
            partId = id[random.nextInt(id.length)];
            if(max < min) {
                //errorLabel.setText("max must be greater than min");
                alert.setHeaderText("Max must be greater than min");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } else if (inventory < min || inventory > max || inventory < min && inventory > max){
                //errorLabel.setText("inventory must be between max and min");
                alert.setHeaderText("Inventory must be between min and max");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } else {
            
            try {
               machineID = parseInt(variableField.getText()); 
            } catch (NumberFormatException e) {
                alert.setHeaderText("Machine ID must be an integer");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            }
            
            
           Inhouse inhouse = new Inhouse(partId, partName, price, inventory, min, max, machineID);
           Inventory.addPart(inhouse);
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
        } else if (outsourcedRadio.isSelected()) {
            partId = id[random.nextInt(id.length)];
            
            String companyName = variableField.getText();
            if(max < min) {
                //errorLabel.setText("max must be greater than min");
                alert.setHeaderText("Max must be greater than min");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } else if (inventory < min || inventory > max || inventory < min && inventory > max){
                //errorLabel.setText("inventory must be between max and min");
                alert.setHeaderText("Inventory must be between min and max");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } else {
            Outsourced outsourced = new Outsourced(partId, partName, price, inventory, min, max, companyName);
            Inventory.addPart(outsourced);
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
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inhouseRadio.setSelected(true);
        variableLabel.setText("Machine ID");
    }    
    
    
}
