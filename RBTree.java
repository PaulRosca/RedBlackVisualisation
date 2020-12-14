import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * Write a description of class RBTree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RBTree extends Actor
{
    private NodeRB root;//Tree root
    private World world;//Current world
    private NodePointer nodePointer;//Pointer to current node (for visualisation)
    public RBTree(World myWorld)
    {
        setImage((GreenfootImage)(null));
        root=null;
        world=myWorld;
        nodePointer = new NodePointer();
    }

    public void insert(int k)
    {
        NodeRB newNode = new NodeRB(k);//Creating a new node with the key desired to be inserted
        NodeRB y,x;
        y=null;//Parent node for iterating the tree
        x=root;//Current node for iterating the tree
        world.addObject(nodePointer,world.getWidth()/2,100);
        while(x!=null)//While we've not reached a leaf
        {
            nodePointer.setLocationTransition(x.getX(),x.getY(),50);
            y=x;//Change the parent to the current node
            x=k>x.getKey()?x.getRight():x.getLeft();//Updating the current node to find the desired inserting location
        }
        newNode.setParent(y);//Setting the newly inserted node's parent to the last non-leaf node
        if(y==null)
        {
            root=newNode;
            world.addObject(newNode,world.getWidth()/2,100);
            world.addObject(newNode.getText(),world.getWidth()/2,100);
        }
        else
        {

            Connector auxConnector = new Connector();//Creating a visual connector to the parent node
            newNode.setParentConnector(auxConnector);//Attributing the connector to the newly inserted node
            int yX,yY;//Parent coodinates
            yX=y.getX();
            yY=y.getY();
            int dXc,dYc;//Distance from parent to child (for Visualisation)
            dXc=50;
            dYc=100;
            double hypotenuse=Math.sqrt(dXc*dXc+dYc*dYc);
            double angle=Math.toDegrees(Math.asin(dYc/hypotenuse));//Calculating the anlge of the connector
            if(k<=y.getKey())
            {
                y.setLeft(newNode);//Linking the parent to the inserted node
                world.addObject(newNode,yX-dXc,yY+dYc);//Adding the inserted node in the world, relative to the parent (for visualisation)
                world.addObject(newNode.getText(),yX-dXc,yY+dYc);//Adding the inserted node's key's visualisation
                world.addObject(auxConnector,yX-dXc/2,yY+dYc/2);//Adding the conector in the world, right between the inserted node and it's parent
                auxConnector.turn(90-(int)angle);//Rotating the connector to match the calculated angle
            }
            else
            {
                y.setRight(newNode);//Linking the parent to the inserted node
                world.addObject(newNode,yX+dXc,yY+dYc);//Adding the inserted node in the world, relative to the parent (for visualisation)
                world.addObject(newNode.getText(),yX+dXc,yY+dYc);//Adding the inserted node's key's visualisation
                world.addObject(auxConnector,yX+dXc/2,yY+dYc/2);//Adding the conector in the world, right between the inserted node and it's parent
                auxConnector.turn(90+(int)angle);//Rotating the connector to match the calculated angle
            }
            nodePointer.setLocation(newNode.getX(),newNode.getY());
            int nX,nY;//Inserted node coordinates
            nX=newNode.getX();
            nY=newNode.getY();
            auxConnector.setScale(5,(int)hypotenuse-55);//Seting the connector's size to match the distance between the connected nodes (for visualisation)
            if(y!=root)
                fixSpacing(newNode);
            nodePointer.setLocation(newNode.getX(),newNode.getY());

        }
        //colorFixup(newNode);
        //world.removeObject(nodePointer);

    }

    private void fixSpacing(NodeRB node)
    {
        int leftChild,leftParent;
        NodeRB parent=node.getParent(),uncle,cousin;
        leftChild=(parent.getLeft()==node)?1:-1;
        while(parent!=root)
        {
            leftParent=(parent.getParent().getLeft()==parent)?1:-1;
            if(leftChild!=leftParent)
            {
                spaceNodes(parent,leftChild);
                spaceNodes(parent.getParent(),-leftChild);
            }
            parent=parent.getParent();
        }
        
    }
    private void spaceNodes(NodeRB node,int right)
    {
        if(node==null||node==root)
            return;
        node.setLocationWithComponents(node.getX()+50*right,node.getY());
        //spaceNodes(node.getLeft(),right);
        //spaceNodes(node.getRight(),right);
        
    }
    private void colorFixup(NodeRB z)
    {
        NodeRB uncle;
            while(z.getParent()!=null&&z.getParent().getColor()==false)//While parent's color is red
            {
                if(z.getParent()==z.getParent().getParent().getLeft())
                {
                    uncle=z.getParent().getParent().getRight();
                    if(uncle!=null && uncle.getColor()==false)
                    {
                        z.getParent().setColor(true);//We make the parent black
                        uncle.setColor(true);//We make the uncle black
                        z.getParent().getParent().setColor(false);//We make the grandparent red
                        z=z.getParent().getParent();//We make the current node the grandparent
                    }
                    else
                    {
                        if(z==z.getParent().getRight())
                        {
                            z=z.getParent();
                            leftRotate(z);
                        }
                        z.getParent().setColor(true);//We make the parent black
                        z.getParent().getParent().setColor(false);//We make the grandparent red;
                        rightRotate(z.getParent().getParent());  
                        
                    }
                }
                else
                {
                    uncle=z.getParent().getParent().getRight();
                    if(uncle!=null && uncle.getColor()==false)
                    {
                        z.getParent().setColor(true);//We make the parent black
                        uncle.setColor(true);//We make the uncle black
                        z.getParent().getParent().setColor(false);//We make the grandparent red
                        z=z.getParent().getParent();//We make the current node the grandparent
                    }
                    else
                    {
                        if(z==z.getParent().getLeft())
                        {
                            z=z.getParent();
                            rightRotate(z);
                        }
                        z.getParent().setColor(true);//We make the parent black
                        z.getParent().getParent().setColor(false);//We make the grandparent red;
                        leftRotate(z.getParent().getParent());
                    }
                
                }
            }
        root.setColor(true); // We make the root black
        
    }
    private void leftRotate(NodeRB x)
    {
        NodeRB parent,a,b,y;
        parent=x.getParent();
        y=x.getRight();
        b=y.getLeft();
        if(parent==null)
                {
                 root=y;
                 x.setParentConnector(y.getParentConnector());
                 y.setParentConnector(null);
                }
        else if(parent.getLeft()==x)
            parent.setLeft(y);
        else
            parent.setRight(y);
        y.setParent(parent);
        y.setLocationWithComponents(x.getX(),x.getY());
        y.setLeft(x);
        x.setParent(y);
        a=x.getLeft();
        int ax,ay;
        if(a==null)
        {
            ax=x.getX()-50;
            ay=x.getY()+100;
        }
        else
        {
           ax=a.getX();
           ay=a.getY();
        }
        x.setRight(b);
        if(b!=null)
        {
            b.setParent(x);
            b.setLocationWithComponents(x.getX()+50,x.getY()+100);
        }
        x.setLocationWithComponents(ax,ay);
        
        spaceNodes(y,1);

    }
    private void rightRotate(NodeRB y)
    {
        NodeRB parent,b,c,x;
        parent=y.getParent();
        x=y.getLeft();
        b=x.getRight();
        if(parent==null)
        {
            root=x;
            y.setParentConnector(x.getParentConnector());
            x.setParentConnector(null);
        }
        else if(parent.getLeft()==y)
            parent.setLeft(x);
        else
            parent.setRight(x);
        x.setParent(parent);
        x.setLocationWithComponents(y.getX(),y.getY());
        x.setRight(y);
        y.setParent(x);
        c=y.getRight();
        int cx,cy;
        if(c==null)
        {
            cx=y.getX()+50;
            cy=y.getY()+100;
        }
        else
        {
            cx=c.getX();
            cy=c.getY();
        }
        y.setLeft(b);
        if(b!=null)
        {
            b.setParent(y);
            b.setLocationWithComponents(y.getX()-50,y.getY()+100);
        }
        y.setLocationWithComponents(cx,cy);
        
        spaceNodes(x,-1);
    
    }
     

}
