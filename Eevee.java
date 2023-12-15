public class Eevee extends Criatura {
    public Eevee(String nombre, boolean imagenEspejada) {
        super(nombre, 21, new String[] {"Placaje", 
                "Doble Filo",
                "Curacion", 
                "Revivir" }, 
            imagenEspejada, true,
            new String[] {  "Causa un daño moderado a un enemigo.", 
                "Causa daño doble al objetivo pero baja la defensa propia 1 punto",
                "Cura al compañero o a si mismo 5 puntos si el objetivo tiene mas de la mitad de la vida y 10 puntos si tiene menos.",
                "Puede revivir a un compañero desmayado." 
            });
        this.ataque = 11;
        this.defensa = 5;
        this.velocidad = 14;
        this.probabilidadDeCritico = 0.25;
        this.pathImage = "Eevee.png";
        this.ataqueRevividor = 4;
    }

    public Eevee(String nombre) {
        this(nombre, false);
    }

    @Override
    public void atacar2(Criatura otro) {
        int daño = ataque*2;
        super.disminuirPuntosDefensa(1);

        otro.recibirDaño(daño,this,nombresAtaque[1]);
    }

    public boolean puedeRealizarAtaque2En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public void atacar3(Criatura otro) {        

        otro.recibirVida(this,nombresAtaque[2]);
    }

    public boolean puedeRealizarAtaque3En(Criatura otro) {
        return esDelMismoEquipoQue(otro);
    }

    public void atacar4(Criatura otro) {
        otro.revivirme();
    }

    public boolean puedeRealizarAtaque4En(Criatura otro) {
        return esDelMismoEquipoQue(otro) && otro.estaDesmayado();
    }

    public String imagePath(){
        return this.pathImage;
    } 

}
