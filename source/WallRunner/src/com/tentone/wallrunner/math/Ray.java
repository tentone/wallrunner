package com.tentone.wallrunner.math;

import com.badlogic.gdx.math.Vector2;

public class Ray
{
	public Vector2 ori,end;
	
	public Ray(float ori_x,float ori_y,float end_x, float end_y)
	{
		ori = new Vector2(ori_x,ori_y);
		end = new Vector2(end_x,end_y);
	}
	
	//Returns Point of Collision of this line with another box returns null if not colliding
	public Vector2 getPointColision(Ray ray)
	{
		Vector2 s1 = new Vector2(end.x - ori.x , end.y - ori.y);
		Vector2 s2 = new Vector2(ray.end.x - ray.ori.x , ray.end.y - ray.ori.y);

		float s = (-s1.y * (ori.x - ray.ori.x) + s1.x * (ori.y - ray.ori.y)) / (-s2.x * s1.y + s1.x * s2.y);
		float t = ( s2.x * (ori.y - ray.ori.y) - s2.y * (ori.x - ray.ori.x)) / (-s2.x * s1.y + s1.x * s2.y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
		{
			//Collision detected
			return new Vector2(ori.x + (t * s1.x),ori.y + (t * s1.y));
		}

		//No Collision Detected
		return null;
	}
}
