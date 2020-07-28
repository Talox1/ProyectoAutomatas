/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author talob
 */
public class AnalizadorSintactico {
    
    private String entrada;	
    List<String> listaReglas = new ArrayList<String>();
    Stack<String> pila = new Stack<String>();
    Stack<String> pilaEntrada = new Stack<String>();
    
    String[] caracterInvalido = new String[2];
    boolean isValid = false;
    public AnalizadorSintactico(String entrada){
        this.entrada = entrada;
        pila.push("$");
        pila.push("IDENTIFICADOR");
        cargarPilaEntrada();
    }

    

    public void cargarPilaEntrada(){
        String [] auxEntrada = entrada.split(" ");
        for (int i = auxEntrada.length-1; i >= 0; i-- ){
            pilaEntrada.push(auxEntrada[i]);
        }
    }

    public void start(){
        Boolean valido = true;
        int cont = 0;
        Tabla tabla2 = new Tabla();
        //System.out.println("|Pila\t\t\t\t\t\t\t|Entrada\t\t\t\t\t\t\t|Salida de la Tabla2");
        
        
        while(!pila.isEmpty() && valido && !pilaEntrada.isEmpty()){
        	cont++;
        	//if(cont > 150)
        		//break;
        	while(pilaEntrada.peek().isEmpty()) {
        		//System.out.println("Proceso 2 41 "+pila.peek());
        		pilaEntrada.pop();
        	}
        	while(pila.peek().isEmpty() && pila.size()>0)
        		pila.pop();
        	//System.out.println("proceso line 41 --> "+pilaEntrada+" last item "+pilaEntrada.peek()+" size stack "+pilaEntrada.size()+" is empty?: "+pilaEntrada.isEmpty());
        	//System.out.println("Proceso2 42 pila.peek(/) = "+pilaEntrada.peek());
            String token = checkTerminal(pilaEntrada.peek());
            //System.out.println("Proceso line 59-->  "+pila.peek()+" token= "+token+" is empty = "+token.isEmpty());
            //System.out.println("Proceso 2 42 pila entrada"+pilaEntrada);
            if(pila.peek().equals("E")) {
            	pila.pop();
            }else {
            	if(isTerminal(pila.peek())) {
            		//System.out.println("Proceso 2 65 pila.peek() "+pila.peek()+" es terminal"+pila);
            		//System.out.println("66"+pila+"\t\t\t|"+pilaEntrada);
                        
                    if(!comparar(pila.peek(), pilaEntrada.peek())) {
                    	break;
                    }
            	}else {
            		String produccion = tabla2.getProduccion(pila.peek(), token);
            		//System.out.println("proceso line 55 --> Produccion obtenida :"+produccion);
            		//System.out.println("75"+pila+"\t\t\t\t\t\t|"+pilaEntrada+"\t\t\t\t\t\t|"+produccion);
                    if(produccion.equals("none") || produccion.equals("no produccion found"))
                    	break;
                    
                    actualizarPila(limpiarProduccion(produccion).split(" "));
            	}
            }
            if(pila.peek().equals("$"))
            	break;
        }
        
        
        validaEntrada();
        
    }


    private String limpiarProduccion(String produccion){
        String []prodAux = produccion.split("-->");
        if(prodAux.length > 1)
            produccion = prodAux[1];
        else if(prodAux.length == 1)
            produccion = prodAux[0];
        //System.out.println("proceso 2 96 Produccion"+ produccion);
        //System.out.println("limpiar produccion"+prodAux[1]);
        return produccion;
    }

    private void actualizarPila(String[] produccion){
        pila.pop();
        if(produccion.length == 1)
            pila.push(produccion[0]);
        else if(produccion.length > 1){
            for(int i = produccion.length-1; i >= 0; i--){
                pila.push(produccion[i]);
            }
        }
        
        //System.out.println("Proceso 82 Pila actualizada:"+pila);
    }

    private boolean isTerminal(String string){
    	//System.out.println("Proceso 2 94 "+string+" es terminal?");
    	boolean terminal = true;
    	if(string.equals(string.toLowerCase())) {
    		terminal = true;
    		//System.out.println("Proceso 87"+terminal);
    	}else if(string.equals("x:")) {
    		terminal = true;
    	}else if(string.equals("y:")) {
    		terminal = true;
    	}else if(string.equals("color:")) {
    		terminal = true;
    	}else if(string.equals("v:")) {
    		terminal = true;
    	}else if(string.equals("t:")) {
    		terminal = true;
    	}else if(string.equals("n:")) {
    		terminal = true;
    	}else if(string.equals(";")) {
    		terminal = true;
    	}else if(string.equals("]")) {
    		terminal = true;
    	}else if(string.equals("[")) {
    		terminal = true;
    	}else if(string.equals("{")) {
    		terminal = true;
    	}else if(string.equals("}")) {
    		terminal = true;
    	}else if(string.equals("cargaP")) {
    		terminal = true;
    	}else if(string.equals("nC")) {
    		terminal = true;
    	}else if(string.equals("mC")) {
    		terminal = true;
    	}else if(string.equals("C")) {
    		terminal = true;
    	}else {
    		terminal = false;
    	}
        
        return terminal;
    }

    private boolean comparar(String terminalPila, String entrada){
    	//System.out.println("Proceso 2 147 comparando: "+terminalPila +"=="+entrada+"?" );
    	Pattern patternTerminal= Pattern.compile("");
    	boolean areEqual = false;
    	if(terminalPila.equals("a-z")) {
    		patternTerminal = Pattern.compile("[a-z]+");
    	}else if(terminalPila.equals("0-9")) {
    		patternTerminal = Pattern.compile("[0-9]*");
    	}else if(terminalPila.equals("(")) {
    		patternTerminal = Pattern.compile("[\\(]");
    	}else if(terminalPila.equals(")")) {
    		patternTerminal = Pattern.compile("[\\)]");
    	}else if(terminalPila.equals("{")) {
    		patternTerminal = Pattern.compile("[\\{]");
    	}else if(terminalPila.equals("}")) {
    		patternTerminal = Pattern.compile("[\\}]");
    	}else if(terminalPila.equals("[")) {
    		patternTerminal = Pattern.compile("[\\[]");
    	}else if(terminalPila.equals("]")) {
    		patternTerminal = Pattern.compile("[\\]]");
    	}else if(terminalPila.equals(":")) {
    		patternTerminal = Pattern.compile("[\\.:]");
    	}else if(terminalPila.equals(";")) {
    		patternTerminal = Pattern.compile("[\\.;]");
    	}else if(terminalPila.equals("+")) {
    		patternTerminal = Pattern.compile("[\\.+]");
    	}else if(terminalPila.equals("-")) {
    		patternTerminal = Pattern.compile("[\\.-]");
    	}else {
    		patternTerminal = Pattern.compile(terminalPila);
    	}
    		
    	
        Matcher match;
        match = patternTerminal.matcher(entrada);
        if (match.matches()){
            //System.out.println("Proceso2 159 Metodo comparar: iguales "+pila.peek()+":"+pilaEntrada.peek());
            if(terminalPila.equals("a-z") || terminalPila.equals("0-9")) {
                String nombre = pilaEntrada.pop();
                nombre = nombre.substring(0,nombre.length()-1);
                //System.out.println("proceso 2 149 ===>"+nombre+":"+pilaEntrada.peek());
                pilaEntrada.push(nombre);
                pila.pop();
                
            }else {
            	pila.pop();
                pilaEntrada.pop();
                
            }
            areEqual = true;
            return areEqual;
        }else{
        	areEqual = false;
            //System.out.println("Proceso 193 No son iguales");
        }
        return areEqual;
    }

    private String checkTerminal(String string){
       //System.out.println("Proceso2 line 153--> "+string+" y "+pilaEntrada.peek());
        boolean flag = false;
        Pattern [] expresionesRegulares ={
            Pattern.compile("grafico"),
            Pattern.compile("\\:"),
            Pattern.compile("\\{"),
            Pattern.compile("\\}"),
            Pattern.compile("carga"),
            Pattern.compile("\\,"),
            Pattern.compile("\\;"),
            Pattern.compile("\\["),
            Pattern.compile("\\]"),
            Pattern.compile("x:"),
            Pattern.compile("y:"),
            Pattern.compile("v:"),
            Pattern.compile("t:"),
            Pattern.compile("n:"),
            Pattern.compile("\\-"),
            Pattern.compile("\\+"),
            Pattern.compile("color:"),
            Pattern.compile("red"),
            Pattern.compile("blue"),
            Pattern.compile("yellow"),
            Pattern.compile("green"),
            Pattern.compile("orange"),
            Pattern.compile("nC"),
            Pattern.compile("mC"),
            Pattern.compile("C"),
            Pattern.compile("[a-z]"),  
            Pattern.compile("[0-9]*"),
        };
        Matcher match;

        for(int i = 0; i < expresionesRegulares.length; i++){
            match = expresionesRegulares[i].matcher(string);
            if (match.matches()){
            	if(i == 26 || i == 25 ) {
            		string = expresionesRegulares[i].toString().replace("\\", "").replace("[", "").replace("]", "").replace("+", "").replace("*", ""); 
            		//System.out.println("Proceso 2 202=> "+string);  		
            	} else {
            		string = expresionesRegulares[i].toString().replace("\\", ""); 
            		//System.out.println("proceso 2 205 =>"+string);
                    //System.out.println("Proceso 146 ==>"+string);
            	}
            		
                flag = true;
                break;
            }
        }
        if(!flag)
            string = "none";
        //System.out.println("proceso 2 215 retornando..."+string);
        return string;

    }
    public void validaEntrada() {

        if(!pila.peek().equals("$") && !pilaEntrada.isEmpty()) {
            System.out.println("Error... entrada no valida "+pila);
            caracterInvalido[0] = pila.peek();
            caracterInvalido[1] = pilaEntrada.peek();
            isValid = false;
            
        }else if(!pilaEntrada.isEmpty()) {
            System.out.println("Error... entrada no valida "+pilaEntrada);
            isValid = false;
            caracterInvalido[0] = pila.peek();
            caracterInvalido[1] = pilaEntrada.peek();
        }else if(pilaEntrada.isEmpty() && pila.peek().equals("RESTOC")) {
            //System.out.println("57"+pila+"\t\t\t\t\t\t|"+pilaEntrada+"\t\t\t\t\t\t|RESTOC-->E");
            pila.pop();
            //System.out.println("57"+pila+"\t\t\t\t\t\t|"+pilaEntrada+"\t\t\t\t\t\t|");
            System.out.println("Entrada valida "+pila+"\t"+pilaEntrada);
               
            isValid = true;
                
        }else { 
        	System.out.println("Entrada ns valida "+pila+"\t"+pilaEntrada);
                
        }
    }
    
    public boolean isEntradaValid(){
        return isValid;
    }
    public String[] getCaracterInvalido (){
        return caracterInvalido;
    }
    
}

