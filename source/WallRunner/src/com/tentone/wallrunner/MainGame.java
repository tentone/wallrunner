package com.tentone.wallrunner;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.tentone.wallrunner.game.Camera;
import com.tentone.wallrunner.game.Menu;
import com.tentone.wallrunner.input.Gamepad;
import com.tentone.wallrunner.input.TactilGamepad;

public class MainGame implements ApplicationListener
{
	//Overlay Variables
	private static SpriteBatch overlayBatch;
	private static BitmapFont overlayFont;
	private static String argLevel;
	
	//TODO Remove this code
	/*private static Animation[] animation;
	private static TextureManager manager;*/
	
	public MainGame(String level)
	{
		argLevel=level;
	}
	
	@Override
	public void create()
	{
		//Set editor_mode flag to false
		Global.editor_mode=false;
		
		//Load Game Data
		Global.loadData();
		Global.loadSettings();
		Global.setDisplayMode(Global.def_graphics_resolution_x,Global.def_graphics_resolution_y,Global.def_graphics_fullscreen);

		//Ini Gamepad and TactilGamepad
		Gamepad.ini();
		TactilGamepad.ini();

		//Ini all debug stuff if debugmode is enabled
		if(Global.def_debugmode)
		{
			//Ini Debug Overlay
			debugOverlayIni();
			
			//Load External Level file if there is one
			if(!argLevel.equals(""))
			{
				try
				{
					Global.camera = new Camera(0,Global.def_graphics_aspect_ratio,0,0,0,false,0,0,false);
					Global.loadLevel(argLevel);
					Global.game_lifecycle_control=2;
					Global.setDisplayMode(Global.def_graphics_resolution_x,Global.def_graphics_resolution_y,Global.def_graphics_fullscreen);
				}
				catch(Exception e)
				{
					Gdx.app.exit();
				}
			}
		}
		
		//TODO Remove this code
		/*try
		{
			manager = new TextureManager("data/animation/knight");
			animation = new Animation[30];
			int i=0;
			while(i<animation.length)
			{
				animation[i] = new Animation(manager);
				animation[i].loadFile("data/animation/knigth_test.gsa");
				animation[i].setPosition(800f+i*600f,800f+i*600f);
				animation[i].setScale(8f);
				i++;
			}
		}
		catch (Exception e)
		{
			Gdx.app.exit();
			e.printStackTrace();
		}*/

		//Ini Menu
		Menu.ini();
		
		//Start the update Timer at fixed rate of 60Hz
		Timer.schedule(new Task()
		{
			@Override
			public void run()
			{
				update();
			}
		},0f,1f/60f);
	}
	
	public static void update()
	{
		Global.updateTouch();
		Gamepad.updateInput();
		
		if(Global.game_lifecycle_control==1) //Menu
		{
			Menu.update();
		}
		else if(Global.game_lifecycle_control==2) //Game
		{
			levelInput();
			if(!Global.game_paused)
			{
				levelUpdate();
			}
		}
	}
	
	@Override
	public void render()
	{	
		if(Global.game_lifecycle_control==1)//Menu
		{
			Menu.draw();
		}
		else if(Global.game_lifecycle_control==2) //Game
		{
			//Render Level
			Global.draw();
			
			//Render OnscreenGamepad
			if(Global.def_touchscreen)
			{
				TactilGamepad.draw();
			}
		}
		
		//Render Debug Overlay
		if(Global.def_debugmode)
		{
			updateDebugInput();
			debugOverlayDraw();
		}
	}
	
	//Level Elements Update
	public static void levelUpdate()
	{
		int i=0;
		
		//Update Game Time
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

		//Player Movement
		Global.player.update();

		//Camera Position Update
		Global.camera.update();
		
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

	//Game flow input 
	public static void levelInput()
	{
		if(Gamepad.pause_just_pressed)
		{
			Global.game_paused=!Global.game_paused;
		}
	}
	
	//Camera Movement processing 
	public static void updateDebugInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.F4)) //Toggle Fullscreen
		{
			if(!Global.def_graphics_fullscreen)
			{
				Global.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width,Gdx.graphics.getDesktopDisplayMode().height,!Global.def_graphics_fullscreen);
			}
			else
			{
				Global.setDisplayMode(1200,400,!Global.def_graphics_fullscreen);
			}

			if(Global.game_lifecycle_control==1)
			{
				Menu.ini();
			}
			
			debugOverlayIni();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP))
		{
			Global.game_lifecycle_control=2;
			if(Global.actual_level<Global.level_file.length-1)
			{
				Global.actual_level++;
			}
			else
			{
				Global.actual_level=0;
			}
			
			try
			{
				Global.loadLevel(Global.level_file[Global.actual_level]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Gdx.app.exit();
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) //Exit
		{
			Gdx.app.exit();
		}
	}
	
	public static void debugOverlayIni()
	{
		overlayBatch = new SpriteBatch();
		overlayFont = new BitmapFont();
		overlayFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		overlayFont.setScale(1f);
	}
	
	public static void debugOverlayDraw()
	{
		overlayBatch.begin();
		overlayFont.draw(overlayBatch,"Wallrunner V"+Global.version_game, 5f, 20f);
		overlayFont.draw(overlayBatch,"Screen Mode "+Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight(),5f,40f);
		overlayFont.draw(overlayBatch,"FPS "+Gdx.graphics.getFramesPerSecond(),5f,60f);
		if(Global.game_lifecycle_control==2)
		{
			overlayFont.draw(overlayBatch,"Player Position X:"+(int)Global.player.pos.x+" Y:"+(int)Global.player.pos.y,5f,80f);
			overlayFont.draw(overlayBatch,"Player Speed X:"+(int)Global.player.speed.x+" Y:"+(int)Global.player.speed.y,5f,100f);
			overlayFont.draw(overlayBatch,"Paused:"+Global.game_paused, 5f, 120f);
		}
		overlayBatch.end();
	}
	
	//Others
	@Override
	public void pause(){}
	@Override
	public void dispose(){}
	@Override
	public void resize(int width, int height){}
	@Override
	public void resume(){}
}
