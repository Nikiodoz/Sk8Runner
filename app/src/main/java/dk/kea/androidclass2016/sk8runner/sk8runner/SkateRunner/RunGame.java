package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import dk.kea.androidclass2016.sk8runner.sk8runner.Game;
import dk.kea.androidclass2016.sk8runner.sk8runner.Screen;

/**
 * Created by Ahmed on 09-05-2016.
 */
public class RunGame extends Game
{
    public Screen createStartScreen()
    {
        return new MenuScreen(this);
    }
}
