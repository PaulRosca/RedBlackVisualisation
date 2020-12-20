import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * Write a description of class NodeRB here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodeRB extends Actor
{
    private int key;
    private NodeRB parent,left,right;
    private boolean color;
    private FloatingText text;
    private Connector parentConnector;
    private NodePointer stickyPointer;
    
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
    private void setColorQuiet(boolean b)
    {
        color=b;
        if(color==true)
            setImage("BlackNode.png");
        else
            setImage("RedNode.png");
    }
    public void setColor(boolean b)
    {
        
        NodePointer np=new NodePointer((Background) getWorld());
        getWorld().addObject(np,getX(),getY());
        np.setImage("NodePointerPurple.png");
        np.getImage().setTransparency(0);
        if(b!=color)
            {
                np.getImage().setTransparency(255);
                np.focusOnThis();
            }
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
    public void setLocationWithPointer(int x,int y,boolean spacing)
    {
        setLocation(x,y);
        if(stickyPointer!=null)
            {
                stickyPointer.setLocation(x,y);
                if(spacing)
                    stickyPointer.focusOnThis();
            }
        
        
    }
    public void setLocationWithComponents(int newX,int newY,boolean spacing)
    {
        int dx,dy;
        dx=newX-this.getX();
        dy=newY-this.getY();
        this.setLocationWithPointer(newX,newY,spacing);
        text.setLocation(newX,newY);
        if(parent!=null)//Unless we are moving the root itself
        {
            parentConnector.setLocation((parent.getX()+this.getX())/2,(parent.getY()+this.getY())/2);
            int dXc=Math.abs(parent.getX()-this.getX());
            int dYc=Math.abs(parent.getY()-this.getY());
            double hypotenuse=Math.sqrt(dXc*dXc+dYc*dYc);
            double angle=Math.toDegrees(Math.asin(dYc/hypotenuse));//Calculating the anlge of the connector
            angle*=((parent.getLeft()==this)?-1:1);
            parentConnector.setRotation(90+(int)angle);//Rotating the connector to match the calculated angle
            parentConnector.setScale(5,(int)hypotenuse-45);//Seting the connector's size to match the distance between the connected nodes (for visualisation)
        }
        //We move the children too
        if(left!=null)
            left.setLocationWithComponents(left.getX()+dx,left.getY()+dy,spacing);
        if(right!=null)
            right.setLocationWithComponents(right.getX()+dx,right.getY()+dy,spacing);
    }
    public void setLocationWithComponentsTransition(int newX,int newY,boolean spacing)
    {
        int dX=newX-getX(),dY=newY-getY();
        int speedX=dX/50,speedY=dY/50;
        int counter = 0;
        int movedX=0;
        while(getX()+movedX!=newX)
            {
                if(counter==1)
                {
                    Greenfoot.delay(1);
                    counter=0;
                }
                setLocationWithComponents(getX()+speedX,getY()+speedY,spacing);
                if(spacing)
                    movedX+=speedX;
                counter++;
            }
        
    }
    public void setStickyPointer(NodePointer np)
    {
        stickyPointer=np;
    }
    public NodeRB clone()
    {
        NodeRB node = new NodeRB(key);
        node.setColorQuiet(color);
        if(parentConnector!=null)
            node.setParentConnector(new Connector());
        return node;
    }
}
