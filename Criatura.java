import java.util.List;
import java.util.Random;

import greenfoot.*;

public abstract class Criatura extends Actor {
    // Info de la criatura
    protected final String nombre;
    protected final int vidaMaxima;
    protected final boolean equipo1;

    protected final String[] nombresAtaque;
    protected final String[] detallesAtaque;
    protected String pathImage;

    // Mapea las stats para mostrar la barra de vida y demás
    private UIInfoCriatura uiInfoCriatura;
    
    //instancia Logger para mostrar acciones en log
    protected Logger2 logger = new Logger2();

    // Booleando que define si se agreanda el tamaño de la foto de la criatura o no
    private boolean visualHover;
    // Booleando que define si la criatura esa resaltado o no
    private boolean visualSeleccionado;
    
    //+++++++++++++++++++++++++++Agregado+++++++++++++++++++++++++++++:
    
    // Aributos que definen las stats de la criatura
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected double probabilidadDeCritico = 0.1;
    protected boolean puedeRevivir;
    protected int ataqueRevividor;
    
    //Atributos para definir estado de la criatura
    public boolean desmayado = false;
    private boolean paralizado = false;
    public  int duracionParalisis;
    

    private final MyGreenfootImage imagenOriginal;

    public Criatura(String nombre, int vida, String[] nombresAtaque, boolean equipo1, boolean puedeRevivir, String[] detallesAtaque) {
        this.nombre = nombre;

        this.vidaMaxima = vida;

        this.nombresAtaque = nombresAtaque;
        this.detallesAtaque = detallesAtaque;

        this.vida = vida;

        this.equipo1 = equipo1;
        
        this.puedeRevivir = puedeRevivir;

        this.imagenOriginal = new MyGreenfootImage(getImage());
        this.imagenOriginal.scale(120, 120);

        this.uiInfoCriatura = new UIInfoCriatura(this);
    }

    @Override
    protected void addedToWorld(World world) {
        render();

        getWorld().addObject(uiInfoCriatura, getX(), getY());
        // Una vez en el mundo, actualizo segun su tamaño
        uiInfoCriatura.setLocation(getX(), getY() + getImage().getHeight() / 2 - /*Sombra*/ 10 + uiInfoCriatura.getImage().getHeight() / 2);
    }

    public void act() {
        boolean _visualHover = visualHover;
        boolean _visualSeleccionado = visualSeleccionado;

        MouseInfo m = Greenfoot.getMouseInfo();

        // TODO: detecta el mouse-over, no tocar
        if (m != null) {
            List<Actor> actor = getWorld().getObjectsAt(m.getX(), m.getY(), Actor.class);

            if (actor.size() > 0 && actor.get(0) == this && !this.estaDesmayado()) {
                visualHover = true;
                ((PantallaDuelo)getWorld()).hover(this);
            } else {
                visualHover = false;
            }
        }

        if (Greenfoot.mouseClicked(this)) {
            ((PantallaDuelo)getWorld()).click(this);
        }

        if (_visualHover != visualHover || _visualSeleccionado != visualSeleccionado) {
            render();
        }
    }

    public void render() {
        MyGreenfootImage nuevaImagen = new MyGreenfootImage(imagenOriginal){
                public void configurar() {
                    if (!equipo1) {
                        flipHorizontally();
                    }
                    if (visualHover) {
                        scaleToRatio(1.15);
                    }
                    if (visualSeleccionado) {
                        highlight(new Color (255, 215, 98 ));
                    }
                    shadow();
                    //si está desmayado se ve la imagen gris
                    if (estaDesmayado()) {
                        grayscale();
                    }
                    //si está paralizado se ve la imagen celeste
                    if (estaParalizado()){
            
                        applyHue(Color.CYAN);
                        
                    }
                }
            };

        setImage(nuevaImagen);
    }

    // atacar1 es un ataque básico común para todas las criaturas que sigue la fórmula que nos dan
    public void atacar1(Criatura otro) {
        // Se puede asignar diferente probabilidad de daño critico para otros ataques.
         
        double rand = 0.5 + (Math.random() * 0.75);
        double daño =  2*(1+(this.ataque/otro.mostrarDefensa())) * rand;
        int dañoInt = (int) daño;

        otro.recibirDaño(dañoInt,this,nombresAtaque[0]);
    }

    //se definen abstract para que los ataques sean definidos en cada criatura
    public abstract void atacar2(Criatura otro);

    public abstract void atacar3(Criatura otro);

    public abstract void atacar4(Criatura otro);

    protected boolean esDelMismoEquipoQue(Criatura otro) {
        return this.equipo1 == otro.equipo1;
    }

    // Los chequeos de si se puede realizar los ataques siguen la misma lógica de hernecias que los ataques.
    public boolean puedeRealizarAtaque1En(Criatura otro) {
        return !esDelMismoEquipoQue(otro);
    }

    public abstract boolean puedeRealizarAtaque2En(Criatura otro);

    public abstract boolean puedeRealizarAtaque3En(Criatura otro);

    public abstract boolean puedeRealizarAtaque4En(Criatura otro);

    //getters daño, defensa, desmayado, vida, vidaMax
    public int mostrarDaño(){
        return this.ataque;
    }

    public int mostrarDefensa(){
        return this.defensa;
    }
    
    public boolean estaDesmayado(){
        return this.desmayado;
    }

    public int getVida() {
        return this.vida;
    }
    public void setVida(int vida){
        this.vida = vida;
    }

    public int getVidaMaxima() {
        return this.vidaMaxima;
    }
    
    public double dañoCritico(){
        double multiplicadorCritico = 1;
        double random = Math.random();
        
        if (random< this.probabilidadDeCritico){
            multiplicadorCritico = 1.5;
        }
        
        return multiplicadorCritico;
    }
    
    //resta vida proporcionada por parametro
    protected int recibirDaño(int daño, Criatura atacante, String nombreAtaque) {
        double critico = atacante.dañoCritico();
        this.vida -= (int)(daño*critico);
        logger.logAtaque(atacante.toString(),this.toString(),nombreAtaque,(int)(daño*critico),critico>1);
        
        if (critico>1){
            textoCritico(this.getX(),this.getY());
        }
        
        // Condición para que no me quede vida negativa. Osea si vida = 0 se murio el pokemon :(
        if (this.vida <= 0){
            this.vida = 0;
            this.desmayado = true;
            logger.log("✚ La criatura "+this.toString()+" se ha desmayado ! ✚");
        }

        // Después de hacer daño se actualiza para que muestre ese cambio en la barra de vida.
        uiInfoCriatura.actualizar();
        return daño;
    }

    public boolean esEquipo1() {
        return equipo1;
    }

    public void setVisualSeleccionado(boolean visualSeleccionado) {
        this.visualSeleccionado = visualSeleccionado;
        render();
    }

    public String toString() {
        return nombre;
    }

    public String[] getNombresAtaque() {
        return nombresAtaque;
    }

    public String[] getDetallesAtaque() {
        return detallesAtaque;
    }

    public String getStats() {
        return nombre + " (" + this.getClass().getSimpleName() + ")\n" +
        " - Ataque: "+this.ataque+"\n" +
        " - Defensa: "+this.defensa+"\n" +
        " - Velocidad: "+this.velocidad+"\n"
        ;
    }

    public String imagePath(){
        return this.pathImage;
    }
    
    //Calculo de accion paralizar con porcentaje de probabilidad
    public void paralizar(int porcentaje, int turnos){
        double rand = Math.random();
        if (100*rand <= porcentaje){
            this.paralizado = true;
            duracionParalisis += turnos;
            logger.log("✧ La criatura "+this.toString()+" ha quedado paralizada! ✧");
            render();
            actualizarUI();
        }
    }
    
    public boolean estaParalizado(){
        return this.paralizado;
    }
    
    public void revertirParalisis(){
        this.paralizado = false;
    }
    
    //Actualiza UI
    public void actualizarUI(){
        uiInfoCriatura.actualizar();
    }
    
    private void textoCritico(int x, int y) {
        Critico critico = new Critico();
        getWorld().addObject(critico, x, y);
        critico.animar();
        getWorld().removeObject(critico);
    }
    
    protected void aumentarAtaque(double valor, boolean puntos){
        if (puntos){
            this.ataque = this.ataque + (int)(valor); 
           logger.log("⇈ " + this.toString() + " incrementó su ataque "+valor+" puntos ⇈");
        }else{
           this.ataque = this.ataque + (int)(this.ataque*valor); 
           logger.log("⇈ " + this.toString() + " incrementó su ataque un "+valor*100+"% ⇈"); 
        }
    }
    
    protected void aumentarVelocidad(int velocidadInicial, double porcentaje){
       this.velocidad = velocidadInicial + (int)(velocidadInicial*porcentaje); 
       logger.log("⇈ " + this.toString() + " incrementó su velocidad un "+porcentaje*100+"% ⇈");
    }
    
    protected void disminuirPuntosDefensa(int puntos){
        if(this.defensa > puntos){
            this.defensa -= puntos;
            logger.log("⚔ La defensa de " + this.toString() + " disminuyó "+puntos+" puntos ⚔");
        }
    }
    
    protected void revivirme(){
        this.desmayado = false;
        this.vida = this.vidaMaxima/3;
        uiInfoCriatura.actualizar();
        logger.log("✚ Eevee revivió a " + this.toString() + " y ahora tiene " + this.vida + " puntos de vida! ✚");
    }
    
    public int getAtaqueRevividor(){
        return this.ataqueRevividor;
    }
    
    protected void recibirVida( Criatura atacante, String nombreAtaque){
        if (this.vida <= this.vidaMaxima/2){
            this.vida = this.vida + 10;
            actualizarUI();
        } else {
            this.vida = this.vida + 5;
            actualizarUI();
        }
    
        logger.log("✚ Eevee curó a " + this.toString() + " y ahora tiene " + this.vida + " puntos de vida! ✚");
    }
}