package com.tentone.wallrunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.tentone.ganimator.TextureManager;
import com.tentone.wallrunner.game.Camera;
import com.tentone.wallrunner.game.Level;

public class Global extends Level
{
	//Game Version
	public static String version_game="0.0.8.1 Pre-Alpha";
	public static String version_editor="0.0.9.6 Pre-Alpha";
	
	//Touch Variables  
	public static boolean is_touched;
	public static float touch_x, touch_y;
	
	//Global Game Camera
	public static Camera camera;
	
	//Data Files Variables
	public static String[] level_file;
	public static String[] texture_set_file,texture_file;
	public static Texture[] texture;
	
	//Texture Set File
	public static TextureManager texture_manager;
	public static String[] animation_file;
	
	//Game Flow Variables
	public static int actual_level;
	public static int game_lifecycle_control;
	public static int time;
	public static boolean game_paused;
	
	//Settings Variables
	public static boolean def_debugmode=true;
	public static boolean def_touchscreen;
	public static boolean def_graphics_fullscreen,def_graphics_vsync;
	public static boolean def_graphics_particles;
	public static int def_graphics_resolution_x,def_graphics_resolution_y;
	public static float def_graphics_aspect_ratio;
	
	//Editor Controll Variables
	public static boolean editor_mode;
	
	//Actual texture_set in use
	public static int texture_set;
	
	//Load Game Settings
	@SuppressWarnings("incomplete-switch")
	public static void loadSettings()
	{
		//Detect Platform
		def_touchscreen=false;
		switch(Gdx.app.getType())
		{
	       case Android:
	       {
	           def_touchscreen=true;
	       }
	    }
		
		//Load Game Settings
		def_graphics_particles=true;
		def_graphics_vsync=false;
		def_graphics_fullscreen=true;
		def_graphics_resolution_x=Gdx.graphics.getDesktopDisplayMode().width;
		def_graphics_resolution_y=Gdx.graphics.getDesktopDisplayMode().height;
		def_graphics_aspect_ratio=def_graphics_resolution_x/def_graphics_resolution_y;
		
		//Set Game ini Vars
		game_lifecycle_control=1;
		game_paused=false;
	}
	
	//Load Game Assets
	public static void loadData()
	{
		//Level Files
		loadLevelFileList("data/level/list/default.list");
		
		//Textures Set Files
		texture_set_file= new String[1];
		texture_set_file[0]="data/texture/list/default.list";
		
		//Shader List (Vertex,Fragment)
		ShaderProgram.pedantic=false;
		shader = new ShaderProgram[3];
		
		//Ambient Color Shader
		shader[0] = new ShaderProgram(Gdx.files.internal("data/shaders/defaultVertexShader.glsl"),Gdx.files.internal("data/shaders/ambientPixelShader.glsl"));
		shader[0].begin();
		shader[0].setUniformf("ambientColor",0.3f,0.3f,0.7f,0.9f);
		shader[0].end();
		
		//Solarize Shader
		shader[1] = new ShaderProgram(Gdx.files.internal("data/shaders/defaultVertexShader.glsl"),Gdx.files.internal("data/shaders/solarizePixelShader.glsl"));
		shader[1].begin();
		shader[1].setUniformf("ambientColor",0.7f,0.5f,0.3f,0.8f);
		shader[1].end();

		//Vignette Shader
		shader[2] = new ShaderProgram(Gdx.files.internal("data/shaders/defaultVertexShader.glsl"),Gdx.files.internal("data/shaders/vignettePixelShader.glsl"));
	}
	
	//Load Level List from file
	public static void loadLevelFileList(String file_name)
	{
		BufferedReader reader;
		int length,i;
		
		try
		{
			reader = new BufferedReader(new InputStreamReader(Gdx.files.internal(file_name).read()));
			length=Integer.parseInt(reader.readLine());
			
			level_file=new String[length];
			i=0;
			while(i<length)
			{
				level_file[i]=reader.readLine();
				i++;
			}
		}
		catch(Exception e)
		{
			Gdx.app.exit();
		}
	}
	
	//Load Texture List from external file
	public static void loadTextureSetFile(String file_name)
	{
		BufferedReader reader;
		int length,i;
		
		//Dispose all textures before load new ones
		if(texture!=null)
		{
			i=0;
			while(i<texture.length)
			{
				texture[i].dispose();
				i++;
			}
		}
		
		try
		{
			reader = new BufferedReader(new InputStreamReader(Gdx.files.internal(file_name).read()));
			length=Integer.parseInt(reader.readLine());
			
			texture_file=new String[length];
			texture = new Texture[length];
			
			i=0;
			while(i<length)
			{
				texture_file[i]=reader.readLine();
				try
				{
					texture[i] = new Texture(Gdx.files.internal(texture_file[i]));
					texture[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
				}
				catch(Exception e)
				{
					//If cant load the texture create a block and use that as texture
					texture[i] = new Texture(new Pixmap(1,1,Pixmap.Format.RGB888));
				}
				i++;
			}
			
			texture_set=-1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	//Update Touch/Mouse Input relative to game camera
	public static void updateTouch()
	{
		if(Gdx.input.isTouched())
		{
			is_touched=true;
			touch_x=Gdx.input.getX()*camera.size.y/def_graphics_resolution_y;
			touch_y=camera.size.y-(Gdx.input.getY()*camera.size.y/def_graphics_resolution_y);
		}
		else
		{
			is_touched=false;
		}
	}
	
	//Returns actual X mouse pointer position relative to game camera
	public static float getX()
	{
		return Gdx.input.getX()*camera.size.y/def_graphics_resolution_y;
	}
	
	//Returns actual Y mouse pointer position relative to game camera
	public static float getY()
	{
		return camera.size.y-(Gdx.input.getY()*camera.size.y/def_graphics_resolution_y);
	}
	
	//Returns last X click position relative to game camera
	public static float getLastX()
	{
		return touch_x;
	}
	
	//Returns last Y click position relative to game camera
	public static float getLastY()
	{
		return touch_y;
	}
	
	
	//Set and Updates Display Settings and ajusts main camera settings to match
	public static void setDisplayMode(int res_x, int res_y, boolean f_screen)
	{
		def_graphics_resolution_x=res_x;
		def_graphics_resolution_y=res_y;
		def_graphics_aspect_ratio=(float)def_graphics_resolution_x/(float)def_graphics_resolution_y;
		def_graphics_fullscreen=f_screen;
		
		//Gdx.graphics.setVSync(def_graphics_vsync);
		Gdx.graphics.setDisplayMode(def_graphics_resolution_x,def_graphics_resolution_y,def_graphics_fullscreen);
		
		if(camera!=null)
		{
			camera.setAspectRatio(def_graphics_aspect_ratio);
		}
	}
}