import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for moving backward in the tree opeation sequence
 * 
 * @see Background.skipBackward()
 * @see WorldInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class SkipBackwardButton extends WorldInteractionButton
{
    /**
     * Constructor for the button.
     * @param myWorld the world in which the button is present
     */
    public SkipBackwardButton(Background myWorld)
    {
        super(myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("SkipBackward.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("SkipBackwardFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("SkipBackwardPressed.png");
    }        

    /**
     * Method that calls for moving backward in the tree opeation sequence
     */
    @Override
    public void function(String k)
    {
        world.skipBackward();
    }
}
