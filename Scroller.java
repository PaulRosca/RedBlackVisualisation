import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is responsible for scrolling the background and hold information about how much we've scrolled.
 * <p>
 * Class inspired by https://www.greenfoot.org/scenarios/18226
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Scroller extends Actor
{
    
    /**
     * The world which we scroll
     */
    private World world;
    /**
     * The number of pixels we've scrolled on each axis (relative to the original world)
     */
    private int scrolledX,scrolledY;
    
    /**
     * Constructor for our scroller.
     * 
     * @param myWorld the world which we will scroll
     */
    public Scroller(World myWorld)
    {
    world=myWorld;
    }

    /**
     * Method for scrolling the world.
     * 
     * @param dx the number of pixels we scroll on X axis
     * @param dy the number of pixels we scroll on Y axis
     */
    public void scroll(int dx,int dy)
    {
        // We update the ammout of pixels we've scrolled on each axis
        scrolledX+=dx;
        scrolledY+=dy;
        for (Object obj : world.getObjects(null))
        {
            Actor actor = (Actor) obj;
            actor.setLocation(actor.getX()+dx, actor.getY()+dy);// We move all the actors in the world by how much we're scrolling
        }
    }
    public int getScrolledX()
    {
        return scrolledX;
    }
    
    public int getScrolledY()
    {
        return scrolledY;
    }
    
}
