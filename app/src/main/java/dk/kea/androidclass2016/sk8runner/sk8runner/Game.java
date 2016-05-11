package dk.kea.androidclass2016.sk8runner.sk8runner;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Game extends Activity implements Runnable, View.OnKeyListener, SensorEventListener
{
    private Thread mainLoopThread;
    private State state = State.Paused;
    private ArrayList<State> stateChanged = new ArrayList<>();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Screen screen;
    private Canvas canvas;
    private boolean pressedKey[] = new boolean[256];
    private Bitmap offscreensurface;

    private KeyEventPool keyEventPool = new KeyEventPool();
    private List<dk.kea.androidclass2016.sk8runner.sk8runner.KeyEvent> keyEvents = new ArrayList<>();
    private List<dk.kea.androidclass2016.sk8runner.sk8runner.KeyEvent> keyEventBuffer = new ArrayList<>();

    private TouchHandler touchHandler;
    private List<TouchEvent> touchEvents = new ArrayList<>();
    private List<TouchEvent> touchEventBuffer = new ArrayList<>();
    private TouchEventPool touchEventPool = new TouchEventPool();

    private float[] accelerometer = new float[3];

    private SoundPool soundPool;

    private int framesPerSecond = -1;

    private Paint paint = new Paint();

    //end of global variables, and start of methods

    public abstract Screen createStartScreen();


    protected void onCreate(Bundle InstanceBundle)
    {
        super.onCreate(InstanceBundle);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        surfaceView = new SurfaceView(this);
        setContentView(surfaceView);
        surfaceHolder = surfaceView.getHolder();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        screen = createStartScreen();

        if(surfaceView.getWidth() > surfaceView.getHeight())
        {
            setOffScreenSurface(480, 320);
        }
        else
        {
            setOffScreenSurface(320, 480);
        }
        surfaceView.setFocusableInTouchMode(true);
        surfaceView.requestFocus();
        surfaceView.setOnKeyListener(this);
        touchHandler = new MultiTouchHandler(surfaceView, touchEventBuffer, touchEventPool);
        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0);
        {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }



    }

    public boolean isKeyPressed(int keyCode)
    {
        return pressedKey[keyCode];
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            pressedKey[keyCode] = true;
        }
        if(event.getAction() == KeyEvent.ACTION_UP)
        {
            pressedKey[keyCode] = false;
        }
        return false;
    }

    public void setOffScreenSurface(int width, int height)
    {
        if(offscreensurface != null) offscreensurface.recycle();
        offscreensurface = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565 );
        canvas = new Canvas(offscreensurface);

    }





    //bliver brugt til at skifte skærmbilledet
    public void setScreen(Screen newScreen)
    {
        if(this.screen != null) screen.dispose();
        screen = newScreen;
    }

    public Typeface loadFont (String fileName)
    {
        Typeface font = Typeface.createFromAsset(getAssets(), fileName);
        if(font == null)
        {
            throw new RuntimeException("Could not load font from asset: " + fileName);
        }
        return font;
    }

    public void drawText(Typeface font, String text, int x, int y, int colour, int size)
    {
        paint.setTypeface(font);
        paint.setTextSize(size);
        paint.setColor(colour);
        canvas.drawText(text, x, y, paint);
    }

    //Finder objekt der skal tegnes
    public Bitmap loadBitmap(String fileName)
    {
        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if(bitmap == null)
            {
                throw new RuntimeException("kunne ikke få bitmap fra fil " + fileName);

            }
            return  bitmap;
        }
        catch (IOException e)
        {
            throw new RuntimeException("kunne ikke loade fil " + fileName);
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    Log.d("closing inputstream", "shit");
                }
            }
        }

    }

    //lang lyd
    public Music loadMusic(String fileName)
    {
        try
        {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(fileName);
            return new Music(assetFileDescriptor);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not load music file: " + fileName + " den er pist væk");
        }

    }

    //kort lyd
    public Sound loadSound(String fileName)
    {
        try
        {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(fileName);
            int soundId = soundPool.load(assetFileDescriptor, 0);
            Sound sound = new Sound(soundPool, soundId);
            return  sound;
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not load sound file: " + fileName + "den er helt gal");
        }

    }

    public void clearFramedbuffer(int color)
    {
    if(canvas != null) canvas.drawColor(color);
    }

    public int getFramedbufferWidth()

    {
        return surfaceView.getWidth();

    }

    public int getFramedbufferHeight()
    {
       return surfaceView.getHeight();
    }

    public int getOffscreenWidth()
    {
        return offscreensurface.getWidth();
    }
    public int getOffscreenHeight()
    {
        return offscreensurface.getHeight();
    }

    public void drawBitmap(Bitmap bitmap, int x, int y)
    {
        if(canvas != null)
        {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    Rect src = new Rect();
    Rect dst = new Rect();

    public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {
        if(canvas==null) return;
        src.left = srcX;
        src.top = srcY;
        src.right = srcX + srcWidth;
        src.bottom = srcY + srcHeight;

        dst.left = x;
        dst.top = y;
        dst.right = x + srcWidth;
        dst.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap, src, dst, null);
    }


    //public boolean isKeyPressed(int KeyCode)
    //{
     //   return false;
    //}

    public boolean isTouchDown(int pointer)
    {

        return touchHandler.isTouchDown(pointer);
    }

    public int getTouchX(int pointer)
    {
        float ratioX = (float)offscreensurface.getWidth() / (float)surfaceView.getWidth();
        int x = touchHandler.getTouchX(pointer);
        x = (int)(x * ratioX);
        return x;
    }

    public int getTouchY(int pointer)
    {
        float ratioY = (float)offscreensurface.getHeight() / (float)surfaceView.getHeight();
        int y = touchHandler.getTouchY(pointer);
        y = (int)(y * ratioY);
        return y;
    }

    public List<dk.kea.androidclass2016.sk8runner.sk8runner.KeyEvent> getKeyEvents()
    {
        return keyEvents;
    }

    public List<TouchEvent> getTouchEvents()
    {
        return touchEvents;
    }

    private  void fillEvents()
    {
        synchronized (keyEventBuffer)
        {
            int stop = keyEventBuffer.size();
            for(int i = 0; i < stop; i++)
            {
                keyEvents.add(keyEventBuffer.get(i));
            }
            keyEventBuffer.clear();
        }
        synchronized (touchEventBuffer)
        {
            int stop = touchEventBuffer.size();
            for(int i = 0; i < stop; i++)
            {
                touchEvents.add(touchEventBuffer.get(i));
            }
            touchEventBuffer.clear();
        }
    }

    private void freeEvents()
    {
        synchronized (keyEvents)
        {
            int stop = keyEventBuffer.size();
            for(int i = 0; i < stop; i++)
            {
                keyEventPool.free(keyEvents.get(i));
            }
            keyEvents.clear();

        }

        synchronized (touchEvents)
        {
            int stop = touchEventBuffer.size();
            for(int i = 0; i < stop; i++)
            {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
        }

    }





    public float[] getAccelerometer()
    {
        return accelerometer;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public void onSensorChanged(SensorEvent event)
    {
        System.arraycopy(event.values, 0, accelerometer, 0, 3);
    }

    public int getFramerate()
    {
        return framesPerSecond;
    }

    //this is the main method for the game loop
    public void run()
    {
        int frames = 0;
        long lastTime = System.nanoTime();
        long currentTime = lastTime;
        while(true)
        {

            synchronized (stateChanged)
            {
                for(int i = 0; i < stateChanged.size(); i++)
                {
                    state = stateChanged.get(i);
                    if(state == State.Disposed)
                    {
                       if(screen != null) screen.dispose();
                       Log.d("Game", "State is Disposed");

                    }
                    else if(state == State.Paused)
                    {
                        if(screen != null) screen.pause();
                        Log.d("Game", "State is paused");

                    }
                    else if(state == State.Resumed)
                    {
                        if(screen != null) screen.resume();
                        state = State.Running;
                        Log.d("Game", "State is resume");

                    }
                }

            }
            stateChanged.clear();

            if(state == State.Running)
            {
                if(!surfaceHolder.getSurface().isValid()) continue;
                Canvas physicalCanvas = surfaceHolder.lockCanvas();
                //here we should do some drawing on the screen
                //canvas.drawColor(Color.YELLOW);
                fillEvents();
                currentTime = System.nanoTime();




                if(screen != null) screen.update((currentTime-lastTime)/1000000000.0f); //game logic

                lastTime = currentTime;
                freeEvents();

                src.left = 0;
                src.top = 0;
                src.right = offscreensurface.getWidth() - 1;
                src.bottom = offscreensurface.getHeight() - 1;
                dst.left = 0;
                dst.top = 0;
                dst.right = surfaceView.getWidth();
                dst.bottom = surfaceView.getHeight();
                physicalCanvas.drawBitmap(offscreensurface, src, dst, null);



                surfaceHolder.unlockCanvasAndPost(physicalCanvas);
                //physicalCanvas = null;
            }
            frames = frames + 1;
            if(System.nanoTime() - lastTime > 1000000000)
            {
                framesPerSecond = frames;
                frames = 0;
                lastTime = System.nanoTime();
            }
        }
    }

    public void onPause()
    {
        super.onPause();
        synchronized (stateChanged)
        {
            if(isFinishing())
            {
                stateChanged.add(stateChanged.size(), State.Disposed);
                ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).unregisterListener(this);
            }
            else
            {
                stateChanged.add(stateChanged.size(), State.Paused);
            }
        }
        try
        {
            mainLoopThread.join();
        }
        catch(InterruptedException e)
        {

        }
        if(isFinishing())
        {
            soundPool.release();
        }
    }

    public void onResume()
    {
        super.onResume();
        mainLoopThread = new Thread(this);
        mainLoopThread.start();
        synchronized (stateChanged)
        {
            stateChanged.add(stateChanged.size(), State.Resumed);
        }

    }


}//firstclass slutter
