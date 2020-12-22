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
    private int shiftedX;
    private int shiftedY;
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
    public void setLocationWithPointer(int x,int y)
    {
        int dx=x-getX();
        int dy=y-getY();
        setLocation(x,y);
        if(stickyPointer!=null)        
                {
                    stickyPointer.setLocation(x,y);
                    stickyPointer.focusOnThis();
                    shiftedX+=dx;
                    shiftedY+=dy;
                    NodeRB parent=this.getParent();
                    while(parent!=null)
                    {
                        parent.setShiftedX(shiftedX);
                        parent.setShiftedY(shiftedY);
                        parent=parent.getParent();
                    }
                    //System.out.println(key+" "+dx+" "+shiftedX);
                }
         
    }
    public void setLocationWithComponents(int newX,int newY)
    {
        int dx,dy;
        dx=newX-this.getX();
        dy=newY-this.getY();
        this.setLocationWithPointer(newX,newY);
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
            left.setLocationWithComponents(left.getX()+dx,left.getY()+dy);
        if(right!=null)
            right.setLocationWithComponents(right.getX()+dx,right.getY()+dy);
        
       
    }
    public void clearShift(NodeRB node)
    {
        if(node==null)
            return;
        node.setShiftedX(0);
        node.setShiftedY(0);
        clearShift(node.getLeft());
        clearShift(node.getRight());
    }
    public void setLocationWithComponentsTransition(int newX,int newY,boolean spacing)
    {
        int dX=newX-getX(),dY=newY-getY();
        int speedX=dX/50,speedY=dY/50;
        int counter = 0;
        clearShift(this);
        while(getX()+shiftedX!=newX)
            {
                if(counter==1)
                {
                    Greenfoot.delay(1);
                    counter=0;
                }
                setLocationWithComponents(getX()+speedX,getY()+speedY);
                if(spacing)
                    {
                        //shiftedX+=speedX;
                        Background world=(Background)getWorld();
                        //world.getScroller().scroll(-speedX,-speedY);
                       // stickyPointer.focusOnThis();
                    }
                counter++;
                //System.out.println("KEY : "+key+"getX : "+getX()+"x : "+newX+"\tSHIFTED : "+shiftedX);
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
