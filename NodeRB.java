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
    private int dxChild,dyChild; //X distance to child and Y distance to child (For visualization)
    public NodeRB(int k)
    {
        color = false;
        parent=null;
        dxChild=50;
        dyChild=100;
        left=null;
        right=null;
        key=k;
        text=new FloatingText();
        text.addChar(String.valueOf(key));
        setImage("RedNode.png");
    }
    
    public void setParentConnector(Connector c)
    {
        parentConnector = c;
    }
    public void setColor(boolean b)
    {
        color=b;
        if(color==true)
            setImage("BlackNode.png");
        else
            setImage("RedNode.png");
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
    public void setDxChild(int dx)
    {
    dxChild=dx;
    }
    public void setDyChild(int dy)
    {
    dyChild=dy;
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
    public int getDxChild()
    {
    return dxChild;
    }
    public int getDyChild()
    {
    return dyChild;
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
    public void setLocationWithComponents(int newX,int newY)
    {
        this.setLocation(newX,newY);
        text.setLocation(newX,newY);
        parentConnector.setLocation((parent.getX()+this.getX())/2,(parent.getY()+this.getY())/2);
        int dXc=Math.abs(parent.getX()-this.getX());
        int dYc=Math.abs(parent.getY()-this.getY());
        double hypotenuse=Math.sqrt(dXc*dXc+dYc*dYc);
        double angle=Math.toDegrees(Math.asin(dYc/hypotenuse));//Calculating the anlge of the connector
        angle*=((parent.getLeft()==this)?-1:1);
        parentConnector.setRotation(90+(int)angle);//Rotating the connector to match the calculated angle
                System.out.println("dXc : "+dXc+"\ndYc : "+dYc+"\n angle : "+angle);
        parentConnector.setScale(5,(int)hypotenuse-55);//Seting the connector's size to match the distance between the connected nodes (for visualisation)

    }
}
