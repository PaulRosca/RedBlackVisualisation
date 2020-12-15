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
    public Button()
    {
        tb=new TextBox(70,20);
        text = new Text();
    }

    public void act() 
    {
        focused=false;
        CheckMouse();
        setState();
        updateTextBox();

    }

    public void updateTextBox()
    {
        if(Greenfoot.mouseClicked(tb))
            {
                MouseTracker.setCurrentFocus(tb);
                String key=Greenfoot.getKey();//We clear key buffer
            }
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
            else if(Greenfoot.isKeyDown("enter"))
                applyFunction();
            else if(key!=null&&text.getString().length()<6)
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
            applyFunction();
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
    private void applyFunction()
    {
        String auxString=text.getString();
        text.clear();
        if(!auxString.isEmpty())
            function(auxString);
    }
    public void function(String k){}
}
