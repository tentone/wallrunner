package com.tentone.wallrunner;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.tentone.wallrunner.editor.EditorInterface;
import com.tentone.wallrunner.game.Camera;
import com.tentone.wallrunner.gameobject.Decoration;
import com.tentone.wallrunner.gameobject.Enemy;
import com.tentone.wallrunner.gameobject.Light;
import com.tentone.wallrunner.gameobject.Particle;
import com.tentone.wallrunner.gameobject.ScriptedObject;
import com.tentone.wallrunner.gameobject.Wall;
import com.tentone.wallrunner.input.Gamepad;
import com.tentone.wallrunner.physics.ColisionBox;

public class MainLevelEditor implements ApplicationListener
{
	enum EditorMode{ObjectPlacing,BakgroundEditing,CollectionPlacing}
	
	//Overlay
	private static SpriteBatch overlayBatch;
	private static BitmapFont overlayFont;
	
	//Render canvas
	public static LwjglAWTCanvas canvas;
	
	//Level Editor Permanent Settings
	public static boolean reset_zoom_file;
	public static int fps_lock; //0 -> no lock | 1 -> 60fps | 2 -> 120fps
	
	//Level Editor Temporary Settings
	public static Vector2 grid_size;
	public static Vector2 grid_offset;
	public static boolean grid_on,snap_to_grid,avoid_overlaping,freeze_level,single_click_mode;
	public static boolean draw_col_box,draw_level_limit,draw_object_cursor,draw_trajectory_lines,draw_particle_emiter;
	
	//Editor State Controll Variables
	public static String[] working_layer_name = {"Wall","Enemy","Decoration Front","Decoration Back","Object","Particles","Light"};
	public static int working_layer; //0 -> Wall | 1->Enemy | 2->Decoration Front | 3 ->Decoration Back | 4-> Object | 5->Particle | 6->Light
	public static EditorMode editor_mode;
	
	//Level Editor Control Variables
	public static boolean is_file_open;
	public static String file_open;
	public static boolean test_mode; //Activates Player
	
	//Level Editor Temporary Game Objects
	public static Wall temp_wall;
	public static Decoration temp_decoration;
	public static Enemy temp_enemy;
	public static ScriptedObject temp_object;
	public static Particle temp_particle;
	public static Light temp_light;
	
	//Auxiliar variables
	private static float last_x,last_y;
	private static String argLevel;
	
	public MainLevelEditor(String arg)
	{
		argLevel=arg;
	}
	
	@Override
	public void create()
	{
		//Set Editor Mode True
		Global.editor_mode=true;
		
		//Load Data and Initialize Editor Camera
		Global.loadSettings();
		Global.loadData();
		Global.camera = new Camera(1200,Global.def_graphics_aspect_ratio,0,0,1f,false,0.4f,0.4f,false);
		
		//Initialize Gamepad
		Gamepad.ini();
		
		//Level Editor Settings
		fps_lock=0;
		reset_zoom_file=true;
		
		//Level Editor Control Variables
		grid_on=true;
		grid_offset= new Vector2(0,0);
		grid_size= new Vector2(32f,32f);
		snap_to_grid=true;
		avoid_overlaping=true;
		single_click_mode=false;
		
		//Info Draw Controll
		draw_level_limit=true;
		draw_col_box=true;
		draw_object_cursor=true;
		draw_trajectory_lines=true;
		draw_particle_emiter=true;
		
		//Editor Flow Controll
		working_layer=0;
		editor_mode=EditorMode.ObjectPlacing;
		freeze_level=true;
		test_mode=false;
		
		//File Handling
		is_file_open=false;
		file_open="";
		
		//Create new Level
		Global.newLevel();
		
		//InitializeEditor Objects
		temp_enemy=new Enemy(0f,0f,4,32f,32f,0,32f,32f,0f,0f,0f,0,0f,0f);
		temp_wall=new Wall(0f,0f,1,32f,32f,32f,32f,0f,0f,0f,0,0f,0f);
		temp_decoration=new Decoration(0f,0f,8,32f,32f,0f,0f,0f,0,0f,0f);
		temp_object=new ScriptedObject(0f,0f,1,32f,32f,0f,0f,32f,32f,0,"temp");
		temp_particle=new Particle(1,0f,0f,32f,32f,32f,32f,0,360,100,150,50,10,5,0,0,false,false);
		temp_light=new Light(0,0,256,true,true,true,true,true,1f,1f,1f,0.5f);
		
		//Ini Overlay, and Start Editor
		overlayIni();
		EditorInterface.start();

		//Create Input Processor to Handle Mouse Scrolling
		Gdx.input.setInputProcessor(new InputAdapter()
		{
			//Mouse Scroller
			public boolean scrolled(int amount)
			{
				try
				{
					Global.camera.setZoom(Global.camera.getZoom()+amount*0.07f);
					if(Global.camera.getZoom()<0.1f)
					{
						Global.camera.setZoom(0.1f);
					}
					else if(Global.camera.getZoom()>12)
					{
						Global.camera.setZoom(12f);
					}
					EditorInterface.updateLevelZoom();
				}
				catch(Exception e){}
				
				return false;
			}
			
			//Key Pressed Down
			public boolean keyDown(int key)
			{
				if(key==Input.Keys.PLUS)
				{
					if(working_layer<working_layer_name.length-1)
					{
						working_layer++;
					}
					EditorInterface.updateWorkingLayer();
				}
				else if(key==Input.Keys.MINUS)
				{
					if(working_layer>0)
					{
						working_layer--;
					}
					EditorInterface.updateWorkingLayer();
				}
				return false;
			}
		});
		
		//If some level received as argument try to open it
		if(argLevel!="")
		{
			try
			{
				Global.loadLevel(argLevel);
			}
			catch(Exception e)
			{
				Global.newLevel();
			}
		}
		
		//Update Timer
		Timer.schedule(new Task()
		{
			@Override
			public void run()
			{
				update();
			}
		},0f,1f/60f);
	}
	
	public void update()
	{
		//Update Touch (Mouse) Coordinates
		Global.updateTouch();
		
		//Update Level Editor State and Changes
		updateLevelEditorState();
		
		//Update game Logic if level running
		if(!freeze_level)
		{
			levelUpdate();
		}
	}
	
	//Render Loop
	@Override
	public void render()
	{
		//Level Render
		Global.draw();

		//Interface Elements Render
		if(grid_on)
		{
			drawGrid();
		}
		
		if(draw_col_box)
		{
			drawColisionBox();
		}
		if(draw_level_limit)
		{
			drawLevelLimit();
		}
		if(draw_object_cursor)
		{
			drawObjectCursor();
		}
		if(draw_trajectory_lines)
		{
			drawTrajectoryLines();
		}
		if(draw_particle_emiter)
		{
			drawParticleEmiter();
		}

		overlayDraw();
		
		if(fps_lock!=0)
		{
			try
			{
				if(fps_lock==1)
				{
					Thread.sleep(16);
				}
				else if(fps_lock==2)
				{
					Thread.sleep(8);
				}
			}
			catch(Exception e){}
		}
	}
	
	//Level Elements Update
	public static void levelUpdate()
	{
		int i=0;
		Global.time++;
		
		//Decoration Back
		i=0;
		while(i<Global.decoration_back.length)
		{
			Global.decoration_back[i].update();
			i++;
		}

		//Walls
		i=0;
		while(i<Global.wall.length)
		{
			Global.wall[i].update();
			i++;
		}

		//Player Movement
		if(test_mode)
		{
			Gamepad.updateInput();
			Global.player.update();
		}
		
		//Enemys
		i=0;
		while(i<Global.enemy.length)
		{
			Global.enemy[i].update();
			i++;
		}

		//Decoration Front
		i=0;
		while(i<Global.decoration_front.length)
		{
			Global.decoration_front[i].update();
			i++;
		}

		//Objects
		i=0;
		while(i<Global.object.length)
		{
			Global.object[i].update();
			i++;
		}
		
		//Particles
		if(Global.def_graphics_particles)
		{
			i=0;
			while(i<Global.particle.length)
			{
				Global.particle[i].update();
				i++;
			}
		}
	}

	//Camera Movement processing 
	public static void updateLevelEditorState()
	{
		//Camera Position Drag with mouse
		if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE))
		{
			Global.camera.pos.sub(((Global.camera.pos.x+(Global.getX()-Global.camera.size_half.x)*Global.camera.getZoom())-last_x),((Global.camera.pos.y+(Global.getY()-Global.camera.size_half.y)*Global.camera.getZoom())-last_y));
		}
		
		//Update Last Mouse Position to actual mouse position
		last_x=(Global.camera.pos.x+(Global.getX()-Global.camera.size_half.x)*Global.camera.getZoom());
		last_y=(Global.camera.pos.y+(Global.getY()-Global.camera.size_half.y)*Global.camera.getZoom());
		
		//Camera Control
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			Global.camera.pos.y+=12f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			Global.camera.pos.y-=12f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			Global.camera.pos.x+=12f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			Global.camera.pos.x-=12f;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.I))
		{
			if(Global.camera.getZoom()<12)
			{
				Global.camera.camera.zoom+=0.035f;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.K))
		{
			if(Global.camera.getZoom()>0.15)
			{
				Global.camera.camera.zoom-=0.035f;
			}
		}

		if(editor_mode==EditorMode.ObjectPlacing)
		{
			updateObjectPlacingMode();
		}

		Global.camera.updatePosition();
	}
	
	//Update Editor Status in Object Placing Mode
	public static void updateObjectPlacingMode()
	{
		//Mouse Clicks Handle
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Global.is_touched)//LEFT_MOUSE Click
		{
			float x = Global.camera.pos.x+((float)Global.touch_x-Global.camera.size_half.x)*Global.camera.getZoom();
			float y = Global.camera.pos.y+((float)Global.touch_y-Global.camera.size_half.y)*Global.camera.getZoom();
			
			//Calculate position near to grid if snap to grid is enabled
			if(snap_to_grid)
			{
				if(x<0)
				{
					x-=grid_size.x;
				}
				if(y<0)
				{
					y-=grid_size.y;
				}
				x=x-((x-grid_offset.x)%grid_size.x);
				y=y-((y-grid_offset.y)%grid_size.y);
			}
			
			//Mouse Click Left Events
			if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))//Resize Objects
			{
				if(working_layer==0)//Wall
				{
					Vector2 temp = new Vector2(x-temp_wall.pos.x,y-temp_wall.pos.y);
					if(temp.x>0 && temp.y>0)
					{
						temp_wall.sprite.setSize(temp.x,temp.y);
						temp_wall.col_box.set(temp.x,temp.y);
					}
					EditorInterface.updateWallElementsSize();
				}
				else if(working_layer==1)//Enemy
				{
					Vector2 temp = new Vector2(x-temp_enemy.pos.x,y-temp_enemy.pos.y);
					if(temp.x>0 && temp.y>0)
					{
						temp_enemy.sprite.setSize(temp.x,temp.y);
						temp_enemy.col_box.set(temp.x,temp.y);
						if(temp_enemy.colision_box_type==1)
						{
							temp_enemy.ori.set(temp_enemy.col_box.x/2f,temp_enemy.col_box.y/2f);
						}
					}
					EditorInterface.updateEnemyElementsSize();
				}
				else if(working_layer==2 || working_layer==3)//Decoration
				{
					Vector2 temp = new Vector2(x-temp_decoration.pos.x,y-temp_decoration.pos.y);
					if(temp.x>0 && temp.y>0)
					{
						temp_wall.sprite.setSize(temp.x,temp.y);
						temp_wall.col_box.set(temp.x,temp.y);
					}
					EditorInterface.updateDecorationElementsSize();
				}
				else if(working_layer==4)//Object
				{
					Vector2 temp = new Vector2(x-temp_object.pos.x,y-temp_object.pos.y);
					if(temp.x>0 && temp.y>0)
					{
						temp_object.sprite.setSize(temp.x,temp.y);
						temp_object.col_box.set(temp.x,temp.y);
					}
					EditorInterface.updateObjectElementsSize();
				}
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))//Define Player Spawn Position (CTRL_LEFT)
			{
				Global.player_spawn.x=x;
				Global.player_spawn.y=y;
				Global.player.pos.x=x;
				Global.player.pos.y=y;
				Global.player.sprite.setPosition(Global.player.pos.x,Global.player.pos.y);
				EditorInterface.updatePlayerSpawn();
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT))//Define Level Origin (SHIFT_LEFT)
			{
				Global.level_ori.set(x,y);
				EditorInterface.updateLevelSize();
			}
			else if((!single_click_mode || (single_click_mode&&Gdx.input.justTouched()))) //Place Blocks on Map
			{				
				if(working_layer==0) //Add new Wall
				{
					Wall temp = new Wall(x,y,temp_wall.texture,temp_wall.sprite.getWidth(),temp_wall.sprite.getHeight(),temp_wall.col_box.x,temp_wall.col_box.y,temp_wall.ori.x,temp_wall.ori.y,temp_wall.angular_speed,temp_wall.move_mode,temp_wall.move_limit,temp_wall.move_speed);
					if(!avoid_overlaping || !isOverlapingWall(temp))
					{
						Global.addGameWall(temp);
					}
				}
				else if(working_layer==1) //Add new Enemy
				{
					Enemy temp = new Enemy(x,y,temp_enemy.texture,temp_enemy.sprite.getWidth(),temp_enemy.sprite.getHeight(),temp_enemy.colision_box_type,temp_enemy.col_box.x,temp_enemy.col_box.y,temp_enemy.ori.x,temp_enemy.ori.y,temp_enemy.angular_speed,temp_enemy.move_mode,temp_enemy.move_limit,temp_enemy.move_speed);
					if(!isOverlapingEnemy(temp) || !avoid_overlaping)
					{
						Global.addGameEnemy(temp);
					}
				}
				else if(working_layer==2) //Add Decoration Front
				{
					Decoration temp = new Decoration(x,y,temp_decoration.texture,temp_decoration.sprite.getWidth(),temp_decoration.sprite.getHeight(),temp_decoration.ori.x,temp_decoration.ori.y,temp_decoration.angular_speed,temp_decoration.move_mode,temp_decoration.move_limit,temp_decoration.move_speed);
					if(!isOverlapingFront(temp) || !avoid_overlaping)
					{
						Global.addGameDecorFront(temp);
					}
				}
				else if(working_layer==3) //Add Decoration back
				{
					Decoration temp = new Decoration(x,y,temp_decoration.texture,temp_decoration.sprite.getWidth(),temp_decoration.sprite.getHeight(),temp_decoration.ori.x,temp_decoration.ori.y,temp_decoration.angular_speed,temp_decoration.move_mode,temp_decoration.move_limit,temp_decoration.move_speed);
					if(!isOverlapingBack(temp) || !avoid_overlaping)
					{
						Global.addGameDecorBack(temp);
					}
				}
				else if(working_layer==4) //Add Object
				{
					ScriptedObject temp = new ScriptedObject(x,y,temp_object.texture,temp_object.sprite.getWidth(),temp_object.sprite.getHeight(),temp_object.ori.x,temp_object.ori.y,temp_object.col_box.x,temp_object.col_box.y,temp_object.script,temp_object.id);
					if(!isOverlapingObject(temp) || !avoid_overlaping)
					{
						Global.addGameObject(temp);
						EditorInterface.updateObjectList();
					}
				}
				else if(working_layer==5) //Particle Emiter
				{
					Particle temp = Particle.createFromDataString(temp_particle.dataString());
					temp.pos.set(x,y);
					if(!isOverlapingParticle(temp) || !avoid_overlaping)
					{
						Global.addGamePart(temp);
						EditorInterface.updateParticleList();
					}
				}
				else if(working_layer==6)
				{
					Light temp = Light.createFromDataString(temp_light.dataString());
					temp.pos.set(x,y);
					if(!isOverlapingLight(temp)||!avoid_overlaping)
					{
						Global.addLight(temp);
					}
				}
			}
		}
		
		//Delete Blocks on Map(MouseRight)
		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && Global.is_touched)//RIGHT_MOUSE Click
		{
			float x = Global.camera.pos.x+(Global.touch_x-Global.camera.size_half.x)*Global.camera.getZoom();
			float y = Global.camera.pos.y+(Global.touch_y-Global.camera.size_half.y)*Global.camera.getZoom();
			
			
			ColisionBox point = new ColisionBox(ColisionBox.Type.Point,x,y,0,0,0,0,0,0);
			
			if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT))//Define Level Origin (SHIFT_LEFT)
			{
				if(snap_to_grid)
				{
					if(x<0)
					{
						x-=grid_size.x;
					}
					if(y<0)
					{
						y-=grid_size.y;
					}
					x=x-((x-grid_offset.x)%grid_size.x);
					y=y-((y-grid_offset.y)%grid_size.y);
				}
				
				Global.level_size.set(x-Global.level_ori.x,y-Global.level_ori.y);
				EditorInterface.updateLevelSize();
			}
			else if((!single_click_mode || (single_click_mode&&Gdx.input.justTouched())))
			{
				if(working_layer==0)//Delete Wall
				{
					int i=0;
					while(i<Global.wall.length)
					{
						if(Global.wall[i].isColliding(point))
						{
							Global.removeWall(i);
						}
						i++;
					}
				}
				else if(working_layer==1) //Delete Enemy
				{
					int i=0;
					while(i<Global.enemy.length)
					{
						if(Global.enemy[i].isColliding(point))
						{
							Global.removeEnemy(i);
						}
						i++;
					}
				}
				else if(working_layer==2) //Delete Decor Front
				{
					int i=0;
					while(i<Global.decoration_front.length)
					{
						if(Global.decoration_front[i].isColliding(point))
						{
							Global.removeDecorFront(i);
						}	
						i++;
					}
				}
				else if(working_layer==3) //Delete Decoration Back
				{
					int i=0;
					while(i<Global.decoration_back.length)
					{
						if(Global.decoration_back[i].isColliding(point))
						{
							Global.removeDecorBack(i);
						}
						i++;
					}
				}
				else if(working_layer==4)//Delete Object
				{
					int i=0;
					while(i<Global.object.length)
					{
						if(Global.object[i].isColliding(point))
						{
							Global.removeObject(i);
						}
						i++;
					}
					EditorInterface.updateObjectList();
				}
				else if(working_layer==5)//Delete Particle
				{
					int i=0;
					while(i<Global.particle.length)
					{
						if(Global.particle[i].isColliding(point))
						{
							Global.removeParticle(i);
						}
						i++;
					}
					EditorInterface.updateParticleList();
				}
				else if(working_layer==6)//Delete Light
				{
					int i=0;
					while(i<Global.light.length)
					{
						if(Global.light[i].isColliding(point))
						{
							Global.removeLight(i);
						}
						i++;
					}
				}
			}
		}
		
		//Update Temporary Particle if needed
		if(working_layer==5)
		{
			temp_particle.update();
		}
	}
	
	public static boolean isOverlapingLight(Light a)
	{
		int i=0;
		
		while(i<Global.light.length)
		{
			if(a.isColliding(Global.light[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	//Return if a objectc is overlaping some other object
	public static boolean isOverlapingWall(Wall a)
	{
		int i=0;
		
		while(i<Global.wall.length)
		{
			if(a.isColliding(Global.wall[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isOverlapingObject(ScriptedObject a)
	{
		int i=0;
		while(i<Global.object.length)
		{
			if(a.isColliding(Global.object[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	//Checks is cursor is overlaping particle emitter
	public static boolean isOverlapingParticle(Particle a)
	{
		int i=0;
		
		while(i<Global.particle.length)
		{
			if(a.isColliding(Global.particle[i]))
			{
				return true;
			}
			i++;
		}
		
		return false;
	}
	
	public static boolean isOverlapingFront(Decoration a)
	{
		int i=0;
		
		while(i<Global.decoration_front.length)
		{
			if(a.isColliding(Global.decoration_front[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isOverlapingBack(Decoration a)
	{
		int i=0;
		
		while(i<Global.decoration_back.length)
		{
			if(a.isColliding(Global.decoration_back[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isOverlapingEnemy(Enemy a)
	{
		int i=0;
		
		while(i<Global.enemy.length)
		{
			if(a.isColliding(Global.enemy[i]))
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	//Draw Grid
	public static void drawGrid()
	{
		ShapeRenderer shape = new ShapeRenderer();
		Float x = (Global.camera.pos.x-Global.camera.size_half.x*Global.camera.getZoom());
		Float y = (Global.camera.pos.y-Global.camera.size_half.y*Global.camera.getZoom());
		Float a,b;
		x=x-((x-grid_offset.x)%grid_size.x)-grid_size.x;
		y=y-((y-grid_offset.y)%grid_size.y)-grid_size.y;

		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.setColor(Color.GRAY);
		shape.begin(ShapeType.Line);
	
		a=x;
		while(a<Global.camera.pos.x+Global.camera.size_half.x*Global.camera.getZoom())
		{
			shape.line(a,y,a,Global.camera.pos.y+Global.camera.size_half.y*Global.camera.getZoom());
			a+=grid_size.x;
		}

		b=y;
		while(b<Global.camera.pos.y+Global.camera.size_half.y*Global.camera.getZoom())
		{
			shape.line(x,b,Global.camera.pos.x+Global.camera.size_half.x*Global.camera.getZoom(),b);
			b+=grid_size.y;
		}

		shape.end();
		shape.dispose();
	}
	
	//Draw Level Borders
	public static void drawLevelLimit()
	{
		ShapeRenderer shape = new ShapeRenderer();
		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.setColor(Color.GREEN);
		shape.begin(ShapeType.Line);
		shape.rect(Global.level_ori.x, Global.level_ori.y, Global.level_size.x,Global.level_size.y);
		shape.end();
		shape.dispose();
	}
	
	//Draw Current Object as mouse cursor
	public static void drawObjectCursor()
	{
		if(!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) //Only show cursor if CTRL or SHIFT key is not pressed
		{
			float x = (Global.camera.pos.x+(Global.getX()-Global.camera.size_half.x)*Global.camera.getZoom());
			float y = (Global.camera.pos.y+(Global.getY()-Global.camera.size_half.y)*Global.camera.getZoom());
			
			if(snap_to_grid)
			{
				if(x<0)
				{
					x-=grid_size.x;
				}
				if(y<0)
				{
					y-=grid_size.y;
				}
				x=x-((x-grid_offset.x)%grid_size.x);
				y=y-((y-grid_offset.y)%grid_size.y);
			}
			
			Global.camera.startFrame();
			
			if(working_layer==0)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_wall.pos.set(x,y);
					temp_wall.pos_ini.set(x,y);
					temp_wall.sprite.setPosition(x,y);
				}
				temp_wall.sprite.setAlpha(0.5f);
				temp_wall.draw();
			}
			else if(working_layer==1)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_enemy.pos.set(x,y);
					temp_enemy.pos_ini.set(x,y);
					temp_enemy.sprite.setPosition(x,y);
				}
				temp_enemy.sprite.setAlpha(0.5f);
				temp_enemy.draw();
			}
			else if(working_layer==2 || working_layer==3)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_decoration.pos.set(x,y);
					temp_decoration.pos_ini.set(x,y);
					temp_decoration.sprite.setPosition(x,y);
				}
				temp_decoration.sprite.setAlpha(0.5f);
				temp_decoration.draw();
			}
			else if(working_layer==4)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_object.pos.set(x,y);
					temp_object.pos_ini.set(x,y);
					temp_object.sprite.setPosition(x,y);
				}
				temp_object.sprite.setAlpha(0.5f);
				temp_object.draw();
				if(!temp_object.script_object.visible)
				{
					ShapeRenderer shape = new ShapeRenderer();
					shape.setProjectionMatrix(Global.camera.camera.combined);
					shape.setColor(Color.ORANGE);
					shape.begin(ShapeType.Line);
					temp_object.drawColisionBox(shape);
					shape.end();
					shape.dispose();
				}
			}
			else if(working_layer==5)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_particle.pos.set(x, y);
				}
				temp_particle.draw();
			}
			else if(working_layer==6)
			{
				if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
				{
					temp_light.pos.set(x,y);
				}
				temp_light.draw();
			}
			Global.camera.endFrame();
		}
	}
	
	//Draw Particle Emiter (Purple)
	public static void drawParticleEmiter()
	{
		ShapeRenderer shape = new ShapeRenderer();
		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.setColor(Color.PURPLE);
		shape.begin(ShapeType.Line);
		
		int i=0;
		while(i<Global.particle.length)
		{
			Global.particle[i].drawColisionBox(shape);
			i++;
		}
		
		shape.end();
		shape.dispose();
	}
	
	//Draw Colision Boxes
	public static void drawColisionBox()
	{
		int j=0;
		ShapeRenderer shape = new ShapeRenderer();
		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.begin(ShapeType.Line);
		
		//Wall Colision Box
		shape.setColor(Color.BLUE);
		j=0;
		while(j<Global.wall.length)
		{
			Global.wall[j].drawColisionBox(shape);
			j++;
		}
		
		//Enemy Colision Box
		shape.setColor(Color.RED);
		j=0;
		while(j<Global.enemy.length)
		{
			Global.enemy[j].drawColisionBox(shape);
			j++;
		}
		
		//Custom Object Colision Box
		shape.setColor(Color.ORANGE);
		j=0;
		while(j<Global.object.length)
		{
			Global.object[j].drawColisionBox(shape);
			j++;
		}
		
		//Light Range Box
		shape.setColor(Color.PINK);
		j=0;
		while(j<Global.light.length)
		{
			Global.light[j].drawColisionBox(shape);
			j++;
		}
		
		shape.end();
		shape.dispose();
	}
	
	//Draw Objects Trajectory Lines (Yellow)
	public static void drawTrajectoryLines()
	{
		ShapeRenderer shape = new ShapeRenderer();
		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.setColor(Color.YELLOW);
		shape.begin(ShapeType.Line);
		
		if(working_layer==0)
		{
			if(temp_wall.move_mode==1)
			{
				shape.line(temp_wall.pos.x,temp_wall.pos.y+temp_wall.ori.y,temp_wall.pos.x+temp_wall.ori.x+temp_wall.move_limit,temp_wall.pos.y+temp_wall.ori.y);
			}
			else if(temp_wall.move_mode==2)
			{
				shape.line(temp_wall.pos.x+temp_wall.ori.x,temp_wall.pos.y+temp_wall.ori.y,temp_wall.pos.x+temp_wall.ori.x,temp_wall.pos.y+temp_wall.ori.y+temp_wall.move_limit);
			}
			else if(temp_wall.move_mode==3)
			{
				float  temp = temp_wall.move_limit;
				if(temp<0)
				{
					temp*=-1f;
				}
				shape.circle(temp_wall.pos.x+temp_wall.ori.x-temp_wall.move_limit, temp_wall.pos.y+temp_wall.ori.y,temp);
			}
		}
		else if(working_layer==1)
		{
			if(temp_enemy.move_mode==1)
			{
				shape.line(temp_enemy.pos.x,temp_enemy.pos.y+temp_enemy.ori.y,temp_enemy.pos.x+temp_enemy.ori.x+temp_enemy.move_limit,temp_enemy.pos.y+temp_enemy.ori.y);
			}
			else if(temp_enemy.move_mode==2)
			{
				shape.line(temp_enemy.pos.x+temp_enemy.ori.x,temp_enemy.pos.y+temp_enemy.ori.y,temp_enemy.pos.x+temp_enemy.ori.x,temp_enemy.pos.y+temp_enemy.ori.y+temp_enemy.move_limit);
			}
			else if(temp_enemy.move_mode==3)
			{
				float  temp = temp_enemy.move_limit;
				if(temp<0)
				{
					temp*=-1f;
				}
				shape.circle(temp_enemy.pos.x+temp_enemy.ori.x-temp_enemy.move_limit, temp_enemy.pos.y+temp_enemy.ori.y,temp);
			}
		}
		else if(working_layer==2 || working_layer==3)
		{
			if(temp_decoration.move_mode==1)
			{
				shape.line(temp_decoration.pos.x,temp_decoration.pos.y+temp_decoration.ori.y,temp_decoration.pos.x+temp_decoration.ori.x+temp_decoration.move_limit,temp_decoration.pos.y+temp_decoration.ori.y);
			}
			else if(temp_decoration.move_mode==2)
			{
				shape.line(temp_decoration.pos.x+temp_decoration.ori.x,temp_decoration.pos.y+temp_decoration.ori.y,temp_decoration.pos.x+temp_decoration.ori.x,temp_decoration.pos.y+temp_decoration.ori.y+temp_decoration.move_limit);
			}
			else if(temp_decoration.move_mode==3)
			{
				float  temp = temp_decoration.move_limit;
				if(temp<0)
				{
					temp*=-1f;
				}
				shape.circle(temp_decoration.pos.x+temp_decoration.ori.x-temp_decoration.move_limit, temp_decoration.pos.y+temp_decoration.ori.y,temp);
			}
		}
		
		int i=0;
		while(i<Global.wall.length)
		{
			if(Global.wall[i].move_mode==1)
			{
				shape.line(Global.wall[i].pos_ini.x,Global.wall[i].pos_ini.y+Global.wall[i].ori.y,Global.wall[i].pos_ini.x+Global.wall[i].ori.x+Global.wall[i].move_limit,Global.wall[i].pos_ini.y+Global.wall[i].ori.y);
			}
			else if(Global.wall[i].move_mode==2)
			{
				shape.line(Global.wall[i].pos_ini.x+Global.wall[i].ori.x,Global.wall[i].pos_ini.y+Global.wall[i].ori.y,Global.wall[i].pos_ini.x+Global.wall[i].ori.x,Global.wall[i].pos_ini.y+Global.wall[i].ori.y+Global.wall[i].move_limit);
			}
			else if(Global.wall[i].move_mode==3)
			{
				shape.circle(Global.wall[i].pos_ini.x+Global.wall[i].ori.x-Global.wall[i].move_limit, Global.wall[i].pos_ini.y+Global.wall[i].ori.y,Global.wall[i].move_limit);
			}
			i++;
		}
		
		i=0;
		while(i<Global.enemy.length)
		{
			if(Global.enemy[i].move_mode==1)
			{
				shape.line(Global.enemy[i].pos_ini.x,Global.enemy[i].pos_ini.y+Global.enemy[i].ori.y,Global.enemy[i].pos_ini.x+Global.enemy[i].ori.x+Global.enemy[i].move_limit,Global.enemy[i].pos_ini.y+Global.enemy[i].ori.y);
			}
			else if(Global.enemy[i].move_mode==2)
			{
				shape.line(Global.enemy[i].pos_ini.x+Global.enemy[i].ori.x,Global.enemy[i].pos_ini.y+Global.enemy[i].ori.y,Global.enemy[i].pos_ini.x+Global.enemy[i].ori.x,Global.enemy[i].pos_ini.y+Global.enemy[i].ori.y+Global.enemy[i].move_limit);
			}
			else if(Global.enemy[i].move_mode==3)
			{
				shape.circle(Global.enemy[i].pos_ini.x+Global.enemy[i].ori.x-Global.enemy[i].move_limit, Global.enemy[i].pos_ini.y+Global.enemy[i].ori.y,Global.enemy[i].move_limit);
			}
			i++;
		}
		
		i=0;
		while(i<Global.decoration_front.length)
		{
			if(Global.decoration_front[i].move_mode==1)
			{
				shape.line(Global.decoration_front[i].pos_ini.x,Global.decoration_front[i].pos_ini.y+Global.decoration_front[i].ori.y,Global.decoration_front[i].pos_ini.x+Global.decoration_front[i].ori.x+Global.decoration_front[i].move_limit,Global.decoration_front[i].pos_ini.y+Global.decoration_front[i].ori.y);
			}
			else if(Global.decoration_front[i].move_mode==2)
			{
				shape.line(Global.decoration_front[i].pos_ini.x+Global.decoration_front[i].ori.x,Global.decoration_front[i].pos_ini.y+Global.decoration_front[i].ori.y,Global.decoration_front[i].pos_ini.x+Global.decoration_front[i].ori.x,Global.decoration_front[i].pos_ini.y+Global.decoration_front[i].ori.y+Global.decoration_front[i].move_limit);
			}
			i++;
		}
		
		i=0;
		while(i<Global.decoration_back.length)
		{
			if(Global.decoration_back[i].move_mode==1)
			{
				shape.line(Global.decoration_back[i].pos_ini.x,Global.decoration_back[i].pos_ini.y+Global.decoration_back[i].ori.y,Global.decoration_back[i].pos_ini.x+Global.decoration_back[i].ori.x+Global.decoration_back[i].move_limit,Global.decoration_back[i].pos_ini.y+Global.decoration_back[i].ori.y);
			}
			else if(Global.decoration_back[i].move_mode==2)
			{
				shape.line(Global.decoration_back[i].pos_ini.x+Global.decoration_back[i].ori.x,Global.decoration_back[i].pos_ini.y+Global.decoration_back[i].ori.y,Global.decoration_back[i].pos_ini.x+Global.decoration_back[i].ori.x,Global.decoration_back[i].pos_ini.y+Global.decoration_back[i].ori.y+Global.decoration_back[i].move_limit);
			}
			i++;
		}
		shape.end();
		shape.dispose();
	}
	
	//Reset All Object Position
	public static void resetPosition()
	{
		Global.time=0;
		
		//Player Position Reset
		Global.player.pos.set(Global.player_spawn);
		Global.player.speed.set(0f,0f);
		Global.player.sprite.setPosition(Global.player.pos.x,Global.player.pos.y);
		
		//Wall Elements Reset
		int i=0;
		while(i<Global.wall.length)
		{
			Global.wall[i].pos.set(Global.wall[i].pos_ini);
			Global.wall[i].sprite.setPosition(Global.wall[i].pos.x, Global.wall[i].pos.y);
			Global.wall[i].sprite.setRotation(0f);
			i++;
		}
		
		//Enemy Reset
		i=0;
		while(i<Global.enemy.length)
		{
			Global.enemy[i].pos.set(Global.enemy[i].pos_ini);
			Global.enemy[i].sprite.setPosition(Global.enemy[i].pos.x, Global.enemy[i].pos.y);
			Global.enemy[i].sprite.setRotation(0f);
			i++;
		}
		
		//Decoration Front Reset
		i=0;
		while(i<Global.decoration_front.length)
		{
			Global.decoration_front[i].pos.set(Global.decoration_front[i].pos_ini);
			Global.decoration_front[i].sprite.setPosition(Global.decoration_front[i].pos.x, Global.decoration_front[i].pos.y);
			Global.decoration_front[i].sprite.setRotation(0f);
			i++;
		}
		
		//Decoration Back Reset
		i=0;
		while(i<Global.decoration_back.length)
		{
			Global.decoration_back[i].pos.set(Global.decoration_back[i].pos_ini);
			Global.decoration_back[i].sprite.setPosition(Global.decoration_back[i].pos.x, Global.decoration_back[i].pos.y);
			Global.decoration_back[i].sprite.setRotation(0f);
			i++;
		}
		
		//Object Reset
		i=0;
		while(i<Global.object.length)
		{
			Global.object[i].pos.set(Global.object[i].pos_ini);
			Global.object[i].sprite.setPosition(Global.object[i].pos.x,Global.object[i].pos.y);
			Global.object[i].sprite.setRotation(0f);
			Global.object[i].script_object.ini();
			i++;
		}
	}
	
	
	//Overlay Ini
	public static void overlayIni()
	{
		overlayBatch = new SpriteBatch();
		overlayFont = new BitmapFont();
		overlayFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		overlayFont.setScale(1f);
	}

	//Overlay Draw
	public static void overlayDraw()
	{
		overlayBatch.begin();
		overlayFont.draw(overlayBatch,"Objects Count",5f,Global.def_graphics_resolution_y-10f);
		overlayFont.draw(overlayBatch,"Wall:"+Global.wall.length,5f,Global.def_graphics_resolution_y-30f);
		overlayFont.draw(overlayBatch,"Enemy:"+Global.enemy.length,5f,Global.def_graphics_resolution_y-50f);
		overlayFont.draw(overlayBatch,"Decoration F:"+Global.decoration_front.length,5f,Global.def_graphics_resolution_y-70f);
		overlayFont.draw(overlayBatch,"Decoration B:"+Global.decoration_back.length,5f,Global.def_graphics_resolution_y-90f);
		overlayFont.draw(overlayBatch,"Particle Emiter:"+Global.particle.length,5f,Global.def_graphics_resolution_y-110f);
		overlayFont.draw(overlayBatch,"Background:"+Global.background.length,5f,Global.def_graphics_resolution_y-130f);
		overlayFont.draw(overlayBatch,"Object:"+Global.object.length,5f,Global.def_graphics_resolution_y-150f);
		
		overlayFont.draw(overlayBatch,"Screen Mode "+Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight(),5f,20f);
		overlayFont.draw(overlayBatch,"FPS "+Gdx.graphics.getFramesPerSecond(),5f,40f);
		overlayFont.draw(overlayBatch,"Zoom "+Global.camera.getZoom(),5f,60f);
		overlayFont.draw(overlayBatch,"Mouse X"+(Global.camera.pos.x+(Global.getX()-Global.camera.size_half.x)*Global.camera.getZoom())+", Y"+(Global.camera.pos.y+(Global.getY()-Global.camera.size_half.y)*Global.camera.getZoom()),5f,80f);
		overlayFont.draw(overlayBatch,"Grid Size "+grid_size.x+" x "+grid_size.y,5f,100f);
		overlayFont.draw(overlayBatch,"Working Layer "+working_layer_name[working_layer],5f,120f);
		overlayFont.draw(overlayBatch,"Snap to Grid "+snap_to_grid,5f,140f);
		overlayFont.draw(overlayBatch,"Avoid Overlaping "+avoid_overlaping,5f,160f);
		overlayBatch.end();
	}	
	
	//updates size of Render Windows Based on Editor Render Container Size
	@SuppressWarnings("deprecation")
	public static void updateSize()
	{
		canvas.getCanvas().setSize(EditorInterface.container.size().width,EditorInterface.container.size().height);
		Global.setDisplayMode(EditorInterface.container.size().width,EditorInterface.container.size().height, false);
	}
	
	
	//Executed when the windows is resized
	@Override
	public void resize(int width, int height)
	{
		Global.setDisplayMode(width,height,false);
		overlayBatch = new SpriteBatch();
	}
	
	//Others
	@Override
	public void pause(){}
	@Override
	public void resume(){}
	@Override
	public void dispose(){}
}
