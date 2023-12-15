import greenfoot.*;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;
public class MenuPrincipal extends World
{

    public MenuPrincipal()
    {    
        super(512,512, 1);
        prepare();
    
    }

    public void prepare(){
        BotonPlay botonPlay = new BotonPlay(258, 261); 
        addObject(botonPlay, 258, 261);
    }

}
