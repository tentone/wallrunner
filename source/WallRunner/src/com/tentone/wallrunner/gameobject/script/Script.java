package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.ScriptedObject;

public abstract class Script
{
	public ScriptedObject object;
	public boolean active,visible;
	
	public Script(ScriptedObject object)
	{
		this.object=object;
		active=true;
		visible=true;
	}
	
	public void draw()
	{
		if(visible)
		{
			object.sprite.draw(Global.camera.batch);
		}
	}

	public abstract int ID();
	public abstract void ini();
	public abstract void update();
}
