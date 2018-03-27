package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.ScriptedObject;

public class CheckPoint extends Script
{
	public CheckPoint(ScriptedObject object)
	{
		super(object);
	}
	
	@Override
	public int ID()
	{
		return 2;
	}
	
	@Override
	public void ini(){}

	@Override
	public void update()
	{
		if(object.isColliding(Global.player) && !Global.editor_mode)
		{
			Global.player_spawn.x=object.pos.x;
			Global.player_spawn.y=object.pos.y;
			active=false;
			visible=false;
		}
	}

}
