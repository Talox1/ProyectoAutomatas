/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author talob
 */
public class AnalizadorLexico  {
    private static Queue<String> queueTokens = new LinkedList<String>();
    private static Queue<String> queueLexemas = new LinkedList<String>();
    private static Queue<String> queueTexto = new LinkedList<String>();

    private static String[] phrase;
    private String[] auxPhrase;
    private String remainCharacters;

    static String lexemasTemp = "";

  
    public AnalizadorLexico(String phrases){
    	this.phrase = phrases.split(" ");
    	this.auxPhrase = phrases.split(" ");
    	
    	
        for (int i = 0; i < auxPhrase.length; i++) {
            queueTexto.add(String.valueOf(auxPhrase[i]));
        }
        //System.out.println(queueTexto);
    }
   


    public static void findReservadas() {
        Pattern patternReservadas = Pattern.compile("[^\\d|^\\.][\\.grafico|\\.carga|||.cargaP|\\color:|\\.x:|\\.y:|\\v:|\\.t:|n:|\\\\.nC|\\\\.mC|\\\\.C  ]+");

        Matcher match;
 

        for (int i = 0; i < phrase.length; i++) {
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches()  ){
                lexemasTemp +=phrase[i].toString()+" , ";
                phrase[i] = "null";
            }
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        findEntero();
    }

    private static void findEntero() {
        Pattern patternReservadas = Pattern.compile("[\\-]*[0-9]+");
        Matcher match;
        for (int i = 0; i < phrase.length; i++) {
            
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches() && !phrase[i].equals("null")){
                lexemasTemp +=phrase[i].toString()+" , ";
                phrase[i] = "null";
            }
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        findAgrupacion();

    }
    
    private static void findAgrupacion() {
        Pattern patternReservadas = Pattern.compile("(\\[|\\]|\\{|\\})");
        Matcher match;
        for (int i = 0; i < phrase.length; i++) {
        	
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches() && !phrase[i].equals("null")){
                lexemasTemp +=phrase[i].toString()+" , ";
                phrase[i] = "null";
            }
            
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        findSigno();
    }

    private static void findSigno() {
        Pattern patternReservadas = Pattern.compile("(\\+|\\-)");
        Matcher match;
        for (int i = 0; i < phrase.length; i++) {
            
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches() && !phrase[i].equals("null")){
                lexemasTemp +=phrase[i].toString()+" , ";
                phrase[i] = "null";
            }
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        findColores();
    }

    private static void findColores() {
        Pattern patternReservadas = Pattern.compile("(red|blue|yellow|green|orange)");
        Matcher match;
        for (int i = 0; i < phrase.length; i++) {
            
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches() && !phrase[i].equals("null")){
                lexemasTemp +=phrase[i].toString()+" ,  ";
                phrase[i] = "null";
            }
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        findSimbolo();
    }

    private static void findSimbolo() {
        Pattern patternReservadas = Pattern.compile("(;|,|:)");
        Matcher match;
        for (int i = 0; i < phrase.length; i++) {
            
            match = patternReservadas.matcher(phrase[i]);
            if (match.matches() && !phrase[i].equals("null")){
                lexemasTemp +=phrase[i].toString()+" - ";
                phrase[i] = "null";
            }
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp ="";
        checkRemain();
    }

    private static void checkRemain(){
        for(int i = 0; i < phrase.length; i++){
        	
        	
            if(!phrase[i].equals("null") && phrase[i].length() != 0){
                //System.out.println("Remains: "+phrase[i].length());
                lexemasTemp += phrase[i]+ " - ";
            }
            	
        }
        queueLexemas.add(lexemasTemp);
        lexemasTemp = "";
    }

    public String getRemains(){
        return remainCharacters;
    }
    public Queue<String> getTokens(){
        return queueTokens;
    }
    public Queue<String> getLexemas(){
    	findReservadas();
        return queueLexemas;
    }
}