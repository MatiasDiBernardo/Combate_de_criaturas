public class Pikachu extends Criatura {
    public Pikachu(String nombre, boolean imagenEspejada) {
        super(nombre, 21, new String[] {"Placaje", 
                                        "Impactrueno",
                                        "Onda Trueno", 
                                        "Trueno" }, 
                                        imagenEspejada, false,
                                        new String[] {  "Causa un daño moderado a un enemigo.", 
                                                        "Causa daño base a un enemigo.\n 30% de probabilidad de paralizar al rival por 1 turno.",
                                                        "Causa parálisis a un enemigo por 1 turno.",
                                                        "Causa daño que reduce la vida del enemigo a la mitad y causa paralisis por 2 turnos." 
                                        });
        this.ataque = 15;
        this.defensa = 8;
        this.velocidad = 12;
        this.probabilidadDeCritico = 0.2;
        this.pathImage = "Pikachu.png";
    }

    public Pikachu(String nombre) {
        this(nombre, false);
    }

    @Override
    public void atacar2(Criatura otro) {
        int daño = ataque;

        otro.paralizar(30,1);
        otro.recibirDaño(daño,this,nombresAtaque[1]);
    }

    public boolean puedeRealizarAtaque2En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public void atacar3(Criatura otro) {
        logger.logAtaque(this.toString(),otro.toString(),nombresAtaque[2],0,false);
        otro.paralizar(100,1);
    }

    public boolean puedeRealizarAtaque3En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public void atacar4(Criatura otro) {
        int daño = otro.getVida()/2;
        otro.recibirDaño(daño, this, nombresAtaque[3]);
        otro.paralizar(100,2);
    }
    
    public boolean puedeRealizarAtaque4En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public String imagePath(){
        return this.pathImage;
    } 

}
