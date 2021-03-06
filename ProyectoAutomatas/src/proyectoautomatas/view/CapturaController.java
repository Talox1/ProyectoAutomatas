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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyectoautomatas.ProyectoAutomatas;
import proyectoautomatas.model.*;

public class CapturaController {
    String entrada;
    String noWordDiccionary = "";
    Queue<Carga> listaCargas = new LinkedList<Carga>();
    String[] caracterInvalido;
    AnalizadorSemantico analizadorSemantico;
    AnalizadorLexico analizadorLexico;
    AnalizadorSintactico analizadorSintactico;
    private double [][] matrizCampoElectrico ;
    
    
    
   
    
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
    void OnMouseClickedReset(MouseEvent event) {
        entradaTexto.setText("");

    }

    @FXML
    void OnMouseClickedEvaluar(MouseEvent event) throws IOException {
        //grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; }
        
        if(entradaTexto.getText().length() > 0){
            String entrada = limpiarCadena(entradaTexto.getText());
            //System.out.println(entrada);
            if(isLexemaValid(entrada)){
                System.out.println("Lexema valido");
                if(isSintacticoValid(entrada)){
                    
                    System.out.println("Sintactico valido");
                    
                    //String entrada = "grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; } , carga : { x: 20 ; y: 3 ; v: 4 ; t: + ; n: nC ; color: blue ; }";
                    analizadorSemantico = new AnalizadorSemantico(entrada);
                    if(analizadorSemantico.identificarDatos()){
                        ArrayList<Carga> aux = new ArrayList<Carga>();
                        listaCargas = analizadorSemantico.getListCargas();
                        
                       

                        CampoElectrico campoElectrico = new CampoElectrico();
                        campoElectrico.setCargas(listaCargas);
                        campoElectrico.crearCampoElectrico();
                        this.matrizCampoElectrico = campoElectrico.getInfoCampoElectrico();
                        System.out.println("captura controller "+listaCargas);
                    }else{
                        Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                        dialogAlert2.setTitle("Advertencia, error en rangos");
                        dialogAlert2.setContentText("Rango no permitido en una de las cargas: ");
                        dialogAlert2.initStyle(StageStyle.UTILITY);
                        dialogAlert2.showAndWait();
                    }
                    
                    
                    //OnMouseClickedGrafico(event);
                    
                }else{
                    Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                    dialogAlert2.setTitle("Advertencia, Sintaxis no valida");
                    dialogAlert2.setContentText("Caracter invalido: "+caracterInvalido[1]+ "\nSe esperaba: "+caracterInvalido[0]);
                    dialogAlert2.initStyle(StageStyle.UTILITY);
                    dialogAlert2.showAndWait();
                }
            }else{
                System.out.println("Lexema no valido");
                System.out.println("Palabras no encontradas en el diccinario: "+noWordDiccionary);
                
                Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert2.setTitle("Advertencia, lexema no valido");
                dialogAlert2.setContentText("Palabras no encontradas en el diccinario: "+noWordDiccionary);
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            }
        }else{
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Inserte algo");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }
        
    }

    @FXML
    void OnMouseClickedGrafico(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Grafico.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        GraficoController graficoController = loader.<GraficoController>getController();
//        graficoController.setEntradaTexto(entradaTexto.getText());
        graficoController.setInfoCampoElectrico(matrizCampoElectrico);
        System.out.println("128 capt conttroller >> is lista cargas empty?"+listaCargas.isEmpty());
        graficoController.setListaCargas(listaCargas);
        graficoController.dibujarCargas();
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
    void OnMouseClickedTablaDatos(MouseEvent event) throws IOException {
        System.out.println("Cambiando vista a tabla resultados");
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Tabla.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        TablaController tablaController = loader.<TablaController>getController();
//        graficoController.setEntradaTexto(entradaTexto.getText());
        
        tablaController.setListCargas(listaCargas);
        
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) btnEvaluar.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }
    
    public void setEntradaTexto(String entradaTexto){
        this.entrada = entradaTexto; 
   }
    
    private boolean isLexemaValid(String entrada){
        boolean isLexemaValid = false;
        
        Queue<String> queueLexemas = new LinkedList<String>();
        
        //System.out.println("142 >> captura controller: "+entrada);
        analizadorLexico = new AnalizadorLexico(entrada);
        queueLexemas = analizadorLexico.getLexemas();

        //System.out.println(queueLexemas);
        while(!queueLexemas.isEmpty()) { 

            if(queueLexemas.size() == 1){
                System.out.println("Evaluando palabras que no estan en el diccionario");
                if(queueLexemas.peek().length() == 0){
                    //System.out.println("Entrada valida");
                    isLexemaValid = true;
                }else{
                    isLexemaValid = false;
                    noWordDiccionary = queueLexemas.peek();
                }
            }
            queueLexemas.poll();
            
            

        }
        
        
        return isLexemaValid;
    }
    
    
    public boolean isSintacticoValid(String entrada){
        boolean isSintacticoValid = false;
        analizadorSintactico = new AnalizadorSintactico(entrada);
        analizadorSintactico.start();
        isSintacticoValid = analizadorSintactico.isEntradaValid();
        if(!isSintacticoValid)
            caracterInvalido = analizadorSintactico.getCaracterInvalido();
        return isSintacticoValid;
    }
    public String limpiarCadena(String cadena) {
        return cadena.replaceAll("\n", "").replaceAll("\t", ""); 
    }
    
    public String isSemanticoValid(){
        String message = "asdasd";
        
        return message;
    }
}
