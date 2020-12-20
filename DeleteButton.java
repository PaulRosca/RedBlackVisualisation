import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DeleteButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeleteButton extends TreeInteractionButton
{
    public DeleteButton(RBTree rbt,Background myWorld)
    {
        super(rbt,myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("DeleteButton.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("DeleteButtonFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("DeleteButtonPressed.png");
    }
    @Override
    public void function(String k)
    {
        int value=Integer.parseInt(k);
        t.deleteKey(value);
        
    }
}
