import greenfoot.*;

public class Critico extends Actor{
    private GifImage gif = new GifImage("textoCritico.gif");

    public void act() {
        setImage(gif.getCurrentImage());
    }

    public void animar() {
        for (int i = 0; i < 53; i++) {
            act();
            Greenfoot.delay(1);
        }
    }
}
