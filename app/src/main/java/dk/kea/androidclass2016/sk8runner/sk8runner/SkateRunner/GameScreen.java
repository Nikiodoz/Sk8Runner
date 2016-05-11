package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import java.util.List;

import dk.kea.androidclass2016.sk8runner.sk8runner.Game;
import dk.kea.androidclass2016.sk8runner.sk8runner.Music;
import dk.kea.androidclass2016.sk8runner.sk8runner.Screen;
import dk.kea.androidclass2016.sk8runner.sk8runner.Sound;
import dk.kea.androidclass2016.sk8runner.sk8runner.TouchEvent;

/**
 * Created by Ahmed on 11-05-2016.
 */
public class GameScreen extends Screen
{
    enum State
    {
        Paused,
        Running,
        GameOver,
    }

    Bitmap background;
    Bitmap resume;
    Bitmap gameOver;
    Typeface font;
    Music music;
    Sound jumpSound;
    Sound smashSound;
    State state = State.Running;

    public GameScreen(Game game)
    {
        super(game);
        background = game.loadBitmap("skateRunnerBg.png");
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");
        font = game.loadFont("font.ttf");
        music = game.loadMusic("skateMusic.wav");
    }

    @Override
    public void update(float deltaTime)
    {

     //       if (world.gameOver == true) state = State.GameOver;
            if(state == State.Paused && game.getTouchEvents().size() > 0);
        {
            state = State.Running;resume();
        }
               if (state == State.GameOver)
        {
            List<TouchEvent> events = game.getTouchEvents();
          int stop = events.size();
            for (int i = 0; i < stop; i++) {
               if (events.get(i).type == TouchEvent.TouchEventType.Up)
               {
                   game.setScreen(new MenuScreen(game));
                   return;
                }
            }

            game.setScreen(new MenuScreen(game));
            return;
        }
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {
        music.play();

    }

    @Override
    public void dispose()
    {

    }
}
