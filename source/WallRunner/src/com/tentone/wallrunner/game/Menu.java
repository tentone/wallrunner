package com.tentone.wallrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.input.Button;
import com.tentone.wallrunner.input.Gamepad;

public class Menu
{
	private static Button[] button;
	private static Sprite[] sprite;
	private static int menu_option;
	
	public static void ini()
	{
		if(Global.camera!=null)
		{
			Global.camera.dispose();
		}
		Global.camera = new Camera(500f,Global.def_graphics_aspect_ratio,250f,0,1,false,0,0,false);
		Global.camera.setPositionToCorner();
		
		Global.loadTextureSetFile("data/texture/list/menu.list");
		
		sprite = new Sprite[1];
		sprite[0] = new Sprite(Global.texture[0]);
		sprite[0].setSize(500,43);
		sprite[0].setPosition(Global.camera.size.x/2f-250f,Global.camera.size.y-80f);
		
		button = new Button[4];
		button[0] = new Button(1,Global.camera.size.x/2f-100,Global.camera.size.y/2f-150,200,200);
		button[1] = new Button(2,Global.camera.size.x/2f+300,Global.camera.size.y/2f-150,200,200);
		button[2] = new Button(3,Global.camera.size.x/2f+700,Global.camera.size.y/2f-150,200,200);
		button[3] = new Button(4,Global.camera.size.x/2f+1100,Global.camera.size.y/2f-150,200,200);
		
		menu_option=0;
	}
	
	public static void update()
	{
		if(buttonIsPressed(0) || (Gamepad.jump_just_pressed && menu_option==0))//New Game
		{
			Global.actual_level=0;
			try
			{
				Global.loadLevel(Global.level_file[Global.actual_level]);
			}
			catch(Exception e)
			{
				Gdx.app.exit();
			}
			Global.game_lifecycle_control=2;
		}
	}
	
	public static void draw()
	{	
		int i=0;
		
		Global.camera.clearScreen();
		Global.camera.startFrame();
		
		i=0;
		while(i<sprite.length)
		{
			sprite[i].draw(Global.camera.batch);
			i++;
		}
		
		i=0;
		while(i<button.length)
		{
			button[i].draw(Global.camera.batch);
			i++;
		}
		
		Global.camera.endFrame();
	}
	
	public static boolean buttonIsPressed(int index)
	{
		if(Gdx.input.isTouched(0))
		{
			if(button[index].isPressed())
			{
				return true;
			}
		}
		return false;
	}
	
}
