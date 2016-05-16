package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;


import android.graphics.Bitmap;

import dk.kea.androidclass2016.sk8runner.sk8runner.Game;

public class SkateRenderer
{
    Game game;
    SkateWorld world;
    Bitmap skateBlock1;
    Bitmap skateBlock2;


//Constructor

    public SkateRenderer(Game game, SkateWorld world)
    {


        this.game = game;
        this.world = world;
        this.skateBlock1 = game.loadBitmap("skateBlock1.png");
        this.skateBlock2 = game.loadBitmap("skateBlock2.png");
    }

    public  void render(){

        SkateBlocks block;


        int stop = world.blocks.size();
        for (int i = 0; i < stop; i++){

            block = world.blocks.get(i);
            game.drawBitmap(skateBlock1,(int)block.x,(int)block.y,0, (int)(block.type * SkateBlocks.HEIGHT), (int)SkateBlocks.Width, (int)SkateBlocks.HEIGHT);

        }



    }
}