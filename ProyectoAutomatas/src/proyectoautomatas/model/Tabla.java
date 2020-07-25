/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas.model;

/**
 *
 * @author talob
 */
public class Tabla {
    String [][] tabla = new String[18][28];

    public Tabla(){
        tabla[0][1] = "grafico";
        tabla[0][2] = ":";
        tabla[0][3] = "{";
        tabla[0][4] = "}";
        tabla[0][5] = "carga";
        tabla[0][6] = ",";
        tabla[0][7] = ";";
        tabla[0][8] = "[";
        tabla[0][9] = "]";
        tabla[0][10] = "x:";
        tabla[0][11] = "y:";
        tabla[0][12] = "v:";
        tabla[0][13] = "t:";
        tabla[0][14] = "n:";
        tabla[0][15] = "-";
        tabla[0][16] = "+";
        tabla[0][17] = "color:";
        tabla[0][18] = "red";
        tabla[0][19] = "blue";
        tabla[0][20] = "yellow";
        tabla[0][21] = "green";
        tabla[0][22] = "orange";
        tabla[0][23] = "nC";
        tabla[0][24] = "mC";
        tabla[0][25] = "M";
        tabla[0][26] = "0-9";
        tabla[0][27] = "$";
        
        

        tabla[1][0] = "IDENTIFICADOR";
        tabla[2][0] = "PARAMETROSG";
        tabla[3][0] = "PARAMETROSCP";
        tabla[4][0] = "RESTOCP";
        tabla[5][0] = "PARAMETROC";
        tabla[6][0] = "COORDENADAX";
        tabla[7][0] = "COORDENADAY";
        tabla[8][0] = "VALOR";
        tabla[9][0] = "TIPOCARGA";
        tabla[10][0] = "SIGNO";
        tabla[11][0] = "NOMENCLATURA";
        tabla[12][0] = "SIMBOLO";
        tabla[13][0] = "DIGITO";
        tabla[14][0] = "RESTOD";
        tabla[15][0] = "COLOR";
        tabla[16][0] = "COLORES";
        tabla[17][0] = "RESTOC";
        

        tabla[1][1] = "IDENTIFICADOR-->grafico : { PARAMETROSG } , carga : { PARAMETROC } RESTOC";
        tabla[2][8] = "PARAMETROSG-->[ cargaP : PARAMETROSCP ] RESTOCP";
        tabla[3][10] = "PARAMETROSCP -->COORDENADAX ; COORDENADAY ; COLOR ;";
        tabla[4][3] = "RESTOCP-->E";
        tabla[4][4] = "RESTOCP-->E";
        tabla[4][5] = "RESTOCP-->E";
        tabla[4][6] = "RESTOCP-->, [ cargaP : PARAMETROSCP ] RESTOCP";
        tabla[4][7] = "RESTOCP-->E";
        tabla[5][10] = "PARAMETROC-->COORDENADAX ; COORDENADAY ; VALOR ; TIPOCARGA ; NOMENCLATURA ; COLOR ; ";
        tabla[6][10] = "COORDENADAX-->x: DIGITO RESTOD";
        tabla[7][11] = "COORDENADAX-->y: DIGITO RESTOD";
        tabla[8][12] = "VALOR-->v:  DIGITO RESTOD";
        tabla[9][13] = "TIPOCARGA-->t: SIGNO";
        tabla[10][15] = "SIGNO-->-";
        tabla[10][16] = "SIGNO-->+";
        tabla[11][14] = "NOMENCLATURA-->n: SIMBOLO";
        tabla[12][23] = "SIMBOLO-->nC";
        tabla[12][24] = "SIMBOLO-->mC";
        tabla[12][25] ="SIMBOLO-->C";
        tabla[13][26] ="DIGITO-->0-9";
        tabla[14][26] ="RESTOD-->DIGITO RESTOD";
        tabla[14][7] ="RESTOD-->E";
        tabla[15][17] = "COLOR-->color: COLORES";
        tabla[16][18] = "COLORES-->red";
        tabla[16][19] = "COLORES-->blue";
        tabla[16][20] = "COLORES-->yellow";
        tabla[16][21] ="COLORES-->green ";
        tabla[16][22] ="COLORES-->orange";
        tabla[17][6] ="RESTOC-->,  carga : { PARAMETROC } RESTOC";
        tabla[17][27] ="RESTOC -->E";
        
        
    }

    public void imprimirTabla(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 12; j++){
                System.out.print("["+tabla[i][j]+"] ");
            }
            System.out.println("\n");
        }
    }
    public String [][] getTable(){
        return tabla;
    }

    public String getProduccion(String noTerminal, String terminal){
    	System.out.println("Tabla line 102  recibidos No terminal="+noTerminal+ " Terminal="+terminal+" is empty?: "+terminal.isEmpty());
        String produccion = new String();
        boolean flag = false;
        for (int i = 1; i < 18; i++){
            //System.out.println("-->"+tabla[i][0].equals (noTerminal));
            //System.out.println("No terminal: "+tabla[i][0]);
            if(tabla[i][0]!=null && tabla[i][0].equals(noTerminal)){
                //System.out.println("No terminal: "+tabla[i][0]+" "+i);
                for (int j = 1; j < 27; j++){
                	//System.out.println("Tabla 69-->"+tabla[0][j].equals (terminal)+": "+terminal +" "+j);
                    if(tabla[0][j]!=null && tabla[0][j].equals(terminal)){
                        //System.out.println("Tabla 71 terminal: "+tabla[0][j]+":::"+tabla[i][j]);
                    	produccion = tabla[i][j];
                    	/*if(noTerminal.equals("RESTOL"))
                    		System.out.println("Tabla line 73==>"+produccion+" "+i+":"+j+":::"+terminal);*/
                    	//System.out.println("Tabla line 73==> PRODUCCION "+produccion+" "+i+":"+j+" TERMINAL:::"+terminal);
                    	if(produccion == null)
                    		produccion = "none";
                        return produccion;
                    }
                }
            }
        }
        if(!flag) {
        	//System.out.println("Tabla 82 no se encontro match:"+noTerminal+" "+terminal);
        	produccion = "no produccion found";
        }
        return produccion;
    }
}