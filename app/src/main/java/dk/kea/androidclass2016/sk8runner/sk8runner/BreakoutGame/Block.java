package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;

/**
 * Created by Ahmed on 11-04-2016.
 */
public class Block
{
    public static final float WIDTH = 40;
    public static final float HEIGHT = 18;
    int type;
    float x;
    float y;

    public Block(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
