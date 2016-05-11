package dk.kea.androidclass2016.sk8runner.sk8runner;


public class KeyEventPool extends Pool<KeyEvent>
{

    @Override
    protected KeyEvent newItem()
    {
        return new KeyEvent();
    }
}
