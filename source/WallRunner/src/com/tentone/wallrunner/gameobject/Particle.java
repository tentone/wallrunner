package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.physics.ColisionBox;

public class Particle extends ColisionBox implements GameObject
{ 
	//Particle Emiter Variables
	public int texture;
	
	//Emiter Variables
	public Vector2 particle_size;
	public float particle_life_time,particle_life_time_variation;
	public float particle_speed, particle_speed_variation;
	public float particle_rotation_speed; //Texture Angular Speed
	public float particle_direction, particle_direction_var;
	public int particle_count;//Particle Count
	
	//Controll Variables
	public boolean one_burst_only,aditive_mode;

	//Particles Array
	private Vector2[] speed;
	private float[] direction,time;
	private Sprite[] sprite;
	private boolean[] isactive;

	//Auxiliar Variables
	private float x,y,temp;

	public Particle(int text,float pos_x,float pos_y,float box_x,float box_y,float size_x, float size_y, float dir_a,float dir_b,int part_count,float life_time,float life_time_var ,float speeed, float speed_var,float angular_speed,int type,boolean one_burst,boolean aditive_draw)
	{
		super(Type.Circle,pos_x,pos_y,box_x,box_y,0,0,0,0);
		
		if(type==0)//Point Emiter
		{
			this.col_box.x=10f;
			this.col_box.y=0f;
		}
		else if(type==1)//Box Emiter
		{
			this.type=Type.Box;
		}
		
		//Emiter Propieties
		particle_size= new Vector2(size_x,size_y);
		texture=text;
		particle_count=part_count;
		particle_life_time=life_time;
		particle_life_time_variation=life_time_var;
		particle_speed=speeed;
		particle_speed_variation=speed_var;
		particle_rotation_speed=angular_speed;
		one_burst_only=one_burst;
		particle_direction=dir_a;
		particle_direction_var=dir_b;
		aditive_mode=aditive_draw;

		//Ini Particle Data Arrays
		speed= new Vector2[part_count];
		time = new float[part_count];
		direction = new float[part_count];
		sprite = new Sprite[part_count];
		isactive = new boolean[part_count];

		//Calculate Emiter Center
		x=box_x/2f;
		y=box_y/2f;
		
		//Auxiliar Counter Var
		int i;
		
		if(this.type==Type.Circle) //Point Emiter
		{
			i=0;
			while(i<part_count)
			{
				temp=MathUtils.random(); //Generate Random Number to Calculate Position for Particle
				x=particle_speed+(MathUtils.random()*particle_speed_variation);
				direction[i]=particle_direction+(MathUtils.random()*particle_direction_var);
				speed[i]= new Vector2(x*MathUtils.cos(direction[i] * 0.017453f),x*MathUtils.sin(direction[i] * 0.017453f));
				time[i]=(particle_life_time+(((MathUtils.random()*2f)-1f)*particle_life_time_variation))*(temp);
				isactive[i]=!one_burst;
				sprite[i]=new Sprite(Global.texture[texture]);
				sprite[i].setRotation(0f);
				sprite[i].setPosition(pos.x+particle_life_time*speed[i].x*temp,pos.y+particle_life_time*speed[i].y*temp);
				sprite[i].setSize(size_x,size_y);
				sprite[i].setOrigin(x,y);
				i++;
			}
		}
		else if(this.type==Type.Box) //Rectangular Emiter
		{
			i=0;
			while(i<part_count)
			{
				temp=MathUtils.random(); //Generate Random Number to Calculate Position for Particle
				x=particle_speed+(MathUtils.random()*particle_speed_variation);
				direction[i]=particle_direction+(MathUtils.random()*particle_direction_var);
				speed[i]= new Vector2(x*MathUtils.cos(direction[i] * 0.017453f),x*MathUtils.sin(direction[i] * 0.017453f));
				time[i]=(particle_life_time+(((MathUtils.random()*2f)-1f)*particle_life_time_variation))*(temp);
				isactive[i]=!one_burst;
				sprite[i]=new Sprite(Global.texture[texture]);
				sprite[i].setRotation(0f);
				sprite[i].setPosition(pos.x+col_box.x*MathUtils.random()+particle_life_time*speed[i].x*temp,pos.y+col_box.y*MathUtils.random()+particle_life_time*speed[i].y*temp);
				sprite[i].setSize(size_x,size_y);
				sprite[i].setOrigin(x,y);
				i++;
			}
		}  
	}
	
	public String dataString()
	{
		int emiter_type=0;
		if(this.type==Type.Box)
		{
			emiter_type=1;
		}
		
		return texture+"|"+pos.x+"|"+pos.y+"|"+col_box.x+"|"+col_box.y+"|"+particle_size.x+"|"+particle_size.y+"|"+particle_direction+"|"+particle_direction_var+"|"+particle_count+"|"+particle_life_time+"|"+particle_life_time_variation+"|"+particle_speed+"|"+particle_speed_variation+"|"+particle_rotation_speed+"|"+emiter_type+"|"+one_burst_only+"|"+aditive_mode;
	}
	
	public static Particle createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new Particle(Integer.parseInt(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),Float.parseFloat(temp[8]),Integer.parseInt(temp[9]),Float.parseFloat(temp[10]),Float.parseFloat(temp[11]),Float.parseFloat(temp[12]),Float.parseFloat(temp[13]),Float.parseFloat(temp[15]),Integer.parseInt(temp[15]),temp[16].equals("true"),temp[17].equals("true"));
	}
	
	@Override
	public boolean isActive()
	{
		return false;
	}
	
	//Updates Particles Position
	public void update()
	{
		int i=0;
		
		while(i<particle_count)
		{
			if(isactive[i])//If particle is active
			{
				sprite[i].setRotation(sprite[i].getRotation()+particle_rotation_speed);
				sprite[i].setPosition(sprite[i].getX()+speed[i].x,sprite[i].getY()+speed[i].y);
				time[i]--;
				if(time[i]<0)
				{
					if(!one_burst_only)//Restart Particle with new settings
					{
						x=particle_speed+(MathUtils.random()*particle_speed_variation);
						direction[i]=particle_direction+(MathUtils.random()*particle_direction_var);
						speed[i]= new Vector2(x*MathUtils.cos(direction[i] * 0.017453f),x*MathUtils.sin(direction[i] * 0.017453f));
						time[i]=particle_life_time+(((MathUtils.random()*2f)-1f)*particle_life_time_variation);
						sprite[i].setRotation(0f);
						if(this.type==Type.Circle)
						{
							sprite[i].setPosition(pos.x,pos.y);
						}
						else if(this.type==Type.Box)
						{
							sprite[i].setPosition((pos.x+(MathUtils.random()*col_box.x)),(pos.y+(MathUtils.random()*col_box.y)));
						}
					}
					else//If on only_one_burst mode deactivate particle
					{
						isactive[i]=false;
					}
				}
			}
			i++;
		}
	}

	//Draw Particle into Global.camera.batch
	public void draw()
	{
		int i=0;
		
		//Aditive Mode Draw
		if(aditive_mode)
		{
			//Set Aditive blend
			Global.camera.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
			
			i=0;
			while(i<particle_count)
			{
				if(isactive[i])
				{
					sprite[i].draw(Global.camera.batch);
				}
				i++;
			}
			
			//Restore Blend Mode
			Global.camera.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		else
		{
			i=0;
			while(i<particle_count)
			{
				if(isactive[i])
				{
					sprite[i].draw(Global.camera.batch);
				}
				i++;
			}
		}
	}

	//Start new particle burst
	public void burst(float emiter_ori_x, float emiter_ori_y)
	{
		//Set new emiter origin
		pos.x=emiter_ori_x;
		pos.y=emiter_ori_y;
		
		int i=0;
		while(i<particle_count)
		{
			//Ini Particle
			x=particle_speed+(MathUtils.random()*particle_speed_variation);
			direction[i]=particle_direction+(MathUtils.random()*particle_direction_var);
			speed[i]= new Vector2(x*MathUtils.cos(direction[i] * 0.017453f),x*MathUtils.sin(direction[i] * 0.017453f));
			time[i]=particle_life_time+(((MathUtils.random()*2f)-1f)*particle_life_time_variation);
			sprite[i].setRotation(0f);
			isactive[i]=true;
			
			//Set Particles Position
			if(this.type==Type.Circle)
			{
				sprite[i].setPosition(pos.x,pos.y);
			}
			else if(this.type==Type.Box)
			{
				sprite[i].setPosition((pos.x+(MathUtils.random()*col_box.x)),(pos.y+(MathUtils.random()*col_box.y)));
			}
			
			i++;
		}
	}
}
