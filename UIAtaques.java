import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class UIAtaques extends Actor {
    Criatura criaturaActual;
    Texto tituloAtaques;
    Boton b1, b2, b3, b4;
    Criatura[] criaturas;
    Boton botonSeleccionado;
    Parrafo descripcion;
    Runnable ataque = null;
    Criatura ataqueObjetivo = null;
    Criatura hoverObjetivo = null;

    public UIAtaques(Criatura[] criaturas) {
        this.criaturas = criaturas;

        b1 = new Boton("", null, 25, Color.WHITE, 320, 30);
        b2 = new Boton("", null, 25, Color.WHITE, 320, 30);
        b3 = new Boton("", null, 25, Color.WHITE, 320, 30);
        b4 = new Boton("", null, 30, Color.WHITE, 320, 36);
        descripcion = new Parrafo("Ataque", 20, Color.WHITE, 344, 192);
        tituloAtaques = new Texto("Ataques", 30, Color.WHITE, null);
    }

    @Override
    protected void addedToWorld(World world) {
        getWorld().addObject(tituloAtaques, 170, 225);
        getWorld().addObject(b1, 176, 265);
        getWorld().addObject(b2, 176, 300);
        getWorld().addObject(b3, 176, 335);
        getWorld().addObject(b4, 176, 373);
        getWorld().addObject(descripcion, 352 + 344 / 2, 300);
    }

    public void asignarCriaturaActual(Criatura criaturaActual) {
        // Inicializa criatura actual sin ningún ataque asociado y la resalta
        this.criaturaActual = criaturaActual;

        tituloAtaques.actualizarTexto(criaturaActual.toString());
        descripcion.setText("");
        botonSeleccionado = null;
        ataque = null;
        resetColorBotones();
        criaturaActual.setVisualSeleccionado(true);

        dibujarFondo();

        // Asocia los botones (que son ataques) con la criatura actual
        // Dentro de BotonReactor esta la lógica de como interactúan los ataques.
        Boton[] botones = { b1, b2, b3, b4 };
        for (int i = 0; i < botones.length; i++) {
            new BotonReactor(botones[i], this, criaturaActual, i + 1);
        }
        // Acá los botón reactor por default "según entendí que dijo Fede" se incializan
        // en la clase World. En general la lógica en GreenFoot esta manejada 
        // por eventos (o la lógica del programa acutal apunta a eso.)

        // Acá, puedo agregar procesar estado. O no, se puede manejar todo desde turno como
        // estaba haciendo.
    }

    public void click(Criatura c) {
        // Esto detecta si clickeo un criatura
        ataqueObjetivo = c;

        // Si cuando clickeo una criatura no tengo seleccionado un ataque me muestra las stats de esa criatura
        if (botonSeleccionado == null) {
            descripcion.setText(c.getStats());
        }
        // Si clickeo una criatura y tengo selecionado un ataque que le saque daño. 
        if (ataque != null) {
            ataque.run();
        }
    }

    public void hover(Criatura c) {
        if (hoverObjetivo == c) {
            return;
        }

        hoverObjetivo = c;
        if (botonSeleccionado == null) {
            // descripcion.actualizarTexto(c.toString());
        }
    }

    void resetColorBotones() {
        b1.actualizar(new Color(166, 135, 200 ));
        b2.actualizar(new Color(159, 118, 203 ));
        b3.actualizar(new Color(143, 87, 203  ));
        b4.actualizar(new Color(128, 57, 202  ));
    }

    private void dibujarFondo() {
        GreenfootImage imagenBarra = new GreenfootImage(700, 200);
        imagenBarra.setColor(new Color(139, 111, 174)); //violeta grisáceo
        imagenBarra.fill();
        imagenBarra.setColor(criaturaActual.esEquipo1() ? new Color(113, 0, 117): new Color(3,0,108));
        imagenBarra.fillRect(4, 4, 344, 192);

        imagenBarra.setColor(new Color(61, 29, 103 )); //violeta oscuro
        imagenBarra.fillRect(352, 4, 344, 192);
        setImage(imagenBarra);
    }
}
