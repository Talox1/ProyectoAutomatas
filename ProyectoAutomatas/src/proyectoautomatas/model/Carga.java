
package proyectoautomatas.model;
/**
 *
 * @author talob
 */

public class Carga{
   private double valor;
   private int posX;
   private int posY;
   private String color;
   private String tipo;
   private String nomenclatura;
   private String signo;
   private boolean isRange = false;
   public Carga(){}

   public Carga(int valor, int posX, int posY){
      this.valor = valor;
      this.posX = posX;
      this.posY = posY;
   }

   public void setValor(double valor){
        if(tipo.equals("carga")){
            if(signo.equals("-")){
                valor = valor * -1;
            }
            if(nomenclatura.equals("nC"))
                this.valor = valor * Math.pow(10,-9);
            else if (nomenclatura.equals("mC"))
                this.valor = valor * Math.pow(10,-6);
            else 
                  this.valor = valor;
        }else
          this.valor = valor;
   }
   public double getValor(){
      return valor;
   }

   public void setPosX(int posX){
      this.posX = posX;
   }
   public int getPosX(){
      return posX;
   }

   public void setPosY(int posY){
      this.posY = posY;
   }
   public int getPosY(){
      return posY;
   }

   public boolean isRange(int x1, int x2,int y1, int y2){
      if(x1 <posX && posX<x2 && y1 < posY && posY < y2 ){
         isRange = true;
      }
      return isRange;
   }
   
   public void setColor(String color){
       this.color = color;
   }
   public String getColor(){
       return color;
   }
   
   public void setTipo(String tipo){
       this.tipo = tipo;
   }
   public String getTipo(){
       return tipo;
   }
   
   public void setNomenclatura(String nomenclatura){
       this.nomenclatura = nomenclatura;
   }
   
   public String getNomenclatura(){
       return nomenclatura;
   }
   
   public void setSigno(String signo){
       this.signo = signo;
   }
   public String getSigno(){
       return signo;
   }
}
