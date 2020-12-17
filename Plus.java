import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Plus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plus extends Button
{
    private Background world;
    public Plus(Background myWorld)
    {
        super();
        world=myWorld;
    }
    @Override
    public void setNormal()
    {
        setImage("Plus.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("PlusFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("PlusPressed.png");
    }   
}
