import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Fixed
{

    private boolean pressed=false,focused=false;

    private int xScale,yScale;
    public Button()
    {

    }

    public void act() 
    {
        focused=false;
        CheckMouse();
        setState();
       

    }



    public void CheckMouse()
    {
        MouseInfo mouse=Greenfoot.getMouseInfo();
        if(mouse!=null)
        {
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Button.class);
            for (Object object : objects)
            {
                if (object == this)
                    focused=true;
            }
        }
        if(Greenfoot.mousePressed(this))
            pressed=true;
        else if(Greenfoot.mouseClicked(this))
        {
            pressed=false;
            applyFunction();
        }
        else if(!focused)
            pressed=false;

    }
    
    public void setState()
    {
        if(pressed)
            setPressed();
        else if(focused)
            setFocused();
        else
            setNormal();
    }

    public void setNormal(){}

    public void setFocused(){}

    public void setPressed(){}
    protected void applyFunction()
    {
    function("");
    }

    public void function(String k){}
}
