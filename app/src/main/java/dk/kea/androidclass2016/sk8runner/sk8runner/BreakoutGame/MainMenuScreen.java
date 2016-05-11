//package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;
//
//import android.graphics.Bitmap;
//import dk.kea.patrick.firstapp.Game;
//import dk.kea.patrick.firstapp.Screen;
//
//public class MainMenuScreen extends Screen
//{
//    Bitmap mainmenu;
//    Bitmap insertCoin;
//    float passedTime = 0;
//    long startTime = System.nanoTime();
//
//    public MainMenuScreen(Game game)
//    {
//        super(game);
//        mainmenu = game.loadBitmap("mainmenu.png");
//        insertCoin = game.loadBitmap("insertcoin.png");
//    }
//
//    @Override
//    public void update(float deltaTime)
//    {
//        //skifter fra main menu til game menu hvis man toucher
//        if(game.isTouchDown(0))
//        {
//            game.setScreen(new GameScreen(game));
//            return;
//        }
//        passedTime = passedTime + deltaTime;
//        game.drawBitmap(mainmenu, 0, 0);
//        if( (passedTime - (int)passedTime) > 0.5f)
//        {
//            game.drawBitmap(insertCoin, 160 - insertCoin.getWidth() / 2, 320);
//        }
//    }
//
//    @Override
//    public void pause()
//    {
//
//    }
//
//    @Override
//    public void resume()
//    {
//
//    }
//
//    @Override
//    public void dispose()
//    {
//
//    }
//}
