package dk.kea.androidclass2016.sk8runner.sk8runner;


public class TouchEventPool extends Pool<TouchEvent>
{
    protected TouchEvent newItem()
    {
        return new TouchEvent();
    }

}
