import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MouseTracker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MouseTracker extends Actor
{
    /**
     * Act - do whatever the MouseTracker wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
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
