import greenfoot.*;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;
public class BotonRestart extends Actor

{   
    SoundManager soundOutro = new SoundManager("outro.mp3");
    
    public BotonRestart(int x, int y) {
        GreenfootImage botonRestart = new GreenfootImage("botonreplay.png");
        setImage(botonRestart); 
        soundOutro.play();
    }

    public void act() {
        if (Greenfoot.mouseClicked(this) || (Greenfoot.isKeyDown("ENTER") || (Greenfoot.isKeyDown("SPACE")))){
            GreenfootImage image = getImage();
            image.scale(55, 55); 
            setImage(image); 
            soundOutro.stop();
            Greenfoot.delay(10);
            GreenfootImage botonRestart = new GreenfootImage("botonreplay.png");
            setImage(botonRestart);
            System.out.println("LLega hasta que se para la canción");
            Greenfoot.delay(10);
            Greenfoot.setWorld(new PantallaDuelo());
        }
    } 
}
