package com.valefrasoftware;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GameAndroid extends Activity
{
    
    SoundManager snd;
	int laser, explode, pickup, meow, bark, moo;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));
        
        
    }
}
