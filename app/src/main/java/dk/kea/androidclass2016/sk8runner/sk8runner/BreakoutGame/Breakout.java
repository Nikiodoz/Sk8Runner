package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;

import dk.kea.patrick.firstapp.Game;
import dk.kea.patrick.firstapp.Screen;

/**
 * Created by Ahmed on 04-04-2016.
 */
public class Breakout extends Game
{

    @Override
    public Screen createStartScreen()
    {
        return new MainMenuScreen(this);
    }
}
