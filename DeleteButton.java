import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is an UI Button responsible for deleting a node from the tree.
 * 
 * @see RBTree.deleteKey()
 * @see TreeInteractionButton
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class DeleteButton extends TreeInteractionButton
{
    /**
     * Constructor for the button.
     * @param rbt the tree to which the button is connected to
     * @param myWorld the world in which the button is present
     */
    public DeleteButton(RBTree rbt,Background myWorld)
    {
        super(rbt,myWorld);
    }

    /**
     * Method that sets the button's image to the normal image
     */
    @Override
    public void setNormal()
    {
        setImage("DeleteButton.png");
    }

    /**
     * Method that sets the button's image to the focused image
     */
    @Override
    public void setFocused()
    {
        setImage("DeleteButtonFocused.png");
    }
    
    /**
     * Method that sets the button's image to the pressed image
     */
    @Override
    public void setPressed()
    {
        setImage("DeleteButtonPressed.png");
    }
    /**
     * Method that calls for the deletion of a node in the tree
     * @param k the key of the node we call the deletion for
     */
    @Override
    public void function(String k)
    {
        world.getInfoAlgorithm().setOperation("Deleting node with key "+k);
        int value=Integer.parseInt(k);
        t.deleteKey(value);// We call for the deletion of the node from the tree
        world.getInfoAlgorithm().clear();
    }
}
