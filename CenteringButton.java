import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CenteringButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CenteringButton extends Button
{
    private Background world;
    public CenteringButton(Background myWorld)
    {
        super();
        world=myWorld;
    }
    @Override
    public void setNormal()
    {
        setImage("Centering.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("CenteringFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("CenteringPressed.png");
    }
    @Override
    public void function(String k)
    {
       world.centerView();
    }
}
