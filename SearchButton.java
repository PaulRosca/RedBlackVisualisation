import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SearchButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SearchButton extends TreeInteractionButton
{
    public SearchButton(RBTree rbt,Background myWorld)
    {
        super(rbt,myWorld);
    }
    @Override
    public void setNormal()
    {
        setImage("SearchButton.png");
    }
    
    @Override
    public void setFocused()
    {
        setImage("SearchButtonFocused.png");
    }
    
    @Override
    public void setPressed()
    {
        setImage("SearchButtonPressed.png");
    }
    @Override
    public void function(String k)
    {
        world.getInfoAlgorithm().setOperation("Searching node with key "+k);
        int value=Integer.parseInt(k);
        t.search(value);
        world.getInfoAlgorithm().clear();
        
    }
}
