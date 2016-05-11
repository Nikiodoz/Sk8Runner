package dk.kea.androidclass2016.sk8runner.sk8runner.SkateRunner;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import dk.kea.androidclass2016.sk8runner.sk8runner.Game;
import dk.kea.androidclass2016.sk8runner.sk8runner.Music;
import dk.kea.androidclass2016.sk8runner.sk8runner.Screen;
import dk.kea.androidclass2016.sk8runner.sk8runner.Sound;

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
