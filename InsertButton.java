import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InsertButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InsertButton extends TreeInteractionButton
{
    public InsertButton(RBTree rbt)
    {
        super(rbt);
    }
    @Override
    public void setNormal()
    {
        setImage("InsertButton.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("InsertButtonFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("InsertButtonPressed.png");
    }
    @Override
    public void function(String k)
    {
        int value=Integer.parseInt(k);
        t.insert(value);
        
    }
}
