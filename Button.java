import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * This is a superclass for all UI Buttons.
 * <p>
 * The class is responsible for setting a button's state and applying it's function.
 * 
 * @see Fixed
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Button extends Fixed
{
    /**
     * Flags that tell us if the user clicked this button or if the mouse is 
     * hovering over it
     */
    private boolean pressed=false,focused=false;
    /**
     * The world in which the button is present
     */
    protected Background world;
    /**
     * The size of the button
     */
    private int xScale,yScale;
    /**
     * Default constructor that sets the button's world
     * @param myWorld the world in which the button is present
     */
    public Button(Background myWorld)
    {
        world=myWorld;
    }

    /**
     * Method that continiously updates the button's state
     */
    public void act() 
    {
        focused=false;
        CheckMouse();
    }

    /**
     * Method that sets the button's state according to mouse information
     */
    private void CheckMouse()
    {
        MouseInfo mouse=Greenfoot.getMouseInfo();
        if(mouse!=null)
        {
            // We get all the objects that the mouse currently points at
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Button.class);
            for (Object object : objects)
            {
                if (object == this)// If the mouse is hovering over this object
                {
                    focused=true;// We flag it as focused
                    if(!pressed)// Unless it's also pressed
                        setFocused();// We set it as focused
                    break;
                }
            }
        }
        if(Greenfoot.mousePressed(this))// If the user pressed on this object
        {
            pressed=true;// We flag it as pressed
            setPressed();// We set it as pressed
        }
        else if(Greenfoot.mouseClicked(this))// If the user clicked on this object
        {
            pressed=false;// We flag it as not pressed
            setNormal();// We set it to normal
            applyFunction();// We apply the button's fucntion
        }
        else if(!focused)// If the button isn't focused
        {
            pressed=false;// We flag it as not pressed also
            setNormal();// We set it to normal
        }

    }

    /**
     * Method called when the button is in normal mode (<b>to be overriden</b>)
     */
    public void setNormal(){}

    /**
     * Method called when the button is in focused mode (<b>to be overriden</b>)
     */
    public void setFocused(){}

    /**
     * Method called when the button is in pressed mode (<b>to be overriden</b>)
     */
    public void setPressed(){}

    /**
     * Method that applies the function of the button.
     * It calls {@link function}
     */
    protected void applyFunction()
    {
        function("");
    }
    /**
     * Method called when the user clicks on the button (<b>to be overriden</b>)
     */
    public void function(String k){}
}
