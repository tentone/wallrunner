package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.input.Gamepad;
import com.tentone.wallrunner.physics.ColisionBox;
import com.tentone.wallrunner.tool.STimer;

public class Player extends ColisionBox implements GameObject
{
	public int texture;
	public Sprite sprite;
	public Particle death_part;
	public boolean is_on_ground,can_double_jump,die_out_of_level;
	public STimer is_alive;
	
	//Control Variable
	private float gravity,friction,wall_friction;
	private int wall_bounce; //1-> L | 2->R
	private int can_wall_jump; //0-> can't jump | 1->left wall jump | 2->Right wall jump | 3 -> left wall bounce | 4 -> right wall bounce
	
	public Player(float pos_x, float pos_y, boolean die_out_of_level)
	{
		super(Type.Box,pos_x,pos_y,40f,40f,0f,0f,0f,0f);
		
		texture=0;

		this.die_out_of_level=die_out_of_level;
		
		//Gravity and Friction Values
		gravity=0.6f;
		friction=0.89f;
		wall_friction=0.91f;

		//Control Variables
		is_alive=new STimer(1f/60f); //Timer to control player dead
		is_on_ground=false;
		can_double_jump=true;
		wall_bounce=0;
		can_wall_jump=0;
		
		//Sprite
		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos_x, pos_y);
		sprite.setSize(40,40);
		sprite.setOrigin(0,0);
		
		//Death Particle
		death_part = new Particle(2,0f,0f,0f,0f,16f,16f,0f,360f,50,120,30,3,2,0,0,true,false);
	}
	
	public static Player createFromDataString(String data)
	{
		return new Player(Global.player_spawn.x,Global.player_spawn.y,data.equals("true"));
	}
	
	public String dataString()
	{
		return die_out_of_level+"";
	}
	
	public boolean isActive()
	{
		return is_alive.value();
	}
	
	//Updates Player movement
	public void update()
	{
		int j;
		
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
				speed.x+=2.4f;
			}
			if(Gamepad.left_pressed)
			{
				wall_bounce=0;
				speed.x-=2.4f;
			}

			if(Gamepad.jump_pressed && is_on_ground)
			{
				speed.y+=15f;
			}
			else if(Gamepad.jump_pressed && Gamepad.jump_was_released)
			{
				if(can_wall_jump==1)//Wall Jump Left
				{
					speed.x=25f;
					speed.y=20f;
				}
				else if(can_wall_jump==2)//wall Jump Right
				{
					speed.x=-25f;
					speed.y=20f;

				}
				else if(can_wall_jump==3)//Wall Bounce Left
				{
					wall_bounce=1;
					speed.x=15f;
					speed.y=20f;
				}
				else if(can_wall_jump==4)//wall Bounce Right
				{
					wall_bounce=2;
					speed.x=-15f;
					speed.y=20f;
				}
				else if(can_double_jump)
				{
					if(speed.y>5f)
					{
						speed.y+=12f;
					}
					else
					{
						speed.y=12f;
					}
					can_double_jump=false;
				}
			}
	
			//Reset Jump Controll flags
			is_on_ground=false;
			can_wall_jump=0;
	
			//Update Y Gravity speed
			speed.y-=gravity;
	
			//Limit Speed on Y
			if(speed.y>60f)
			{
				speed.y=60f;
			}
			else if(speed.y<-30)
			{
				speed.y=-30;
			}
			
			//Add speed if wall_bounce was made
			if(wall_bounce==1)
			{
				speed.x+=2f;
			}
			else if(wall_bounce==2)
			{
				speed.x-=2f;
			}
			
			//Update Player Position and process colisions
			j=0;
			while(j<Global.wall.length)
			{
				//If its already colliding probably a wall as moved to it so lets move player as well
				if(isColliding(Global.wall[j]))
				{
					pos.add(Global.wall[j].speed);
				}
				
				//Process X movement and colisions
				if(willCollide(Global.wall[j],speed.x,0f))
				{
					wall_bounce=0;
					
					pos.x+=Global.wall[j].speed.x;
					if(speed.x<0)//Left Colision
					{
						pos.x=Global.wall[j].pos.x+Global.wall[j].col_box.x;
						speed.x=0;
	
						if(Gamepad.left_pressed)
						{
							speed.y=speed.y*wall_friction;
							can_wall_jump=1;
						}
					}
					else if(speed.x>0)//Rigth Colision
					{
						pos.x=Global.wall[j].pos.x-col_box.x;	
						speed.x=0;
	
						if(Gamepad.rigth_pressed)
						{
							speed.y=speed.y*wall_friction;
							can_wall_jump=2;
						}
					}
				}
	
				//Process Y movement and coklisions
				if(willCollide(Global.wall[j],0,speed.y))
				{
					pos.x+=Global.wall[j].speed.x;
					if(speed.y<0) //Top Coklision
					{
						pos.y=Global.wall[j].pos.y+Global.wall[j].col_box.y;
						speed.y=0;
						wall_bounce=0;
						is_on_ground=true;
						can_double_jump=true;
					}
					else if(speed.y>0) //Bot Collision
					{
						pos.y=Global.wall[j].pos.y-col_box.y;
						speed.y=0;
					}
				}
					
				//Check if close to wall after correction and assign walljump-able state
				if(willCollide(Global.wall[j],-1.5f,0) && !Gamepad.left_pressed)
				{
					can_wall_jump=3;
					can_double_jump=false;
				}
				else if(willCollide(Global.wall[j],1.5f,0) && !Gamepad.rigth_pressed)
				{
					can_wall_jump=4;
					can_double_jump=false;
				}
				j++;
			}
	
			//Update X Speed with Friction
			speed.x*=friction;
			
			//Apply Correction to X Speed if close to 0
			if(speed.x<0.4f && speed.x>-0.4f)
			{
				speed.x=0;
			}
			
			//Update Player Position
			pos.add(speed);
	
			//Process Player Collisions with enemy
			j=0;
			while(j<Global.enemy.length)
			{
				if(isColliding(Global.enemy[j])) //Kill Player wait and restart level
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
			
			sprite.setPosition(pos.x,pos.y);
		}
	}
	
	//Draws player into Global.camera.batch
	public void draw()
	{
		if(Global.def_graphics_particles)
		{
			death_part.draw();
		}
		
		if(is_alive.value())
		{
			sprite.draw(Global.camera.batch);
		}
	}
	
	//Kill Player
	public void kill()
	{
		if(Global.def_graphics_particles)
		{
			death_part.burst(pos.x,pos.y);
		}
		
		pos.x=Global.player_spawn.x;
		pos.y=Global.player_spawn.y;
		speed.x=0;
		speed.y=0;
		wall_bounce=0;
		can_wall_jump=0;
		
		is_alive.start(1.5f);
	}
	
	//Checks if the player is outside the level
	public boolean isOutsideLevel()
	{
		return !(pos.y<(Global.level_ori.y+Global.level_size.y) && ((pos.y+col_box.y)>Global.level_ori.y) && (pos.x<(Global.level_ori.x+Global.level_size.x)) && ((pos.x+col_box.x)>Global.level_ori.x));
	}
	
	
	//Change gravity value
	public void setGravity(float gravity)
	{
		this.gravity=gravity;
	}
}
