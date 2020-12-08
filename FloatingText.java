import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FloatingText extends Actor
{
    /**
     * Act - do whatever the Text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String text="";
    public FloatingText()
    {
        updateImage();
    }

    public void updateImage()
    {
        setImage(new GreenfootImage(text,20,Color.BLACK,null));
    }

    public void addChar(String c)
    {
        if(!c.equals("0")||text.length()>0)
        text+=c;
        updateImage();
    }

    public void removeChar()
    {
        if(text.length()>0)
        {
            text=text.substring(0,text.length()-1);
            updateImage();
        }
    }
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
