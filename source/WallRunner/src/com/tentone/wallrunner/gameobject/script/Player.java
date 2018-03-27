package com.tentone.wallrunner.gameobject.script;

import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.Particle;
import com.tentone.wallrunner.gameobject.ScriptedObject;
import com.tentone.wallrunner.input.Gamepad;
import com.tentone.wallrunner.tool.STimer;

public class Player extends Script
{
	public Particle death_part;
	public boolean is_on_ground,can_double_jump,die_out_of_level;
	public STimer is_alive;
	
	//Control Variabls
	private float gravity,friction,wall_friction;
	private int wall_bounce; //1-> L | 2->R
	private int can_wall_jump; //0-> cant jump | 1->left wall jump | 2->Rigth wall jump | 3 -> left wall bounce | 4 -> right wall bounce
	
	public Player(ScriptedObject object)
	{
		super(object);

		//Death Particle
		death_part = new Particle(2,0f,0f,0f,0f,16f,16f,0f,360f,50,120,30,3,2,0,0,true,false);
	}

	@Override
	public int ID()
	{
		return 4;
	}
	
	@Override
	public void ini()
	{
		//Gravity and Friction Values
		gravity=0.6f;
		friction=0.89f;
		wall_friction=0.91f;

		//Control Variables
		die_out_of_level=false;
		is_alive=new STimer(1f/60f);
		is_on_ground=false;
		can_double_jump=true;
		wall_bounce=0;
		can_wall_jump=0;
	}
	
	//Updates Player movement
	public void update()
	{	
		if(Global.def_graphics_particles)
		{
			death_part.update();
		}
		
		is_alive.update();
		if(is_alive.value())
		{
			//Player Control input
			if(Gamepad.rigth_pressed)
			{
				wall_bounce=0;
				object.speed.x+=2.4f;
			}
			if(Gamepad.left_pressed)
			{
				wall_bounce=0;
				object.speed.x-=2.4f;
			}

			if(Gamepad.jump_pressed && is_on_ground)
			{
				object.speed.y+=15f;
			}
			else if(Gamepad.jump_pressed && Gamepad.jump_was_released)
			{
				if(can_wall_jump==1)//Wall Jump Left
				{
					object.speed.x=25f;
					object.speed.y=20f;
				}
				else if(can_wall_jump==2)//wall Jump Right
				{
					object.speed.x=-25f;
					object.speed.y=20f;

				}
				else if(can_wall_jump==3)//Wall Bounce Left
				{
					wall_bounce=1;
					object.speed.x=15f;
					object.speed.y=20f;
				}
				else if(can_wall_jump==4)//wall Bounce Right
				{
					wall_bounce=2;
					object.speed.x=-15f;
					object.speed.y=20f;
				}
				else if(can_double_jump)
				{
					if(object.speed.y>5f)
					{
						object.speed.y+=12f;
					}
					else
					{
						object.speed.y=12f;
					}
					can_double_jump=false;
				}
			}
	
			//Reset Jump Controll flags
			is_on_ground=false;
			can_wall_jump=0;
	
			//Update Y Gravity object.speed
			object.speed.y-=gravity;
	
			//Limit Speed on Y
			if(object.speed.y>60f)
			{
				object.speed.y=60f;
			}
			else if(object.speed.y<-30)
			{
				object.speed.y=-30;
			}
			
			//Add object.speed if wall_bounce was made
			if(wall_bounce==1)
			{
				object.speed.x+=2f;
			}
			else if(wall_bounce==2)
			{
				object.speed.x-=2f;
			}
			
			//Update Player Position and process colisions
			int j=0;
			while(j<Global.wall.length)
			{
				//If its already colliding probably a wall as moved to it so lets move player as well
				if(object.isColliding(Global.wall[j]))
				{
					object.pos.add(Global.wall[j].speed);
				}
				
				//Process X movement and colisions
				if(object.willCollide(Global.wall[j],object.speed.x,0f))
				{
					wall_bounce=0;
					
					object.pos.x+=Global.wall[j].speed.x;
					if(object.speed.x<0)//Left Colision
					{
						object.pos.x=Global.wall[j].pos.x+Global.wall[j].col_box.x;
						object.speed.x=0;
	
						if(Gamepad.left_pressed)
						{
							object.speed.y=object.speed.y*wall_friction;
							can_wall_jump=1;
						}
					}
					else if(object.speed.x>0)//Rigth Colision
					{
						object.pos.x=Global.wall[j].pos.x-object.col_box.x;	
						object.speed.x=0;
	
						if(Gamepad.rigth_pressed)
						{
							object.speed.y=object.speed.y*wall_friction;
							can_wall_jump=2;
						}
					}
				}
	
				//Process Y movement and coklisions
				if(object.willCollide(Global.wall[j],0,object.speed.y))
				{
					object.pos.x+=Global.wall[j].speed.x;
					if(object.speed.y<0) //Top Coklision
					{
						object.pos.y=Global.wall[j].pos.y+Global.wall[j].col_box.y;
						object.speed.y=0;
						wall_bounce=0;
						is_on_ground=true;
						can_double_jump=true;
					}
					else if(object.speed.y>0) //Bot Collision
					{
						object.pos.y=Global.wall[j].pos.y-object.col_box.y;
						object.speed.y=0;
					}
				}
					
				//Check if close to wall after correction and assign walljump-able state
				if(object.willCollide(Global.wall[j],-1.5f,0) && !Gamepad.left_pressed)
				{
					can_wall_jump=3;
					can_double_jump=false;
				}
				else if(object.willCollide(Global.wall[j],1.5f,0) && !Gamepad.rigth_pressed)
				{
					can_wall_jump=4;
					can_double_jump=false;
				}
				j++;
			}
	
			//Update X Speed with Friction
			object.speed.x*=friction;
			
			//Apply Correction to X Speed if close to 0
			if(object.speed.x<0.4f && object.speed.x>-0.4f)
			{
				object.speed.x=0;
			}
			
			//Update Player Position
			object.pos.add(object.speed);
	
			//Process Player Collisions with enemy
			j=0;
			while(j<Global.enemy.length)
			{
				if(object.isColliding(Global.enemy[j])) //Kill Player wait and restart level
				{
					kill();
					break;
				}
				j++;
			}
			
			if(die_out_of_level && isOutsideLevel())
			{
				kill();
			}
			
			object.sprite.setPosition(object.pos.x,object.pos.y);
		}
	}
	
	//Draws player into Global.camera.batch
	@Override
	public void draw()
	{
		if(Global.def_graphics_particles)
		{
			death_part.draw();
		}
		
		if(is_alive.value())
		{
			object.sprite.draw(Global.camera.batch);
		}
	}
	
	//Kill Player
	public void kill()
	{
		if(Global.def_graphics_particles)
		{
			death_part.burst(object.pos.x,object.pos.y);
		}
		
		object.pos.x=object.pos_ini.x;
		object.pos.y=object.pos_ini.y;
		object.speed.x=0;
		object.speed.y=0;
		wall_bounce=0;
		can_wall_jump=0;
		is_alive.start(1f);
	}
	
	//Checks if the player is outside the level
	public boolean isOutsideLevel()
	{
		return !(object.pos.y<(Global.level_ori.y+Global.level_size.y) && ((object.pos.y+object.col_box.y)>Global.level_ori.y) && (object.pos.x<(Global.level_ori.x+Global.level_size.x)) && ((object.pos.x+object.col_box.x)>Global.level_ori.x));
	}
	
	
	//Change gravity value
	public void setGravity(float gravity)
	{
		this.gravity=gravity;
	}
}
