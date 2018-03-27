package com.tentone.wallrunner.tool;

public class STimer
{
	private float time_base;
	private int time;
	
	public STimer(float time_base)
	{
		this.time_base=time_base;
		this.time=0;
	}
		
	public void update()
	{
		if(time>0)
		{
			time--;
		}
	}
	
	public void start(float time_sec)
	{
		time=(int)(time_sec/time_base);
	}
	
	public boolean value()
	{
		return time==0;
	}
}
