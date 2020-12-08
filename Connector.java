import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Connector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Connector extends Actor
{
    /**
     * Act - do whatever the Connector wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Connector()
    {
    }

    public Connector(int x,int y)
    {
        GreenfootImage img=getImage();
        img.scale(x,y);
        setImage(img);
    }

    public void setScale(int x,int y) 
    {
        GreenfootImage img=getImage();
        img.scale(x,y);
        setImage(img);
    }    
}
