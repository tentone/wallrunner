package com.tentone.wallrunner.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ColisionBox
{
	public enum Type{Box,Circle,Point};
	
	public Vector2 pos,ori,col_box,speed;
	public Vector2 pos_ini;
	public Type type;
	
	public ColisionBox(Type type,float pos_x,float pos_y,float col_box_x,float col_box_y,float ori_x,float ori_y,float speed_x, float speed_y)
	{
		this.type=type;
		pos = new Vector2(pos_x,pos_y);
		pos_ini = new Vector2(pos_x,pos_y);
		col_box = new Vector2(col_box_x,col_box_y);
		ori = new Vector2(ori_x,ori_y);
		speed = new Vector2(speed_x,speed_y);
	}
	
	//Returns if a ColisionBox is colliding with another ColisionBox
	public boolean isColliding(ColisionBox a)
	{
		ColisionBox b=this;
		
		if(b.type==Type.Box)
		{
			if(a.type==Type.Box)
			{
				return b.pos.y<(a.pos.y+a.col_box.y) && ((b.pos.y+b.col_box.y)>a.pos.y) && (b.pos.x<(a.pos.x+a.col_box.x)) && ((b.pos.x+b.col_box.x)>a.pos.x);
			}
			else if(a.type==Type.Circle)
			{
				//Fix Collision Between Circle and Box
				float dist=(a.col_box.x/2f);
				
				return Vector2.dst(b.pos.x,b.pos.y,a.pos.x+a.ori.x,a.pos.y+a.ori.y)<dist ||
				Vector2.dst(b.pos.x,b.pos.y+b.col_box.y,a.pos.x+a.ori.x,a.pos.y+a.ori.y)<dist ||
				Vector2.dst(b.pos.x+b.col_box.x,b.pos.y,a.pos.x+a.ori.x,a.pos.y+a.ori.y)<dist ||
				Vector2.dst(b.pos.x+b.col_box.x,b.pos.y+b.col_box.y,a.pos.x+a.ori.x,a.pos.y+a.ori.y)<dist;
			}
			else if(a.type==Type.Point)
			{
				return a.pos.x>b.pos.x && a.pos.x<b.pos.x+b.col_box.x && a.pos.y>b.pos.y && a.pos.y<b.pos.y+b.col_box.y;
			}
		}
		else if(b.type==Type.Circle)
		{
			if(a.type==Type.Point)
			{
				return Vector2.dst(b.ori.x+b.pos.x,b.ori.y+b.pos.y,a.pos.x,a.pos.y)<(b.col_box.x/2f);
			}
			else if(a.type==Type.Circle)
			{
				return Vector2.dst(b.ori.x+b.pos.x,b.ori.y+b.pos.y,a.ori.x+a.pos.x,a.ori.y+a.pos.y)<(a.col_box.x/2f)+(b.col_box.x/2f);
			}
			else
			{
				return a.isColliding(this);
			}
		}
		else if(b.type==Type.Point)
		{
			if(a.type==Type.Point)
			{
				return b.pos.equals(a.pos);
			}
			else
			{
				return a.isColliding(this);
			}
		}
		
		return false;
	}
	
	//Check is a ColisiBox is Close to another ColisionBox
	public boolean isCloseTo(ColisionBox a)
	{
		ColisionBox b=this;
		
		if(b.type==Type.Box && a.type==Type.Box)
		{
			return b.pos.y==(a.pos.y+a.col_box.y) || ((b.pos.y+b.col_box.y)==a.pos.y) || (b.pos.x==(a.pos.x+a.col_box.x)) || ((b.pos.x+b.col_box.x)==a.pos.x);
		}
	
		return false;
	}
	
	//Check is Collision between Boxes is imminent
	public boolean willCollide(ColisionBox a, float speed_x, float speed_y)
	{
		if(type==Type.Box && a.type==Type.Box)
		{
			return pos.y+speed_y<(a.pos.y+a.col_box.y) && ((pos.y+speed_y+col_box.y)>a.pos.y) && (pos.x+speed_x<(a.pos.x+a.col_box.x)) && ((pos.x+speed_x+col_box.x)>a.pos.x);
		}
		
		return false;
	}
	
	//Draw ColisionBox Using a ShapeRenderer
	public void drawColisionBox(ShapeRenderer shape)
	{
		if(type==Type.Box)
		{
			shape.rect(pos.x,pos.y,col_box.x,col_box.y);
		}
		else if(type==Type.Circle)
		{
			shape.circle(pos.x+ori.x,pos.y+ori.y,col_box.x/2);
		}
		else if(type==Type.Point)
		{
			shape.circle(pos.x,pos.y,5f);
		}
	}
}
