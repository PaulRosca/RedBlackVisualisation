import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Clock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Clock extends WorldInteractionButton
{
    public Clock(Background myWorld)
    {
        super(myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("Clock.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("ClockFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("ClockPressed.png");
    }
    @Override
    public void function(String k)
    {
       world.resetWorldSpeed();
    }  
}
