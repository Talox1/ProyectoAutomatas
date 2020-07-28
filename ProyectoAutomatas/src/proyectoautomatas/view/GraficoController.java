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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import proyectoautomatas.model.Carga;

public class GraficoController {
    Queue<Carga> listaCargas = new LinkedList<Carga>();
    Queue<Carga> listaCargasAux2 = new LinkedList<Carga>();
    Queue<Circle> listCircle = new LinkedList<Circle>();
    
    private double [][] matrizCampoElectrico ;
    
    Circle circle;
    
    String entradaTexto ;
    
    int x1 = 175; 
    int y1 = 100;
    int x2 = 1175;
    int y2 = 700;
    
    @FXML
    private AnchorPane root2;
    
    @FXML
    private AnchorPane anchorPane;

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
    void OnMouseClickedTablaResultados(MouseEvent event) throws IOException {
        System.out.println("Cambiando vista a tabla resultados: lista vacia? "+listaCargas.isEmpty());
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Tabla.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        TablaController tablaController = loader.<TablaController>getController();
//        graficoController.setEntradaTexto(entradaTexto.getText());
        
        tablaController.setListCargas(this.listaCargasAux2);
        
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) auxBtn.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }
    
    public void setEntradaTexto(String texto){
        this.entradaTexto = texto;
    }
    public void setListaCargas(Queue<Carga> listaCargas){
        this.listaCargas = listaCargas;
        this.listaCargasAux2 = listaCargas;
        
    }
    public void dibujarCargas(){
        //System.out.println("grafico controller "+listaCargas);
        //System.out.println("graph contrller 112>>> is lista empt y "+listaCargas.isEmpty());
        while(!this.listaCargasAux2.isEmpty()){
            ellipse(this.listaCargasAux2.peek().getPosX(), this.listaCargasAux2.peek().getPosY(), 4, 4);
            this.listaCargasAux2.poll();
        }
        System.out.println("graph contrller 117>>> is lista empt y "+listaCargas.isEmpty());
            
    }
    
    void ellipse(int xm, int ym, int a, int b){
        int dx = 0, dy = b; /* im I. Quadranten von links oben nach rechts unten */
        long a2 = a*a, b2 = b*b;
        long err = b2-(2*b-1)*a2, e2; /* Fehler im 1. Schritt */
        double tamano2 = Double.parseDouble("3");
        Circle circle2 = new Circle();
        Circle circle3 = new Circle();
        Circle circle4 = new Circle();
        
        //System.out.println("graph contrller 130>>> is lista empt y "+listaCargas.isEmpty());
        do {
            
            circle = new Circle();
            circle.setFill(getColor(this.listaCargasAux2.peek().getColor()));
            circle.setStroke(Color.TRANSPARENT);
            circle.setCenterX(xm+dx);
            circle.setCenterY(ym+dy);
            circle.setRadius(tamano2);
            listCircle.add(circle);
            anchorPane.getChildren().addAll(circle);
            
            //setPixel(xm+dx, ym+dy); /* I. Quadrant */
            circle2 = new Circle();
            circle2.setFill(getColor(this.listaCargasAux2.peek().getColor()));
            circle2.setStroke(Color.TRANSPARENT);
            circle2.setCenterX(xm-dx);
            circle2.setCenterY(ym+dy);
            circle2.setRadius(tamano2);
            listCircle.add(circle2);
            anchorPane.getChildren().addAll(circle2);
            //setPixel(xm-dx, ym+dy); /* II. Quadrant */
            circle3 = new Circle();
            circle3.setFill(getColor(this.listaCargasAux2.peek().getColor()));
            circle3.setStroke(Color.TRANSPARENT);
            circle3.setCenterX(xm-dx);
            circle3.setCenterY(ym-dy);
            circle3.setRadius(tamano2);
            listCircle.add(circle3);
            anchorPane.getChildren().addAll(circle3);
            //setPixel(xm-dx, ym-dy); /* III. Quadrant */
            circle4 = new Circle();
            circle4.setFill(getColor(this.listaCargasAux2.peek().getColor()));
            circle4.setStroke(Color.TRANSPARENT);
            circle4.setCenterX(xm+dx);
            circle4.setCenterY(ym-dy);
            circle4.setRadius(tamano2);
            listCircle.add(circle4);
            anchorPane.getChildren().addAll(circle4);
            //setPixel(xm+dx, ym-dy); /* IV. Quadrant */
            
            e2 = 2*err;
            if (e2 <  (2*dx+1)*b2) { dx++; err += (2*dx+1)*b2; }
            if (e2 > -(2*dy-1)*a2) { dy--; err -= (2*dy-1)*a2; }
        } while (dy >= 0);
        //System.out.println("graph contrller 175>>> is lista empt y "+listaCargas.isEmpty());
    }
    
    
    public Color getColor(String color){
        switch(listaCargas.peek().getColor()){
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
            case "green":
                return Color.GREEN;
        }
        return Color.RED;
    }
    
    public void setInfoCampoElectrico (double [][] matrizCampoElectrico){
        this.matrizCampoElectrico = matrizCampoElectrico;
        drawCampoElectrico();
    }
    
    private void drawCampoElectrico(){
        int filas = matrizCampoElectrico.length;
        int columnas = matrizCampoElectrico[0].length;
        
        
        int longitudX = x2 - x1;
        int longitudY = y2 - y1;
        
        double longitudRectX = longitudX / columnas;
        double longitudRectY = longitudY / filas;
        int x1Rect = 0;
        int y1Rect = 0;
        int x2Rect = (int)longitudRectX;
        int y2Rect = (int)longitudRectY;
        System.out.println("===========================================================================================================");
        System.out.println("Numero de filas "+filas+" Numero de coluimnas "+columnas);
        System.out.println("Longitud x"+longitudX+ " longitud Y: "+longitudY);
        System.out.println("tam rec X "+longitudRectX+" tam rec y "+longitudRectY);
        
        double valorMax = 0;
        double valorMin = 100000;
        DecimalFormat df = new DecimalFormat("#.00000000");
        
        for(int y = 0 ; y < filas; y++){
            for (int x = 0 ; x < columnas; x++){
                //System.out.print("|"+df.format(matrizCampoElectrico[y][x]));
                if(matrizCampoElectrico[x][y] > valorMax)
                    valorMax = matrizCampoElectrico[y][x];
                if(matrizCampoElectrico[y][x] < valorMin)
                    valorMin = matrizCampoElectrico[y][x];
            }
            //System.out.print("\tmayor "+valorMax+" minimo "+valorMin);
            //System.out.println("\n");
        }
//        System.out.println("================================================================");
        double rangoValores = (valorMax - valorMin)/4; // para saber los limites de los 4 valores bajo,medio bajo, medio alto y alto
        System.out.println("rango valores "+ rangoValores);
//        
        System.out.println("Valor maximo = "+df.format(valorMax)+" valor minimo: "+df.format(valorMin));
//        
        System.out.println("\nbajo= "+valorMin+" < "+(valorMin + ( rangoValores * 1 ) ));
        System.out.println("medio bajo = "+(valorMin + ( rangoValores * 1 ) )*1+" < "+(valorMin + ( rangoValores * 2 ) ));
        System.out.println("medio alto = "+(valorMin + ( rangoValores * 2 ) )+" < "+(valorMin + ( rangoValores * 3 ) ));
        System.out.println("alto = "+(valorMin + ( rangoValores * 3 ) )+" < "+valorMax);
        
        for(int y = filas -1 ; y >= 0; y--){
            //System.out.println("======================================================");
            //System.out.println("fila >>>"+y);
            for (int x = columnas -1; x >= 0; x--){
                //System.out.println("columna>>>"+x);
                //System.out.print("|"+matrizCampoElectrico[y][x]);
                
                
                Rectangle r1 = new Rectangle(x1Rect,y1Rect,x2Rect,y2Rect);
                r1.setStroke(null);
                //System.out.println(x1Rect+":"+y1Rect+":"+x2Rect+":"+y2Rect);
                if(matrizCampoElectrico[x][y] >= valorMin && matrizCampoElectrico[x][y] <= (valorMin + ( rangoValores * 1 ) )){
                    r1.setFill(Color.rgb(58,132,56));//bajo, verde
//                    System.out.println("verde "+df.format(matrizCampoElectrico[y][x]));
                }else if(matrizCampoElectrico[x][y] >= (valorMin + ( rangoValores * 1 ) ) *1 && matrizCampoElectrico[x][y] <= (valorMin + ( rangoValores * 2 ) )){
                    r1.setFill(Color.rgb(255,255, 0));// medio bajo, amarillio
//                    System.out.println("amarillo "+df.format(matrizCampoElectrico[y][x]));
                }else if(matrizCampoElectrico[x][y] >= (valorMin + ( rangoValores * 2 ) ) && matrizCampoElectrico[x][y] <= (valorMin + ( rangoValores * 3 ) )){
                    r1.setFill(Color.rgb(255,165,0));// medio alto, anaranjado
//                   System.out.println("anaranjado "+df.format(matrizCampoElectrico[y][x]));
                }else if(matrizCampoElectrico[x][y] >= (valorMin + ( rangoValores * 3 ) ) && matrizCampoElectrico[x][y] <= valorMax){
                    r1.setFill(Color.rgb(255,63,0));//  alto, rojo
//                    System.out.println("rojo "+df.format(matrizCampoElectrico[y][x]));
                }else{
                    r1.setFill(Color.rgb(153,0,0));
                    
//                    System.out.println("no color "+df.format(matrizCampoElectrico[y][x]));
                
                }
                r1.setStrokeWidth(3);
                anchorPane.getChildren().addAll(r1);
                
                x1Rect += longitudRectX;
                
                //System.out.println("\nCorrd x1 :"+x1Rect+" y1: "+y1Rect+"   x2 :"+x2Rect+" y2: "+y2Rect);
            }
            x1Rect = 0;
            y1Rect += longitudRectY;
            
            //System.out.println("\n");
        }
    }

    
}
