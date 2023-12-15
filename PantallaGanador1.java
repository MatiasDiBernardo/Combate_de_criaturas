import greenfoot.*;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;
public class PantallaGanador1 extends World
{

    /**
     * Constructor for objects of class StartMenu.
     * 
     */
    public PantallaGanador1()
    {    
        super(512,512, 1);
        prepare();
        
    }

    public void prepare(){
        BotonRestart botonRestart = new BotonRestart(256,275); 
        addObject(botonRestart, 256,275); 
    }
}
