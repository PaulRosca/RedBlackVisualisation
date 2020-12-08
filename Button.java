import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Fixed
{

    private boolean pressed=false,focused=false;
    private Text text;
    private TextBox tb;
    private int timer=0;
    private int xScale,yScale;
    public Button(int x,int y)
    {
        tb=new TextBox(200,20);
        text = new Text();
        xScale=x;
        yScale=y;
    }

    public void act() 
    {
        focused=false;
        CheckMouse();
        setState();
        updateTextBox();
        scale();

    }

    public void scale()
    {
        GreenfootImage img=getImage();
        img.scale(xScale,yScale);
        setImage(img);
    }

    public void updateTextBox()
    {
        if(Greenfoot.mouseClicked(tb))
            MouseTracker.setCurrentFocus(tb);
        if(MouseTracker.getCurrentFocus()==tb)
        {
            String key=Greenfoot.getKey();
            if(Greenfoot.isKeyDown("backspace"))
            {
                timer++;
                if(timer==4)
                {   
                    text.removeChar();
                    timer=0;
                }
            }
            if(key!=null)
            {
                if(key.compareTo("0")>=0&&key.compareTo("9")<=0)
                    text.addChar(key);  
            }

        }
    }

    public void CheckMouse()
    {
        MouseInfo mouse=Greenfoot.getMouseInfo();
        if(mouse!=null)
        {
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Button.class);
            for (Object object : objects)
            {
                if (object == this)
                    focused=true;
            }
        }
        if(Greenfoot.mousePressed(this))
            pressed=true;
        else if(Greenfoot.mouseClicked(this))
        {
            pressed=false;
            String auxString=text.getString();
            text.clear();
            if(!auxString.isEmpty())
                function(auxString);

        }
        else if(!focused)
            pressed=false;

    }

    public void setState()
    {
        if(pressed)
            setPressed();
        else if(focused)
            setFocused();
        else
            setNormal();
    }

    public void setNormal(){}

    public void setFocused(){}

    public void setPressed(){}

    public TextBox getTextBox()
    {
        return tb;
    }

    public Text getText()
    {
        return text;
    }

    public void function(String k){}
}
