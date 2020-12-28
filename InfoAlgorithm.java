import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is used for displaying the information of the algorithm
 * 
 * @see Fixed
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class InfoAlgorithm extends Fixed
{
    /**
     * The main operation currenlty in the algorithm
     */
    private Text operation;
    /**
     * Secondary information about the main operation
     */
    private Text subOperation;
    /**
     * Specific details of the main operation
     */
    private Text details;
    /**
     * Flag that tells us if the window with the information is visible
     */
    private boolean visible;
    
    /**
     * Constructor for the window displaying the information of the algorithm.
     * <p>
     * We only set the colors of the text here
     */
    public InfoAlgorithm()
    {
        operation=new Text(new Color(0,126,255),18);
        subOperation=new Text(new Color(255,234,0),18);
        details=new Text(new Color(0,126,255),18);
        visible=true;
    }
    
    /**
     * Method that makes the window visible
     */
    public void makeVisible()
    {
        // We make all components visible
        getImage().setTransparency(255);
        operation.getImage().setTransparency(255);
        subOperation.getImage().setTransparency(255);
        details.getImage().setTransparency(255);
        // And flag the object as visible
        visible=true;
    }
    
    /**
     * Method that makes the window invisible
     */
    public void makeInvisible()
    {
        // We make all components invisible
        getImage().setTransparency(0);
        operation.getImage().setTransparency(0);
        subOperation.getImage().setTransparency(0);
        details.getImage().setTransparency(0);
        // And flag the object as invisible
        visible=false;
    }
    
    /**
     * Method for setting a component's text
     * @param text the component we change
     * @param string the new string that we put in the component
     */
    private void setInfo(Text text,String string)
    {
        text.clear();// We clear the previous text
        text.addString(string);// We add the new one
        if(!visible)// If the window is invisible
            text.getImage().setTransparency(0);// We make the new text also invisible
    }
    
    
    public void setOperation(String o)
    {
        setInfo(operation,o);
    }
    public void setSubOperation(String so)
    {
        setInfo(subOperation,so);
    }
    public void setDetails(String d)
    {
        setInfo(details,d);
    }
    
    /**
     * Method for flushing all the components
     */
    public void clear()
    {
        operation.clear();
        subOperation.clear();
        details.clear();
    }

    public Text getOperation()
    {
        return operation;
    }
    public Text getSubOperation()
    {
        return subOperation;
    }
    public Text getDetails()
    {
        return details;
    }
}
