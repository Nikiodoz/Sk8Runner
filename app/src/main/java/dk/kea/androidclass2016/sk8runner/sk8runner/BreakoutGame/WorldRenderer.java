//package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;
//
//import android.graphics.Bitmap;
//
//import dk.kea.patrick.firstapp.Game;
//
///**
// * world; by Ahmed on 04-04-2016.
// */
//public class WorldRenderer
//{
//    Game game;
//    World world;
//    Bitmap ballImage;
//    Bitmap paddleImage;
//    Bitmap blocksImage;
//    //Bitmap gameOverImage;
//
//    //constructor
//    public WorldRenderer(Game game, World world)
//    {
//        //objekter i min world
//        this.game = game;
//        this.world = world;
//        this.ballImage = game.loadBitmap("ball.png");
//        this.paddleImage = game.loadBitmap("paddle.png");
//        this.blocksImage = game.loadBitmap("blocks.png");
//        //this.gameOverImage = game.loadBitmap("gameover.png");
//    }
//
//    //metode til og tegne world
//    public void render()
//    {
//        game.drawBitmap(ballImage, (int)world.ball.x, (int)world.ball.y);
//        game.drawBitmap(paddleImage, (int)world.paddle.x, (int)world.paddle.y);
//
//        Block block;
//        int stop = world.blocks.size();
//        for (int i = 0; i < stop; i++)
//        {
//            block = world.blocks.get(i);
//            game.drawBitmap(blocksImage, (int)block.x, (int)block.y,
//            0, (int)(block.type * Block.HEIGHT), (int)Block.WIDTH, (int)Block.HEIGHT);
//        }
//    }
//}
