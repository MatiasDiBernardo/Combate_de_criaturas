import greenfoot.*;

public class BotonReactor {
    Boton boton;
    UIAtaques uiAtaques;
    Criatura criaturaActual;
    int numeroAtaque;

    public BotonReactor(Boton boton, UIAtaques uiAtaques, Criatura criaturaActual, int numeroAtaque) {
        this.boton = boton;
        this.uiAtaques = uiAtaques;
        this.criaturaActual = criaturaActual;
        this.numeroAtaque = numeroAtaque;

        boton.actualizar(this);
    }

    public void run() {
        // Aca descomente lo que estaba para que pasen los turnos.
        PantallaDuelo pantallaDuelo = ((PantallaDuelo) uiAtaques.getWorld());

        if (uiAtaques.botonSeleccionado == boton) {
            // Si clickeo lo que está seleccionado, lo des-selecciona
            uiAtaques.ataque = null;
            uiAtaques.resetColorBotones();
            uiAtaques.descripcion.setText("");
            uiAtaques.botonSeleccionado = null;
        } else {
            // Si clickeo algo NO seleccionado, lo selecciona y prepara el posible ataque
            uiAtaques.ataque = () -> {
                if (puedeAtacarlo(uiAtaques.ataqueObjetivo)) {
                    // Si puedo realizar el ataque (controlado según tipo de ataque) entonces realizar
                    // el ataque de la criatura actual respecto a la criatura objetivo.Y cuando termino el ataque
                    // paso al siguiente turno.
                    atacar(uiAtaques.ataqueObjetivo);
                    pantallaDuelo.turno();
                }
            };
            uiAtaques.resetColorBotones();
            boton.actualizar(new Color(255, 215, 98 ));
            uiAtaques.descripcion.setText(getDetalleAtaque(criaturaActual));
            uiAtaques.botonSeleccionado = boton;
        }
    }

    public String getNombreAtaque() {
        return this.criaturaActual.getNombresAtaque()[this.numeroAtaque - 1];
    }

    public String getDetalleAtaque(Criatura c) {
        return this.criaturaActual.getDetallesAtaque()[this.numeroAtaque - 1];
    }

    public boolean puedeAtacarlo(Criatura otro) {
        int ataqueloco = this.criaturaActual.getAtaqueRevividor();
        if (otro.estaDesmayado()) {
            // Verificar si la criatura actual tiene la capacidad de revivir
            if (this.criaturaActual.puedeRevivir) {
                if(this.criaturaActual.getAtaqueRevividor() == this.numeroAtaque){
                    return true;
                }
            }
            return false;
        }
        switch (this.numeroAtaque) {
            case 1:
                return this.criaturaActual.puedeRealizarAtaque1En(otro);
            case 2:
                return this.criaturaActual.puedeRealizarAtaque2En(otro);
            case 3:
                return this.criaturaActual.puedeRealizarAtaque3En(otro);
            case 4:
                return this.criaturaActual.puedeRealizarAtaque4En(otro);
        }
        return false;
    }

    public void atacar(Criatura otro) {
        switch (this.numeroAtaque) {
            case 1:
                this.criaturaActual.atacar1(otro);
                break;
            case 2:
                this.criaturaActual.atacar2(otro);
                break;
            case 3:
                this.criaturaActual.atacar3(otro);
                break;
            case 4:
                this.criaturaActual.atacar4(otro);
                break;
        }
    }
}
