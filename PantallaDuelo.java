import greenfoot.*;
import greenfoot.GreenfootImage;

import java.awt.Font;

public class PantallaDuelo extends World {
    private Texto turnoTexto;
    private Texto criticoTexto;
    private UIAtaques uiAtaques;
    private Logger2 logger = new Logger2();
    private Criatura[] criaturas = new Criatura[4];
    private SoundManager soundFight = new SoundManager("figth.mp3");

    // Variables que establecen el orden del juego
    private int ronda = 0;
    private int turno = 0;
    public int[] ordenIndex;
    public int[] ordenIndexDisplay = {575, 610, 645, 680};
    public UIOrdenRonda[] ordenDisplay = new UIOrdenRonda[4];

    public PantallaDuelo() {
        // Esto define las dimensiones del  mundo
        super(700, 400, 1);

        agregarCriaturas();

        // Texto que se ve arriba que marca las rondas
        turnoTexto = new Texto("Ronda 1 | Turno 1", 20, Color.WHITE, null, (new Color(215, 179, 255 )));
        addObject(turnoTexto, turnoTexto.getImage().getWidth() / 2, turnoTexto.getImage().getHeight() / 2);

        // Texto que aparece cuando hay un golpe crítico
        criticoTexto = new Texto("¡Golpe crítico!", 20, Color.WHITE,null, (new Color(255, 215, 98 )));

        // Esto asigna en el mundo el menú con los ataques
        uiAtaques = new UIAtaques(criaturas);
        addObject(uiAtaques, 350, 300);

        // Inicializa el fondo
        GreenfootImage imagenFondo = new GreenfootImage("fondop.jpg");
        getBackground().drawImage(imagenFondo, -30,0);

        // Incializa el orden
        this.ordenIndex = ordenRonda(criaturas);
        agregarOrden();

        // Inicializa la música
        this.soundFight.play();

        ronda();
    }

    private void agregarCriaturas() {
        // Inicializa las criaturas en el array
        criaturas[0] = new Pikachu("Pikachu");
        criaturas[1] = new Rayquaza("Rayquaza");
        criaturas[2] = new Eevee ("Eevee", true);
        criaturas[3] = new Treecko("Treecko", true);

        logger.logStart(criaturas);

        // Asigna las criaturas en el mundo y establece su posición
        addObject(criaturas[0], 100, 80);
        addObject(criaturas[1], 240, 80);
        addObject(criaturas[2], 460, 80);
        addObject(criaturas[3], 600, 80);
    }

    private void agregarOrden(){
        ordenDisplay[0] = new UIOrdenRonda(criaturas[0].imagePath());
        ordenDisplay[1] = new UIOrdenRonda(criaturas[1].imagePath());
        ordenDisplay[2] = new UIOrdenRonda(criaturas[2].imagePath());
        ordenDisplay[3] = new UIOrdenRonda(criaturas[3].imagePath());

        addObject(ordenDisplay[0], this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 0)], 200);
        addObject(ordenDisplay[1], this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 1)], 200);
        addObject(ordenDisplay[2], this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 2)], 200);
        addObject(ordenDisplay[3], this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 3)], 200);

    }

    private void removeOrden(){
        removeObject(ordenDisplay[0]);
        removeObject(ordenDisplay[1]);
        removeObject(ordenDisplay[2]);
        removeObject(ordenDisplay[3]);

    }

    private void ajustarOrden(int turnoPokemon){
        // Preguntar porque hacer esto no funciona, me superpone las imagenes en vez de correrlas
        ordenDisplay[0].setLocation(this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 0)], 370);
        ordenDisplay[1].setLocation(this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 1)], 370);
        ordenDisplay[2].setLocation(this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 2)], 370);
        ordenDisplay[3].setLocation(this.ordenIndexDisplay[this.findIndex(this.ordenIndex, 3)], 370);

        ordenDisplay[turnoPokemon].highlightTurn();

        for (int i = 0; i < criaturas.length; i++){
            if (criaturas[i].estaDesmayado()){
                ordenDisplay[i].deadTurn();
            }
        }
    }

    private void ronda() {
        ronda++;
        logger.log("▶ Ronda "+ronda);
        turno = 0;
        this.removeOrden();
        this.agregarOrden();
        turno();
    }

    // Los chequeos se realizan en función a lo que paso el turno anterior.
    public void turno() {
        // Cada vez que se llama a la función turno se incrementa el valor de turno.
        turno++;
        logger.log("▷ Turno "+turno);

        if (turno == 5){
            ronda();
            return;
            // Tengo que agregar el return porque sino me corre la parte de 
            // abajo del código.
        }

        // Si dejo de sonar la música hago que arranque de nuevo
        if (!this.soundFight.soundIsPlaying()){
            this.soundFight.play();
        }

        //al iniciar el turno recorre todas las criaturas para control de imagen
        for (int i = 0; i < criaturas.length; i++) {
            // clearea el highligth de todas las criaturas
            // setVisual hace un recuadro en la criatura
            criaturas[i].setVisualSeleccionado(false);

            //si la duracion de paralisis llegó a 0, le saca el paralizado.
            if (criaturas[i].estaParalizado() && criaturas[i].duracionParalisis == 0){
                logger.log("la criatura "+criaturas[i]+" ya no está paralizada");
                criaturas[i].revertirParalisis();
                criaturas[i].render();
                criaturas[i].actualizarUI();
            }
        }

        // Control de victoria
        if (ganoEquipo1(criaturas)){
            this.soundFight.stop();
            logger.log("############################Ganó el equipo1#######################");
            return;
        }

        if (ganoEquipo2(criaturas)){
            this.soundFight.stop();
            logger.log("############################Ganó el equipo2#######################");
            return;
        }

        // Solución por ahora sin hacer la lista criaturas que estan disponibles o no.
        this.ordenIndex = ordenRonda(criaturas);
        ajustarOrden(this.ordenIndex[turno - 1]);
        int turnoCriatura = this.ordenIndex[turno - 1];

        // Skipea el turno si en este turno le toca a un pokemon que ya esta muerto
        if (criaturas[turnoCriatura].estaDesmayado()){
            turno();
            return;
        }

        //Si le toca a una criatura paralizada, baja una unidad en su duracion de paralisis y salta el turno.
        if (criaturas[turnoCriatura].estaParalizado()) {
            criaturas[turnoCriatura].duracionParalisis--;
            turno();
            return;
        }

        ajustarOrden(this.ordenIndex[turno - 1]);
        turnoTexto.actualizarTexto("Ronda " + ronda + " | Turno " + turno);
        

        // Asocía la criatura actual con su set de ataques.
        uiAtaques.asignarCriaturaActual(criaturas[turnoCriatura]);
    }

    public void click(Criatura c) {
        // Acción que realiza cuando se clickea una criatura
        uiAtaques.click(c);
    }

    public void hover(Criatura c) {
        // Acción que realizaw cuando el mouse pasa por encima de la criatura
        uiAtaques.hover(c);
    }
    // Asumo que el orden del array de criaturas es fijo como esta definido en agregar criaturas.
    public boolean ganoEquipo1(Criatura c[]){
        if (c[2].estaDesmayado() && c[3].estaDesmayado()){
            Greenfoot.setWorld(new PantallaGanador1());
            return true;
        }
        return false;
    }

    public boolean ganoEquipo2(Criatura c[]){
        if (c[0].estaDesmayado() && c[1].estaDesmayado()){
            Greenfoot.setWorld(new PantallaGanador2());
            return true;
        }
        return false;
    }

    public int[] getVel(Criatura c[]){
        int vel[] = new int[4];
        for (int i = 0; i < c.length; i++){
            // Si el pokemon esta muerto se le asigna velocidad 1 que es lo más bajo para que en la 
            // lista de orden aparezca al final. NO se cambia el valor velocidad del pokemon por si 
            // es revivido siga teniendo su valor de velocidad original.
            if (!c[i].estaDesmayado()){
                vel[i] = c[i].velocidad;
                }
            else{vel[i] = 1;}
        }
        return vel;
    }

    // Crea un array con el orden de los pokemones según la velocidad.
    public int[] ordenRonda(Criatura c[]){
        int[] v = getVel(c);
        int indexRonda[] = new int[4];

        for (int i = 0; i < v.length; i++){
            int maxIndex = 0;
            for (int j = 1; j < v.length; j++){
                if (v[j] > v[maxIndex]){
                    maxIndex = j;
                }
            }
            v[maxIndex] = 0;
            indexRonda[i] = maxIndex;
        }
        return indexRonda;
    }

    private int findIndex(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
