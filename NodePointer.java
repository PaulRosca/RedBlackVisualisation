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
    public void setLocationTransition(int x,int y,int t)
    {
       int dX=x-getX(),dY=y-getY();
       int counter=0;
        while(getX()!=x)
        {
            if(counter==t)
                {
                    Greenfoot.delay(2);
                    counter=1;
                }
            setLocation(getX()+2,getY()+2);
            System.out.println("x : " + getX()+"\tdesired x : "+x);
            counter++;
        }
        System.out.println("\nx: "+getX()+"\ny : "+getY());
    }
}
