import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents the visual connector from a node to it's parent.
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Connector extends Actor
{
    /**
     * Default consturctor for creating a connector
     */
    public Connector()
    {
    }
    /**
     * Constructor for creating a connector of a specific size
     * @param x length
     * @param y width
     */
    public Connector(int x,int y)
    {
        GreenfootImage img=getImage();
        img.scale(x,y);
        setImage(img);
    }
    /**
     * Method for chaning the size of the connector 
     */
    public void setScale(int x,int y) 
    {
        GreenfootImage img=getImage();
        img.scale(x,y);
        setImage(img);
    }    
}
