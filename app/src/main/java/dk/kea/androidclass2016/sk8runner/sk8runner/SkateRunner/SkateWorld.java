package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import java.util.ArrayList;
import java.util.List;

import dk.kea.androidclass2016.sk8runner.sk8runner.CollisionListener;

/**
 * Created by Ahmed on 16-05-2016.
 */
public class SkateWorld
{
    public static final float MIN_X = 0;
    public static final float  MAX_X = 319;
    public static final float MIN_Y = 32;
    public static final float MAX_Y = 479;

    int points = 0;
    int lives = 3;
    boolean justLostLife = false;
    boolean gameOver = false;
    int level = 1;
    int charHits = 0;

    List<SkateBlocks> blocks = new ArrayList<>();
    CollisionListener listener;

    public SkateWorld(CollisionListener listener)
    {

        this.listener = listener;
    }

    public void generateBlocks()
    {
       blocks.clear();
        int type = 0;
        int startY = (int)(MIN_Y);
        //next 2 lines: level. better would be colour, shape etc...
        startY = startY + level * 20; //just a simple and stupid level change
        if(startY > 200) startY = 200; //don't go too low
        for(int y = startY; y < startY + 8 * SkateBlocks.HEIGHT; y = y + (int)SkateBlocks.HEIGHT, type++)
        {
            for(int x = 20; x < 320 - SkateBlocks.Width/2; x=x+ (int)SkateBlocks.Width)
            {
                blocks.add(new SkateBlocks(x, y, type));
            }
        }
    }

    public void update(float deltaTime, float accelX, int touchX)
    {
        if(blocks.size() == 0)
        {
            generateBlocks();
        }
    }
}
