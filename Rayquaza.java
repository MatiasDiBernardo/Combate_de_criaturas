public class Rayquaza extends Criatura {
    public Rayquaza(String nombre, boolean imagenEspejada) {
        super(nombre, 50, new String[] { "Vuelo", "Poder Pesado", "Triturar", "Danza Dragón" }, imagenEspejada, false,
                new String[] { "Causa daño a un enemigo.", 
                "Causa daño y tiene una probabilidad del 30% de subir 2 puntos de ataque.", 
                "Causa daño y tiene una probabilidad del 20% de bajar 2 puntos de la defensa del objetivo.", 
                "Aumenta el ataque y la velocidad del usuario en un 25%" });

        this.ataque = 20;
        this.defensa = 6;
        this.velocidad = 8;
        this.pathImage = "Rayquaza.png";
    }

    public Rayquaza(String nombre) {
        this(nombre, false);
    }

    public void atacar2(Criatura otro) {
        double random = Math.random();
        
        otro.recibirDaño(ataque,this,nombresAtaque[1]);
        if (random< 0.30){
            super.aumentarAtaque(2.0, true);
        }
    }

    public boolean puedeRealizarAtaque2En(Criatura otro) {
        return  !esDelMismoEquipoQue(otro);
    }

    public void atacar3(Criatura otro) {
        double random = Math.random();
        
        otro.recibirDaño(ataque,this,nombresAtaque[2]);
        if (random< 0.20){
            otro.disminuirPuntosDefensa(2);
        }
    }

    public boolean puedeRealizarAtaque3En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public void atacar4(Criatura otro) {
        super.aumentarAtaque(0.25, false);
        super.aumentarVelocidad(this.velocidad,0.25);
    }

    public boolean puedeRealizarAtaque4En(Criatura otro) {
        return this == otro;
    }

    public String imagePath(){
        return this.pathImage;
    } 

}
