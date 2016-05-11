package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.List;

import dk.kea.patrick.firstapp.CollisionListener;
import dk.kea.patrick.firstapp.Game;
import dk.kea.patrick.firstapp.Music;
import dk.kea.patrick.firstapp.Screen;
import dk.kea.patrick.firstapp.Sound;
import dk.kea.patrick.firstapp.TouchEvent;

/**
 * Created by Ahmed on 04-04-2016.
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
    Sound bounceSound;
    Sound blockSound;
    State state = State.Running;

    World world;
    WorldRenderer renderer;

    public GameScreen(Game game)
    {
        super(game);
        background = game.loadBitmap("background.png");
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");
        font = game.loadFont("font.ttf");
        music = game.loadMusic("music.ogg");
        bounceSound = game.loadSound("bounce.wav");
        blockSound = game.loadSound("blocksplosion.wav");
        world = new World(new CollisionListener()
        {
            public void collisionWall(){bounceSound.play(1);}
            public void collisionPaddle(){bounceSound.play(1);}
            public void collisionBlock() {blockSound.play(1);}
        });
        renderer = new WorldRenderer(game, world);
    }

    @Override
    public void update(float deltaTime)
    {
        if (world.gameOver == true) state = State.GameOver;
        if(state == State.Paused && game.getTouchEvents().size() > 0);
        {
            state = State.Running;
            resume();
        }
        if (state == State.GameOver)
        {
            List<TouchEvent> events = game.getTouchEvents();
            int stop = events.size();
            for (int i = 0; i < stop; i++)
            {
                if (events.get(i).type == TouchEvent.TouchEventType.Up)
                {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }

            game.setScreen(new MainMenuScreen(game));
            return;
        }
        //game.dispose();
        if(state == State.Running && game.getTouchY(0) <36 && game.getTouchX(0) > 320-36)
        {
            state = State.Paused;

            //possibly create the resume screen

        }
        game.drawBitmap(background, 0, 0);

        //do something will ball, blocks and paddle
        if (state == State.Running)
        {
            int touchX = -1;
            if (game.isTouchDown(0))
            {
                touchX = game.getTouchX(0);
            }
            world.update(deltaTime, game.getAccelerometer()[0], touchX);
        }
        game.drawText(font, "Points: " + Integer.toString(world.points), 30, 20, Color.GREEN, 12);
        game.drawText(font, "Lives: " + Integer.toString(world.lives), 24 + 80, 11, Color.GREEN, 12);
        renderer.render();

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
        //super.onPause();
        music.pause();
        if(state == State.Running)
        {
            state = State.Paused;
        }
        //game.music.pause();
    }

    @Override
    public void resume()
    {
        music.play();
    }

    @Override
    public void dispose()
    {
        //game.music.stop();
    }
}
