
package proyectoautomatas.model;
/**
 *
 * @author talob
 */

public class Carga{
   private double valor;
   private double posX;
   private double posY;
   private String color;
   private String tipo;
   private String nomenclatura;
   private String signo;
   private boolean isRange = false;
   public Carga(){}

   public Carga(double valor, double posX, double posY){
      this.valor = valor;
      this.posX = posX;
      this.posY = posY;
   }

   public void setValor(double valor){
      this.valor = valor;
   }
   public double getValor(){
      return valor;
   }

   public void setPosX(double posX){
      this.posX = posX;
   }
   public double getPosX(){
      return posX;
   }

   public void setPosY(double posY){
      this.posY = posY;
   }
   public double getPosY(){
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
