import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SkipForward here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SkipForwardButton extends WorldInteractionButton
{
    public SkipForwardButton(Background myWorld)
    {
        super(myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("SkipForward.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("SkipForwardFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("SkipForwardPressed.png");
    }
        @Override
    public void function(String k)
    {
       world.skipForward();
    }
}
