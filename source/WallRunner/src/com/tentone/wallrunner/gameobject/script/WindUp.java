package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.ScriptedObject;

public class WindUp extends Script
{
	public WindUp(ScriptedObject object)
	{
		super(object);
	}
	
	@Override
	public int ID()
	{
		return 3;
	}
	
	@Override
	public void ini()
	{
		visible=false;
	}

	@Override
	public void update()
	{
		if(object.isColliding(Global.player))
		{
			Global.player.speed.y+=0.7f;
		}
	}
}
