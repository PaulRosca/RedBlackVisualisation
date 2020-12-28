import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for decreasing the world's operation speed.
 * 
 * @see Background.decreaseWorldSpeed()
 * @see WorldInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Minus extends WorldInteractionButton
{

    /**
     * Constructor for the button.
     * @param myWorld the world in which the button is present
     */
    public Minus(Background myWorld)
    {
        super(myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("Minus.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("MinusFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("MinusPressed.png");
    }  

    /**
     * Method that calls for the decrease of the world's operation speed
     */
    @Override
    public void function(String k)
    {
        world.decreaseWorldSpeed();
    }
}
