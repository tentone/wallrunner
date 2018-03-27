package com.tentone.wallrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;

public class Camera
{
	//Camera Variables
	public OrthographicCamera camera;
	public SpriteBatch batch;
	
	//Camera Settings Variables
	public Vector2 pos, size, size_half;
	public float aspect_ratio;
	
	//Camera Object Interaction
	public Vector2 margin_percent,margin;
	public boolean limited_level_borders;
	public boolean camera_follow_player;
	
	public Camera(float y_size,float aspect_ratio, float pos_x,float pos_y,float zoom,boolean camera_follow_player,float camera_margin_percent_x, float camera_margin_percent_y,boolean limited_level_borders)
	{
		pos = new Vector2(pos_x,pos_y);
		size = new Vector2(y_size*aspect_ratio,y_size); //Fixed y_size x calculated from aspect ration of the screen
		this.aspect_ratio=aspect_ratio;
		
		this.camera_follow_player=camera_follow_player;
		this.limited_level_borders=limited_level_borders;
		
		margin_percent = new Vector2(camera_margin_percent_x,camera_margin_percent_y);
		size_half = new Vector2(size.x*0.5f,size.y*0.5f);
		margin = new Vector2(size_half.x-(size.x*margin_percent.x),size_half.y-(size.y*margin_percent.y));
		
		//Batch and Camera Ini
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, size.x, size.y);
		camera.position.set(pos.x, pos.y,0);
		camera.zoom=zoom;
		camera.update();
		
		//Set Clean color to Cyan
		setClearColor(0.8f,1,1);
	}

	//Update Camera Position Based on Camera Settings and Game Flow
	public void update()
	{		
		//Correct camera zoom
		checkCameraZoomLevel();
		
		//Camera Follow Object Player Position
		if(camera_follow_player)
		{
			if(Global.player.is_alive.value())
			{
				if(Global.player.pos.x<(pos.x-margin.x))
				{
					pos.x=Global.player.pos.x+margin.x;
				}
				else if(Global.player.pos.x>(pos.x+margin.x))
				{
					pos.x=Global.player.pos.x-margin.x;
				}
	
				if(Global.player.pos.y<(pos.y-margin.y))
				{
					pos.y=Global.player.pos.y+margin.y;
				}
				else if(Global.player.pos.y>(pos.y+margin.y))
				{
					pos.y=Global.player.pos.y-margin.y;
				}
			}
		}
		
		//Limit Camera Position Inside of Level
		if(limited_level_borders)
		{
			if((pos.x-(size_half.x*camera.zoom))<Global.level_ori.x)
			{
				pos.x=Global.level_ori.x+(size_half.x*camera.zoom);
			}
			else if((pos.x+(size_half.x*camera.zoom))>(Global.level_ori.x+Global.level_size.x))
			{
				pos.x=(Global.level_ori.x+Global.level_size.x)-(size_half.x*camera.zoom);
			}

			if((pos.y-(size_half.y*camera.zoom))<Global.level_ori.y)
			{
				pos.y=Global.level_ori.y+(size_half.y*camera.zoom);
			}
			else if((pos.y+(size_half.y*camera.zoom))>(Global.level_ori.y+Global.level_size.y))
			{
				pos.y=(Global.level_ori.y+Global.level_size.y)-(size_half.y*camera.zoom);
			}
		}
		
		//Update Camera Position
		updatePosition();
	}
	
	public void setPositionToCorner()
	{
		pos.set(size.x/2f,size.y/2f);
		camera.position.set(pos, 0f);
		camera.update();
	}
	
	//Check is camera inside of level only and fixes if outside
	public void checkCameraZoomLevel()
	{
		//Check camera size and correct zoom
		if(size.x*Global.level_zoom>Global.level_size.x)
		{
			camera.zoom=Global.level_size.x/Global.camera.size.x;
		}
		else
		{
			camera.zoom=Global.level_zoom;
		}
	}
	
	//Get zoom
	public float getZoom()
	{
		return camera.zoom;
	}
	
	//Set Zoom variable
	public void setZoom(float zoom)
	{
		camera.zoom=zoom;
	}
	
	//Attach a GLSL shader to this camera
	public void setShader(ShaderProgram shader)
	{
		batch.setShader(shader);
	}
	
	//Update Camera Position
	public void updatePosition()
	{
		camera.position.set(pos.x,pos.y,0);
		camera.update();
	}
	
	public void updateInternalAtributes()
	{
		size.set(size.y*aspect_ratio,size.y);
		
		//Recalculate Camera Size
		size.x=size.y*aspect_ratio;
		size_half.set(size.x*0.5f,size.y*0.5f);
		margin.set(size_half.x-(size.x*margin_percent.x),size_half.y-(size.y*margin_percent.y));
		
		//Update Orthographic Camera
		camera.setToOrtho(false, size.x, size.y);
		camera.update();
	}
	
	//Initializes Camera With New Settings
	public void setAspectRatio(float aspect_ratio)
	{
		this.aspect_ratio=aspect_ratio;
		
		//Recalculate Camera Size
		size.x=size.y*aspect_ratio;
		size_half.set(size.x*0.5f,size.y*0.5f);
		margin.set(size_half.x-(size.x*margin_percent.x),size_half.y-(size.y*margin_percent.y));
		
		//Update Orthographic Camera
		camera.setToOrtho(false, size.x, size.y);
		camera.update();
	}
	
	//Clear Screen
	public void clearScreen()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	//Set Clear Color
	public void setClearColor(float r,float g, float b)
	{
		Gdx.gl.glClearColor(r,g,b,1f);
	}
	
	//Start new Frame
	public void startFrame()
	{
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
	}
	
	public void endFrame()
	{
		batch.end();
	}
	
	public void dispose()
	{
		batch.dispose();
	}
}
