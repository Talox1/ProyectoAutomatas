/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author talob
 */
public class AnalizadorSemantico {
    String entrada;
    private static Queue<Carga> listaCargas = new LinkedList<Carga>();
    int posInicial = 0;
    int posFinal = 0;
    public AnalizadorSemantico(){}
    
    public AnalizadorSemantico(String entrada){
        this.entrada = entrada;
    }
    
    //"grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; }";
    //grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; } , carga : { x: 20 ; y: 3 ; v: 4 ; t: + ; n: nC ; color: blue ; }
    public void identificarDatos(){
        String [] entradaStrings = entrada.split(" ");
        

        findCargasP(entradaStrings);
        findCargas(entrada);
    }

    public void findCargasP(String[] entradaStrings){
        String auxCargas ="" ;
        String[] datosCargas;
        for (int i = 0; i < entradaStrings.length; i++){
            if(entradaStrings[i].equals("{"))
                posInicial = i;
            else if(entradaStrings[i].equals("}")){
                posFinal = i;
                break;
            }
        }

        for (int i = posInicial; i < posFinal; i++)
           auxCargas = auxCargas+""+ entradaStrings[i];
        
        auxCargas = auxCargas.replace("{", "").replace("[", "").replace("]", "");
        datosCargas = auxCargas.split(",");

        for (int i = 0; i < datosCargas.length; i++){
            //System.out.println(datosCargas[i].replace("cargaP:", ""));
            String cargaP = datosCargas[i].replace("cargaP:", "");
            crearCargasP(cargaP);
        }
    }

    public void crearCargasP(String  cargaP){
        //System.out.println("=====================================");
        String [] cargas =cargaP.split(";");
        Carga carga = new Carga();
        carga.setTipo("cargaP");
        /*for (int i = 0; i < cargas.length; i++){
            System.out.println(cargas[i].replace("x:", "").replace("y:", "").replace("color:", ""));
        }*/
        carga.setPosX(Integer.parseInt(cargas[0].replace("x:", "").replace("y:", "").replace("color:", "")));
        carga.setPosY(Integer.parseInt(cargas[1].replace("x:", "").replace("y:", "").replace("color:", "")));
        carga.setColor(cargas[2].replace("x:", "").replace("y:", "").replace("color:", ""));
        carga.setValor(1 * Math.pow(10,-9));
        
        
        listaCargas.add(carga);
    }


    public void findCargas(String entrada){
        String [] entradaStrings = entrada.split(",");
        for (int i = posInicial; i < entradaStrings.length; i++){
            crearCargas(entradaStrings[i].replace(" ", "").replace("{", "").replace("}", "").replace("carga:", ""));
        }

    }

    public void crearCargas(String carga){
        //System.out.println("=====================================");
        // System.out.println(carga);

        String [] cargas =carga.split(";");
        Carga carga2 = new Carga();
        carga2.setTipo("carga");
        /*for (int i = 0; i < cargas.length; i++){
            System.out.println(cargas[i].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", ""));
        }*/
        carga2.setPosX(Integer.parseInt(cargas[0].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", "")));
        carga2.setPosY(Integer.parseInt(cargas[1].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", "")));
        
        carga2.setSigno(cargas[3].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", ""));
        carga2.setNomenclatura(cargas[4].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", ""));
        carga2.setValor(Integer.parseInt(cargas[2].replace("x:", "").replace("y:", "").replace("color:", "").replace("v:", "").replace("t:", "").replace("n:", "")));
        
        carga2.setColor(cargas[5].replace("x:", "").replace("y:", "").replace("color:", "".replace("v:", "").replace("t:", "").replace("n:", "")));
        

        listaCargas.add(carga2);
    }

    public Queue<Carga> getListCargas(){
        return listaCargas;
    } 
    
}