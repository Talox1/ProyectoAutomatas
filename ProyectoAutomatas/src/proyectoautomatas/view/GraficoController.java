/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.view;

/**
 *
 * @author talob
 */


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GraficoController {
    String entradaTexto ;
    @FXML
    private AnchorPane root2;

    @FXML
    private Label label;

    @FXML
    private Button auxBtn;

    @FXML
    void OnMouseClickedCaptura(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Captura.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        CapturaController controller = loader.<CapturaController>getController();
        controller.setEntradaTexto(entradaTexto);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) auxBtn.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void OnMouseClickedTablaResultados(MouseEvent event) {

    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }
    
    public void setEntradaTexto(String texto){
        this.entradaTexto = texto;
    }
}
