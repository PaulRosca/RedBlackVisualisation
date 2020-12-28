import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents a canvas for a field in which the user can enter information
 * 
 * @see Fixed
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class TextBox extends Fixed
{
    /**
     * Constructor for our TextBox. It just sets it's image.
     */
    public TextBox()
    {
        setImage("TextBox.png");
    }
    
    /**
     * Method that chnages the TextBox's image according to it's state (focused/unfocused)
     * @param focused flag that tells us if the user is focused on this textbox
     */
    public void setFocused(boolean focused)
    {
       if(focused)
            setImage("TextBoxFocused.png");
       else
            setImage("TextBox.png");
    }
}
