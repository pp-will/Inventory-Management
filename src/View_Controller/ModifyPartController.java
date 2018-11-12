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
    
    Inventory inventory;
    
    Inhouse tempIn;
    Outsourced tempOut;
    private Part part;
    private Part part1;

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
        try {
        //for confirming add part
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Modify Part");
        confirm.setHeaderText("This part will be modified");
        confirm.initModality(Modality.NONE);
        confirm.setContentText("Are you sure?");
        confirm.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> conf = confirm.showAndWait();
        
        String name = partNameField.getText();
        double price = parseDouble(partPriceField.getText());
        int inventory = parseInt(partInvField.getText());
        int min = parseInt(partMinField.getText());
        int max = parseInt(partMaxField.getText());
        int partID = parseInt(partIDLabel.getText());
        if(inhouseRadio.isSelected()) {
            variableLabel.setText("Machine ID");
            int machineID = parseInt(variableField.getText());
            System.out.println(machineID);
            if(max < min) {
                errorLabel.setText("max must be greater than min");
            }
            
            
            
            
        } else if (outsourcedRadio.isSelected()) {
            variableLabel.setText("Company Name");
            if(max < min) {
                errorLabel.setText("max must be less than min");
            }
            
            
            
           
        } 
        
        if(conf.isPresent() && conf.get() == ButtonType.OK) {
            
            if(this.part instanceof Inhouse) {
                int machineID = parseInt(variableField.getText());
                tempIn = (Inhouse)this.part;
                Inhouse inhouse = new Inhouse(partID, name, price, inventory, min, max, machineID);
                Inventory.addPart(inhouse);
                
                
            } else if (this.part instanceof Outsourced) {
                tempOut = (Outsourced)this.part;
                String companyName = variableField.getText();
                Outsourced outsourced = new Outsourced(partID, name, price, inventory, min, max, companyName);
                Inventory.addPart(outsourced);
                
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
        } catch (IOException e) {
            //for errors
        Alert err = new Alert(Alert.AlertType.ERROR);
        err.setTitle("Modify Part");
        err.setHeaderText("Something went wrong");
        err.getDialogPane().setPrefSize(350, 200);
        err.setContentText(e.getMessage());
        Optional<ButtonType> error = err.showAndWait();
        if(error.isPresent() && error.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) modifyPartSaveBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
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
