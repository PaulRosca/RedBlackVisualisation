import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for searching a node in the tree.
 * 
 * @see RBTree.search()
 * @see TreeInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class SearchButton extends TreeInteractionButton
{

    /**
     * Constructor for the button.
     * @param rbt the tree to which the button is connected to
     * @param myWorld the world in which the button is present
     */
    public SearchButton(RBTree rbt,Background myWorld)
    {
        super(rbt,myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("SearchButton.png");
    }

        /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("SearchButtonFocused.png");
    }
    
    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("SearchButtonPressed.png");
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
            function(auxString);// We call the button's function with the text from the textbox
        world.maxWorldSpeed();// We revert the world to it's max speed
    }
     /**
     * Method that calls for the search of a node in the tree
     * @param k the key of the node we call the search for
     */
    @Override
    public void function(String k)
    {
        world.getInfoAlgorithm().setOperation("Searching node with key "+k);
        int value=Integer.parseInt(k);
        t.search(value);// We call for the search of the node in the tree
        world.getInfoAlgorithm().clear();

    }
}
