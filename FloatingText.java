import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * This class is used to hold and edit a string in the form of a visual object. 
 * <p>
 * This object has the same functions as {@link Text}, but will be moved when the world is scrolling, 
 * because it's not a fixed object.
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class FloatingText extends Actor
{
    /**
     * The actual string of the Text
     */
    private String text="";

    /**
     *Constructor for the FloatingText object. We just set the image.
     */
    public FloatingText()
    {
        updateImage();
    }

    /**
     * Method used to update the image of the object with the current text
     */
    private void updateImage()
    {
        setImage(new GreenfootImage(text,20,Color.BLACK,null));
    }

    /**
     * Method used to add a String to our current text
     * 
     * @param c the string desired to be added to our text
     */
    public void addChar(String c)
    {
        if(!c.equals("0")||text.length()>0)
            text+=c;
        updateImage();
    }

    /**
     * Method used for removing the last character in our text
     */
    public void removeChar()
    {
        if(text.length()>0)
        {
            text=text.substring(0,text.length()-1);
            updateImage();
        }
    }

    /**
     * Method used for flushing the text of our object
     */
    public void clear()
    {
        text="";
        updateImage();
    }

    public String getString()
    {
        return text;
    }
}
