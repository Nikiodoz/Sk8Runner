package dk.kea.androidclass2016.sk8runner.sk8runner.BreakoutGame;

import java.util.ArrayList;
import java.util.List;

import dk.kea.patrick.firstapp.CollisionListener;

/**
 * Created by Ahmed on 04-04-2016.
 */
public class World
{
    public static final float MIN_X = 0;
    public static final float  MAX_X = 319;
    public static final float MIN_Y = 32;
    public static final float MAX_Y = 479;

    int points = 0;
    int lives = 3;
    boolean justLostLife = false;
    boolean gameOver = false;
    int level = 1;
    int paddleHits = 0;

    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    List<Block> blocks = new ArrayList<>();
    CollisionListener listener;

    public World(CollisionListener listener)
    {
        generateBlocks();
        this.listener = listener;
    }

    public void generateBlocks()
    {
        blocks.clear();
        int type = 0;
        int startY = (int)(MIN_Y + Ball.HEIGHT * 1.7);
        //next 2 lines: level. better would be colour, shape etc...
        startY = startY + level * 20; //just a simple and stupid level change
        if(startY > 200) startY = 200; //don't go too low
        for(int y = startY; y < startY + 8 * Block.HEIGHT; y = y + (int)Block.HEIGHT, type++)
        {
            for(int x = 20; x < 320 - Block.WIDTH/2; x=x+ (int)Block.WIDTH)
            {
                blocks.add(new Block(x, y, type));
            }
        }
    }

    public void update (float deltaTime, float accelX, int touchX) //deltaTime = measured in seconds
    {
        if (justLostLife)
        {
            if(touchX > 0)
            {
                justLostLife = false;
                Ball tempBall = new Ball();
                ball.velocityX = new Ball().velocityX;
                ball.velocityY = tempBall.velocityY;
                tempBall = null;
            }
        }
        if(ball.y + Ball.HEIGHT > MAX_Y)
        {
            lives = lives -1;
            ball.x = paddle.x + Paddle.WIDTH/2;
            ball.y = paddle.y - Ball.HEIGHT - 10;
            ball.velocityX = 0;
            ball.velocityY = 0;
            justLostLife = true;
            if(lives == 0)
            {
                gameOver = true;
                return;
            }

        }

        if(blocks.size() == 0)
        {
            generateBlocks();
            ball = new Ball();
            ball.velocityX = ball.velocityX * (1.0f + (float) level * 0.1f);
            ball.velocityY = ball.velocityY * (1.0f + (float) level * 0.1f);
            paddle = new Paddle();
        }

        ball.x = ball.x + ball.velocityX * deltaTime;
        ball.y = ball.y + ball.velocityY * deltaTime;
        collideWalls();

        paddle.x = paddle.x - (accelX * 50 * deltaTime);
        if (touchX>0) paddle.x = touchX - (int)Paddle.WIDTH/2;

        if (paddle.x < MIN_X)
        {
            paddle.x = MIN_X;
        }

        if (paddle.x + Paddle.WIDTH > MAX_X)
        {
            paddle.x = MAX_X - Paddle.WIDTH;
        }

        collideBallPaddle();
        collideBallBlocks(deltaTime);
    }

    private boolean collideRects(float x, float y, float width, float height, float x2, float y2, float width2, float height2)
    {
        if(x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
        {
            return true;
        }
        return false;
    }

    private void collideBallBlocks(float deltaTime)
    {
        Block block;
        int stop = blocks.size();
        for(int i = 0; i < stop; i++)
        {

            block = blocks.get(i);
            if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, Block.WIDTH, Block.HEIGHT))
            {
                points = points + 10 - block.type;
                listener.collisionBlock();
                blocks.remove(i);
                i--;
                stop--;
                float oldvx = ball.velocityX;
                float oldvy = ball.velocityY;
                reflectBall(ball, block);
                ball.x = ball.x - oldvx * deltaTime * 1.01f;
                ball.y = ball.y - oldvy * deltaTime * 1.01f;
            }
        }
    }

    private void reflectBall(Ball ball, Block block)
    {
        //check top left corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y,1,1)) //check top corner of the block
        {
            if (ball.velocityX > 0) ball.velocityX = -ball.velocityX;
            if (ball.velocityY > 0) ball.velocityY = -ball.velocityY;
            return;
        }
        //Check top right corner of the block
        if (collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x + Block.WIDTH, block.y, 1, 1))
        {
            if(ball.velocityX < 0) ball.velocityX = -ball.velocityX;
            if(ball.velocityY > 0) ball.velocityY = -ball.velocityY;
            return;
        }
        //check bottom left corner
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y + Block.HEIGHT, 1, 1))
        {
            if(ball.velocityX > 0) ball.velocityX = -ball.velocityX;
            if(ball.velocityY < 0) ball.velocityY = -ball.velocityY;
            return;
        }
        //check bottom right corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x + Block.WIDTH, block.y + Block.HEIGHT, 1, 1))
        {
            if(ball.velocityX < 0) ball.velocityX =- ball.velocityX;
            if(ball.velocityY < 0) ball.velocityY = -ball.velocityY;
            return;
        }
        //check the top edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, Block.WIDTH, 1))
        {
            if(ball.velocityY > 0) ball.velocityY = -ball.velocityY;
            return;
        }
        //check the bottom edge of the block
        if (collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y + Block.HEIGHT, Block.WIDTH, 1))
        {
            if (ball.velocityY < 0) ball.velocityY = -ball.velocityY;
            return;

        }
        //check the left edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x, block.y, 1, Block.HEIGHT))
        {
            if(ball.velocityX > 0) ball.velocityX = -ball.velocityX;
            return;
        }
        //check the right edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, block.x + Block.WIDTH, block.y, 1, Block.HEIGHT))
        {
            if(ball.velocityX < 0) ball.velocityX = - ball.velocityX;
            return;
        }
    }

    private void collideBallPaddle()
    {
        if (ball.y > paddle.y) return;

        //check if ball hits left paddle corner
        if(ball.x + Ball.WIDTH >= paddle.x && ball.x + Ball.WIDTH <= paddle.x + 3 && ball.y + Ball.HEIGHT >= paddle.y +2)
        {
            listener.collisionPaddle();
            ball.y = paddle.y - Ball.HEIGHT - 0;
            ball.velocityY = - ball.velocityY;
            if(ball.velocityX > 0) ball.velocityX = -ball.velocityX;
            advanceBlocks();
            return;
        }
        //check if ball hits right paddle corner
        if(ball.x < paddle.x + Paddle.WIDTH && ball.x > paddle.x + Paddle.WIDTH -3 && ball.y + Ball.HEIGHT  >= paddle.y +2)
        {
            ball.y = paddle.y - Ball.HEIGHT - 0;
            ball.velocityY = - ball.velocityY;
            if(ball.velocityX < 0) ball.velocityX = -ball.velocityX;
            advanceBlocks();
            return;
        }
        //check if ball hits top edge of the paddle
        if (ball.x + Ball.WIDTH >= paddle.x && ball.x < paddle.x + Paddle.WIDTH
                && ball.y + Ball.HEIGHT  >= paddle.y +2)
        {
            listener.collisionPaddle();
            ball.y = paddle.y - Ball.HEIGHT - 0;
            ball.velocityY = - ball.velocityY;
            advanceBlocks();
        }
    }

    private void advanceBlocks()
    {
        paddleHits++;
        if(paddleHits == 3)
        {
            paddleHits = 0;
            //advance = advance + (int)Block.HEIGHT;
            Block tempBlock;
            int stop = blocks.size();
            for (int i = 0; i < stop; i++)
            {
                tempBlock = blocks.get(i);
                tempBlock.y = tempBlock.y + (int)Block.HEIGHT;
            }
        }
    }

    private void collideWalls()
    {
        if(ball.x < MIN_X)
        {
            ball.velocityX = -ball.velocityX; // changing the direction
            ball.x = MIN_X;
            listener.collisionWall();
        }
       else if (ball.x + Ball.WIDTH > MAX_X)
        {
            ball.velocityX = -ball.velocityX;
            ball.x = MAX_X - Ball.WIDTH;
            listener.collisionWall();
        }

        if (ball.y < MIN_Y)
        {
            ball.velocityY = -ball.velocityY;
            ball.y = MIN_Y;
            listener.collisionWall();
        }

    }
}
