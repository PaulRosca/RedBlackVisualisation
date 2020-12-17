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
    private Scroller scroller;
    private RBTree rbt;
    private boolean pressed=false;
    private int initialPositionX,initialPositionY;
    private MouseInfo mouse;
    private InsertButton ib;
    private SearchButton sb;
    private CenteringButton cb;
    private Minus mb;
    private Clock clk;
    private Plus pb;
    private boolean followPointer;
    public Background()
    {    
        super(1000, 600, 1,false); 
        Greenfoot.setSpeed(51);
        scroller = new Scroller(this);
        rbt = new RBTree(this);
        addObject(rbt,0,0);
        ib=new InsertButton(rbt);
        sb=new SearchButton(rbt);
        addObject(ib,930,560);
        addObject(ib.getTextBox(),830,561);
        addObject(ib.getText(),830,561);
        addObject(sb,700,560);
        addObject(sb.getTextBox(),600,561);
        addObject(sb.getText(),600,561);
        cb=new CenteringButton(this);
        addObject(cb,960,40);
        mb=new Minus(this);
        addObject(mb,35,555);
        clk=new Clock();
        addObject(clk,90,550);
        pb= new Plus(this);
        addObject(pb,150,552);
        setPaintOrder(Fixed.class,NodePointer.class,FloatingText.class,NodeRB.class,Connector.class);
        followPointer=false;

    }

    public void act()
    {
        
        mouse = Greenfoot.getMouseInfo();
        checkMouse();
        if(MouseTracker.getCurrentFocus()==null&&pressed&&mouse!=null)
        {
            scroller.scroll(mouse.getX()-initialPositionX,mouse.getY()-initialPositionY);
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
        }
    }

    public void checkMouse()
    {
        if(Greenfoot.mousePressed(null)&&mouse!=null)
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
    public int getScrolledX()
    {
    return scroller.getScrolledX();
    }
    public int getScrolledY()
    {
    return scroller.getScrolledY();
    }

}
