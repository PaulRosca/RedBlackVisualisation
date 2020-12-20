import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SkipBackwardButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SkipBackwardButton extends WorldInteractionButton
{
    public SkipBackwardButton(Background myWorld)
    {
        super(myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("SkipBackward.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("SkipBackwardFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("SkipBackwardPressed.png");
    }        
    @Override
    public void function(String k)
    {
       world.skipBackward();
    }
}
