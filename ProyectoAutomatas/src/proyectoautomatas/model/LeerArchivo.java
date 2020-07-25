/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author talob
 */
public class LeerArchivo {
    String ruta;
    public LeerArchivo(){}
    
    public LeerArchivo(String ruta){
        this.ruta = ruta;
    }
    
    
    
    public void readFile(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).
           archivo = new File (ruta);
           fr = new FileReader (archivo);
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           while((linea=br.readLine())!=null)
              System.out.println(linea);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
    }
}
