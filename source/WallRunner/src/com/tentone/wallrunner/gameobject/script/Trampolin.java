package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.ScriptedObject;

public class Trampolin extends Script
{
	public Trampolin(ScriptedObject object)
	{
		super(object);
	}
	
	boolean active;
	
	@Override
	public int ID()
	{
		return 4;
	}
	
	@Override
	public void ini()
	{
		active=true;
	}

	@Override
	public void update()
	{
		if(active && object.isColliding(Global.player))
		{
			Global.player.speed.y+=30f;
			active=false;
		}
		else if(!object.isColliding(Global.player))
		{
			active=true;
		}
	}
}
