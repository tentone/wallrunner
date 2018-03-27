package com.tentone.wallrunner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = "WallRunner";
		cfg.resizable = false;
		cfg.vSyncEnabled = false;
		cfg.foregroundFPS = 120;
		cfg.backgroundFPS = 120;
		
		if(args.length!=0)
		{
			new LwjglApplication(new MainGame(args[0]),cfg);
		}
		else
		{
			new LwjglApplication(new MainGame(""),cfg);
		}
	}
}
