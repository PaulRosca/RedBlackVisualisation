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
        world.addObject(nodePointer,world.getWidth()/2,100);
        nodePointer.getImage().setTransparency(0);
    }

    public void insert(int k)
    {
        NodeRB newNode = new NodeRB(k);//Creating a new node with the key desired to be inserted
        NodeRB y,x;
        y=null;//Parent node for iterating the tree
        x=root;//Current node for iterating the tree
        if(root!=null)
            nodePointer.setLocation(root.getX(),root.getY());
        else
            nodePointer.setLocation(world.getWidth()/2,100);
        nodePointer.getImage().setTransparency(255);
        while(x!=null)//While we've not reached a leaf
        {
            nodePointer.setLocationTransition(x.getX(),x.getY());
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
            
            
            newNode.getImage().setTransparency(0);
            newNode.getText().getImage().setTransparency(0);
            auxConnector.getImage().setTransparency(0);
            
            auxConnector.setScale(5,(int)hypotenuse-55);//Seting the connector's size to match the distance between the connected nodes (for visualisation)
            
            
            if(y!=root)
                    {
                        y.setStickyPointer(nodePointer);
                        startSpacing(newNode);
                        y.setStickyPointer(null);
                    }
            nodePointer.setLocationTransition(newNode.getX(),newNode.getY());
            newNode.getImage().setTransparency(255);
            newNode.getText().getImage().setTransparency(255);
            auxConnector.getImage().setTransparency(255);

        }
        //colorFixup(newNode);
        Greenfoot.delay(50);
        nodePointer.getImage().setTransparency(0);

    }

    private void startSpacing(NodeRB node)
    {
        int leftChild,leftParent;
        NodeRB parent=node.getParent();
        leftChild=(parent.getLeft()==node)?1:-1;
        fixSpacing(parent, leftChild);
    }
    private void fixSpacing(NodeRB parent,int leftChild)
    {
        if(parent==root)
            return;
        int leftParent;
        leftParent=(parent.getParent().getLeft()==parent)?1:-1;
        fixSpacing(parent.getParent(),leftChild);
        if(leftChild!=leftParent)
            {
                spaceNodes(parent.getParent(),-leftChild);
                spaceNodes(parent,leftChild);
            }
        
    }
    private void spaceNodes(NodeRB node,int right)
    {
        if(node==null||node==root)
            return;
        node.setLocationWithComponentsTransition(node.getX()+50*right,node.getY(),50);
        
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
                    uncle=z.getParent().getParent().getLeft();
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
        NodeRB parent,b,a,y;
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
        int ax,ay,auxx;
        if(a==null)
        {
            ax=x.getX()-50;
            ay=y.getY()+100;
            auxx=1;
        }
        else
        {
            ax=a.getX();
            ay=a.getY();
            auxx=(x.getX()-ax)/50;
        }
        x.setRight(b);
        if(b!=null)
        {
            b.setParent(x);
            b.setLocationWithComponents(x.getX()+50,y.getY()+100);
        }
        x.setLocationWithComponents(ax,ay);
        
        while(auxx>0)
            {
                spaceNodes(y,1);
                auxx--;
            }
        
        if(b!=null)
        {
            NodeRB aux=b.getLeft();
            int count=0;
            while(aux!=null)
            {
                    count+=(aux.getParent().getX()-aux.getX())/50;
                    aux=aux.getLeft();
            }
            while(count>0)
            {
                spaceNodes(x,-1);
                spaceNodes(y,1);
                spaceNodes(b,1);
                count--;
            }
            count=1;
            aux=b.getRight();
            while(aux!=null)
            {
            count+=(aux.getX()-aux.getParent().getX())/50;
            aux=aux.getRight();
            }
            aux=x.getLeft();
            if(aux!=null)
                {
                    aux=aux.getRight();
                    while(aux!=null)
                    {
                        count-=(aux.getX()-aux.getParent().getX())/50;
                        aux=aux.getRight();
                    }
                    
                }
            while(count>0)
            {
                spaceNodes(x,-1);
                spaceNodes(y,1);
                count--;
            }
            while(count<0)
            {
                spaceNodes(x,1);
                spaceNodes(y,-1);
                count++;
            }
            

        }
        

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
        int cx,cy,auxx;
        if(c==null)
        {
            cx=y.getX()+50;
            cy=y.getY()+100;
            auxx=1;
        }
        else
        {
            cx=c.getX();
            cy=c.getY();
            auxx=(cx-y.getX())/50;
        }
        y.setLeft(b);
        if(b!=null)
        {
            b.setParent(y);
            b.setLocationWithComponents(y.getX()-50,y.getY()+100);
        }
        y.setLocationWithComponents(cx,cy);
        
        while(auxx>0)
            {
                spaceNodes(x,-1);
                auxx--;
            }
        if(b!=null)
        {
            NodeRB aux=b.getRight();
            int count=0;
            while(aux!=null)
            {
                    count+=(aux.getX()-aux.getParent().getX())/50;
                    aux=aux.getRight();
            }
            while(count>0)
            {
                spaceNodes(y,1);
                spaceNodes(x,-1);
                spaceNodes(b,-1);
                count--;
            }
            count=1;
            aux=b.getLeft();
            while(aux!=null)
            {
            count+=(aux.getParent().getX()-aux.getX())/50;
            aux=aux.getLeft();
            }
            aux=y.getRight();
            if(aux!=null)
                {
                    aux=aux.getLeft();
                    while(aux!=null)
                    {
                        count-=(aux.getParent().getX()-aux.getX())/50;
                        aux=aux.getLeft();
                    }
                    
                }
            while(count>0)
            {
                spaceNodes(y,1);
                spaceNodes(x,-1);
                count--;
            }
            while(count<0)
            {
                spaceNodes(y,-1);
                spaceNodes(x,1);
                count++;
            }
            

        }
    }
     

}
