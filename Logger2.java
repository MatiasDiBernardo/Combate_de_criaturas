import greenfoot.*;

public class Logger2  
{   
    public void log(String mensaje)
    {
        System.out.println(mensaje);
    }
    
    public void logAtaque(String atacante, String atacado, String nombreAtaque, int daño, boolean critico){
        System.out.println(atacante+" ataca a "+atacado + " utilizando "+nombreAtaque+" [daño: "+daño+"]"+ (critico? " ¡DAÑO CRITICO!":""));
    }
    
    public void logStart(Criatura[] criaturas){
        System.out.flush();  
        System.out.println("####################################################################");
        System.out.println("                             CREATURE BATTLE                        ");
        System.out.println("####################################################################");
        System.out.println("############################Comienza el juego#######################");
        for(int i = 0; i<criaturas.length; i++){
            System.out.println("Criatura "+(int)(i+1)+" -> "+criaturas[i].toString());
        }
    }
}