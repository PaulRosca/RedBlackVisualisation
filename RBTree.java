import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
/**
 * This class represents a red black tree.
 * <p>
 * It holds information like it's root, the world in which the tree is present, and the main Node Pointer
 * 
 * @see NodeRB
 * @see NodePointer
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class RBTree extends Actor
{
    /**
     * Tree's root
     */
    private NodeRB root;
    /**
     * Tree's world
     */
    private Background world;
    /**
     * Main node pointer
     */
    private NodePointer nodePointer;
    /**
     * Default coordinates of the root
     */
    private int rootDefaultX;
    private int rootDefaultY;
    /**
     * The window responsible for displaying algorithm's info
     */
    private InfoAlgorithm infoAlg;

    /**
     * Constructor for our tree.
     * <p>
     * It set's the tree's world and root (default null) and retrieves some  from the world.
     */
    public RBTree(Background myWorld)
    {
        setImage((GreenfootImage)(null));
        root=null;
        world=myWorld;
        // We get the defaul root position from the world
        rootDefaultX=world.getRootDefaultX();
        rootDefaultY=world.getRootDefaultY();
        // We create and add the main Node Pointer
        nodePointer = new NodePointer(world);
        world.addObject(nodePointer,rootDefaultX,rootDefaultY);
        nodePointer.getImage().setTransparency(0);
        // We get the algorithm's info window form the world
        infoAlg=world.getInfoAlgorithm();
    }

    /**
     * Method for searching a node with a specific key in the tree and/or it's parent
     * 
     * @param k the key for which we search
     * @param findFirst flag that tells us if we're actually searching for a node (<b>true</b>) or if we're searching for it's parent (<b>false</b>)
     * @return the node we're searching for, or it's parent (if findFirst flag is true), the node's parent otherwise
     * 
     */
    private NodeRB searchNode(int k,boolean findFirst)
    {
        NodeRB y,x;
        y=null;// The parent of the current node
        x=root;// The current node
        // We set the pointer on the root
        if(root!=null)
            nodePointer.setLocation(root.getX(),root.getY());
        else

            nodePointer.setLocation(rootDefaultX,rootDefaultY);
        nodePointer.getImage().setTransparency(255);// We make pointer visible
        nodePointer.focusOnThis();// We focus on the pointer
        while(x!=null)// Until we reach a leaf
        {
            Greenfoot.delay(25);
            nodePointer.setLocationTransition(x.getX(),x.getY());// We move the pointer to the current node
            if(x.getKey()==k&&findFirst)// If we're searching for an existant node and we've found it
                return x;// We return it
            y=x;
            infoAlg.setSubOperation("Comparing "+k+" with "+x.getKey());
            // We move in the tree according to the searched node's key
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

        nodePointer.getImage().setTransparency(0);// We make the pointer invisible
        return y;// We return the node's parent

    }

    /**
     * Method used excusively for finding a node in the tree (if the node is present in the tree). It uses {@link searchNode} with the <i>findFirst</i> flag.
     * 
     * @param k the key of the node we're looking for
     * @return the searched node, or it's supossed parent if the not is not present in tree
     */
    public NodeRB search(int k)
    {
        nodePointer.setImage("NodePointerYellow.png");// We change the color of the pointer for this operation
        NodeRB node=searchNode(k,true);
        infoAlg.getDetails().clear();
        nodePointer.getImage().setTransparency(255);// We make the pointer visible again
        if(node==null)// If we didn't have any nodes in the tree
        {
            infoAlg.setSubOperation("Tree is empty");
            nodePointer.setImage("NodePointerRed.png");
        }
        else if(node.getKey()==k)// If the returned node has the matching key
        {
            Greenfoot.delay(25);
            infoAlg.setSubOperation("Node found");
            nodePointer.setImage("NodePointerGreen.png");
        }
        else// If the returned node should be the parent of our searched node
        {
            int leftChild=(k>node.getKey())?1:-1;
            nodePointer.setLocationTransition(node.getX()+50*leftChild,node.getY()+100);// We move the pointer to where our node should have been
            Greenfoot.delay(50);
            nodePointer.setImage("NodePointerRed.png");
            infoAlg.setSubOperation("Node not found");
        }

        Greenfoot.delay(50);
        nodePointer.setImage("NodePointer.png");
        nodePointer.getImage().setTransparency(0);
        return node;

    }

    /**
     * Method that searches for the successor of a node and moves a pointer for visualising the search.
     * 
     * @param node the node's who's successor we search for
     * @param scsPointer the pointer to the successor
     * 
     * @return the successor of the node 
     */
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

    /**
     * Method that searches for a node with a specific key and calls for it's deletion if the node is present in tree.
     * 
     * @see search()
     * @see delete()
     * 
     * @param k the key of the node we want to delete
     */
    public void deleteKey(int k)
    {
        NodeRB node = search(k);// We search for the node
        if(node==null||node.getKey()!=k)// If we didn't find it
        {
            infoAlg.setSubOperation("Node not found");
            return;// We stop the operation here
        }
        delete(node);// Else we deielete the found node
    }

    /**
     * Method that deletes a node from the tree, fixes the visualisation and calls for tree recoloring if necessary.
     * 
     * @see deleteColorFixup
     * 
     * @param node the node we wish to delete from the tree
     */
    private void delete(NodeRB node)
    {

        // Flat that tells us if the deleted node was black
        boolean blackNode=node.getColor();
        /* Flag that tells us if the parent is the left child of the grandparent
         * -1 - the parent is a right child
         * 0 - the parent is the tree root
         * 1 - the parent is a left child
         */
        int leftParent=0;
        // The node's replacement and parent
        NodeRB replacement=null,parent=node.getParent();
        /* Flag that tells us if we need to move ancestors (to compensate for the removal of the node) to mantain 
         * the distance between nodes uniform (visually)
         */
        boolean moveAncestor=false;
        // Refference to an ancestor that needs to be moved (visually)
        NodeRB ancestor=parent;
        /* Flag that tells us if the ancestor is the left child of it's parent
         * -1 - the ancestor is a right child
         * 0 - the ancestor is the tree root
         * 1 - the ancestor is a left child
         */
        int leftAncestor=0;
        if(node.getLeft()==null||node.getRight()==null)// If  the node doesn't have both children
        {
            // We make the node invisible to make it look like we deleted it
            node.getImage().setTransparency(0);
            node.getText().getImage().setTransparency(0);          
            if(node.getLeft()==null)// If the node doesn't have a left child
            {
                /*
                 * Flag that tells us if the node is a left child
                 * -1 - right child
                 * 0 - root
                 * 1 - left child
                 */
                int leftNode=0;
                replacement=node.getRight();// The replacement will be the right child
                if(parent==null)// If we delete the root
                    root=replacement;// We mark the root as the replacement node
                else
                {
                    if(parent.getLeft()==node)// If the deleted node is a left child
                    {
                        parent.setLeft(replacement);// We connect the parent to the replacement
                        leftNode=1;// We flag it as a left child
                    }
                    else// Otherwise
                    {
                        parent.setRight(replacement);// We connect the parent to the replacement
                        leftNode=-1;// We flag it as a right child
                    }
                    NodeRB grandParent=parent.getParent();
                    leftParent=(grandParent==null)?0:(grandParent.getLeft()==parent)?1:-1;// We flag the parent as a left child accordingly

                }
                if(replacement!=null)// If the node's right child is not a null leaf
                {
                    infoAlg.setSubOperation("Node has only right child");
                    // The difference between the node and it's replacement (on X axis)
                    int dx=replacement.getX()-node.getX();
                    // We connect the replacement to the node's parent
                    replacement.setParent(node.getParent());
                    // We remove the replacemnet's parent connector
                    world.removeObject(replacement.getParentConnector());
                    // We take the parent connector from the deleted node
                    replacement.setParentConnector(node.getParentConnector());
                    // We disconnect the deleted node from it's parent connector
                    node.setParentConnector(null);
                    infoAlg.setDetails("Moving child in node's place");
                    // We move the replacement in the deleted node's place
                    replacement.setLocationWithComponentsTransition(node.getX(), node.getY());

                    if(replacement!=root&&leftNode==1)// If the deleted node is a left child and we replaced it with it's right child
                    // That subtree can be moved to the right
                        replacement.setLocationWithComponentsTransition(replacement.getX()+dx, replacement.getY());
                }
                else// If the node we deleted has no children
                {
                    infoAlg.setSubOperation("Node has no children");
                    infoAlg.setDetails("We simply delete it");
                    if(leftParent!=0)// Unless we deleted a child of the root
                    {
                        leftAncestor=leftParent;
                        /* We move up the tree until we've reached a child of the root or an ancestor that is a different
                         * kind of child (left/right) than the deleted node 
                         */
                        while(ancestor.getParent()!=root&&leftAncestor==leftNode)
                        {
                            ancestor=ancestor.getParent();
                            leftAncestor=(ancestor.getParent().getLeft()==ancestor)?1:-1;// We update the ancestor's children type flag
                        }
                        if(leftNode!=leftAncestor)// If we've found an ancestor that is a different type of child
                            moveAncestor=true;// We flag it, to know we will need to move nodes to compensate for the removal of the node
                    }
                }

            }
            else if(node.getRight()==null)// If the node doesn't have a right child
            // We know for sure that it has a left child, because otherwise it would have netered the first "if"
            {
                /*
                 * Flag that tells us if the node is a right child
                 * 
                 * false - left child
                 * true - right child
                 */
                boolean rightNode=false;
                replacement=node.getLeft();// The replacement will be the left child
                if(parent==null)// If we delete the root
                    root=replacement;// We mark the root as the replacement node
                else
                {
                    if(parent.getLeft()==node)// If the deleted node is a left child
                        parent.setLeft(replacement);// We connect the parent to the replacement
                    else// Otherwise
                    {
                        parent.setRight(replacement);// We connect the parent to the replacement
                        rightNode=true;// We flag it as a right child
                    }
                }
                // The difference between the node and it's replacement (on X axis)
                int dx=replacement.getX()-node.getX();
                // We connect the replacement to the node's parent
                replacement.setParent(node.getParent());
                // We remove the replacemnet's parent connector
                world.removeObject(replacement.getParentConnector());
                // We take the parent connector from the deleted node
                replacement.setParentConnector(node.getParentConnector());
                // We disconnect the deleted node from it's parent connector
                node.setParentConnector(null);
                infoAlg.setSubOperation("Node has only left child");
                infoAlg.setDetails("Moving child in node's place");
                // We move the replacement in the deleted node's place
                replacement.setLocationWithComponentsTransition(node.getX(), node.getY());
                if(replacement!=root&&rightNode)// If the deleted node is a right child and we replaced it with it's left child
                // That subtree can be moved to the left
                    replacement.setLocationWithComponentsTransition(replacement.getX()+dx, replacement.getY());

            }
            // We actually remove the node and it's components from the world
            world.removeObject(node);
            world.removeObject(node.getText());
            world.removeObject(node.getParentConnector());

        }
        else// If the node has both children
        {
            infoAlg.setSubOperation("Node has two children");
            nodePointer.getImage().setTransparency(255);
            NodePointer scsPointer = new NodePointer(world);// We create a pointer for the successor
            Greenfoot.delay(50);
            NodeRB scs=successor(node,scsPointer);//We get the succesor of the node we need to delete
            nodePointer.focusOnThis();// We refocus on the deleted node
            Greenfoot.delay(50);
            infoAlg.setDetails("Node gets key from successor");
            node.setKey(scs.getKey());
            Greenfoot.delay(50);
            infoAlg.getDetails().clear();
            infoAlg.setOperation("Deleting successor");
            /* We stick the main pointer to our node to be deleted, because we will actually delete the successor
             * which might lead to the movement of our node
             */
            node.setStickyPointer(nodePointer);
            world.removeObject(scsPointer);
            delete(scs);// We delete the successor
            node.setStickyPointer(null);
            replacement=node;
            nodePointer.getImage().setTransparency(0);
            return;// We stop here in the deletion of our actual node, because we didn't actually delete it, just made it borrow data from it's successor

        }

        Greenfoot.delay(50);
        if(moveAncestor)// If the node's require moving
        {
            int leftNode;
            // We move the first ancestor which we've decided needs moving by one unit [50 px] to the left or right accordingly
            ancestor.setLocationWithComponentsTransition(ancestor.getX()+50*leftAncestor, ancestor.getY());
            leftNode=leftAncestor;// We consider the ancestor as the child
            ancestor=ancestor.getParent();// We get it's parent
            while(ancestor!=null&&ancestor!=root)// Unless we've reached the root (or even upwards)
            {
                leftAncestor=(ancestor.getParent().getLeft()==ancestor)?1:-1;// We update the flag that tells us if the ancestor is a left (or right) child
                if(leftNode!=leftAncestor)// If we've found an ancestor which is a different type of child from the previous one
                {
                    ancestor.setLocationWithComponentsTransition(ancestor.getX()+50*leftAncestor, ancestor.getY());// We move it by one unit [50 px] to the left or right accordingly
                    leftNode=leftAncestor;// We consider it our new child
                }
                ancestor=ancestor.getParent();// We move upwards in the tree
            }

        }
        if(blackNode)// If the deleted node was black
        {
            Greenfoot.delay(50);
            infoAlg.setSubOperation("Recoloring tree");
            Greenfoot.delay(50);
            deleteColorFixup(replacement,parent);// We start recoloring
            infoAlg.getDetails().clear();
            infoAlg.setSubOperation("Recoloring finished");
            Greenfoot.delay(50);
        }
    }

    /**
     * Method used for getting the color of a node. It is used because it handles the null leafs case.
     * 
     * @returns true - the node is black (or null)
     *          false - the node is red
     */
    private boolean getColorOfNode(NodeRB node)
    {
        if(node==null)
            return true;
        return node.getColor();
    }

    /**
     * Method that handles the recoloring of the tree in case of the deletion of a black node. It requires the parent parameter, because we can't determine the parent of a null node.
     * 
     * @param x the node from which we start recoloring
     * @param xParent it's parent
     */
    private void deleteColorFixup(NodeRB x,NodeRB xParent)
    {
        if(x!=root&&getColorOfNode(x)==true)// If the node is black or is not the root
        {
            // Refference to x's brother
            NodeRB w;
            if(x==xParent.getLeft())// If x is a left child
            {
                w=xParent.getRight();// It's brother is a right child
                if(getColorOfNode(w)==false)// If it's borther is red
                {
                    infoAlg.setSubOperation("Case 1");
                    infoAlg.setDetails("Coloring brother black");
                    w.setColor(true);
                    infoAlg.setDetails("Coloring parent red");
                    xParent.setColor(false);
                    infoAlg.setDetails("Rotating parent left");
                    leftRotate(xParent);
                    w=xParent.getRight();// We update the brother
                }
                if(getColorOfNode(w)==true)// If it's brother is black
                {
                    if(getColorOfNode(w.getLeft())==true&&getColorOfNode(w.getRight())==true)// If both it's nephews are black
                    {
                        infoAlg.setSubOperation("Case 2");
                        infoAlg.setDetails("Coloring brother red");
                        w.setColor(false);
                        infoAlg.setDetails("Moving to parent");
                        deleteColorFixup(xParent,xParent.getParent());
                        return;
                    }
                    if(getColorOfNode(w.getLeft())==false&&getColorOfNode(w.getRight())==true)// If the close nephew is red and the far one is black
                    {
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring close nephew black");
                        w.getLeft().setColor(true);
                        infoAlg.setDetails("Coloring brother red");
                        w.setColor(false);
                        infoAlg.setDetails("Rotating brother right");
                        rightRotate(w);
                        w=xParent.getRight();
                    }
                    infoAlg.setSubOperation("Case 4");
                    infoAlg.setDetails("Coloring brother same as parent");
                    w.setColor(getColorOfNode(xParent));
                    infoAlg.setDetails("Coloring parent black");
                    xParent.setColor(true);
                    infoAlg.setDetails("Coloring far nehpew black");
                    w.getRight().setColor(true);
                    infoAlg.setDetails("Rotating parent left");
                    leftRotate(xParent);
                }
            }
            else// If x is a right child
            {
                w=xParent.getLeft();// It's brother is a left child
                if(getColorOfNode(w)==false)// If it's borther is red
                {
                    infoAlg.setSubOperation("Case 1");
                    infoAlg.setDetails("Coloring brother black");
                    w.setColor(true);
                    infoAlg.setDetails("Coloring parent red");
                    xParent.setColor(false);
                    infoAlg.setDetails("Rotating parent right");
                    rightRotate(xParent);
                    w=xParent.getLeft();// We update the brother
                }
                if(getColorOfNode(w)==true)// If it's brother is black
                {
                    if(getColorOfNode(w.getLeft())==true&&getColorOfNode(w.getRight())==true)// If both it's nephews are black
                    {
                        infoAlg.setSubOperation("Case 2");
                        infoAlg.setDetails("Coloring brother red");
                        w.setColor(false);
                        infoAlg.setDetails("Moving to parent");
                        deleteColorFixup(xParent,xParent.getParent());
                        return;
                    }
                    if(getColorOfNode(w.getRight())==false&&getColorOfNode(w.getLeft())==true)// If the close nephew is red and the far one is black
                    {
                        infoAlg.setSubOperation("Case 3");
                        infoAlg.setDetails("Coloring near nephew black");
                        w.getRight().setColor(true);
                        infoAlg.setDetails("Coloring brother red");
                        w.setColor(false);
                        infoAlg.setDetails("Rotating brother left");
                        leftRotate(w);
                        w=xParent.getLeft();
                    }
                    infoAlg.setSubOperation("Case 4");
                    infoAlg.setDetails("Coloring brother same as parent");
                    w.setColor(getColorOfNode(xParent));
                    infoAlg.setDetails("Coloring parent black");
                    xParent.setColor(true);
                    infoAlg.setDetails("Coloring far nehpew black");
                    w.getLeft().setColor(true);
                    infoAlg.setDetails("Rotating parent right");
                    rightRotate(xParent);
                }
            }
        }
        if(x!=null)// Unless the replacement is null
        {
            infoAlg.setSubOperation("Case 0");
            infoAlg.setDetails("Coloring node black");
            x.setColor(true);// We color it black
        }
    }

    /**
     * Method for inserting a node into the tree. It also makes sure the tree looks uniform (visually).
     * 
     * @see insertColorFixup()
     * 
     * @param k the key of the node we want to insert
     */
    public void insert(int k)
    {
        NodeRB newNode = new NodeRB(k);//Creating a new node with the key desired to be inserted
        NodeRB y,x;
        y=searchNode(k,false);//Parent node of k
        nodePointer.getImage().setTransparency(255);
        newNode.setParent(y);//Setting the newly inserted node's parent
        infoAlg.getDetails().clear();
        if(y==null)// If the tree is empty
        {
            infoAlg.setSubOperation("Tree was empty");
            root=newNode;
            // We add the root in the default posistion
            world.addObject(newNode,rootDefaultX,rootDefaultY);
            world.addObject(newNode.getText(),rootDefaultX,rootDefaultY);
        }
        else// Otherwise
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

            // We make the node temporarily invisible, until we finish moving nodes around
            newNode.getImage().setTransparency(0);
            newNode.getText().getImage().setTransparency(0);
            auxConnector.getImage().setTransparency(0);

            auxConnector.setScale(5,(int)hypotenuse-55);//Seting the connector's size to match the distance between the connected nodes (for visualisation)

            if(y!=root)// Unless the parent is actually the root (case in which that node won't actually move)
            {
                y.setStickyPointer(nodePointer);// We stick the pointer to our parent
                startSpacing(newNode);// We start spacing the nodes (from the inserted node upwards)
                y.setStickyPointer(null);// We unstick the pointer
            }
            nodePointer.setLocationTransition(newNode.getX(),newNode.getY());// We move the main pointer to our inserted node
            // And make the node visible
            Greenfoot.delay(50);
            newNode.getImage().setTransparency(255);
            newNode.getText().getImage().setTransparency(255);
            auxConnector.getImage().setTransparency(255);

        }
        Greenfoot.delay(50);
        infoAlg.setSubOperation("Trying to recolor tree");
        Greenfoot.delay(50);
        insertColorFixup(newNode);// Starting to recolor the tree from the inserted node upwards
        infoAlg.getDetails().clear();
        infoAlg.setSubOperation("Recoloring finished");
        Greenfoot.delay(50);
        nodePointer.getImage().setTransparency(0);

    }

    /**
     * Method that gathers some information about a node and calls for it's spacing (to make the tree uniform) accordingly.
     * 
     * @see fixSpacing()
     * 
     * @param node the node from which we start spacing
     */
    private void startSpacing(NodeRB node)
    {
        int leftChild;
        NodeRB parent=node.getParent();
        leftChild=(parent.getLeft()==node)?1:-1;
        fixSpacing(parent, leftChild);
    }

    /**
     * Method that fixes the spacing of the nodes, using {@link spaceNodes()}.
     * 
     * @param parent the node for which we apply spacing (always a parent of another node)
     * @param leftChild flag that tells us if the original child (usually a newly inserted node) is a left or a right child
     */
    private void fixSpacing(NodeRB parent,int leftChild)
    {
        if(parent==root)// If we've reached the root we stop spacing
            return;
        /*
         * Flag that tells us if the curent node is a left or a right child
         * -1 - right child
         * 1 - left child
         */
        int leftParent;
        leftParent=(parent.getParent().getLeft()==parent)?1:-1;// We update the parent flag
        fixSpacing(parent.getParent(),leftParent);// We move upwards in the tree
        if(leftChild!=leftParent)// If we find a node that's a different type of child (left or right) compared to the original child
            spaceNodes(parent,leftChild);// We start spacing that node in the appropiate direction
    }

    /**
     * Method that moves a subtree by one unit [50px] to the right or left accordingly.
     * 
     * @param node the root of the subreee we want to move
     * @param right flag that tells us if we need to move right or left (-1 - left; 1 - right)
     */
    private void spaceNodes(NodeRB node,int right)
    {
        if(node==null||node==root)// If we try spacing a leaf or the root
            return;// We stop
        node.setLocationWithComponentsTransition(node.getX()+50*right,node.getY());

    }

    /** 
     * Method that handles the recoloring of the tree after inserting a node.
     * 
     * @param z the node from which we start recoloring
     */
    private void insertColorFixup(NodeRB z)
    {
        // Reference to the current node's uncle
        NodeRB uncle;
        while(z.getParent()!=null&&z.getParent().getColor()==false)// While parent's color is red
        {
            if(z.getParent()==z.getParent().getParent().getLeft())// If the parent is a left child
            {
                uncle=z.getParent().getParent().getRight();// The uncle is grandparent's right child
                if(getColorOfNode(uncle)==false)// If the uncle is red
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
                else// Otherwise the uncle is black
                {
                    if(z==z.getParent().getRight())// If the current node is a right child
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
            else// If the parent is a right child
            {
                uncle=z.getParent().getParent().getLeft();// The uncle is grandparent's left child
                if(getColorOfNode(uncle)==false)// If the uncle is red
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
                else// Otherwise the uncle is black
                {
                    if(z==z.getParent().getLeft())// If the current node is a left child
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
        if(root.getColor()==false)// If the root is red
        {
            infoAlg.setSubOperation("Case 0");
            infoAlg.setDetails("Coloring root black");
            root.setColor(true); // We make the root black
        }
    }

    /**
     * Method for rotating a node left, and making sure the tree stays uniform (visually).
     * 
     * @param x the node which we use a pivot for the rotation
     */
    private void leftRotate(NodeRB x)
    {
        x.setStickyPointer(nodePointer);// We stick the main pointer to the pivot
        x.setLocationWithPointer(x.getX(),x.getY());// We refresh the location so the pointer moves there
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

        int xX=x.getX(),xY=x.getY();// x's coordinates before moving it

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
        x.setLocationWithComponentsTransition(ax,ay);//We move x down in a's place

        if(b!=null)//If b is not a null leaf
        {
            x.setRight(b);//We connect b to x            
            b.setParent(x);//We connect b to x
            b.setLocationWithComponentsTransition(x.getX()+50,x.getY()+100);//We move it to it's corescponding coordinates (visually)
        }

        y.setLocationWithComponentsTransition(xX-x.getShiftedX(),xY-x.getShiftedY());//We put y in x's place (visually)
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
        root.clearShift(root);// We clear the shift of all nodes in the tree
    }

    /**
     * Method for rotating a node right, and making sure the tree stays uniform (visually).
     * 
     * @param x the node which we use a pivot for the rotation
     */
    private void rightRotate(NodeRB y)
    {
        y.setStickyPointer(nodePointer);// We stick the main pointer to the pivot
        y.setLocationWithPointer(y.getX(),y.getY());// We refresh the location so the pointer moves there
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

        y.setLocationWithComponentsTransition(cx,cy);//We move y down (in c's place)

        if(b!=null)//If b is not a null leaf
        {
            y.setLeft(b);//We connect y to b  
            b.setParent(y);//We connect b to y
            b.setLocationWithComponentsTransition(y.getX()-50,y.getY()+100);//We move it to it's coresponding coordinates (visually) 

        }  

        x.setLocationWithComponentsTransition(yX-y.getShiftedX(),yY-y.getShiftedY());//We put x in y's place (visually)
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

    /**
     * Method for setting the root of the tree.
     * 
     * @param r the new root
     */
    public void setRoot(NodeRB r)
    {
        root=r;
    }

    /**
     * Method for getting the root of the tree.
     * 
     * @return the root of the tree
     */
    public NodeRB getRoot()
    {
        return root;
    }

    /**
     * Method for getting the main pointer of the tree.
     * 
     * @return main pointer
     */
    public NodePointer getNodePointer()
    {
        return nodePointer;
    }

}
