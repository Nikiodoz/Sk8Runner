package dk.kea.androidclass2016.sk8runner.sk8runner;


public class TouchEvent
{
    public enum TouchEventType
    {
        Down,
        Up,
        Dragged
    }

    public TouchEventType type;

    public int x;
    public int y;

    public int pointer;

}
