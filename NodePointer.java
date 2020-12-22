import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NodePointer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodePointer extends Actor
{
    Background world;
    public NodePointer(Background myWorld)
    {
        world=myWorld;
    }
    public void focusOnThis()
    {
        world.getScroller().scroll(world.getRootDefaultX()-getX(),world.getRootDefaultY()-getY());
    }
    public void setLocationTransition(int x,int y)
    {
       int dX=x-getX(),dY=y-getY();
       int speedX=dX/50,speedY=dY/50;
       int counter=0;
       int shiftedX=0;
        while(getX()+shiftedX!=x)
        {
            if(counter==1)
                {
                    Greenfoot.delay(1);
                    counter=0;
                }
            setLocation(getX()+speedX,getY()+speedY);
            focusOnThis();
            shiftedX+=speedX;
            counter++;
        }
        
    }
}
