package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.gameobject.ScriptedObject;

public class NullScript extends Script
{
	public NullScript(ScriptedObject object)
	{
		super(object);
	}
	
	@Override
	public int ID()
	{
		return 0;
	}
	
	@Override
	public void ini(){}

	@Override
	public void update(){}
}
