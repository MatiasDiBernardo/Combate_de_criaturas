import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class Boton extends Actor {
    private String texto;
    private int size;
    private Color color;
    private Color bgColor;
    
    private BotonReactor onClick;

    private int forceWidth;
    private int forceHeight;

    public Boton(String texto, BotonReactor onClick, int size, Color color, int width, int height) {
        this.texto = texto;

        this.onClick = onClick;

        this.size = size;
        this.color = color;
        this.bgColor = Color.LIGHT_GRAY;

        this.forceWidth = width;
        this.forceHeight = height;

        render();
    }

    public Boton(String texto, BotonReactor onClick) {
        this(texto, onClick, 30, Color.BLACK, 0, 0);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            onClick.run();
        }
    }
    
    public void actualizar(BotonReactor onClick) {
        this.texto = onClick.getNombreAtaque();
        this.onClick = onClick;

        render();
    }
    
    public void actualizar(String texto, BotonReactor onClick, Color bgColor) {
        this.texto = texto;
        this.onClick = onClick;
        this.bgColor = bgColor;

        render();
    }
    
    public void actualizar(Color bgColor) {
        this.bgColor = bgColor;

        render();
    }
    
    
    private void render() {
        // 1.: Calculate planned size based on font size and bigBorder configuration
        GreenfootImage txtImg = new GreenfootImage(" " + texto + " ", size, color, bgColor);
        int BORDER_X = 2;
        int BORDER_Y = 2;


        int height = forceHeight != 0 ? forceHeight : txtImg.getHeight() + 2 * BORDER_Y;
        int width = forceWidth != 0 ? forceWidth : txtImg.getWidth() + 2 * BORDER_X;


        // 3.: Create actual image instance

        GreenfootImage image = new GreenfootImage(width, height);


        // 4.: Fill background with theme main color

        image.setColor(Color.LIGHT_GRAY);
        image.fill();
        image.setColor(bgColor);
        image.fillRect(BORDER_X, BORDER_Y, width - BORDER_X * 2, height - BORDER_Y * 2);


        // 5.: Draw text onto background

        image.drawImage(
            txtImg,
            (width - txtImg.getWidth()) / 2,
            (height - txtImg.getHeight()) / 2
        );

        /*
        if(drawOutline) {
            image.setColor(elementColor("outline"));
            for(int i=0; i<outlineWidth; i++)
                image.drawRect(i, i, width - 2 * i - 1, height - 2 * i - 1);
        }
        */

        setImage(image);
    }

}
