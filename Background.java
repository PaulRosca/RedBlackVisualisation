import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * This class creates the background (canvas) in which the tree will be built.
 * <p>
 * The class is also responsible for spawining the UI buttons and handling operation skipping (moving between iterations of the tree created by altering it)
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class Background extends World
{

    /**
     * Class that holds information about a Node, 
     * like : key, coordinates in word, visual connector coordinates and rotation in world
     * 
     */
    class NodeInformation
    {
        /**
         * Node informations (excluding world specific information)
         */
        private NodeRB node;
        /**
         * Node's X coordinate in world
         */
        private int nodeX;
        /**
         * Node's Y coordinate in world
         */
        private int nodeY;
        /**
         * Connector's length
         */
        private int connectorLength;
        /**
         * Connector's X coordinate in world
         */
        private int connectorX;
        /**
         * Connector's Y coordinate in world
         */
        private int connectorY;
        /**
         * Connector's rotation
         */
        private int connectorRotation;
        public void setNode(NodeRB n)
        {
            node=n;
        }

        public void setNodeCoordinates(int x,int y)
        {
            nodeX=x;
            nodeY=y;
        }

        public void setConnectorLength(int x)
        {
            connectorLength = x;
        }

        public void setConnectCoordinates(int x,int y)
        {
            connectorX=x;
            connectorY=y;
        }

        public void setConnectorRotation(int r)
        {
            connectorRotation=r;
        }

        public NodeRB getNode()
        {
            return node;
        }

        public int getNodeX()
        {
            return nodeX;
        }

        public int getNodeY()
        {
            return nodeY;
        }

        public int getConnectorLength()
        {
            return connectorLength;
        }

        public int getConnectorX()
        {
            return connectorX;
        }

        public int getConnectorY()
        {
            return connectorY;
        }

        public int getConnectorRotation()
        {
            return connectorRotation;
        }

        public String toString()//For debugging
        {
            return "Node : "+node+"\tLeft : "+node.getLeft()+"\tRight : "+node.getRight()+"\tKey : "+node.getKey()+"\t nodeX : "+nodeX+"\t nodeY : "+nodeY+"\t con X : "+connectorX + "\t con Y : "+connectorY+"\t con Rot : "+connectorRotation;
        }
    }
    /**
     * Number of tree operation we hold in memory
     */
    private final int MAXSIZE=10;
    /**
     * The list with the previous treeListMaxSize operations.
     */
    private List<List<NodeInformation>> treeList = new ArrayList<List<NodeInformation>>();
    /**
     * The current tree iteration
     */
    private int treeIndex=0;

    /**
     * Scroller object that handles scrolling the world
     */
    private Scroller scroller;
    /**
     * The red black tree object
     */
    private RBTree rbt;
    /**
     * The default coordinates for inserting the root
     */
    private int rootDefaultX;
    private int rootDefaultY;

    /**
     * Flag that tells us if the mouse left click is pressed
     */
    private boolean pressed=false;
    /**
     * Mouse coordinates before we start scrolling
     */
    private int initialPositionX,initialPositionY;
    private MouseInfo mouse;
    /**
     * UI buttons
     */
    private InsertButton ib;
    private SearchButton sb;
    private DeleteButton db;
    private CenteringButton cb;
    private Minus mb;
    private Clock clk;
    private Plus pb;
    private SkipForwardButton sfb;
    private SkipBackwardButton sbb;
    /**
     * Info menus
     */
    private InfoUI iui;
    private boolean visibleIUI;
    private InfoNodePointer inp;
    private boolean visibleINP;
    /**
     * The world speed used in tree operations
     */
    private int worldSpeed=55;
    /**
     * Object that handles displaying the the information about the algorithm
     */
    private InfoAlgorithm ia;
    private boolean visibleIA;
    public Background()
    {    
        super(1000, 600, 1,false);
        rootDefaultX=getWidth()/2;
        rootDefaultY=150;
        ia=new InfoAlgorithm();
        addObject(ia,140,65);
        addObject(ia.getOperation(),135,40);
        addObject(ia.getSubOperation(),135,65);
        addObject(ia.getDetails(),135,90);
        visibleIA=true;
        // We add the empty tree as the first tree in the operation's sequence
        treeList.add(new ArrayList<NodeInformation>());
        maxWorldSpeed();
        scroller = new Scroller(this);
        rbt = new RBTree(this);
        addObject(rbt,0,0);
        ib=new InsertButton(rbt,this);
        sb=new SearchButton(rbt,this);
        db=new DeleteButton(rbt,this);
        addObject(ib,930,560);
        addObject(ib.getTextBox(),830,561);
        addObject(ib.getText(),830,561);
        addObject(sb,710,560);
        addObject(sb.getTextBox(),610,561);
        addObject(sb.getText(),610,561);
        addObject(db,490,560);
        addObject(db.getTextBox(),390,561);
        addObject(db.getText(),390,561);
        cb=new CenteringButton(this);
        addObject(cb,960,40);
        mb=new Minus(this);
        addObject(mb,35,560);
        clk=new Clock(this);
        addObject(clk,90,555);
        pb= new Plus(this);
        addObject(pb,150,560);
        sfb=new SkipForwardButton(this);
        addObject(sfb,280,560);
        sbb=new SkipBackwardButton(this);
        addObject(sbb,210,560);
        iui=new InfoUI();
        visibleIUI=false;
        inp=new InfoNodePointer();
        visibleINP=false;
        setPaintOrder(Fixed.class,NodePointer.class,FloatingText.class,NodeRB.class,Connector.class);

    }

    /**
     * Method that continiously checks if scrolling should be called, or if menu popups should toggle
     */
    public void act()
    {

        mouse = Greenfoot.getMouseInfo();
        checkMouse();
        /*
         * If we're not focused on any textbox, the mouse is still pressed and we have at least
         * one node in the world
         */
        if(MouseTracker.getCurrentFocus()==null&&pressed&&mouse!=null&&rbt.getRoot()!=null)
        {
            /*
             * We scroll the world oposite to the mouse's direction
             */
            scroller.scroll(mouse.getX()-initialPositionX,mouse.getY()-initialPositionY);
            /*
             * We update the mouse initial position to be used the next time we scroll
             */
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
        }

        //If we're not focused on a textbox
        if(MouseTracker.getCurrentFocus()==null)
            checkKey();// We check if any key is pressed

    }

    /**
     * Method that checks if an information menu button is pressed and make's sure the info menu visibility is set accordingly
     * @see InfoAlgorithm#makeVisible()
     * @see InfoAlgorithm#makeInvisible()
     */
    private void checkKey()
    {
        String key=Greenfoot.getKey();
        if(key!=null)
        {
            switch(key)
            {
                case ","://If UI info menu button is pressed
                if(!visibleIUI)// If the menu is currently not visible
                {
                    addObject(iui,this.getWidth()/2,this.getHeight()/2-25);// We add it to the world
                    visibleIUI=true;// And flag it as visible
                }
                else// If the menu button is currently visible
                {
                    removeObject(iui);// We remove it from the world
                    visibleIUI=false;// And flag it not visible
                }
                break;
                case "."://If Node Pointer info menu button is pressed
                if(!visibleINP)// If the menu is currently not visible
                {
                    addObject(inp,this.getWidth()/2,this.getHeight()/2);// We add it to the world
                    visibleINP=true;// And flag it as visible
                }
                else// If the menu button is currently visible
                {
                    removeObject(inp);// We remove it from the world
                    visibleINP=false;// And flag it as not visible
                }
                break;
                case "space"://If Algorithm's info menu button is pressed
                if(!visibleIA)
                {
                    ia.makeVisible();// We make it visible
                    visibleIA=true;// And flag it as visible
                }
                else
                {
                    ia.makeInvisible();// We make it invisible
                    visibleIA=false;// And flag it as not visible
                }

            }
        }
    }

    /**
     * Method that check's mouse's state (currently pressing/clicked)
     */
    private void checkMouse()
    {
        // If the mouse initated a left click on anything but a TextBox
        if(Greenfoot.mousePressed(null)&&mouse!=null)
        {
            // We get it's coordinates
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
            // And flag it as pressed
            pressed=true;
        }
        // Else if the left click has finished
        else if(Greenfoot.mouseClicked(null))
        {
            // We flag it as not pressed
            pressed=false;
            Actor a = Greenfoot.getMouseInfo().getActor();
            if(a==null||!(a instanceof TextBox))// If we didn't click on a TextBox
            {
                // We set the mouse focus on null
                MouseTracker.setCurrentFocus(null);
                // We set the world speed to max, to make scrolling as smooth as possible
                maxWorldSpeed();
            }
        }

    }

    /**
     * Method that reverts all the scrolling
     */
    public void centerView()
    {
        scroller.scroll(-scroller.getScrolledX(),-scroller.getScrolledY());
    }

    public InfoAlgorithm getInfoAlgorithm()
    {
        return ia;
    }

    public int getRootDefaultX()
    {
        return rootDefaultX;
    }

    public int getRootDefaultY()
    {
        return rootDefaultY;
    }

    public Scroller getScroller()
    {
        return scroller;
    }

    public int getScrolledX()
    {
        return scroller.getScrolledX();
    }

    public int getScrolledY()
    {
        return scroller.getScrolledY();
    }

    /**
     * Method that increases the world speed used in tree operations
     */
    public void increaseWorldSpeed()
    {
        worldSpeed+=2;
    }

    /**
     * Method that decreases the world speed used in tree operations
     */
    public void decreaseWorldSpeed()
    {
        if(worldSpeed<40)// We cap it at just under 40, to avoid extremely low speeds which are not necessary
            return;
        worldSpeed-=2;
    }

    /**
     * Method that reverts the world speed used in tree operations to the default value
     */
    public void resetWorldSpeed()
    {
        worldSpeed=55;
    }

    /**
     * Method that maxes out the current world speed
     */
    public void maxWorldSpeed()
    {
        Greenfoot.setSpeed(100);
    }

    /**
     * Method that applies the world speed (Used when a {@link TreeInteractionButton} calls a tree operation)
     */
    public void applyWorldSpeed()
    {
        Greenfoot.setSpeed(worldSpeed);
    }

    /**
     * Method that adds a tree's information to a list of {@link NodeInformation}
     * 
     * @param n the root of the tree we add
     * @param l the list to which we add the information
     * @param memOnly a flag that tells us the tree we add is not present is the world at the moment
     * 
     * @return the clone of node n
     */
    private NodeRB addNodesInfoToList(NodeRB n,List<NodeInformation> l,boolean memOnly)
    {
        if(n==null)// If we've reached a leaf
            return null;// We stop

        NodeInformation nodeInfo = new NodeInformation();
        /* We clone the node's information 
         * so that we don't hold an actual refference of the real node
         */
        NodeRB currentNode = n.clone();
        nodeInfo.setNode(currentNode);
        if(!memOnly)// If the tree is present in the actual world
        {
            // We also hold the world specific information like coordinates

            nodeInfo.setNodeCoordinates(n.getX()-getScrolledX(), n.getY()-getScrolledY());
            Connector con=n.getParentConnector();
            if(con!=null)
            {
                nodeInfo.setConnectorLength(con.getImage().getHeight());
                nodeInfo.setConnectCoordinates(con.getX()-getScrolledX(), con.getY()-getScrolledY());
                nodeInfo.setConnectorRotation(con.getRotation());
            }
        }
        NodeRB leftChild,rightChild;

        // We recursively add the information of the children too

        leftChild=addNodesInfoToList(n.getLeft(),l,memOnly);
        rightChild=addNodesInfoToList(n.getRight(),l,memOnly);

        // We link the current node (held in the information list) to it's children (also from the information list)
        currentNode.setLeft(leftChild);
        currentNode.setRight(rightChild);

        //We also link the children to it's parent
        if(leftChild!=null)
            leftChild.setParent(currentNode);
        if(rightChild!=null)
            rightChild.setParent(currentNode);

        // We add the current node's info to the list
        l.add(nodeInfo);
        return currentNode;

    }

    /**
     * Method that gets the current tree node information and puts it in the list with the tree iterations
     */
    public void addCurrentTreeToList()
    {
        // We remove all the operations that succede the one's we're at currently
        treeList.subList(treeIndex+1,treeList.size()).clear();
        NodeRB n=rbt.getRoot();
        // We get the tree's node information as a list
        List<NodeInformation> currentTreeInfo=new ArrayList<NodeInformation>();
        addNodesInfoToList(n,currentTreeInfo,false);
        treeList.add(currentTreeInfo);
        if(treeList.size()>MAXSIZE)// If we've exceeded the maximum number of tree operations stored
            treeList.remove(0);// We remove the first one in the sequence
        treeIndex=treeList.size()-1;// We update the current tree index with the last operation
    }

    /**
     * Method that removes a tree from the world.
     * @param n the root of the tree we want to remove
     */
    private void removeTree(NodeRB n)
    {
        if(n!=null)// If we didn't reach a leaf
        {
            // We remove the current node's components
            this.removeObject(n.getParentConnector());
            this.removeObject(n.getText());
            // We do the same recursively to it's children
            removeTree(n.getLeft());
            removeTree(n.getRight());
            this.removeObject(n);
        }
    }

    /**
     * Method that adds to the world a tree from the list of tree operations
     * @param index the index from the list, of the tree we want to add into the world
     */
    private void buildTree(int index)
    {
        List<NodeInformation> l = treeList.get(index);// We get the node information of that tree
        if(l.size()==0)// If it's the empty tree
            return;// We stop here
        NodeRB n=l.get(l.size()-1).getNode();// We get the root of that tree (the root will always be the last element in the list)
        List<NodeInformation> clonedList =new ArrayList<NodeInformation>();
        addNodesInfoToList(n,clonedList,true);// We clone all the node's information (so we don't add the actual refference of the nodes)
        n=clonedList.get(l.size()-1).getNode();// We get the root of the cloned tree
        int i;
        for(i=0;i<l.size()-1;i++)
        {
            // We add the node into the world
            addObject(clonedList.get(i).getNode(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
            addObject(clonedList.get(i).getNode().getText(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
            Connector con=clonedList.get(i).getNode().getParentConnector();
            // We also add the connector and set it's world properties (coordinates, rotation)
            addObject(con,l.get(i).getConnectorX()+getScrolledX(),l.get(i).getConnectorY()+getScrolledY());
            con.setScale(5,l.get(i).getConnectorLength());
            con.setRotation(l.get(i).getConnectorRotation());
        }
        // We add the root into the world
        addObject(clonedList.get(i).getNode(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
        addObject(clonedList.get(i).getNode().getText(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
        // We set our world's tree root to the new root
        rbt.setRoot(n);
    }

    /**
     * Method that moves in the tree operation sequence list and replaces the current tree with the corresponding new one
     * @param i the number of position we move in the sequence
     */
    private void skip(int i)
    {
        NodeRB n=rbt.getRoot();
        removeTree(n);// We remove the current tree
        rbt.setRoot(null);// We make the root null
        treeIndex+=i;// We move the index in the sequence
        buildTree(treeIndex);// We build the appropiate tree

    }

    /**
     * Method that moves backward in the tree opeation sequence and builds that tree in the world.
     */
    public void skipBackward()
    {
        if(treeIndex>0)// Unless we've reached the start of the sequence
            skip(-1);// We skip backward
    }

    /**
     * Method that moves forward in the tree opeation sequence and builds that tree in the world.
     */
    public void skipForward()
    {
        if(treeIndex<treeList.size()-1)// Unless we've reached the end of the sequence
            skip(1);// We skip forward
    }

}
