/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author talob
 */
public class CampoElectrico {
    private static ArrayList<Carga> listaCargas = new ArrayList<Carga>();
    public static void main(String[] args) {
      

        
        Scanner sc = new Scanner(System.in);
        int numCargas;
        double [] arrayCargas;
        Carga carga;

        int x1, x2;
        int y1, y2;
        int longitudX, longitudY; //longitudes del campoMagnetico

        double [][]campoElectrico;
        double saltosX, saltosY, puntosMediosX, puntosMediosY;
        double compX, compY, distancia, compXTotal=0, compYTotal=0;
        double fuerzaResultante ;

        double catetoX, catetoY;
        double angulo;
        System.out.print("Rango del campo electrico\nX inicial: ");
        x1 = sc.nextInt();                //datos para calcular la distancia de x1 a x2
        System.out.print("X final: ");
        x2 = sc.nextInt();
        System.out.print("Y inicial: ");
        y1 = sc.nextInt();              //datos para calcular la distancia de y1 a y2
        System.out.print("Y final: ");
        y2 = sc.nextInt();

        /*Ordenando menor a mayor */
        if(x1>x2){
           int aux = x1;
           x1 = x2;
           x2 = aux;
        }
        if(y1 > y2){
           int aux = y1;
           y1 = y2;
           y2 = aux;
        }

        System.out.print("Numero de cargas a incluir: ");     
        numCargas = sc.nextInt();              //numero de cargas repartidas en el campo electrico
        //arrayCargas = new double [numCargas];       //inicializar tamaño del arrray
        int numDivisiones;
        String opc;
        do{
           System.out.print("\n\n\nTamaño de la matriz: ");
           numDivisiones = sc.nextInt();
           System.out.print("Tamaño de la matriz ["+numDivisiones+"] x ["+numDivisiones+"] es correcto? (S/N): ");
           opc = sc.next();
        }while(opc.charAt(0) == 'N' || opc.charAt(0)=='n');
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

        // colocar cargas en el campo magnetico

        crearCargas(numCargas, x1, x2, y1, y2); //===========================================AQUI SE CREAN LAS CARGAS=========================
       // for(int i = x1; i<=x2; i+=puntosMediosX){
       //coordenadas puntos medios
        System.out.print("\n\n====Desea ver los datos de salida ? (S/N): ====");
        String opcion = sc.next();
        double auxY = y2 - puntosMediosY;
        for(int i = 0; i < numDivisiones; i++){//desde el inicio de y
           double auxX = x1 + puntosMediosX;   
           for(int j = 0; j < numDivisiones; j++){//desde el inicio de x
              // System.out.println(i+":"+j);
                for (Carga auxCarga : listaCargas) {//hacer los calculos para todas las cargas, obtener todas las cargas
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

        sc.close();
}//end of method main



   public static void crearCargas(int numCargas,int x1,int x2, int y1, int y2){
      Scanner sc = new Scanner(System.in);  
     
      for(int i = 0; i<numCargas; i++){
         System.out.print("=====================tamaño de la carga===================== \n1.- Nano\n2.- Micro\n3.- Coulomb\nOpcion: ");
         int opcion = sc.nextInt();
         Carga carga;
         if(opcion==1){
            System.out.print(">>Magnitud de la carga: ");
            carga = new Carga();
            carga.setValor(sc.nextInt() * Math.pow(10,-9));
            do{
               System.out.print("posicion X:");
               carga.setPosX(sc.nextInt());

               System.out.print("posicion Y:");
               carga.setPosY(sc.nextInt());
               if(carga.isRange(x1, x2, y1, y2) == false){     //valido si esta dentro del rango
                  System.out.println("La carga esta fuera del rango del campo magnetico...");
                  System.out.println("Introduce nueva posicion");
               }
            }while(carga.isRange(x1, x2, y1, y2) == false);
            listaCargas.add(carga);

            //arrayCargas[i] = sc.nextInt() * Math.pow(10,-9);     //lo que introduzca el usuario X 10^-9

         }else if (opcion ==2){
            System.out.print(">>Magnitud de la carga: ");
            carga = new Carga();
            carga.setValor(sc.nextInt() * Math.pow(10,-6));
            do{
               System.out.print("posicion X:");
               carga.setPosX(sc.nextInt());

               System.out.print("posicion Y:");
               carga.setPosY(sc.nextInt());
               if(carga.isRange(x1, x2, y1, y2) == false){
                  System.out.println("La carga esta fuera del rango del campo magnetico...");
                  System.out.println("Introduce nueva posicion");
               }
            }while(carga.isRange(x1, x2, y1, y2) == false);
            listaCargas.add(carga);
            //arrayCargas[i] = sc.nextInt() * Math.pow(10,-6);     //lo que introduzca el usuario X 10^-6
         }else {
            System.out.print(">>Magnitud de la carga: ");
            carga = new Carga();
            carga.setValor(sc.nextInt());
            do{
               System.out.print("posicion X:");
               carga.setPosX(sc.nextInt());

               System.out.print("posicion Y:");
               carga.setPosY(sc.nextInt());
               if(carga.isRange(x1, x2, y1, y2) == false){
                  System.out.println("La carga esta fuera del rango del campo magnetico...");
                  System.out.println("Introduce nueva posicion");
               }
            }while(carga.isRange(x1, x2, y1, y2) == false);
            listaCargas.add(carga);          //se guarda en el array 
            //arrayCargas[i] = sc.nextInt();     //lo que introduzca el usuario X 10^0
         }
         

      }
      // System.out.println(arrayCargas[0]);
   }
   
}//end of class Main