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
    private Background world;//Current world
    private NodePointer nodePointer;//Pointer to current node (for visualisation)
    private int rootDefaultX;
    private int rootDefaultY;
    private InfoAlgorithm infoAlg;
    public RBTree(Background myWorld)
    {
        setImage((GreenfootImage)(null));
        root=null;
        world=myWorld;
        rootDefaultX=world.getRootDefaultX();
        rootDefaultY=world.getRootDefaultY();
        nodePointer = new NodePointer(world);
        world.addObject(nodePointer,rootDefaultX,rootDefaultY);
        nodePointer.getImage().setTransparency(0);
        infoAlg=world.getInfoAlgorithm();
    }
    private NodeRB searchNode(int k,boolean findFirst)
    {
        NodeRB y,x;
        y=null;
        x=root;
        if(root!=null)
            nodePointer.setLocation(root.getX(),root.getY());
        else

            nodePointer.setLocation(rootDefaultX,rootDefaultY);
        nodePointer.getImage().setTransparency(255);
        nodePointer.focusOnThis();
        while(x!=null)
        {
            Greenfoot.delay(25);
            nodePointer.setLocationTransition(x.getX(),x.getY());
            if(x.getKey()==k&&findFirst)
                return x;
            y=x;
            infoAlg.setSubOperation("Comparing "+k+" with "+x.getKey());
            if(k>x.getKey())
            {
                infoAlg.setDetails(k+" > "+x.getKey());
                x=x.getRight();
            }
            else
            {
                infoAlg.setDetails(k+" <= "+x.getKey());
                x=x.getLeft();
            }

            
        }
        Greenfoot.delay(50);
        
        nodePointer.getImage().setTransparency(0);
        return y;

    }
    public NodeRB search(int k)
    {
        nodePointer.setImage("NodePointerYellow.png");
        NodeRB node=searchNode(k,true);
        infoAlg.getDetails().clear();
        nodePointer.getImage().setTransparency(255);
        if(node==null)
        {
            infoAlg.setSubOperation("Tree is empty");
            nodePointer.setImage("NodePointerRed.png");
        }
        else if(node.getKey()==k)
        {
            Greenfoot.delay(25);
            infoAlg.setSubOperation("Node found");
            nodePointer.setImage("NodePointerGreen.png");
        }
        else
        {
        int leftChild=(k>node.getKey())?1:-1;
        nodePointer.setLocationTransition(node.getX()+50*leftChild,node.getY()+100);
        Greenfoot.delay(50);
        nodePointer.setImage("NodePointerRed.png");
        infoAlg.setSubOperation("Node not found");
        }
        
        Greenfoot.delay(50);
        nodePointer.setImage("NodePointer.png");
        nodePointer.getImage().setTransparency(0);
        return node;
        
    }
    private NodeRB successor(NodeRB node,NodePointer scsPointer)
    {
        infoAlg.setSubOperation("Finding node's successor");
        NodeRB scs=null;
        NodeRB current=node.getRight();
        if(current==null)
        world.addObject(scsPointer,node.getX()+50,node.getY()+100);
        else
        world.addObject(scsPointer,current.getX(),current.getY());
        scsPointer.setImage("NodePointerOrange.png");
        scsPointer.focusOnThis();
        while(current!=null)
        {
            scsPointer.setLocationTransition(current.getX(), current.getY());
            Greenfoot.delay(50);
            scs=current;
            current=current.getLeft();
        }
        return scs;
    }
    public void deleteKey(int k)
    {
        NodeRB node = search(k);
        if(node==null||node.getKey()!=k)
            {
                infoAlg.setSubOperation("Node not found");
                return;
            }
        delete(node);
    }
    private void delete(NodeRB node)
    {
        
        boolean blackNode=node.getColor();
        boolean blackReplacement=true;
        int leftParent=0;
        NodeRB child=null,parent=node.getParent();
        boolean moveAncestor=false;
        NodeRB ancestor=parent;
        int leftAncestor=0;
        if(node.getLeft()==null||node.getRight()==null)
        {
            node.getImage().setTransparency(0);
            node.getText().getImage().setTransparency(0);          
            if(node.getLeft()==null)
            {
                int leftNode=0;
                child=node.getRight();
                if(parent==null)
                    root=child;
                else
                {
                    if(parent.getLeft()==node)
                        {
                            parent.setLeft(child);
                            leftNode=1;
                        }
                    else
                        {
                            parent.setRight(child);
                            leftNode=-1;
                        }
                    NodeRB grandParent=parent.getParent();
                    leftParent=(grandParent==null)?0:(grandParent.getLeft()==parent)?1:-1;

                }
                if(child!=null)
                {
                    blackReplacement=getColorOfNode(child);
                    infoAlg.setSubOperation("Node has only right child");
                    int dx=child.getX()-node.getX();//Child dx
                    child.setParent(node.getParent());
                    world.removeObject(child.getParentConnector());
                    child.setParentConnector(node.getParentConnector());
                    node.setParentConnector(null);
                    infoAlg.setDetails("Moving child in node's place");
                    child.setLocationWithComponentsTransition(node.getX(), node.getY(),false);
                    
                    if(child!=root&&leftNode==1)
                    child.setLocationWithComponentsTransition(child.getX()+dx, child.getY(),false);
                }
                else
                {
                    infoAlg.setSubOperation("Node has no children");
                    infoAlg.setDetails("We simply delete it");
                    if(leftParent!=0)
                    {
                        leftAncestor=leftParent;
                        while(ancestor.getParent()!=null&&leftAncestor==leftNode)
                            {
                                leftAncestor=(ancestor.getParent().getLeft()==ancestor)?1:-1;
                                ancestor=ancestor.getParent();
                            }
                        if(leftNode!=leftAncestor)
                        moveAncestor=true;
                    }
                }

            }
            else if(node.getRight()==null)
            {
                
                boolean rightNode=false;
                child=node.getLeft();
                blackReplacement=getColorOfNode(child);
                if(parent==null)
                    root=child;
                else
                {
                    if(parent.getLeft()==node)
                        parent.setLeft(child);
                    else
                        {
                            parent.setRight(child);
                            rightNode=true;
                        }
                }
                int dx=child.getX()-node.getX();
                child.setParent(node.getParent());
                world.removeObject(child.getParentConnector());
                child.setParentConnector(node.getParentConnector());
                node.setParentConnector(null);
                infoAlg.setSubOperation("Node has only left child");
                infoAlg.setDetails("Moving child in node's place");
                child.setLocationWithComponentsTransition(node.getX(), node.getY(),false);
                if(child!=root&&rightNode)
                    child.setLocationWithComponentsTransition(child.getX()+dx, child.getY(),false);
    
            }
            world.removeObject(node);
            world.removeObject(node.getText());
            world.removeObject(node.getParentConnector());

        }
        else
        {
            infoAlg.setSubOperation("Node has two children");
            nodePointer.getImage().setTransparency(255);
            NodePointer scsPointer = new NodePointer(world);
            Greenfoot.delay(50);
            NodeRB scs=successor(node,scsPointer);//We get the succesor of the node we need to delete
            nodePointer.focusOnThis();
            Greenfoot.delay(50);
            infoAlg.setDetails("Node gets key from successor");
            node.setKey(scs.getKey());
            //node.setColor(scs.getColor());
            Greenfoot.delay(50);
            infoAlg.getDetails().clear();
            infoAlg.setOperation("Deleting successor");
            node.setStickyPointer(nodePointer);
            world.removeObject(scsPointer);
            delete(scs);
            node.setStickyPointer(null);
            child=node;
            nodePointer.getImage().setTransparency(0);
            return;
            
        }
        
        Greenfoot.delay(50);
        if(moveAncestor)
            {
                int leftNode;
                ancestor.setLocationWithComponentsTransition(ancestor.getX()+50*leftAncestor, ancestor.getY(),false);
                leftNode=leftAncestor;
                ancestor=ancestor.getParent();
                while(ancestor!=root)
                {
                    leftAncestor=(ancestor.getParent().getLeft()==ancestor)?1:-1;
                    if(leftNode!=leftAncestor)
                    {
                    ancestor.setLocationWithComponentsTransition(ancestor.getX()+50*leftAncestor, ancestor.getY(),false);
                    leftNode=leftAncestor;
                    }
                    ancestor=ancestor.getParent();
                }
                
            }
        if(blackNode)
                {
                    Greenfoot.delay(50);
                    infoAlg.setSubOperation("Recoloring tree");
                    Greenfoot.delay(50);
                    deleteColorFixup(child,parent);
                    infoAlg.getDetails().clear();
                    infoAlg.setSubOperation("Recoloring finished");
                    Greenfoot.delay(50);
                }
    }
    private boolean getColorOfNode(NodeRB node)
    {
        if(node==null)
            return true;
        return node.getColor();
    }
    private void deleteColorFixup(NodeRB x,NodeRB xParent)
    {
        if(x!=root&&getColorOfNode(x)==true)
        {
            NodeRB w;
            if(x==xParent.getLeft())
            {
                w=xParent.getRight();
                if(getColorOfNode(w)==false)
                    {
                        infoAlg.setSubOperation("Case 1");
                        infoAlg.setDetails("Coloring uncle black");
                        w.setColor(true);
                        infoAlg.setDetails("Coloring parent red");
                        xParent.setColor(false);
                        infoAlg.setDetails("Rotating parent left");
                        leftRotate(xParent);
                        w=xParent.getRight();
                    }
                if(getColorOfNode(w)==true)
                {
                    if(getColorOfNode(w.getLeft())==true&&getColorOfNode(w.getRight())==true)
                    {
                        infoAlg.setSubOperation("Case 2");
                        infoAlg.setDetails("Coloring uncle red");
                        w.setColor(false);
                        infoAlg.setDetails("Moving to parent");
                        deleteColorFixup(xParent,xParent.getParent());
                        return;
                    }
                    if(getColorOfNode(w.getLeft())==false&&getColorOfNode(w.getRight())==true)
                    {
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring close cousin black");
                        w.getLeft().setColor(true);
                        infoAlg.setDetails("Coloring uncle red");
                        w.setColor(false);
                        infoAlg.setDetails("Rotating uncle right");
                        rightRotate(w);
                        w=xParent.getRight();
                    }
                    infoAlg.setSubOperation("Case 4");
                    infoAlg.setDetails("Coloring uncle same as parent");
                    w.setColor(getColorOfNode(xParent));
                    infoAlg.setDetails("Coloring parent black");
                    xParent.setColor(true);
                    infoAlg.setDetails("Coloring far cousin black");
                    w.getRight().setColor(true);
                    infoAlg.setDetails("Rotating parent left");
                    leftRotate(xParent);
                }
            }
            else
            {
                w=xParent.getLeft();
                if(getColorOfNode(w)==false)
                    {
                        infoAlg.setSubOperation("Case 1");
                        infoAlg.setDetails("Coloring uncle black");
                        w.setColor(true);
                        infoAlg.setDetails("Coloring parent red");
                        xParent.setColor(false);
                        infoAlg.setDetails("Rotating parent right");
                        rightRotate(xParent);
                        w=xParent.getLeft();
                    }
                if(getColorOfNode(w)==true)
                {
                    if(getColorOfNode(w.getLeft())==true&&getColorOfNode(w.getRight())==true)
                    {
                        infoAlg.setSubOperation("Case 2");
                        infoAlg.setDetails("Coloring uncle red");
                        w.setColor(false);
                        infoAlg.setDetails("Moving to parent");
                        deleteColorFixup(xParent,xParent.getParent());
                        return;
                    }
                    if(getColorOfNode(w.getRight())==false&&getColorOfNode(w.getLeft())==true)
                    {
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring near cousin black");
                        w.getRight().setColor(true);
                        infoAlg.setDetails("Coloring uncle red");
                        w.setColor(false);
                        infoAlg.setDetails("Rotating uncle left");
                        leftRotate(w);
                        w=xParent.getLeft();
                    }
                    infoAlg.setSubOperation("Case 4");
                    infoAlg.setDetails("Coloring uncle same as parent");
                    w.setColor(getColorOfNode(xParent));
                    infoAlg.setDetails("Coloring parent black");
                    xParent.setColor(true);
                    infoAlg.setDetails("Coloring far cousin black");
                    w.getLeft().setColor(true);
                    infoAlg.setDetails("Rotating parent right");
                    rightRotate(xParent);
                }
            }
        }
        if(x!=null)
        {
            infoAlg.setSubOperation("Case 0");
            infoAlg.setDetails("Coloring node black");
            x.setColor(true);
        }
    }
    public void insert(int k)
    {
        NodeRB newNode = new NodeRB(k);//Creating a new node with the key desired to be inserted
        NodeRB y,x;
        y=searchNode(k,false);//Parent node of k
        nodePointer.getImage().setTransparency(255);
        newNode.setParent(y);//Setting the newly inserted node's parent
        infoAlg.getDetails().clear();
        if(y==null)
        {
            infoAlg.setSubOperation("Tree was empty");
            root=newNode;
            int scrolledX=world.getScrolledX();
            int scrolledY=world.getScrolledY();
            world.addObject(newNode,rootDefaultX,rootDefaultY);
            world.addObject(newNode.getText(),rootDefaultX,rootDefaultY);
        }
        else
        {
            infoAlg.getSubOperation().clear();
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
            Greenfoot.delay(50);
            newNode.getImage().setTransparency(255);
            newNode.getText().getImage().setTransparency(255);
            auxConnector.getImage().setTransparency(255);

        }
        Greenfoot.delay(50);
        infoAlg.setSubOperation("Trying to recolor tree");
        Greenfoot.delay(50);
        insertColorFixup(newNode);
        infoAlg.getDetails().clear();
        infoAlg.setSubOperation("Recoloring finished");
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
        fixSpacing(parent.getParent(),leftParent);
        if(leftChild!=leftParent)
                spaceNodes(parent,leftChild);
    }
    private void spaceNodes(NodeRB node,int right)
    {
        if(node==null||node==root)
            return;
        node.setLocationWithComponentsTransition(node.getX()+50*right,node.getY(),true);
        
    }
    private void insertColorFixup(NodeRB z)
    {
        NodeRB uncle;
            while(z.getParent()!=null&&z.getParent().getColor()==false)//While parent's color is red
            {
                if(z.getParent()==z.getParent().getParent().getLeft())
                {
                    uncle=z.getParent().getParent().getRight();
                    if(uncle!=null && uncle.getColor()==false)
                    {
                        infoAlg.setSubOperation("Case 1");
                        infoAlg.setDetails("Coloring parent black");
                        z.getParent().setColor(true);//We make the parent black
                        infoAlg.setDetails("Coloring uncle black");
                        uncle.setColor(true);//We make the uncle black
                        infoAlg.setDetails("Coloring grandparent red");
                        z.getParent().getParent().setColor(false);//We make the grandparent red
                        z=z.getParent().getParent();//We make the current node the grandparent
                    }
                    else
                    {
                        if(z==z.getParent().getRight())
                        {
                            infoAlg.setSubOperation("Case 2");
                            z=z.getParent();
                            infoAlg.setDetails("Rotating parent left");
                            leftRotate(z);
                        }
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring parent black");
                        z.getParent().setColor(true);//We make the parent black
                        infoAlg.setDetails("Coloring grandparent red");
                        z.getParent().getParent().setColor(false);//We make the grandparent red;
                        infoAlg.setDetails("Rotating grandparent right");
                        rightRotate(z.getParent().getParent());
                        
                    }
                }
                else
                {
                    uncle=z.getParent().getParent().getLeft();
                    if(uncle!=null && uncle.getColor()==false)
                    {
                        infoAlg.setSubOperation("Case 1");
                        infoAlg.setDetails("Coloring parent black");
                        z.getParent().setColor(true);//We make the parent black
                        infoAlg.setDetails("Coloring uncle black");
                        uncle.setColor(true);//We make the uncle black
                        infoAlg.setDetails("Coloring grandparent red");
                        z.getParent().getParent().setColor(false);//We make the grandparent red
                        z=z.getParent().getParent();//We make the current node the grandparent
                    }
                    else
                    {
                        if(z==z.getParent().getLeft())
                        {
                            infoAlg.setSubOperation("Case 2");
                            z=z.getParent();
                            infoAlg.setDetails("Rotating parent right");
                            rightRotate(z);
                        }
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring parent black");
                        z.getParent().setColor(true);//We make the parent black
                        infoAlg.setDetails("Coloring grandparent red");
                        z.getParent().getParent().setColor(false);//We make the grandparent red;
                        infoAlg.setDetails("Rotating grandparent left");
                        leftRotate(z.getParent().getParent());
                    }
                
                }
            }
        if(root.getColor()==false)
            {
                infoAlg.setSubOperation("Case 0");
                infoAlg.setDetails("Coloring root black");
                root.setColor(true); // We make the root black
            }
    }
    private void leftRotate(NodeRB x)
    {
        x.setStickyPointer(nodePointer);
        x.setLocationWithPointer(x.getX(),x.getY());
        NodeRB parent,b,a,y;//Auxiliary nodes used in rotation
        parent=x.getParent();//We get the parent of the sub-tree rooted in x
        y=x.getRight();//We get x's right child (y)
        b=y.getLeft();//We get t's left child (b)
        if(parent==null)//If x is the root of the tree
        {
            root=y;//We point the root to y, because y will take x's place
            
            //We take y's connector (because it will not be needed as root)
            //and give it to x
            x.setParentConnector(y.getParentConnector());
            y.setParentConnector(null);
        }
        else if(parent.getLeft()==x)//If x is a left child
                parent.setLeft(y);//We make y take it's place
        else//If x is a right child
                parent.setRight(y);//We make t take it's place
        y.setParent(parent);//We connect y to it's new parent
        
        int xX=x.getX(),xY=x.getY();
        
        x.setParent(y);//We make y x's parent
        
        
        a=x.getLeft();//We get x's left child
        int ax,ay;//a's coordinates
        int aDistanceMultiplier;//Number of times a was spaced (visually) from x
        if(a==null)//If a is a null leaf
        {
            //We get it's (theoretical) coordinates
            ax=x.getX()-50;
            ay=x.getY()+100;
            //We attribute the number of time it was spaced from x (default 1)
            aDistanceMultiplier=1;
        }
        else
        {
            //We get it's coordinates
            ax=a.getX();
            ay=a.getY();
            //We calculate the number of times it was spaced from x
            aDistanceMultiplier=(x.getX()-ax)/50;
        }
        x.setRight(null);//We disconnect y from x
        y.setLeft(null);//We disconnect b from y
        x.getParentConnector().getImage().setTransparency(0);//We make x's connector invisible (to avoid visual distorsion)
        if(y.getParentConnector()!=null)
        y.getParentConnector().getImage().setTransparency(0);
        x.setLocationWithComponentsTransition(ax,ay,false);//We move x down in a's place

        if(b!=null)//If b is not a null leaf
        {
            x.setRight(b);//We connect b to x            
            b.setParent(x);//We connect b to x
            b.setLocationWithComponentsTransition(x.getX()+50,x.getY()+100,false);//We move it to it's corescponding coordinates (visually)
        }
        
        
        
        //-(nodePointer.getX()-world.getWidth()/2)
        //-(nodePointer.getY()-200)
        y.setLocationWithComponentsTransition(xX-x.getShiftedX(),xY-x.getShiftedY(),false);//We put y in x's place (visually)
        y.setLeft(x);//We make x y's left child
        if(y.getParentConnector()!=null)
        y.getParentConnector().getImage().setTransparency(255);
        x.setLocationWithComponents(x.getX(),x.getY());//We refresh the connector's angle and position
        
        
        
        x.getParentConnector().getImage().setTransparency(255);//We make x's connector visible again
        
        
        //By moving x in a's place we essentially shift the subtree to the left
        //(with exactly the initial distance from a to x)
        while(aDistanceMultiplier>0)//We compensate the shift to the left
            {
               spaceNodes(y,1);//By moving the subtree right (for every unit [50px] that a was away from x)
               aDistanceMultiplier--;
            }
        
        if(b!=null)//Again if b is not a null leaf
        {
            NodeRB aux=b.getLeft();//We get b's left child
            int leftMultiplier=0;//Number of units [50px] b extends to the left
            while(aux!=null)
            {
                //We calculate then umber of units aux is spaced to the left from it's parent
                //And add that to the total left units multiplier
                leftMultiplier+=(aux.getParent().getX()-aux.getX())/50;
                aux=aux.getLeft();
            }
            while(leftMultiplier>0)//For every left unit
            {
                spaceNodes(x,-1);//We move x's subtree to the left so a doens't collide with b
                spaceNodes(y,1);//We move y's subtree to the right so a doesn't collide with another subtree in the left
                spaceNodes(b,1);
                leftMultiplier--;
            }
            
            int rightMultiplier=1;//Number of units [50px] b extends to the right, we need to consider b itself, so we start at 1
            aux=b.getRight();//We get b's right child
            while(aux!=null)
            {
                //We calculate the number of units aux is spaced to the right from it's parent
                //And add that to the total right units multiplier
                rightMultiplier+=(aux.getX()-aux.getParent().getX())/50;
                aux=aux.getRight();
            }
            aux=x.getLeft();//We get x's left child
            if(aux!=null)//If the child is not a null leaf
                {
                    aux=aux.getRight();//We get the child's right child
                    while(aux!=null)
                    {
                        //We calculate the number of units aux is spaced to the right from it's parent
                        //And we substract that from the total right units multiplier, because those units compensate for the previous right shift
                        rightMultiplier-=(aux.getX()-aux.getParent().getX())/50;
                        aux=aux.getRight();
                    }
                    
                }
            while(rightMultiplier>0)//For every excessive right unit caused by appending b
            {
                spaceNodes(x,-1);//We move x's subtree to the left, so b doesn't collide with another subtree in the right
                spaceNodes(y,1);//We move y's subtree to the right, so x doesn't collide with another subtree in the left
                rightMultiplier--;
            }
            while(rightMultiplier<0)//For every deficit right unit caused by appending b
            {
                spaceNodes(x,1);//We move x's subtree to the right, to keep an uniform distance between nodes (in visualisation)
                spaceNodes(y,-1);//We move y's subtree to the left, to compensate from the previous x move
                rightMultiplier++;
            }
            

        }
        Greenfoot.delay(50);
        x.setStickyPointer(null);
        root.clearShift(root);
    }
    private void rightRotate(NodeRB y)
    {
        y.setStickyPointer(nodePointer);
        y.setLocationWithPointer(y.getX(),y.getY());
        NodeRB parent,b,c,x;//Auxiliary nodes used in rotation
        parent=y.getParent();//We get the parent of the sub-tree rooted in y
        x=y.getLeft();//We get y's left child (x)
        b=x.getRight();//We get x's right child (b)
        if(parent==null)//If y is the root of the tree
        {
            root=x;//We point to root to x, because x will take y's place
            
            //We take x's connector (because it will not be needed as root)
            //and give it to y
            y.setParentConnector(x.getParentConnector());
            x.setParentConnector(null);
        }
        else if(parent.getLeft()==y)//If y is a left child
                parent.setLeft(x);//We make x take it's place
        else//If y is a right child
                parent.setRight(x);//We make x take it's place
        x.setParent(parent);//We connect x to it's new parent
        
        
        int yX=y.getX(),yY=y.getY();
        
        y.setParent(x);//We make x y's parent
        
        c=y.getRight();//We get y's right child (c)
        int cx,cy;//c's coordinates
        int cDistanceMultiplier;//Number of times c was spaced (visually) from y
        if(c==null)//If c is a null leaf
        {
           //We get it's (theoretical) coordinates
           cx=y.getX()+50;
           cy=y.getY()+100;
           //We attriubte the number of times it was spaced from y (default 1)
           cDistanceMultiplier=1;
        }
        else
        {
            //We get it's coordinates
            cx=c.getX();
            cy=c.getY();
            //We calculate the number of times it was spaced from y
            cDistanceMultiplier=(cx-y.getX())/50;
        }
        y.setLeft(null);//We disconnect x from y
        x.setRight(null);//We disconnect b from x  
        y.getParentConnector().getImage().setTransparency(0);//We make y's connector invisible (to avoid visual distorsion)
        if(x.getParentConnector()!=null)
        x.getParentConnector().getImage().setTransparency(0);
        
        y.setLocationWithComponentsTransition(cx,cy,false);//We move y down (in c's place)
        

        if(b!=null)//If b is not a null leaf
        {
            y.setLeft(b);//We connect y to b  
            b.setParent(y);//We connect b to y
            b.setLocationWithComponentsTransition(y.getX()-50,y.getY()+100,false);//We move it to it's coresponding coordinates (visually) 
        
        }  
        
        //-(nodePointer.getX()-world.getWidth()/2)
        //-(nodePointer.getY()-200)
        x.setLocationWithComponentsTransition(yX-y.getShiftedX(),yY-y.getShiftedY(),false);//We put x in y's place (visually)
        x.setRight(y);//We make y x's right child
        if(x.getParentConnector()!=null)
        x.getParentConnector().getImage().setTransparency(255);
        y.setLocationWithComponents(y.getX(),y.getY());//We refresh the connector's angle and position
        
        y.getParentConnector().getImage().setTransparency(255);//We make y's connector visible again    

        
        //By moving y in c's place we essentially shift the subtree to the right
        //(with exactly the inital distance from y to c)
        while(cDistanceMultiplier>0)//We compensate the shift to the right
            {
                spaceNodes(x,-1);//By moving the subtree left (for every unit [50px] that c was away from y)
                cDistanceMultiplier--;
            }
        if(b!=null)//Again if b is not a null leaf
        {
            NodeRB aux=b.getRight();//We get b's right child
            int rightMultiplier=0;//Number of units [50 px] b extends to the right
            while(aux!=null)
            {
                    //We calculate the number of units aux is spaced to the right from it's parent
                    //And add that to the total right units multiplier
                    rightMultiplier+=(aux.getX()-aux.getParent().getX())/50;
                    aux=aux.getRight();
            }
            while(rightMultiplier>0)//For every right unit
            {
                spaceNodes(y,1);//We move y's subtree to the right so c doesn't collide with b
                spaceNodes(x,-1);//We move x's subtree to the left so c doesn't collide with another subtree in the right
                spaceNodes(b,-1);//We move b's subtree to the lef to compensate to y's movement to the right
                rightMultiplier--;
            }
            
            int leftMultiplier=1;//Number of units [50 px] b extends to the left, we need to consider b itself, so we start at 1
            aux=b.getLeft();//We get b's left child
            while(aux!=null)
            {
                    //We calculate the number of units aux is spaced to the left from it's parent
                    //And add that to the total left units multiplier
                    leftMultiplier+=(aux.getParent().getX()-aux.getX())/50;
                    aux=aux.getLeft();
            }
            aux=y.getRight();//We get y's right child
            if(aux!=null)//If the child is not a null leaf
                {
                    aux=aux.getLeft();//We get the child's left child
                    while(aux!=null)
                    {
                        //We calculate the number of units aux is spaced to the left from it's parent
                        //And we substract that from the total left units multiplier, because those units compensate for the previous left units
                        leftMultiplier-=(aux.getParent().getX()-aux.getX())/50;
                        aux=aux.getLeft();
                    }
                    
                }
            while(leftMultiplier>0)//For every excessive left unit caused by appending b
            {
                spaceNodes(y,1);//We move y's subtree to the right, so b doesn't collide with another subtree in the left
                spaceNodes(x,-1);//We move x's subtree to the left, so y dones't collide with another subtree in the right
                leftMultiplier--;
            }
            while(leftMultiplier<0)//For every deficit left unit caused by appending b
            {
                spaceNodes(y,-1);//We move y's subtree to the left, to keep an uniform distance between nodes (in visualisation))
                spaceNodes(x,1);//We move x's subtree to the right, to compensate from the previous y move;
                leftMultiplier++;
            }
            

        }
        Greenfoot.delay(50);
        y.setStickyPointer(null);
        root.clearShift(root);
    }
    public void setRoot(NodeRB r)
    {
        root=r;
    }
    public NodeRB getRoot()
    {
        return root;
    }
    public NodePointer getNodePointer()
    {
        return nodePointer;
    }
     

}
