import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * This class creates the background (canvas) in which the tree will be built.
 * <p>
 * The class is also responsible for spawining the UI buttons and handling operation skipping (moving between iterations of the tree created by altering it)
 * @author Roșca Paul-Teodor 
 * @version (a version number or a date)
 */
public class Background extends World
{

    class NodeInformation
    {
        private NodeRB node;
        private int nodeX,nodeY;
        private int connectorLength;
        private int connectorX,connectorY;
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
    private final int treeListMaxSize=5;
    private List<List<NodeInformation>> treeList = new ArrayList<List<NodeInformation>>();
    private int treeIndex=0;
    
    private Scroller scroller;
    private RBTree rbt;
    private int rootDefaultX;
    private int rootDefaultY;
    
    private boolean pressed=false;
    private int initialPositionX,initialPositionY;
    private MouseInfo mouse;
    private InsertButton ib;
    private SearchButton sb;
    private DeleteButton db;
    private CenteringButton cb;
    private Minus mb;
    private Clock clk;
    private Plus pb;
    private SkipForwardButton sfb;
    private SkipBackwardButton sbb;
    private InfoUI iui;
    private boolean visibleIUI;
    private InfoNodePointer inp;
    private boolean visibleINP;
    private int worldSpeed=55;
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

    public void act()
    {
        
        mouse = Greenfoot.getMouseInfo();
        checkMouse();
        if(MouseTracker.getCurrentFocus()==null&&pressed&&mouse!=null&&rbt.getRoot()!=null)
        {
            scroller.scroll(mouse.getX()-initialPositionX,mouse.getY()-initialPositionY);
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
        }
        if(MouseTracker.getCurrentFocus()==null)
        checkKey();
        
    }
    private void checkKey()
    {
        String key=Greenfoot.getKey();
        if(key!=null)
        {
            switch(key)
            {
                case ",":
                    if(!visibleIUI)
                        {
                            addObject(iui,this.getWidth()/2,this.getHeight()/2-25);
                            visibleIUI=true;
                        }
                    else
                        {
                            removeObject(iui);
                            visibleIUI=false;
                        }
                        break;
                case ".":
                    if(!visibleINP)
                        {
                            addObject(inp,this.getWidth()/2,this.getHeight()/2);
                            visibleINP=true;
                        }
                    else
                        {
                            removeObject(inp);
                            visibleINP=false;
                        }
                    break;
                case "space":
                    if(!visibleIA)
                    {
                        ia.makeVisible();
                        visibleIA=true;
                    }
                    else
                    {
                        ia.makeInvisible();
                        visibleIA=false;
                    }
                    
                
            }
        }
    }
    private void checkMouse()
    {
        if(Greenfoot.mousePressed(null)&&mouse!=null)
        {
            initialPositionX=mouse.getX();
            initialPositionY=mouse.getY();
            pressed=true;
        }
        else if(Greenfoot.mouseClicked(null))
        {
            pressed=false;
            Actor a = Greenfoot.getMouseInfo().getActor();
            if(a==null||!(a instanceof TextBox))
                {
                    MouseTracker.setCurrentFocus(null);
                    maxWorldSpeed();
                }
        }

    }
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
    public void increaseWorldSpeed()
    {
        worldSpeed+=2;
    }
    public void decreaseWorldSpeed()
    {
        if(worldSpeed<40)
            return;
        worldSpeed-=2;
    }
    public void resetWorldSpeed()
    {
        worldSpeed=55;
    }
    public void maxWorldSpeed()
    {
        Greenfoot.setSpeed(100);
    }
    public void applyWorldSpeed()
    {
        Greenfoot.setSpeed(worldSpeed);
    }
    private NodeRB addNodesInfoToList(NodeRB n,List<NodeInformation> l,boolean memOnly)
    {
        if(n==null)
            return null;

            NodeInformation nodeInfo = new NodeInformation();
            NodeRB currentNode = n.clone();
            nodeInfo.setNode(currentNode);
            if(!memOnly)
            {
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
            leftChild=addNodesInfoToList(n.getLeft(),l,memOnly);
            rightChild=addNodesInfoToList(n.getRight(),l,memOnly);
            currentNode.setLeft(leftChild);
            currentNode.setRight(rightChild);
            if(leftChild!=null)
                leftChild.setParent(currentNode);
            if(rightChild!=null)
                rightChild.setParent(currentNode);
            l.add(nodeInfo);
            return currentNode;
 
    }
    public void addCurrentTreeToList()
    {
        treeList.subList(treeIndex+1,treeList.size()).clear();
        NodeRB n=rbt.getRoot();
        List<NodeInformation> currentTreeInfo=new ArrayList<NodeInformation>();
        addNodesInfoToList(n,currentTreeInfo,false);
        treeList.add(currentTreeInfo);
        if(treeList.size()>treeListMaxSize)
                treeList.remove(0);
        treeIndex=treeList.size()-1;
    }
    private void removeTree(NodeRB n)
    {
        if(n!=null)
        {
            this.removeObject(n.getParentConnector());
            this.removeObject(n.getText());
            removeTree(n.getLeft());
            removeTree(n.getRight());
            this.removeObject(n);
        }
    }
    private void buildTree(int index)
    {
        List<NodeInformation> l = treeList.get(index);
        if(l.size()==0)
            return;
        NodeRB n=l.get(l.size()-1).getNode();
        List<NodeInformation> clonedList =new ArrayList<NodeInformation>();
        addNodesInfoToList(n,clonedList,true);
        n=clonedList.get(l.size()-1).getNode();
        for(int i=0;i<l.size();i++)
        {
            addObject(clonedList.get(i).getNode(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
            addObject(clonedList.get(i).getNode().getText(),l.get(i).getNodeX()+getScrolledX(),l.get(i).getNodeY()+getScrolledY());
            Connector con=clonedList.get(i).getNode().getParentConnector();
            if(con!=null)
                {
                    addObject(con,l.get(i).getConnectorX()+getScrolledX(),l.get(i).getConnectorY()+getScrolledY());
                    con.setScale(5,l.get(i).getConnectorLength());
                    con.setRotation(l.get(i).getConnectorRotation());
                }
        }
        rbt.setRoot(n);
    }
    private void skip(int i)
    {
        NodeRB n=rbt.getRoot();
        removeTree(n);
        rbt.setRoot(null);
        treeIndex+=i;
        buildTree(treeIndex);
        
    }
    public void skipBackward()
    {
       if(treeIndex>0)
            skip(-1);
    }
    public void skipForward()
    {
        if(treeIndex<treeList.size()-1)
            skip(1);
    }

}
