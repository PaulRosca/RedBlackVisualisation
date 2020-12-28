import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class is used for statically holding the Actor to which the world is currently focused on and changing 
 * which actor that is.
 * 
 * @author Roșca Paul-Teodor 
 * @version 1.0 (22/12/2020)
 */
public class MouseTracker extends Actor
{
    /**
     * The actor to which the world is currently focused on
     */
    private static Actor currentFocus=null; 
    
    public static void setCurrentFocus(Actor a)
    {
        currentFocus=a;
    }
    public static Actor getCurrentFocus()
    {
        return currentFocus;
    }
}
