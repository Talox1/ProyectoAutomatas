package proyectoautomatas.view;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proyectoautomatas.model.CampoElectrico;
import proyectoautomatas.model.Carga;
import java.util.Arrays;
public class TablaController {
    private Queue<Carga> listaCargas = new LinkedList<Carga>();
    private double [][] matrizCampoElectrico ;
    
    
    @FXML
    private AnchorPane root2;

    @FXML
    private Label label;

    @FXML
    private TableView tablaDatos;
    
    @FXML
    private TableColumn verCarga;

    @FXML
    private TableColumn verPunto;

    @FXML
    private TableColumn verDistancia;

    @FXML
    private TableColumn verComponenteX;

    @FXML
    private TableColumn verComponenteY;

    @FXML
    private TableColumn verAngulo;

    @FXML
    private TableColumn verFuerzaResultante;
    
    @FXML
    void OnMouseClickedCaptura(MouseEvent event) throws IOException {
         Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Captura.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        CapturaController controller = loader.<CapturaController>getController();
        //controller.setEntradaTexto(entradaTexto);//
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) tablaDatos.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void OnMouseClickedGrafico(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Grafico.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        GraficoController graficoController = loader.<GraficoController>getController();
//        graficoController.setEntradaTexto(entradaTexto.getText());
        graficoController.setInfoCampoElectrico(matrizCampoElectrico);
        graficoController.setListaCargas(listaCargas);
        graficoController.dibujarCargas();
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) tablaDatos.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void OnMouseClickedTablaDatos(MouseEvent event) {

    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }
    
    public void setListCargas(Queue<Carga> listaCargas){
        String [][] datosFuerzas;
        System.out.println("Set list cargas");
        this.listaCargas = listaCargas;
        CampoElectrico campoElectrico = new CampoElectrico();
        datosFuerzas = campoElectrico.calcularDatos(listaCargas);
        
        
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(datosFuerzas));
        ObservableList<String> row = FXCollections.observableArrayList();
        
        
        for (int i = 0; i < datosFuerzas.length; i++){
            for (int j = 0; j< datosFuerzas[0].length; j++){
                System.out.print("|"+datosFuerzas[i][j]);
                
                row.add(datosFuerzas[i][j]);
                //System.out.println(""+row.get(i));
            }
//            
//            data.add(row);
//            System.out.println("");
//            tablaDatos.setItems(data);
        }
        //FINALLY ADDED TO TableView
        System.out.println("data: "+data);
        
        
//        tablaDatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
//                //Check whether item is selected and set value of selected item to Label
//                if(tablaDatos.getSelectionModel().getSelectedItem() != null) {    
//                   System.out.println(newValue);
//                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
//                   verCarga.setText(values[0]);
//                   verPunto.setText(values[1]);
//                   verDistancia.setText(values[2]);
//                   verComponenteX.setText(values[3]);
//                   verComponenteY.setText(values[4]);
//                   verAngulo.setText(values[5]);
//                   verFuerzaResultante.setText(values[6]);
//                }
//            }
//        });
    }
    

}
