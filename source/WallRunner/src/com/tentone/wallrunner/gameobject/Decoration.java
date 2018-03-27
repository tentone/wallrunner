package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.physics.ColisionBox;

public class Decoration extends ColisionBox implements GameObject
{
	public Sprite sprite;
	public int texture;
	
	public int move_mode; //0 -> dont move / 1 -> move x / 2-> move y
	public float move_limit,move_speed,angular_speed;

	//Auxiliar Vars
	private boolean active;
	
	public Decoration(float pos_x, float pos_y, int texture, float size_x, float size_y, float ori_x, float ori_y, float angular_speed,int move_mode, float move_limit,float move_speed)
	{
		super(Type.Box,pos_x,pos_y,size_x,size_y,ori_x,ori_y,0f,0f);
		pos_ini = new Vector2(pos_x,pos_y);
		
		this.texture=texture;
		this.angular_speed=angular_speed;
		this.move_mode=move_mode;
		this.move_limit=move_limit;
		this.move_speed=move_speed;
		
		if(move_mode==0 || move_mode==3)
		{
			speed = new Vector2(0,0);
		}
		else if(move_mode==1)
		{
			speed = new Vector2(move_speed,0);
		}
		else if(move_mode==2)
		{
			speed = new Vector2(0,move_speed);
		}
		
		active=move_mode!=0 || angular_speed!=0f;
		
		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos.x, pos.y);
		sprite.setOrigin(ori.x,ori.y);
		sprite.setSize(size_x,size_y);
	}
	
	public static Decoration createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new Decoration(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Integer.parseInt(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),Integer.parseInt(temp[8]),Float.parseFloat(temp[9]),Float.parseFloat(temp[10]));
	}
	
	public String dataString()
	{
		return pos_ini.x+"|"+pos_ini.y+"|"+texture+"|"+sprite.getWidth()+"|"+sprite.getHeight()+"|"+ori.x+"|"+ori.y+"|"+angular_speed+"|"+move_mode+"|"+move_limit+"|"+move_speed;
	}
	
	public void update()
	{
		if(active)
		{
			if(move_mode==1)//Horizontal Move
			{
				if(move_limit>0 && ((pos.x>(pos_ini.x+move_limit)) || (pos.x<pos_ini.x)))
				{
					speed.x*=-1;
				}
				else if(move_limit<0 &&(pos.x>pos_ini.x||pos.x<(pos_ini.x+move_limit)))
				{
					speed.x*=-1;
				}
				pos.x+=speed.x;
			}
			else if(move_mode==2)//Vertical Move
			{
				if(move_limit>0  && ((pos.y>(pos_ini.y+move_limit)) || (pos.y<pos_ini.y)))
				{
					speed.y*=-1;
				}
				else if(move_limit<0 && ((pos.y<(pos_ini.y+move_limit))||(pos.y>pos_ini.y)))
				{
					speed.y*=-1;
				}
				pos.y+=speed.y;
			}
			else if(move_mode==3)//Circular trajectory
			{
				pos.x=pos_ini.x+MathUtils.cos(Global.time*move_speed)*move_limit-move_limit;
				pos.y=pos_ini.y+MathUtils.sin(Global.time*move_speed)*move_limit;
				//Speed Values not needed
				//speed.x=-1f*move_speed*MathUtils.sin(Global.time*move_speed)*move_limit;
				//speed.y=move_speed*MathUtils.cos(Global.time*move_speed)*move_limit;
			}
			sprite.rotate(angular_speed);
		}
	}
	
	public void draw()
	{
		sprite.setPosition(pos.x,pos.y);
		sprite.draw(Global.camera.batch);
	}
	
	public boolean isActive()
	{
		return active;
	}
}
