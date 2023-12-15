import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Treecko extends Criatura
{
    public Treecko(String nombre, boolean imagenEspejada) {
        super(nombre, 40, new String[] {"Golpe Hoja", 
                                        "Viento Acorazado",
                                        "Sagaz", 
                                        "Impacto Natural" }, 
                                        imagenEspejada, false,
                                        new String[] {  "Causa daño a un enemigo.", 
                                                        "Aumenta un 10% de probabilidad de critico (Máximo 50%). Luego ataca con un daño moderado.",
                                                        "Aumenta su velocidad y ataque en proporción a la vida actual.",
                                                        "Causa un daño considerable pero reduce la defensa propia." 
                                        });
        this.ataque = 17;
        this.defensa = 8;
        this.velocidad = 13;
        this.pathImage = "Treecko.png";
    }

    public Treecko(String nombre) {
        this(nombre, false);
    }

    @Override
    public void atacar2(Criatura otro) {

        if (probabilidadDeCritico < 0.51){
            probabilidadDeCritico += 0.1;
        }

        // Calculo de daño moderado
        double rand = 0.2 + (Math.random() * 0.5);
        double daño =  (1+(this.ataque/otro.mostrarDefensa())) * rand;
        int dañoInt = (int) daño;

        otro.recibirDaño(dañoInt, this, nombresAtaque[1]);
    }

    public boolean puedeRealizarAtaque2En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public void atacar3(Criatura otro) {
        // Incremento ponderado según la vida (Suavizado con distribución gaussiana)
        double Increment = Math.exp(-(Math.pow(this.vida - 2, 2)/(500)));
        aumentarVelocidad(this.velocidad, Increment);
        aumentarAtaque(Increment, false);
    }

    public boolean puedeRealizarAtaque3En(Criatura otro) {
        return this == otro;
    }

    public void atacar4(Criatura otro) {
        // Este ataque ignora la defensa del rival
        int daño = ataque;

        if (this.defensa > 3){
            this.defensa -= 2;
            logger.log("⚔ La defensa de" + this.toString() + "bajo a " + String.valueOf(this.defensa) +" ⚔");
        }

        otro.recibirDaño(daño, this, nombresAtaque[3]);
    }
    
    public boolean puedeRealizarAtaque4En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public String imagePath(){
        return this.pathImage;
    } 

}
