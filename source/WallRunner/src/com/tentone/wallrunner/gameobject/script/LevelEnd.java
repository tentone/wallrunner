package com.tentone.wallrunner.gameobject.script;

import com.badlogic.gdx.Gdx;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.ScriptedObject;

public class LevelEnd extends Script
{
	public LevelEnd(ScriptedObject object)
	{
		super(object);
	}
	
	@Override
	public int ID()
	{
		return 1;
	}
	
	@Override
	public void ini(){}
	
	@Override
	public void update()
	{
		if(object.isColliding(Global.player) && !Global.editor_mode)
		{
			//Load Next Level Code
			if(Global.actual_level<Global.level_file.length-1)
			{
				Global.actual_level++;
			}
			else
			{
				Global.actual_level=0;
			}
			
			try
			{
				Global.loadLevel(Global.level_file[Global.actual_level]);
			}
			catch(Exception e)
			{
				Gdx.app.exit();
			}
		}
	}
}
