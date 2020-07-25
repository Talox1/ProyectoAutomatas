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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proyectoautomatas.ProyectoAutomatas;

public class CapturaController {
    String entrada;
    @FXML
    private TextArea entradaTexto;
    @FXML
    private AnchorPane root2;

    @FXML
    private Label label;

    @FXML
    private Button btnImportar;

    @FXML
    private Button btnEvaluar;

    @FXML
    void OnMouseClickedEvaluar(MouseEvent event) {

    }

    @FXML
    void OnMouseClickedGrafico(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Grafico.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        GraficoController controller = loader.<GraficoController>getController();
        controller.setEntradaTexto(entradaTexto.getText());
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) btnEvaluar.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void OnMouseClickedImportar(MouseEvent event) throws IOException {
       
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File fileChoice = chooser.showOpenDialog(btnImportar.getScene().getWindow());
        try {
            BufferedReader in;
            in = new BufferedReader(new FileReader(fileChoice));
            String line = in.readLine();
            String text = line;
            while (line != null) {
                
                line = in.readLine();
                text = text +"\n"+line;
                
            }
            entradaTexto.setText(text);
        } catch (Error ex) {
            System.out.println(ex);
        } 
    }

    @FXML
    void OnMouseClickedTablaDatos(MouseEvent event) {

    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }
    
    public void setEntradaTexto(String entradaTexto){
        this.entrada = entradaTexto; 
   }
}
