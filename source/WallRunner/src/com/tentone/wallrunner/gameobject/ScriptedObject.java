package com.tentone.wallrunner.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.gameobject.script.*;
import com.tentone.wallrunner.physics.ColisionBox;

public class ScriptedObject extends ColisionBox implements GameObject
{
	//Script List Variable
	public static String[] script_list = {"None", "Game End Level", "Game CheckPoint", "Wind UP", "Player","Trampolin"};
	
	public Sprite sprite;
	public int texture;
	
	//Objects Variables
	public int script;
	public String id;
	
	public Script script_object;
	
	public ScriptedObject(float pos_x, float pos_y,int texture, float size_x, float size_y, float ori_x, float ori_y, float col_box_x, float col_box_y, int script,String id)
	{
		super(Type.Box,pos_x,pos_y,col_box_x,col_box_y,ori_x,ori_y,0f,0f);
		pos_ini = new Vector2(pos_x,pos_y);
		
		this.script=script;
		this.id=id;
		this.texture=texture;
		
		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos.x, pos.y);
		sprite.setOrigin(ori.x,ori.y);
		sprite.setSize(size_x,size_y);
		
		iniScript();
	}
	
	public static ScriptedObject createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new ScriptedObject(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Integer.parseInt(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),Float.parseFloat(temp[8]),Integer.parseInt(temp[9]),temp[10]);
	}
	
	public String dataString()
	{
		return pos.x+"|"+pos.y+"|"+texture+"|"+sprite.getWidth()+"|"+sprite.getHeight()+"|"+ori.x+"|"+ori.y+"|"+col_box.x+"|"+col_box.y+"|"+script+"|"+id;
	}
	
	public void iniScript()
	{
		if(script==1)
		{
			script_object = new LevelEnd(this);
		}
		else if(script==2)
		{
			script_object = new CheckPoint(this);
		}
		else if(script==3)
		{
			script_object = new WindUp(this);
		}
		else if(script==4)
		{
			script_object = new com.tentone.wallrunner.gameobject.script.Player(this);
		}
		else if(script==5)
		{
			script_object = new Trampolin(this);
		}
		else
		{
			script_object = new NullScript(this);
		}
		script_object.ini();
	}
	
	public boolean isActive()
	{
		return script_object.active;
	}
	
	//Updates objet status based on selected script
	public void update()
	{
		script_object.update();
	}
	
	//Draws Object into Global.camera.batch
	public void draw()
	{
		script_object.draw();
	}
}
