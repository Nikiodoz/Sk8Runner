package dk.kea.androidclass2016.sk8runner.sk8runner;


public class SimpleGame extends Game

{
    public SimpleGame()
    {
    }

    @Override
    public Screen createStartScreen()
    {
        return new SimpleScreen(this);
    }

}
