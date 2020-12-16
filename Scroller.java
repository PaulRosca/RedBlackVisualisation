import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Scroller here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scroller extends Actor
{
    /**
     * Act - do whatever the Scroller wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private World world;
    private int scrolledX,scrolledY;
    public Scroller(World myWorld)
    {
    world=myWorld;
    }
    public void act() 
    {
        
    }   
    public void scroll(int dx,int dy)
    {
        
        scrolledX+=dx;
        scrolledY+=dy;
        for (Object obj : world.getObjects(null))
        {
            Actor actor = (Actor) obj;
            actor.setLocation(actor.getX()+dx, actor.getY()+dy);
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
