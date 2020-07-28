/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author talob
 */
public class CampoElectrico {
    private Queue<Carga> listaCargas = new LinkedList<Carga>();
    private double [][] campoElectrico ;
    public void crearCampoElectrico(){
        
        EscribirArchivo archivo = new EscribirArchivo();
        String fileName="data";
        String rutaArchivo= "C:\\Users\\talob\\Desktop\\IDS\\8cuatri\\Compiladores\\3 corte\\ProyectoAutomatas\\ProyectoAutomatas\\src"+fileName;
        
        double [] arrayCargas;
        Carga carga;
        int x1 = 175; 
        int x2 = 1175;
        int y1 = 100;
        int y2 = 700;
        
        int longitudX, longitudY; //longitudes del campoMagnetico

        
        double saltosX, saltosY, puntosMediosX, puntosMediosY;
        double compX, compY, distancia, compXTotal=0, compYTotal=0;
        double fuerzaResultante ;

        double catetoX, catetoY;
        double angulo;
        
        int numCargas = listaCargas.size();              //numero de cargas repartidas en el campo electrico
        //arrayCargas = new double [numCargas];       //inicializar tamaño del arrray
        int numDivisiones = 200; 
        
        campoElectrico = new double [numDivisiones][numDivisiones];    //declaracion tamaño de la matriz

        longitudX = x2-x1;
        longitudY = y2-y1;   

        saltosX= longitudX/numDivisiones;
        saltosY = longitudY/numDivisiones;
        // System.out.println(saltosX+";"+saltosY);

        //saber puntos medios
        puntosMediosX = saltosX/2;
        puntosMediosY = saltosY/2;
        System.out.println("Puntos medios X,Y: "+puntosMediosX+": "+puntosMediosY);
        String opcion = "n"; //para ver los datos de salida
        
        double auxY = y2 - puntosMediosY;
        for(int i = 0; i < numDivisiones; i++){//desde el inicio de y
            double auxX = x1 + puntosMediosX;   
            for(int j = 0; j < numDivisiones; j++){//desde el inicio de x
                // System.out.println(i+":"+j);
                for (Carga auxCarga : listaCargas) {//hacer los calculos para todas las cargas, obtener todas las cargas
                    //System.out.println("Carga X:"+auxCarga.getPosX()+" y: "+auxCarga.getPosY());
                    //System.out.println("Punto medio X: "+auxX+" Y:_ "+auxY);
                    if(auxX > auxCarga.getPosX()){ //si la posicion en x del punto medio es mayor a la posicion x de la carga, 
                        catetoX = auxX - auxCarga.getPosX();//entonces resto 
                    }else{
                       catetoX = auxCarga.getPosX() - auxX;
                    }
                    if(auxY > auxCarga.getPosY()){
                       catetoY = auxY - auxCarga.getPosY();
                    }else{
                       catetoY = auxCarga.getPosY() - auxY;
                    }
                    distancia = Math.hypot(catetoX, catetoY)/100;
                 /*UNA VEZ TENIENDO LA DISTANCIA SE REALIZA LA FORMULA F = KQ/r^2 */
                    double fuerzaCampo = Math.abs( (8.99 * Math.pow(10, 9)) *auxCarga.getValor() ) /Math.pow(distancia, 2); 

                    //para saber el angulo de la fuerza, este angulo me va a servir para saber las componenetes de las fuerzas para despues
                    //hacer las sumatorias
                    angulo= Math.atan2(catetoY, catetoX);//angulo en radianes
                    if(auxX > auxCarga.getPosX()){ //si la posicion en x del punto medio es mayor a la posicion x de la carga, 
                        if(auxCarga.getValor()<0 ){//si el valor de la carga es negativa, la componente en x es negativa por que va hacia la derecha
                           compX = (fuerzaCampo * Math.cos(angulo))*-1;
                        }else{
                           compX = (fuerzaCampo * Math.cos(angulo));
                        }
                    }else{
                        if(auxCarga.getValor()<0 ){//si el valor de la carga es negativa, la componente en x es negativa por que va hacia la derecha
                         compX = (fuerzaCampo * Math.cos(angulo));
                        }else{
                        compX = (fuerzaCampo * Math.cos(angulo))*-1;
                        }
                    }

                    if(auxY > auxCarga.getPosY()){
                        if(auxCarga.getValor()<0){//si la carga es negativa
                            compY = ( fuerzaCampo * Math.sin(angulo)) * -1;//la componente es negativa
                        }else{
                            compY = ( fuerzaCampo * Math.sin(angulo) );
                        }
                    }else{
                        if(auxCarga.getValor()<0){//si la carga es negativa
                        compY = ( fuerzaCampo * Math.sin(angulo));//la componente es negativa
                        }else{
                                 compY = ( fuerzaCampo * Math.sin(angulo) * -1);
                             }
                         }
                                /*AQUI ME QUEDE, FALTA CALCULAR LAS COMPONENESTE DE LAS FUERZAS PARA DESPUES HACER LAS SUMATORIAS
                                Y CALCULAR LA FUERZA RESULTANTE */
                    compXTotal = compXTotal + compX;
                    compYTotal = compYTotal + compY;

                    fuerzaResultante = Math.hypot(compXTotal, compYTotal);
                    campoElectrico[i][j] = fuerzaResultante * (1*Math.pow(10,-6));

                    if(opcion.charAt(0) == 's' || opcion.charAt(0) == 'S'){
                        System.out.println("\n\n\nCoordenada del punto medio: "+auxX+", "+auxY);
                        System.out.println("Distancia de la carga al punto medio: "+distancia+" m");
                        System.out.println("fuerza: "+fuerzaCampo+" N");
                        System.out.println("Cateto X: "+catetoX+"cm\nCateto y: "+catetoY+" cm");
                        System.out.println("angulo: "+angulo+"°");
                        System.out.println("Sumatoria Componente X: "+compXTotal+"\nSumatoria Componente Y: "+compYTotal);
                        System.out.println("\nfuerza resultante: "+fuerzaResultante+" NC");
                    }
                     // compX = fuerzaCampo * Math.
                }//end of foreach
                compXTotal = 0;//se resetea los valores
                compYTotal = 0;
                auxX += saltosX;
             }//end of bucle for j
             auxY -= saltosY;
        }//end of bucle for i
        
//        
        fileName+=".xls";
        archivo.CreateExcel(fileName, campoElectrico);
        
    }
        



    public void setCargas(Queue<Carga> listaCargas){
        this.listaCargas = listaCargas;
    }
    
    public double [][] getInfoCampoElectrico(){
        return campoElectrico;
    }
    
    
    
    public String [][] calcularDatos(Queue<Carga> listaCargasAux){
        //System.out.println("Calculando datos, "+listaCargasAux.isEmpty());
        ArrayList<Carga> listaCargasP = new ArrayList<Carga>();
        ArrayList<Carga> listaCargasN = new ArrayList<Carga>();
        int contFilas = 1;
        
        while(!listaCargasAux.isEmpty()){
            if(listaCargasAux.peek().getTipo().equals("cargaP")){
                listaCargasP.add(listaCargasAux.poll());
                contFilas ++;
            }
            else{
                listaCargasN.add(listaCargasAux.poll());
                contFilas ++;
            }
            
        }
        String [][] datosFuerzas = new String [contFilas][7];
        
        
        
        Carga cargaP;
        Carga cargaN;
        double catetoX, catetoY;
        double fuerzaResultante ;
        double angulo;
        double compX, compY, distancia, compXTotal=0, compYTotal=0;
        int cont = 0;
        for (int i = 0; i < listaCargasP.size(); i++ ){
            cargaP = listaCargasP.get(i);
            System.out.println("tipp"+cargaP.getTipo()+" color"+cargaP.getColor() );
            
            
            for (int j = 0; j < listaCargasN.size(); j++){
                cargaN = listaCargasN.get(j);
                
                catetoX = Math.abs(cargaP.getPosX() - cargaN.getPosX());
                catetoY = Math.abs(cargaP.getPosY() - cargaN.getPosY());
                
                distancia = Math.hypot(catetoX, catetoY)/100;
                double fuerzaCampo = Math.abs( (8.99 * Math.pow(10, 9)) *cargaN.getValor() ) /Math.pow(distancia, 2); 
                
                angulo= Math.atan2(catetoY, catetoX);//angulo en radianes
                
                if(cargaP.getPosX() > cargaN.getPosX()){ //si la posicion en x del punto medio es mayor a la posicion x de la carga, 
                    if(cargaN.getValor()<0 ){//si el valor de la carga es negativa, la componente en x es negativa por que va hacia la derecha
                        compX = (fuerzaCampo * Math.cos(angulo))*-1;
                    }else{
                        compX = (fuerzaCampo * Math.cos(angulo));
                    }
                }else{
                    if(cargaN.getValor()<0 ){//si el valor de la carga es negativa, la componente en x es negativa por que va hacia la derecha
                        compX = (fuerzaCampo * Math.cos(angulo));
                    }else{
                        compX = (fuerzaCampo * Math.cos(angulo))*-1;
                    }
                }
                
                if(cargaP.getPosY() > cargaN.getPosY()){
                    if(cargaN.getValor()<0){//si la carga es negativa
                        compY = ( fuerzaCampo * Math.sin(angulo)) * -1;//la componente es negativa
                    }else{
                        compY = ( fuerzaCampo * Math.sin(angulo) );
                    }
                }else{
                    if(cargaN.getValor()<0){//si la carga es negativa
                        compY = ( fuerzaCampo * Math.sin(angulo));//la componente es negativa
                    }else{
                        compY = ( fuerzaCampo * Math.sin(angulo) * -1);
                    }
                }
                
                compXTotal = compXTotal + compX;
                compYTotal = compYTotal + compY;
                
                fuerzaResultante = Math.hypot(compXTotal, compYTotal);
                
                datosFuerzas[cont][0] = "Carga "+( j + 1 );
                datosFuerzas[cont][1] = "P "+( i + 1 );
                datosFuerzas[cont][2] = ""+distancia;
                datosFuerzas[cont][3] = ""+compX;
                datosFuerzas[cont][4] = ""+compY;
                datosFuerzas[cont][5] = ""+angulo;
                datosFuerzas[cont][6] = ""+fuerzaResultante;
                
                        
                cont ++;
            }
        }
        
//       for (int i = 0; i < datosFuerzas.length; i++){
//           for (int j = 0; j< datosFuerzas[0].length; j++){
//               System.out.print("|"+datosFuerzas[i][j]);
//           }
//           System.out.println("");
//       }
        return datosFuerzas;
    }

   
}//end of class Main