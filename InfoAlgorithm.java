import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class InfoAlgorithm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InfoAlgorithm extends Fixed
{
    private Text operation;
    private Text subOperation;
    private Text details;
    private boolean visible;
    public InfoAlgorithm()
    {
        operation=new Text(new Color(0,126,255),18);
        subOperation=new Text(new Color(255,234,0),18);
        details=new Text(new Color(0,126,255),18);
        visible=true;
    }
    public void makeVisible()
    {
        getImage().setTransparency(255);
        operation.getImage().setTransparency(255);
        subOperation.getImage().setTransparency(255);
        details.getImage().setTransparency(255);
        visible=true;
    }
    public void makeInvisible()
    {
        getImage().setTransparency(0);
        operation.getImage().setTransparency(0);
        subOperation.getImage().setTransparency(0);
        details.getImage().setTransparency(0);
        visible=false;
    }
    private void setInfo(Text text,String string)
    {
        text.clear();
        text.addString(string);
        if(!visible)
            text.getImage().setTransparency(0);
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
