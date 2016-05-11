package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import android.graphics.Bitmap;

import dk.kea.patrick.firstapp.BreakoutGame.GameScreen;
import dk.kea.patrick.firstapp.Game;
import dk.kea.patrick.firstapp.Screen;

/**
 * Created by Ahmed on 09-05-2016.
 */
public class MenuScreen extends Screen
{
    Bitmap mainmenu;
    Bitmap playButton;
    float passedTime = 0;
    long startTime = System.nanoTime();

    public MenuScreen (Game game)
    {
        super(game);
        mainmenu = game.loadBitmap("menuBackground.png");
        playButton = game.loadBitmap("playButt.png");

    }

    public void update (float deltaTime)
    {
        if(game.isTouchDown(0))
        {
            game.setScreen(new GameScreen(game));
            return;
        }
        passedTime = passedTime + deltaTime;
        game.drawBitmap(mainmenu, 0,0);
        if((passedTime - (int)passedTime) > 0.5f)
        {
            game.drawBitmap(playButton, 160 - playButton.getWidth() / 2, 320);
        }

    }

    public void pause()
    {

    }

    public void resume()
    {

    }

    public void dispose()
    {

    }
}
