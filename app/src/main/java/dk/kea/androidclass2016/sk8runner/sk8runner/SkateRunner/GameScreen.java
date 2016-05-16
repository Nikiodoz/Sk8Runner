package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import android.graphics.Bitmap;
import android.graphics.Color;
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
            state = State.Running;
            resume();
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
        //game.dispose();
        if(state == State.Running && game.getTouchY(0) <36 && game.getTouchX(0) > 320-36)
        {
            state = State.Paused;

            //create the resume screen -v-
        }
        game.drawBitmap(background, 0, 0);
        //do something with the (objekter vi bruger i world)
        if (state == State.Running)
        {
            int touchX = -1;
            if (game.isTouchDown(0))
            {
                touchX = game.getTouchX(0);
            }
            //world.update(deltaTime, game.getAccelerometer() [0], touchX);
        }
        //game.drawText(font, "Points: " + Integer.toString((world.points), 30, 20, Color.GREEN, 12));

        if(state == State.Paused)
        {
            game.drawBitmap(resume, 160 - resume.getWidth() / 2, 240 - resume.getHeight() / 2);
        }
        if(state == State.GameOver)
        {
            game.drawBitmap(gameOver, 160 - gameOver.getWidth() / 2, 240 - resume.getHeight() / 2);
        }

    }

    @Override
    public void pause()
    {
        music.pause();
        if(state == State.Running)
        {
            state = State.Paused;
        }
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
