import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NodePointer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodePointer extends Actor
{
    /**
     * Act - do whatever the NodePointer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */


    public void setLocationTransition(int x,int y)
    {
     
       int dX=x-getX(),dY=y-getY();
       int absX=Math.abs(dX),absY=Math.abs(dY);
       int speedX=dX/50,speedY=dY/50;
       int counter=0;
        while(getX()!=x)
        {
            if(counter==1)
                {
                    Greenfoot.delay(1);
                    counter=0;
                }
            setLocation(getX()+speedX,getY()+speedY);
            counter++;
        }
    }
}
