import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TextBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextBox extends Fixed
{
    /**
     * Act - do whatever the TextBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public TextBox(int x,int y)
    {
        setImage("TextBox.png");
    }
    public void setFocused(boolean focused)
    {
       if(focused)
            setImage("TextBoxFocused.png");
       else
            setImage("TextBox.png");
    }
}
