package com.tentone.wallrunner.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tentone.wallrunner.Global;

public class Gamepad
{
	//Key code variable
	public static int keyboard_left, keyboard_right,keyboard_jump,keyboard_pause,keyboard_action;

	//Individual Keys Control Variables
	public static boolean rigth_was_released=true,rigth_just_released=false,rigth_just_pressed=false,rigth_pressed=false;
	public static boolean left_was_released=true,left_just_released=false,left_just_pressed=false,left_pressed=false;
	public static boolean jump_was_released=true,jump_just_released=false,jump_just_pressed=false,jump_pressed=false;
	public static boolean pause_was_released=true,pause_just_released=false,pause_just_pressed=false,pause_pressed=false;
	public static boolean action_was_released=true,action_just_released=false,action_just_pressed=false,action_pressed=false;
	
	public static boolean action_enabled=false;
	
	public static void ini()
	{
		keyboard_pause=Input.Keys.P;
		keyboard_left=Input.Keys.LEFT;
		keyboard_right=Input.Keys.RIGHT;
		keyboard_jump=Input.Keys.SPACE;
		keyboard_action=Input.Keys.SHIFT_LEFT;
	}
	
	public static void updateInput()
	{	
		//Reset Pressed Flags
		left_just_pressed=false;
		rigth_just_pressed=false;
		jump_just_pressed=false;
		pause_just_pressed=false;
		action_just_pressed=false;
		
		//Reset Released Flags
		left_just_released=false;
		rigth_just_released=false;
		jump_just_released=false;
		pause_just_released=false;
		action_just_released=false;
		
		//Action Input
		if(action_enabled)
		{
			if((Gdx.input.isKeyPressed(keyboard_action) || (Global.def_touchscreen && TactilGamepad.isActionPressed())))
			{
				if(!action_pressed)
				{
					action_just_pressed=true;
				}
				else
				{
					action_was_released=false;
				}
				action_pressed=true;
			}
			else
			{
				if(action_pressed)
				{
					action_just_released=true;
					action_was_released=true;
				}
				action_pressed=false;
			}
		}
		
		//Pause Key
		if(Gdx.input.isKeyPressed(keyboard_pause) || (Global.def_touchscreen && TactilGamepad.isPausePressed()))
		{
			if(!pause_pressed)
			{
				pause_just_pressed=true;
			}
			else
			{
				pause_was_released=false;
			}
			pause_pressed=true;
		}
		else
		{
			if(pause_pressed)
			{
				pause_just_released=true;
				pause_was_released=true;
			}
			pause_pressed=false;
		}
		
		//Left Input
		if(Gdx.input.isKeyPressed(keyboard_left) ||(Global.def_touchscreen && TactilGamepad.isLeftPressed()))
		{
			if(!left_pressed)
			{
				left_just_pressed=true;
			}
			else
			{
				left_was_released=false;
			}
			left_pressed=true;
		}
		else
		{
			if(left_pressed)
			{
				left_just_released=true;
				left_was_released=true;
			}
			left_pressed=false;
		}
		
		//Right Input
		if(Gdx.input.isKeyPressed(keyboard_right)|| (Global.def_touchscreen && TactilGamepad.isRigthPressed()))
		{
			if(!rigth_pressed)
			{
				rigth_just_pressed=true;
			}
			else
			{
				rigth_was_released=false;
			}
			rigth_pressed=true;
		}
		else
		{
			if(rigth_pressed)
			{
				rigth_just_released=true;
				rigth_was_released=true;
			}
			rigth_pressed=false;
		}
		
		//Jump Input
		if(Gdx.input.isKeyPressed(keyboard_jump)|| (Global.def_touchscreen && TactilGamepad.isJumpPressed()))
		{
			if(!jump_pressed)
			{
				jump_just_pressed=true;
			}
			else
			{
				jump_was_released=false;
			}
			jump_pressed=true;
		}
		else
		{
			if(jump_pressed)
			{
				jump_just_released=true;
				jump_was_released=true;
			}
			jump_pressed=false;
		}
	}
}
