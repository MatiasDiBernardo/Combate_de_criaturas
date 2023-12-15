import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class UIOrdenRonda here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UIOrdenRonda extends Actor
{

    MyGreenfootImage imagenOrden;
    public UIOrdenRonda(String path){
        GreenfootImage imagenOrdenG = new GreenfootImage(path);
        imagenOrden = new MyGreenfootImage(imagenOrdenG);
        
        // Set the image of the actor
        imagenOrden.scale(40, 40);
        setImage(imagenOrden);
    }

    public void highlightTurn(){
        imagenOrden.highlight2(new Color (255, 215, 98 ));
    }

    public void deadTurn(){
        imagenOrden.grayscale();
    }

}
