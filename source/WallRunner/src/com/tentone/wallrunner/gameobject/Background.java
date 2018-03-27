package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;

public class Background implements GameObject
{
	//Backgrounds Variables
	public Vector2 parallax,pos,size;
	public int repeat_mode;
	public Sprite sprite;
	public int texture;
	
	//Auxiliar Variables
	private float x,y,a,b,c;
	
	public Background(int texture,float pos_x, float pos_y,float size_x, float size_y, float parallax_x,float parallax_y, int repeat_mode)
	{
		parallax = new Vector2(parallax_x,parallax_y);
		pos = new Vector2(pos_x,pos_y);
		size = new Vector2(size_x,size_y);
		
		this.texture=texture;
		this.repeat_mode=repeat_mode; //0 -> none | 1-> XY | 2->X | 3-> Y
		
		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos.x,pos.y);
		sprite.setSize(size.x,size.y);
	}
	
	public static Background createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new Background(Integer.parseInt(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Integer.parseInt(temp[7]));
	}
	
	public String dataString()
	{
		return texture+"|"+pos.x+"|"+pos.y+"|"+size.x+"|"+size.y+"|"+parallax.x+"|"+parallax.y+"|"+repeat_mode;
	}
	
	//Draw background into Global.camera.batch
	public void draw()
	{
		//Origin camera positiion on left botom of screen (a,b)
		a=Global.camera.pos.x-Global.camera.size_half.x*Global.camera.getZoom();
		b=Global.camera.pos.y-Global.camera.size_half.y*Global.camera.getZoom(); 
		
		//Calculate First background Draw Position from a and b
		if(pos.x>a)
		{
			x=a-(size.x-((pos.x-a*parallax.x)%size.x));
		}
		else
		{
			x=a-((a*parallax.x-pos.x)%size.x);
		}

		if(pos.y>b)
		{

			y=b-(size.y-((pos.y-b*parallax.y)%size.y));
		}
		else
		{
			y=b-((b*parallax.y-pos.y)%size.y);
		}

		//Calculate Draw Limit Reusing a and b
		a=a+Global.camera.size.x*Global.camera.getZoom();
		b=b+Global.camera.size.y*Global.camera.getZoom();
		
		//BackGround Draw Loop
		if(repeat_mode==0)//Dont Repeat
		{
			sprite.setPosition(pos.x,pos.y);
			sprite.draw(Global.camera.batch);
		}
		else if(repeat_mode==1) //Repeat XY
		{
			c=x;
			while(y<=b)
			{
				x=c;
				while(x<a)
				{
					sprite.setPosition(x,y);
					sprite.draw(Global.camera.batch);
					x+=size.x;
				}
				y+=size.y;
			}
		}
		else if(repeat_mode==2)//Repeat X
		{
			while(x<a)
			{
				sprite.setPosition(x,pos.y-Global.camera.pos.y*parallax.y);
				sprite.draw(Global.camera.batch);
				x+=size.x;
			}
		}
		else if(repeat_mode==3)//Repeat Y
		{
			while(y<b)
			{
				sprite.setPosition(pos.x-Global.camera.pos.x*parallax.x,y);
				sprite.draw(Global.camera.batch);
				y+=size.y;
			}
		}
		
	}

	public void update(){}
	
	public boolean isActive()
	{
		return false;
	}
}
