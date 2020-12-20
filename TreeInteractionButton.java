import greenfoot.*;
/**
 * Write a description of class TreeInteractionButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TreeInteractionButton  extends Button
{
    /**
     * Constructor for objects of class TreeInteractionButton
     */
    protected RBTree t;
    private Text text;
    private TextBox tb;
    private int timer=0;
    public TreeInteractionButton(RBTree rbt,Background myWorld)
    {
        super(myWorld);
        t=rbt;
        tb=new TextBox(70,20);
        text = new Text();
    }
    public void act()
    {
     super.act();
     updateTextBox();
    }
        public void updateTextBox()
    {
        if(Greenfoot.mouseClicked(tb)||Greenfoot.mouseClicked(text))
            {
                MouseTracker.setCurrentFocus(tb);
                String key=Greenfoot.getKey();//We clear key buffer
            }
        if(MouseTracker.getCurrentFocus()==tb)
        {
            tb.setFocused(true);
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
        else
            tb.setFocused(false);
    }
        public TextBox getTextBox()
    {
        return tb;
    }

    public Text getText()
    {
        return text;
    }
    @Override
    protected void applyFunction()
    {
        String auxString=text.getString();
        text.clear();
        if(!auxString.isEmpty())
            {
                function(auxString);
                world.addCurrentTreeToList();
            }
    }


}
