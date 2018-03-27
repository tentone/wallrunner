package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.physics.ColisionBox;

public class Enemy extends ColisionBox implements GameObject
{
	public static String[] move_mode_list = {"None","Horizontal Move","Vertical Move","Circular Move","Directional Move"};
	public Sprite sprite;
	
	public int texture;
	
	public int colision_box_type; // 0-> square / 1 -> circle col
	public int move_mode; //0 -> dont move / 1 -> move x / 2-> move y /3-> Circular Trajectory
	public float move_limit, move_speed, angular_speed;
	
	public boolean active, visible;
	
	public Enemy(float pos_x, float pos_y, int texture, float size_x, float size_y,int colision_box_type, float col_box_x,float col_box_y, float ori_x, float ori_y, float angular_speed,int move_mode, float move_limit,float move_speed)
	{
		super(Type.Box,pos_x,pos_y,col_box_x,col_box_y,ori_x,ori_y,0f,0f);
		pos_ini = new Vector2(pos_x,pos_y);
		
		if(colision_box_type==1)
		{
			type=Type.Circle;
		}
		
		this.texture=texture;
		this.colision_box_type=colision_box_type;
		this.angular_speed=angular_speed;
		this.move_mode=move_mode;
		this.move_limit=move_limit;
		this.move_speed=move_speed;
		
		if(move_mode==0 || move_mode==3)//Static or circular movement
		{
			speed = new Vector2(0,0);
		}
		else if(move_mode==1)//Horizontal Movement
		{
			speed = new Vector2(move_speed,0);
		}
		else if(move_mode==2)//Vertical Movement
		{
			speed = new Vector2(0,move_speed);
		}
		else if(move_mode==4)//Direction Movement
		{
			speed=new Vector2(move_speed*MathUtils.cos(move_limit),move_speed*MathUtils.sin(move_limit));
		}
		
		active=(move_mode!=0||angular_speed!=0);
		visible=true;

		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos_x, pos_y);
		sprite.setOrigin(ori_x,ori_y);
		sprite.setSize(size_x,size_y);
	}
	
	public static Enemy createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new Enemy(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Integer.parseInt(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Integer.parseInt(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),Float.parseFloat(temp[8]),Float.parseFloat(temp[9]),Float.parseFloat(temp[10]),Integer.parseInt(temp[11]),Float.parseFloat(temp[12]),Float.parseFloat(temp[13]));
	}
	
	public String dataString()
	{
		return pos_ini.x+"|"+pos_ini.y+"|"+texture+"|"+sprite.getWidth()+"|"+sprite.getHeight()+"|"+colision_box_type+"|"+col_box.x+"|"+col_box.y+"|"+ori.x+"|"+ori.y+"|"+angular_speed+"|"+move_mode+"|"+move_limit+"|"+move_speed;
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
			}
			else if(move_mode==4)
			{
				pos.x+=speed.x;
				pos.y+=speed.y;
			}
			
			sprite.rotate(angular_speed);
			sprite.setPosition(pos.x,pos.y);
		}
	}
	
	public void draw()
	{
		if(visible)
		{
			sprite.draw(Global.camera.batch);
		}
	}
	
	public boolean isActive()
	{
		return active;
	}
}
