import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Minus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Minus extends WorldInteractionButton
{
    public Minus(Background myWorld)
    {
        super(myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("Minus.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("MinusFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("MinusPressed.png");
    }  
    @Override
    public void function(String k)
    {
       world.decreaseWorldSpeed();
    }
}
