import greenfoot.*;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;
import greenfoot.core.GreenfootMain;

public class BotonPlay extends Actor
{   
    SoundManager soundIntro = new SoundManager("intro.mp3"); 
    
    public BotonPlay(int x, int y) {
        GreenfootImage botonPlay = new GreenfootImage("play.png");
        setImage(botonPlay);
    }
   
    public void act() {
        // Código medio random para que no empiece la música hasta que le des a run en Greenfoot
        // No sabía como preguntar si el mundo esta corriendo o no.
        if (!Greenfoot.isKeyDown("3")){
            soundIntro.play();
        }

        if (Greenfoot.mouseClicked(this) || (Greenfoot.isKeyDown("ENTER") || (Greenfoot.isKeyDown("SPACE")))){
            GreenfootImage image = getImage();
            image.scale(490, 490); 
            setImage(image); 
            Greenfoot.delay(10);
            image.scale(500, 500); 
            setImage(image);
            Greenfoot.delay(10);
            Greenfoot.setWorld(new PantallaDuelo());
            soundIntro.stop();
        }
    }
}
