import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class UIInfoCriatura extends Actor {
    private static final int MARGIN = 8;
    Criatura criatura;
    int width = 135;

    public UIInfoCriatura(Criatura criatura) {
        this.criatura = criatura;
    }

    @Override
    protected void addedToWorld(World world) {
        actualizar();
    }

    public void actualizar() {

        GreenfootImage imagenNombre = new GreenfootImage(criatura.toString(), 20, Color.WHITE, null);

        //variable para definir si muestra la vida o texto "desmayado"
        String vidaInfo= "";
        if (criatura.estaDesmayado()) {
            vidaInfo = "DESMAYADO"; 
        } else if (criatura.estaParalizado()) {
            vidaInfo = "Paralizado! " + criatura.getVida() + " / " + criatura.getVidaMaxima() + " ";
        } else {
            vidaInfo = " " + criatura.getVida() + " / " + criatura.getVidaMaxima() + " ";
        }

        GreenfootImage imagenVida = new GreenfootImage(vidaInfo, 20, Color.WHITE, null);
        double barraWidth = width - MARGIN * 2.5;
        GreenfootImage imagenBarra = new GreenfootImage(width - MARGIN * 2, 12);
        
        imagenBarra.setColor(new Color(192, 0, 32 )); //borgoña

        double doubleVida = criatura.getVida();
        double doubleVidaMax = criatura.getVidaMaxima();

        //comentado
        //imagenBarra.fillRect(0, 0, (int) (barraWidth / 2), 11);
        imagenBarra.fillRect(0, 0, (int) (barraWidth * (doubleVida/doubleVidaMax)), 11);

        imagenBarra.setColor(Color.WHITE);
        imagenBarra.drawRect(0, 0, (int) barraWidth - 1, 11);

        int height = imagenNombre.getHeight() + imagenBarra.getHeight() + imagenVida.getHeight();

        GreenfootImage imagen = new GreenfootImage(width, height);

        //imagen.setColor(criatura.esEquipo1() ? Color.PINK : Color.CYAN);
        //imagen.fill();

        imagen.drawImage(imagenNombre, (width - imagenNombre.getWidth()) / 2, 0);
        imagen.drawImage(imagenBarra, MARGIN, imagenNombre.getHeight());
        imagen.drawImage(imagenVida, (width - imagenVida.getWidth()) / 2,
                         imagenNombre.getHeight() + imagenBarra.getHeight());
            

        setImage(imagen);
    }
}
