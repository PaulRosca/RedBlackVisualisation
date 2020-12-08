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
    public RBTree(World myWorld)
    {
        setImage((GreenfootImage)(null));
        root=null;
        world=myWorld;
    }

    public void insert(int k)
    {
        NodeRB newNode = new NodeRB(k);//Creating a new node with the key desired to be inserted
        NodeRB y,x;
        y=null;//Parent node for iterating the tree
        x=root;//Current node for iterating the tree
        while(x!=null)//While we've not reached a leaf
        {
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

            if(y!=root)
            {
                NodeRB parent,uncle;
                parent = y;
                uncle=(y==y.getParent().getLeft())?y.getParent().getRight():y.getParent().getLeft();
                //fixSpacing(parent);
                //fixSpacing(uncle);
            }
            Connector auxConnector = new Connector();//Creating a visual connector to the parent node
            newNode.setParentConnector(auxConnector);//Attributing the connector to the newly inserted node
            int yX,yY;//Parent coodinates
            yX=y.getX();
            yY=y.getY();
            int dXc,dYc;//Distance to child (for Visualisation)
            dXc=y.getDxChild();
            dYc=y.getDyChild();
            double angle=Math.toDegrees(Math.asin(Math.sin(dYc/Math.sqrt(dXc*dXc+dYc*dYc))));//Calculating the anlge of the connector
            if(k<=y.getKey())
            {
                y.setLeft(newNode);//Linking the parent to the inserted node
                world.addObject(newNode,yX-dXc,yY+dYc);//Adding the inserted node in the world, relative to the parent (for visualisation)
                world.addObject(newNode.getText(),yX-dXc,yY+dYc);//Adding the inserted node's key's visualisation
                world.addObject(auxConnector,yX-dXc/2,yY+dYc/2);//Adding the conector in the world, right between the inserted node and it's parent
                auxConnector.turn(90-(int)Math.round(angle));//Rotating the connector to match the calculated angle
            }
            else
            {
                y.setRight(newNode);//Linking the parent to the inserted node
                world.addObject(newNode,yX+dXc,yY+dYc);//Adding the inserted node in the world, relative to the parent (for visualisation)
                world.addObject(newNode.getText(),yX+dXc,yY+dYc);//Adding the inserted node's key's visualisation
                world.addObject(auxConnector,yX+dXc/2,yY+dYc/2);//Adding the conector in the world, right between the inserted node and it's parent
                auxConnector.turn(90+(int)Math.round(angle));//Rotating the connector to match the calculated angle
            }
            int nX,nY;//Inserted node coordinates
            nX=newNode.getX();
            nY=newNode.getY();
            auxConnector.setScale(5,(int)Math.round(Math.sqrt((yX-nX)*(yX-nX)+(yY-nY)*(yY-nY)))-55);//Seting the connector's size to match the distance between the connected nodes (for visualisation)
        }

    }

    /*public void fixSpacing(NodeRB p)
    {
    Connector pConnector = p.getParentConnector();
    int pdx=p.getDxParent();
    pdx*=2;
    p.setLocation(p.getParent().getX()+pdx,p.getY());
    p.getText().setLocation(p.getX(),p.getY());

    }
     */

}
