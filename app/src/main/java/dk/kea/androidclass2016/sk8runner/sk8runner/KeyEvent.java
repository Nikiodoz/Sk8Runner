package dk.kea.androidclass2016.sk8runner.sk8runner;

public class KeyEvent
{
    public enum KeyEventType
    {
        Down,
        Up

    }

    public KeyEventType type;
    public int KeyCode;
    public char character;
}
