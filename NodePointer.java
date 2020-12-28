import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents a visual pointer to a node.
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class NodePointer extends Actor
{
    /**
     * The world in which this object is present.
     */
    Background world;

    /**
     * Constructor for our Node Pointer. We only set the world of the object here.
     * 
     * @param myWorld the world in which this object is present
     */
    public NodePointer(Background myWorld)
    {
        world=myWorld;
    }

    /**
     * Method for scrolling the world in such a way that this object gets in the defaul root coordinates
     * @see Background#RootDefaultX
     * @see Background#RootDefaultY
     */
    public void focusOnThis()
    {
        world.getScroller().scroll(world.getRootDefaultX()-getX(),world.getRootDefaultY()-getY());
    }

    /**
     * Method for moving the object the object in the world slowly.
     * <p>
     * It is designed to work on this project specific possible new X and new Y values 
     * (in multiples of 50)
     * 
     * @param x new X coordinate
     * @param y new Y coordinate
     */
    public void setLocationTransition(int x,int y)
    {
        // The difference from the new coordinates to the old ones
        int dX=x-getX(),dY=y-getY();
        // The number of pixels we move on X and Y axis in one iteration
        int speedX=dX/50,speedY=dY/50;
        // Variable used to speed up (slow down the slow down) the transition
        int timer=0;
        /* The number of pixels the pointer shifted on the X axis
         * We need to account for it because every time the pointer changes location with this method
         * it is guaranteed to be focused on (so the actual coordinates won't change)
         */
        int shiftedX=0;
        while(getX()+shiftedX!=x)// Until we've reached the desired new X coordinate
        // We only need to account for one axis, because both will get there at the same time, being multiples of 50
        {
            if(timer==1)
            {
                Greenfoot.delay(1);// We slow down the execution
                timer=0;
            }
            setLocation(getX()+speedX,getY()+speedY);// We move the pointer in the desired direction
            focusOnThis();// We focus the view on the pointer
            shiftedX+=speedX;// We update how much the node has shifted on the X axis
            timer++;
        }

    }
}
