import greenfoot.*;
/**
 * This is a superclass for all the UI buttons that have functions related to the tree.
 * <p>
 * This class handles user's interaction with the button's assigned TextBox
 * @see Button
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class TreeInteractionButton  extends Button
{

    /**
     * The tree to which the button is connected to
     */
    protected RBTree t;
    /**
     * The TextBox of the button
     */
    private TextBox tb;
    /**
     * The text displayed over the TextBox
     */
    protected Text text;

    /**
     * Variable used to slow down the deletion of characters from the text
     */
    private int timer=0;

    /**
     * Constructor for the button.
     * 
     * @param rbt the tree to which the button is connected to
     * @param myWorld the world in which the button is present
     */
    public TreeInteractionButton(RBTree rbt,Background myWorld)
    {
        super(myWorld);
        t=rbt;
        tb=new TextBox();
        text = new Text(Color.BLACK,21);// Configuring the text's settings
    }

    /**
     * Method that contiounsly updates the button's TextBox 
     * (while also doing the previous functions from {@link Button.act()})
     */
    public void act()
    {
        super.act();
        updateTextBox();
    }
    
    /**
     * Method that updates the TextBox according to user's input
     */
    private void updateTextBox()
    {
        // If the user clicked the TextBox or the text inside it
        if(Greenfoot.mouseClicked(tb)||Greenfoot.mouseClicked(text))
        {
            MouseTracker.setCurrentFocus(tb);// We set the mouse's focuns on the textbox
            String key=Greenfoot.getKey();//We clear key buffer
        }
        // If the mouse is focused on this button's textbox
        if(MouseTracker.getCurrentFocus()==tb)
        {
            // We set the world speed to 49 to make the interaction with the textbox optimal
            Greenfoot.setSpeed(49);
            tb.setFocused(true);// We flag the textbox as being focused on
            String key=Greenfoot.getKey();
            if(Greenfoot.isKeyDown("backspace"))
            {
                timer++;
                if(timer==4)// Every 4th iteration
                {   
                    text.removeChar();// We remove a char
                    timer=0;
                }
            }
            else if(Greenfoot.isKeyDown("enter"))
                applyFunction();// We apply the function of the button
            // If the user pressed a key and our text hasn't reached the character limit
            else if(key!=null&&text.getString().length()<6)
            {
                if(key.compareTo("0")>=0&&key.compareTo("9")<=0)// If the key is a digit
                    text.addString(key);  // We add the digit to the text
            }
        }
        else
        {
            tb.setFocused(false);// We flag the textbox as not focused
        }
    }

    public TextBox getTextBox()
    {
        return tb;
    }

    public Text getText()
    {
        return text;
    }
    /**
     * Method that overrides {@link Button.applyFunction()} for adapting the world's speed and applying the fucntion
     * with the String in {@link text}
     */
    @Override
    protected void applyFunction()
    {
        world.applyWorldSpeed();// We set the world speed to the one reserved for tree operations
        String auxString=text.getString();
        text.clear();// We clear the text in the TextBox
        if(!auxString.isEmpty())
        {
            function(auxString);// We call the button's function with the text from the textbox
            world.addCurrentTreeToList();// We add the current tree in the operations' sequence
        }
        world.maxWorldSpeed();// We revert the world to it's max speed
    }

}
