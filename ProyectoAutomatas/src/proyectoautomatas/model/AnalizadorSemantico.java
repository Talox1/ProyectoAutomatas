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
    
    boolean isSemanticoValid = false;
    int x1 = 175; 
    int x2 = 1175;
    int y1 = 100;
    int y2 = 700;
    int contCP = 0;
    int contCN = 0;
    public AnalizadorSemantico(){}
    
    public AnalizadorSemantico(String entrada){
        this.entrada = entrada;
    }
    
    //"grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; }";
    //grafico : { [ cargaP : x: 10 ; y: 20 ; color: blue ; ] , [ cargaP : x: 40 ; y: 10 ; color: red ; ] } , carga : { x: 20 ; y: 3 ; v: 4 ; t: - ; n: nC ;  color: blue ; } , carga : { x: 20 ; y: 3 ; v: 4 ; t: + ; n: nC ; color: blue ; }
    public boolean identificarDatos(){
        String [] entradaStrings = entrada.split(" ");
        

        findCargasP(entradaStrings);
        if(isSemanticoValid){
            System.out.println("Semantico valido, primera fase");
            findCargas(entradaStrings);
        }
        else{
            //System.out.println("Semantico no valido");
            return isSemanticoValid;
        }
        return isSemanticoValid;
    }

    public boolean findCargasP(String[] entradaStrings){
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
            if(!isSemanticoValid)
                break;
        }
        return isSemanticoValid;
    }

    public boolean crearCargasP(String  cargaP){
        contCP ++;
        //System.out.println("=====================================");
        String [] cargas =cargaP.split(";");
        Carga carga = new Carga();
        carga.setTipo("cargaP");
        /*for (int i = 0; i < cargas.length; i++){
            System.out.println(cargas[i].replace("x:", "").replace("y:", "").replace("color:", ""));
        }*/
        carga.setPosX(Integer.parseInt(cargas[0].replace("x:", "").replace("y:", "").replace("color:", "")));
        if(carga.getPosX() >= x1 && carga.getPosX() <= x2){
            
            isSemanticoValid = true;
        }else{
            isSemanticoValid = false;
            return isSemanticoValid;
        }
        carga.setPosY(Integer.parseInt(cargas[1].replace("x:", "").replace("y:", "").replace("color:", "")));
        if(carga.getPosY() >= y1 && carga.getPosY() <= y2){
            isSemanticoValid = true;
            //System.out.println("true "+carga.getPosY()+">"+y1+" && "+carga.getPosY()+"<"+y2);
        }else{
            isSemanticoValid = false;
            //System.out.println("false: "+carga.getPosY());
            return isSemanticoValid;
        }
        carga.setColor(cargas[2].replace("x:", "").replace("y:", "").replace("color:", ""));
        carga.setValor(1 * Math.pow(10,-9));
        
        
        listaCargas.add(carga);
        
        
        return isSemanticoValid;
    }


    public void findCargas(String[] entradaStrings){
        String auxCargas ="" ;
        String[] datosCargas;
       
        for (int i = 0; i < entradaStrings.length; i++){
            if(entradaStrings[i].equals("{")){
                posInicial = i+1;
            }
            else if(entradaStrings[i].equals("}")){
                posFinal = i;
                break;
            }
        }
        //System.out.println(entradaStrings[posInicial]+" ||| "+entradaStrings[posFinal]);
        for (int i = posFinal; i < entradaStrings.length; i++)
           auxCargas = auxCargas+""+ entradaStrings[i];

        System.out.println(auxCargas);
        // crearCargas(auxCargas);


        System.out.println("=====================================");
        System.out.println(auxCargas);

        String [] cargas =auxCargas.replace("}", "").replace("{", "").split(",");
     
        for (int i = 0; i < cargas.length; i++){
            //System.out.println(cargas[i]);
            crearCargas(cargas[i]);
        }

    }

    public boolean crearCargas(String carga){
        
        System.out.println("=====================================");
        // System.out.println(carga);
        Carga carga2 = new Carga();
        String [] cargas =carga.replace("carga:", "").replace("x:", "").replace("y:", "").replace("v:", "").replace("t:", "").replace("n:", "").replace("color:", "").replace(" ", "").replace("color:", "").split(";");
        for (int i = 0; i < cargas.length; i++){
             System.out.println("|>>"+cargas[i]+" = "+cargas[i].length());
         }
        if(cargas.length == 6){
            contCN ++;
            int posX = Integer.parseInt(cargas[0]);
            int posY = Integer.parseInt(cargas[1]);
            double valor = Double.parseDouble(cargas[2]);
            String signo =cargas[3];
            String nomenclatura = cargas[4];
            String color = cargas[5];
            
            carga2.setPosX(posX);
            if(carga2.getPosX() > x1 && carga2.getPosX() < x2){
                isSemanticoValid = true;
            }else{
                isSemanticoValid = false;
                return isSemanticoValid;
            }
            carga2.setPosY(posY);
            if(carga2.getPosY() > y1 && carga2.getPosY() < y2){
                isSemanticoValid = true;
            }else{
                isSemanticoValid = false;
                return isSemanticoValid;
            }
//            System.out.println("here");
            carga2.setTipo("carga");
            carga2.setSigno(signo);
            carga2.setNomenclatura(nomenclatura);
            carga2.setValor(valor);
            
            
            carga2.setColor(color);
            
            listaCargas.add(carga2);
        }
        return isSemanticoValid;
    }

    public Queue<Carga> getListCargas(){
        
        System.out.println("Numero de cargasP: "+contCP+" numero de cargasN: "+contCN);
        return listaCargas;
    } 
    
    public boolean isSemanticoValid(){
        
        return isSemanticoValid;
    }
    
}