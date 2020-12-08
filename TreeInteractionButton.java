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
    public TreeInteractionButton(int x,int y,RBTree rbt)
    {
        super(x,y);
        t=rbt;
    }

}
