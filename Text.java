import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * This class is used to hold and edit a string in the form of a visual object
 * 
 * @see Fixed
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Text extends Fixed
{

    /**
     * The actual string of the Text
     */
    private String text="";
    /**
     * The color of the text
     */
    private Color color;
    /**
     * The font of the text
     */
    private int fontSize;
    
    /**
     * Constructor for the Text object. In this we set the color and the font size of the text.
     * 
     * @param c the color desired for the text
     * @param fs the font size desired for the text
     */
    public Text(Color c,int fs)
    {
        color=c;
        fontSize=fs;
        updateImage();
    }
    
    /**
     * Method used to update the image of the object with the current text
     */
    private void updateImage()
    {
        setImage(new GreenfootImage(text,fontSize,color,null));
    }

    /**
     * Method used to add a String to our current text
     * 
     * @param c the string desired to be added to our text
     */
    public void addString(String c)
    {
        if(!c.equals("0")||text.length()>0)// We make sure we can't start a number with 0
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
