package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import android.graphics.Bitmap;
import dk.kea.androidclass2016.sk8runner.sk8runner.Game;
import dk.kea.androidclass2016.sk8runner.sk8runner.Screen;


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
        playButton = game.loadBitmap("SkateButt.png");

    }

    public void update (float deltaTime)
    {
        //skifter fra main menu til game menu hvis man toucher
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
    @Override
    public void pause()
    {

    }
    @Override
    public void resume()
    {

    }
    @Override
    public void dispose()
    {

    }
}
