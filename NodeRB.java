import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * This class represents a node in a red black tree.
 * <p>
 * It holds information like key, color, refferences to children and parent and visual connector to parent.
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class NodeRB extends Actor
{
    private int key;
    private NodeRB parent,left,right;
    /**
     * The color of the node
     * false - red
     * true - black
     */
    private boolean color;
    /**
     * The text with the key (for visualisation)
     */
    private FloatingText text;
    private Connector parentConnector;
    /**
     * The node pointer stuck to this node 
     * Used when we the node pointer is pointing to this node and we need to move this node
     */
    private NodePointer stickyPointer;
    /**
     * Number of pixels this node has moved in the world
     * Used when the world is focused on this node and the node is moving
     */
    private int shiftedX;
    private int shiftedY;

    /**
     * Constructor for the Node.
     * <p>
     * We set the node's key, color (default red), refference to children and parents (default null)
     * 
     * @param k the desired key for the node
     */
    public NodeRB(int k)
    {
        color = false;
        parent=null;
        left=null;
        right=null;
        key=k;
        text=new FloatingText();
        text.addChar(String.valueOf(key));
        setImage("RedNode.png");
    }

    public void setKey(int k)
    {
        text.clear();
        key=k;
        text.addChar(String.valueOf(key));
    }

    public void setParentConnector(Connector c)
    {
        parentConnector = c;
    }

    /**
     * Method for changing the color of the node directly, without any visualisation
     * Used when the node is not actually in world
     */
    private void setColorQuiet(boolean b)
    {
        color=b;
        if(color==true)
            setImage("BlackNode.png");
        else
            setImage("RedNode.png");
    }

    /**
     * Method used for changing the color of the node.
     * <p>
     * It also adds a purple NodePointer and focuses the world on it.
     */
    public void setColor(boolean b)
    {

        NodePointer np=new NodePointer((Background) getWorld());
        getWorld().addObject(np,getX(),getY());
        np.setImage("NodePointerPurple.png");
        np.getImage().setTransparency(255);
        np.focusOnThis();
        Greenfoot.delay(50);
        color=b;
        if(color==true)
            setImage("BlackNode.png");
        else
            setImage("RedNode.png");
        getWorld().removeObject(np);
    }

    public void setLeft(NodeRB x)
    {
        left=x;
    }

    public void setRight(NodeRB x)
    {
        right=x;
    }

    public void setParent(NodeRB x)
    {
        parent=x;
    }

    public void setShiftedX(int dx)
    {
        shiftedX=dx;
    }

    public int getShiftedX()
    {
        return shiftedX;
    }

    public void setShiftedY(int dy)
    {
        shiftedY=dy;
    }

    public int getShiftedY()
    {
        return shiftedY;
    }

    public NodeRB getLeft()
    {
        return left;
    }

    public NodeRB getRight()
    {
        return right;
    }

    public NodeRB getParent()
    {
        return parent;
    }

    public boolean getColor()
    {
        return color;
    }

    public Connector getParentConnector()
    {
        return parentConnector;
    }

    public int getKey()
    {
        return key;
    }

    public FloatingText getText()
    {
        return text;
    }

    /**
     * Method used for moving the node along with it's sticky NodePointer (if it has one)
     * 
     * @param x the new X coordinate
     * @param y the new Y coordinate
     */
    public void setLocationWithPointer(int x,int y)
    {
        // We get the difference from the new coordinates to the old ones
        int dx=x-getX();
        int dy=y-getY();
        setLocation(x,y);
        if(stickyPointer!=null)// If the node has a stuck pointer        
        {
            stickyPointer.setLocation(x,y);// We also move it
            stickyPointer.focusOnThis();// We focuse the world on it

            // Then we need to take into account shifting (because after focusing the coordinates don't actually change)
            shiftedX+=dx;
            shiftedY+=dy;

            // We also acknowledge that all it's parents also shifted
            NodeRB parent=this.getParent();
            while(parent!=null)
            {
                parent.setShiftedX(shiftedX);
                parent.setShiftedY(shiftedY);
                parent=parent.getParent();
            }
        }

    }

    /**
     * Method for moving the subtree rooted in this node, along with all it's visual components 
     * 
     * @param newX new X coordinate
     * @param newY new Y coordinate
     */
    public void setLocationWithComponents(int newX,int newY)
    {
        // We get the difference between the new coordinates and the old ones
        int dx,dy;
        dx=newX-this.getX();
        dy=newY-this.getY();
        // We move the node and it's sticky pointer
        this.setLocationWithPointer(newX,newY);
        text.setLocation(newX,newY);
        if(parent!=null)//Unless we are moving the root itself
        {
            // We move the connect
            parentConnector.setLocation((parent.getX()+this.getX())/2,(parent.getY()+this.getY())/2);
            int dXc=Math.abs(parent.getX()-this.getX());
            int dYc=Math.abs(parent.getY()-this.getY());
            double hypotenuse=Math.sqrt(dXc*dXc+dYc*dYc);
            double angle=Math.toDegrees(Math.asin(dYc/hypotenuse));//Recalculating the anlge of the connector
            angle*=((parent.getLeft()==this)?-1:1);
            parentConnector.setRotation(90+(int)angle);//Rotating the connector to match the calculated angle
            parentConnector.setScale(5,(int)hypotenuse-45);//Seting the connector's size to match the distance between the connected nodes (for visualisation)
        }
        //We move the children too
        if(left!=null)
            left.setLocationWithComponents(left.getX()+dx,left.getY()+dy);
        if(right!=null)
            right.setLocationWithComponents(right.getX()+dx,right.getY()+dy);

    }
    /**
     * Method for reseting {@link shiftedX} and {@link shiftedY} variables for a subtree
     * 
     * @param node the root of the subtree
     */
    public void clearShift(NodeRB node)
    {
        if(node==null)
            return;
        node.setShiftedX(0);
        node.setShiftedY(0);
        clearShift(node.getLeft());
        clearShift(node.getRight());
    }

    /**
     * Method for slowly moving the subtree rooted in this node, along with all it's visual components 
     * 
     * @param newX new X coordinate
     * @param newY new Y coordinate
     * 
     */
    public void setLocationWithComponentsTransition(int newX,int newY)
    {
        // We get the difference between the new coordinates and the old ones
        int dX=newX-getX(),dY=newY-getY();
        // The number of pixels we move on X and Y axis in one iteration
        int speedX=dX/50,speedY=dY/50;
        // Variable used to speed up (slow down the slow down) the transition
        int timer = 0;
        // We clear the shift of the subtree rooted in this node 
        clearShift(this);
        while(getX()+shiftedX!=newX)// Until we've reached the desired new X coordinate
        // If the node has a sticky pointer shiftedX will change while the actual coordinates will remain the same
        // If the node doesn't have a sticky pointer shiftedX will stay 0 and actual coordinates will change
        {
            if(timer==1)
            {
                Greenfoot.delay(1);// We slow down the execution
                timer=0;
            }
            setLocationWithComponents(getX()+speedX,getY()+speedY);// We move the subtree rooted in this node in the desired direction
            timer++;
        }

    }

    public void setStickyPointer(NodePointer np)
    {
        stickyPointer=np;
    }

    /**
     * Method that clones the base information of this node (key and color)
     * 
     * @return a clone of this node
     */
    public NodeRB clone()
    {
        NodeRB node = new NodeRB(key);
        node.setColorQuiet(color);
        if(parentConnector!=null)
            node.setParentConnector(new Connector());
        return node;
    }
}
