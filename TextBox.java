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
        GreenfootImage img = getImage();
        img.scale(x,y);
        setImage(img);

    }
}
