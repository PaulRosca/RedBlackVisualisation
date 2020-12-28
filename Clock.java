import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for reseting the world's operation speed.
 * 
 * @see Background.resetWorldSpeed()
 * @see WorldInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Clock extends WorldInteractionButton
{
    /**
     * Constructor for the button.
     * @param myWorld the world in which the button is present
     */
    public Clock(Background myWorld)
    {
        super(myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("Clock.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("ClockFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("ClockPressed.png");
    }

    /**
     * Method that calls for the reseting of the world's operation speed
     */
    @Override
    public void function(String k)
    {
        world.resetWorldSpeed();
    }  
}
