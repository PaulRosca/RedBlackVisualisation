import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends World
{

    /**
     * Constructor for objects of class Background.
     * 
     */
    Scroller scroller;
    RBTree rbt;
    boolean pressed=false;
    int initialPositionX,initialPositionY;
    MouseInfo mouse;
    InsertButton ib;
    CenteringButton cb;
    boolean followPointer;
    public Background()
    {    
        super(1000, 600, 1,false); 
        Greenfoot.setSpeed(50);
        scroller = new Scroller(this);
        rbt = new RBTree(this);
        addObject(rbt,0,0);
        ib=new InsertButton(rbt);
        addObject(ib,930,560);
        addObject(ib.getTextBox(),830,561);
        addObject(ib.getText(),830,561);
        cb=new CenteringButton(this);
        addObject(cb,960,40);
        setPaintOrder(Fixed.class,NodePointer.class);
        followPointer=false;

    }

    public void act()
    {
        
        mouse = Greenfoot.getMouseInfo();
        checkMouse();
        if(pressed&&mouse!=null)
        {
            scroller.scroll(mouse.getX()-initialPositionX,mouse.getY()-initialPositionY);
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
        }
    }

    public void checkMouse()
    {
        if(Greenfoot.mousePressed(null))
        {
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
            pressed=true;
        }
        else if(Greenfoot.mouseClicked(null))
        {
            pressed=false;
            MouseTracker.setCurrentFocus(null);
        }

    }
    public void setFollowPointer(boolean fp)
    {
        followPointer = fp;
    }
    public void centerView()
    {
        scroller.scroll(-scroller.getScrolledX(),-scroller.getScrolledY());
    }

}
