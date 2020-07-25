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
import java.util.LinkedList;
import java.util.Queue;
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
import proyectoautomatas.model.*;

public class CapturaController {
    String entrada;
    String noWordDiccionary = "";
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
        //grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; }
        Queue<Carga> listaCargas = new LinkedList<Carga>();
        if(entradaTexto.getText().length() > 0){
            String entrada = limpiarCadena(entradaTexto.getText());
            //System.out.println(entrada);
            if(isLexemaValid(entrada)){
                System.out.println("Lexema valido");
                if(isSintacticoValid(entrada)){
                    System.out.println("Sintactico valido");
                    
                    //String entrada = "grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; } , carga : { x: 20 ; y: 3 ; v: 4 ; t: + ; n: nC ; color: blue ; }";
                    AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(entrada);
                    analizadorSemantico.identificarDatos();

                    listaCargas = analizadorSemantico.getListCargas();
                    int contador =1 ;
                    while(!listaCargas.isEmpty()){
                        System.out.println("Carga n°: "+contador+" tipo: "+listaCargas.peek().getTipo());
                        System.out.println("\tX: "+listaCargas.peek().getPosX()+" Y: "+listaCargas.peek().getPosY()+" color: "+listaCargas.peek().getColor());
                        if(listaCargas.peek().getTipo().equals("carga")){
                            System.out.println("\tv: "+listaCargas.peek().getValor()+" t: "+listaCargas.peek().getSigno()+" nomenclatura: "+listaCargas.peek().getNomenclatura()+" color: "+listaCargas.peek().getColor());
                        }
                        listaCargas.poll();
                        contador ++;
                    }
                }
            }else{
                System.out.println("Lexema no valido");
                System.out.println("Palabras no encontradas en el diccinario: "+noWordDiccionary);
            }
        }
        
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
    
    private boolean isLexemaValid(String entrada){
        boolean isLexemaValid = false;
        
        Queue<String> queueLexemas = new LinkedList<String>();
        
        //System.out.println("142 >> captura controller: "+entrada);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(entrada);
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
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(entrada);
        analizadorSintactico.start();
        isSintacticoValid = analizadorSintactico.isEntradaValid();
        return isSintacticoValid;
    }
    public String limpiarCadena(String cadena) {
        return cadena.replaceAll("\n", "").replaceAll("\t", ""); 
    }
}
