package com.tentone.wallrunner.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;

public class TactilGamepad
{
	//Final Variables
	private final static int max_touch=3;
	private final static float size_y=250;
	
	//Tactil Gamepad own Camera and SpriteBatch
	private static SpriteBatch gamepadBatch;
	private static OrthographicCamera gamepadCamera;
	private static Vector2 size;
	private static float width,height;
	
	//Button
	private static Button left,right,jump,pause,action;
	
	public static void ini()
	{
		//Get Window Size Values
		width=Gdx.graphics.getWidth();
		height=Gdx.graphics.getHeight();
		size = new Vector2(size_y*Global.def_graphics_aspect_ratio,size_y);
		
		//Initialize Buttons
		left = new Button(new Sprite(new Texture(Gdx.files.internal("data/texture/gamepad/but_left.png"))),0f,0f,64f,64f);
		right = new Button(new Sprite(new Texture(Gdx.files.internal("data/texture/gamepad/but_right.png"))),64f,0f,64f,64f);
		jump = new Button(new Sprite(new Texture(Gdx.files.internal("data/texture/gamepad/but_jump.png"))),size.x-64f,0f,64f,64f);
		action = new Button(new Sprite(new Texture(Gdx.files.internal("data/texture/gamepad/but_action.png"))),size.x-45f,64f,32f,32f);
		pause = new Button(new Sprite(new Texture(Gdx.files.internal("data/texture/gamepad/but_pause.png"))),size.x-32f,size.y-32f,32f,32f);

		//Initialize and Configure Camera
		gamepadCamera = new OrthographicCamera();
		gamepadCamera.setToOrtho(false,size.x,size.y);
		gamepadBatch = new SpriteBatch();
		gamepadBatch.setProjectionMatrix(gamepadCamera.combined);
	}
	
	//Update Size of TactilGamepad
	public static void updateSettings()
	{
		size.x=size.y*Global.def_graphics_aspect_ratio;
		width=Gdx.graphics.getWidth();
		height=Gdx.graphics.getHeight();
		
		gamepadCamera.setToOrtho(false,size.x,size.y);
		gamepadBatch = new SpriteBatch();
		gamepadBatch.setProjectionMatrix(gamepadCamera.combined);
	}
	
	//Draws gamepad to Screen
	public static void draw()
	{
		gamepadBatch.begin();
		jump.draw(gamepadBatch);
		left.draw(gamepadBatch);
		right.draw(gamepadBatch);
		pause.draw(gamepadBatch);
		if(Gamepad.action_enabled)
		{
			action.draw(gamepadBatch);
		}
		gamepadBatch.end();
	}
	
	public static boolean isPausePressed()
	{
		int i=0;
		
		while(i<max_touch)
		{
			if(Gdx.input.isTouched(i))
			{
				if(pause.isPressed((Gdx.input.getX(i)/width)*size.x, size.y-(Gdx.input.getY(i)/height)*size.y))
				{
					return true;
				}
			}
			i++;
		}
		
		return false;
	}
	
	//Checks if left Key is pressed
	public static boolean isLeftPressed()
	{
		int i=0;
		
		while(i<max_touch)
		{
			if(Gdx.input.isTouched(i))
			{
				if(left.isPressed((Gdx.input.getX(i)/width)*size.x, size.y-(Gdx.input.getY(i)/height)*size.y))
				{
					return true;
				}
			}
			i++;
		}
		
		return false;
	}
	
	//Checks if right Key is pressed
	public static boolean isRigthPressed()
	{
		int i=0;
		
		while(i<max_touch)
		{
			if(Gdx.input.isTouched(i))
			{
				if(right.isPressed((Gdx.input.getX(i)/width)*size.x, size.y-(Gdx.input.getY(i)/height)*size.y))
				{
					return true;
				}
			}
			i++;
		}
		
		return false;
	}
	
	//check if jump key is pressed
	public static boolean isJumpPressed()
	{
		int i=0;
		
		while(i<max_touch)
		{
			if(Gdx.input.isTouched(i))
			{
				if(jump.isPressed((Gdx.input.getX(i)/width)*size.x, size.y-(Gdx.input.getY(i)/height)*size.y))
				{
					return true;
				}
			}
			i++;
		}
		
		return false;
	}
	
	//Check if action key is pressed
	public static boolean isActionPressed()
	{
		int i=0;
		
		while(i<max_touch)
		{
			if(Gdx.input.isTouched(i))
			{
				if(action.isPressed((Gdx.input.getX(i)/width)*size.x, size.y-(Gdx.input.getY(i)/height)*size.y))
				{
					return true;
				}
			}
			i++;
		}
		
		return false;
	}
}
