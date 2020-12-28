import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for inserting a node in the tree.
 * 
 * @see RBTree.insert()
 * @see TreeInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class InsertButton extends TreeInteractionButton
{
    /**
     * Constructor for the button.
     * @param rbt the tree to which the button is connected to
     * @param myWorld the world in which the button is present
     */
    public InsertButton(RBTree rbt,Background myWorld)
    {
        super(rbt,myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("InsertButton.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("InsertButtonFocused.png");
    }

    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("InsertButtonPressed.png");
    }

    /**
     * Method that calls for the insertion of a new node in the tree
     * @param k the key of the node we call the insertion for
     */
    @Override
    public void function(String k)
    {
        world.getInfoAlgorithm().setOperation("Inserting node with key "+k);
        int value=Integer.parseInt(k);
        t.insert(value);// We call for the insertion of the node in the tree
        world.getInfoAlgorithm().clear();
    }
}
