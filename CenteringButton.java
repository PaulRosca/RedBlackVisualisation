import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for centering the world.
 * 
 * @see Background.centerView()
 * @see WorldInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class CenteringButton extends WorldInteractionButton
{
    /**
     * Constructor for the button.
     * @param myWorld the world in which the button is present
     */
    public CenteringButton(Background myWorld)
    {
        super(myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("Centering.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("CenteringFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("CenteringPressed.png");
    }

    /**
     * Method that calls for the centering of the world
     */
    @Override
    public void function(String k)
    {
        world.centerView();
    }
}
