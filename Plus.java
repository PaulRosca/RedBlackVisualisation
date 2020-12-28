import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for increasing the world's operation speed.
 * 
 * @see Background.increaseWorldSpeed()
 * @see WorldInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Plus extends WorldInteractionButton
{

    /**
     * Constructor for the button.
     * @param myWorld the world in which the button is present
     */
    public Plus(Background myWorld)
    {
        super(myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("Plus.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("PlusFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("PlusPressed.png");
    }   

    /**
     * Method that calls for the increase of the world's operation speed
     */
    @Override
    public void function(String k)
    {
        world.increaseWorldSpeed();
    }
}
