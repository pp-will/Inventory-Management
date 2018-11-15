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
import static java.lang.Double.parseDouble;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class ModifyPartController implements Initializable {

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
    private Label partIDLabel;

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
    private Label errorLabel;

    @FXML
    private Button modifyPartSaveBtn;

    @FXML
    private Button modifyPartCancelBtn;
    
    Inhouse inhouse;
    Outsourced outsourced;
    
    Inhouse tempIn;
    Outsourced tempOut;
    private Part part;
    private Part part1;
    private double price;
    private int inventory, min, max, machineID, partID;
    private String partName;

    @FXML
    void modifyPartCancelBtnHandler(ActionEvent event) throws IOException{
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("This part will not be added");
        alert.setContentText("Are you ok with this?");
        alert.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) modifyPartCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("OK PRESSED");
            if(this.part instanceof Inhouse) {
                tempIn = (Inhouse)this.part;
                Inventory.addPart(tempIn);
            } else if (this.part instanceof Outsourced) {
                tempOut = (Outsourced)this.part;
                Inventory.addPart(tempOut);
            }
         } else {
            if(this.part instanceof Inhouse) {
                tempIn = (Inhouse)this.part;
                setPart(tempIn);
            } else if(this.part instanceof Outsourced) {
                tempOut = (Outsourced)this.part;
                setPart(tempOut);
            }
            alert.close();
           
            
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifyPartSave(ActionEvent event) throws IOException {
       
        //for confirming add part
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Modify Part");
        confirm.setHeaderText("This part will be modified");
        confirm.initModality(Modality.NONE);
        confirm.setContentText("Are you sure?");
        confirm.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> conf = confirm.showAndWait();
        
        String partName = partNameField.getText();
        
        
        //alert dialog if exception
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        
        try {
            price = parseDouble(partPriceField.getText());
            
        } catch (NumberFormatException e) {
            alert.setHeaderText("Price must be in the following format:");
            alert.setContentText("X.XX");
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
            variableLabel.setText("Machine ID");
            
            try {
             machineID = parseInt(variableField.getText()); 
             
             
            } catch (NumberFormatException e) {
                alert.setHeaderText("Machine ID must be an integer");
            
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            }
            if(max < min) {
                errorLabel.setText("max must be greater than min");
            } else if (inventory < min || inventory > max || inventory < min && inventory > max) {
            alert.setHeaderText("Inventory must be greater than min, and less than max");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            
        } else {
                alert.setHeaderText("Error:");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } 
            }else if (outsourcedRadio.isSelected()) {
            variableLabel.setText("Company Name");
            if(max < min) {
                errorLabel.setText("max must be less than min");
            }
             
        } 
        
        if(conf.isPresent() && conf.get() == ButtonType.OK) {
            
            if(this.part instanceof Inhouse) {
                partID = parseInt(partIDLabel.getText());
                tempIn = (Inhouse)this.part;
                tempIn.setPartID(partID);
                tempIn.setName(partName);
                tempIn.setPrice(price);
                tempIn.setInStock(inventory);
                tempIn.setMin(min);
                tempIn.setMax(max);
                tempIn.setMachineID(machineID);
                //Inhouse inhouse = new Inhouse(partID, partName, price, inventory, min, max, machineID);
                Inventory.addPart(tempIn);
                
                
            } else if (this.part instanceof Outsourced) {
                tempOut = (Outsourced)this.part;
                String companyName = variableField.getText();
                partID = parseInt(partIDLabel.getText());
                tempOut.setPartID(partID);
                tempOut.setName(partName);
                tempOut.setPrice(price);
                tempOut.setInStock(inventory);
                tempOut.setMin(min);
                tempOut.setMax(max);
                tempOut.setCompanyName(companyName);
                //Outsourced outsourced = new Outsourced(partID, partName, price, inventory, min, max, companyName);
                Inventory.addPart(tempOut);
                
            }
            
            
            Stage stage;
            Parent root;
            stage = (Stage) modifyPartSaveBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            
            if(this.part instanceof Inhouse) {
               tempIn = (Inhouse)this.part;
               
               setPart(tempIn);
               
            } else if (this.part instanceof Outsourced) {
                tempOut = (Outsourced)this.part;
                setPart(tempOut);
               
            }
            confirm.close();
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
    
    /**
     *
     * @param part
     */
    public void setPart(Part part) throws IOException {
        
        this.part = part;
        partIDLabel.setText(Integer.toString(part.getPartID()));
        partNameField.setText(part.getName());
        partInvField.setText(Integer.toString(part.getInStock()));
        partPriceField.setText(Double.toString(part.getPrice()));
        partMinField.setText(Integer.toString(part.getMin()));
        partMaxField.setText(Integer.toString(part.getMax()));
        
        //determine if inhouse or outsourced
        if(part instanceof Inhouse) {
            variableField.setText(Integer.toString(Inhouse.getMachineID()));
            variableLabel.setText("Machine ID");
            inhouseRadio.setSelected(true);
            
        } else if (part instanceof Outsourced) {
            variableField.setText(Outsourced.getCompanyName());
            variableLabel.setText("Company Name");
            outsourcedRadio.setSelected(true);
        } 
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //getters to prepopulate screen
    }    

    
    
}
