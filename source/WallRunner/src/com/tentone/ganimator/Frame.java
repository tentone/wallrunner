package com.tentone.ganimator;

import com.badlogic.gdx.math.Vector2;

public class Frame implements Comparable<Frame>, Cloneable
{
	public float time,rotation,alpha;
	public Vector2 pos,ori,size;
	public int texture;
	
	public Frame(float time, float pos_x, float pos_y, float size_x, float size_y, float ori_x, float ori_y, float rotation, float alpha,int texture)
	{
		this.time=time;
		this.rotation=rotation;
		this.alpha=alpha;
		this.texture=texture;
		pos = new Vector2(pos_x,pos_y);
		ori = new Vector2(ori_x,ori_y);
		size = new Vector2(size_x,size_y);
	}
	
	@Override
	public String toString()
	{
		return "Pos:("+(int)pos.x+","+(int)pos.y+"), ori:("+(int)ori.x+","+(int)ori.y+")"+", size:("+(int)size.x+","+(int)size.y+")"+", rotation:"+(int)rotation+", alpha:"+alpha;
	}
	
	//Compares frames based on their time 
	@Override
	public int compareTo(Frame f)
	{
		if((time-f.time)>0)
		{
			return 1;
		}
		if((time-f.time)<0)
		{
			return -1;
		}

		return 0;
	}
	
	//Clones Frame into new Object
	@Override
	public Frame clone()
	{
		return new Frame(0,new Float(pos.x),new Float(pos.y),new Float(size.x),new Float(size.y),new Float(ori.x),new Float(ori.y),rotation,alpha,texture);
	}
}