package com.tentone.wallrunner.gameobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.math.Angle;
import com.tentone.wallrunner.math.AngleList;
import com.tentone.wallrunner.physics.ColisionBox;
import com.tentone.wallrunner.physics.ColisionBox.Type;

@SuppressWarnings("unused")
public class Light extends ColisionBox implements GameObject
{
	//Light Control Variables
	public Vector2 pos; //Position
	public float range; //Range
	public float intensity; //Blend Alpha
	public Vector3 color;//Light Color
	
	//Control Variables
	public boolean shadow_player, shadow_wall, shadow_enemy,shadow_decor_back,shadow_decor_front;
	private boolean active,visible;
	
	//Shape Renderer
	private ShapeRenderer shape;
	
	//Debug info control variable
	private boolean debug_info=true;
	
	//Light ColidingObjects List
	private ArrayList<ColisionBox> object_list;
	private AngleList object_angle; //Object list has the angles where there are objects placed in range of light
	private AngleList light_angle;
	
	public Light(float pos_x,float pos_y,float range,boolean shadow_player, boolean shadow_wall, boolean shadow_enemy,boolean shadow_decor_front,boolean shadow_decor_back,float color_r,float color_g,float color_b,float intensity)
	{
		//Ini Colision Box for ligth cast
		super(ColisionBox.Type.Circle,pos_x,pos_y,2*range,0,0,0,0,0);
		
		//Light Atributes
		this.pos= new Vector2(pos_x,pos_y);
		this.range=range;
		this.intensity=intensity; //Blend Alpha
		this.color=new Vector3(color_r,color_g,color_b);
		
		//Shadow Cast Objects
		this.shadow_enemy=shadow_enemy;
		this.shadow_player=shadow_player;
		this.shadow_wall=shadow_wall;
		this.shadow_decor_back=shadow_decor_back;
		this.shadow_decor_front=shadow_decor_front;
		
		//Set Control Flags
		active=true;
		visible=true;
		
		//Ini Angle List
		light_angle = new AngleList(true);
		object_angle = new AngleList(false);
		object_list = new ArrayList<ColisionBox>();
	}

	//Factory that builds Light object based on data String
	public static Light createFromDataString(String data)
	{
		String[] temp=data.split("\\|");
		return new Light(Float.parseFloat(temp[0]),Float.parseFloat(temp[1]),Float.parseFloat(temp[2]),temp[3].equals("true"),temp[4].equals("true"),temp[5].equals("true"),temp[6].equals("true"),temp[7].equals("true"),Float.parseFloat(temp[8]),Float.parseFloat(temp[9]),Float.parseFloat(temp[10]),Float.parseFloat(temp[11]));
	}
	
	//Returns Data String
	public String dataString()
	{
		return pos.x+"|"+pos.y+"|"+range+"|"+shadow_player+"|"+shadow_wall+"|"+shadow_enemy+"|"+shadow_decor_back+"|"+shadow_decor_front+"|"+color.x+"|"+color.y+"|"+color.z+"|"+intensity;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	@Override
	public void update(){}
	
	@Override
	public void draw()
	{
		int i=0;

		//Start Shape Renderer to render Debug Stuff
		if(debug_info)
		{
			shape = new ShapeRenderer();
			shape.setProjectionMatrix(Global.camera.camera.combined);
			shape.setColor(Color.BLACK);
			shape.begin(ShapeType.Line);
		}

		//Clear Lists
		object_angle.clear();
		light_angle.clear();
		
		//Add Wall in range to shadow cast 
		if(shadow_wall)
		{
			i=0;
			while(i<Global.wall.length)
			{
				if(isInRange(Global.wall[i]))
				{
					addObjectToList(Global.wall[i]);
				}
				i++;
			}
		}

		//Add enemy in range to shadow cast 
		if(shadow_enemy)
		{
			i=0;
			while(i<Global.enemy.length)
			{
				if(isInRange(Global.enemy[i]))
				{
					addObjectToList(Global.enemy[i]);
				}
				i++;
			}
		}
		
		//Add Player in range to shadow cast 
		if(shadow_player && Global.player.is_alive.value() && isInRange(Global.player))
		{
			addObjectToList(Global.player);
		}

		//End and Dispose Debug shape Renderer
		if(debug_info)
		{
			shape.end();
			shape.dispose();
		}
		
		//Draw Stuff to screen
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		
		//Prepare shape renderer to draw stuff
		shape = new ShapeRenderer();
		shape.setProjectionMatrix(Global.camera.camera.combined);
		shape.setColor(color.x*intensity,color.y*intensity,color.z*intensity,intensity);
		shape.begin(ShapeType.Filled);
		
		//Draw Angles 
		Iterator<Angle> it=light_angle.iterator();
		Angle t;
		while(it.hasNext())
		{
			t=it.next();
			shape.arc(pos.x,pos.y,range,t.min,t.max-t.min,30);
		}
		
		//End and Dispose ShapeRenderer
		shape.end();
		shape.dispose();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	//Add object to list of light collision objects
	private void addObjectToList(ColisionBox object)
	{
		Float[] t;
		int j=0;
		
		
		if(object.type==ColisionBox.Type.Box) //Box ColisionBox
		{
			t = new Float[4];
			t[0] = angleToPointDeg(object.pos.x,object.pos.y);
			t[1] = angleToPointDeg(object.pos.x,object.pos.y+object.col_box.y);
			t[2] = angleToPointDeg(object.pos.x+object.col_box.x,object.pos.y);
			t[3] = angleToPointDeg(object.pos.x+object.col_box.x,object.pos.y+object.col_box.y);
			t=Angle.getMinAndMaxAngle(t);
		}
		else if(object.type==ColisionBox.Type.Circle) //TODO Circle Objects
		{
			t = new Float[2];
			t[0] = angleToPointDeg(object.pos.x+object.ori.x,object.pos.y+object.ori.y);
			t[1] = t[0] + (float)Math.atan((object.col_box.x/2f)/Vector2.dst(pos.x,pos.y,object.pos.x,object.pos.y))*57.2957795f;
			t[0] -= (float)Math.atan((object.col_box.x/2f)/Vector2.dst(pos.x,pos.y,object.pos.x,object.pos.y))*57.2957795f;
		}
		else
		{
			return;
		}
		
		//Add object to list of colliding object
		object_list.add(object);
		
		//Add Angle to List Of Light Passing Zone
		light_angle.add(new Angle(t[0],t[1]));
		
		//Add Angle to List of Zones to Be Casted
		object_angle.add(new Angle(t[0],t[1]));
	}
	
	//Check if a object is in range of light
	private boolean isInRange(ColisionBox object)
	{
		if(object.type==ColisionBox.Type.Box)
		{
			return isInRange(object.pos.x,object.pos.y) || isInRange(object.pos.x,object.pos.y+object.col_box.y) || isInRange(object.pos.x+object.col_box.x,object.pos.y) || isInRange(object.pos.x+object.col_box.x,object.pos.y+object.col_box.y);
		}
		else if(object.type==ColisionBox.Type.Circle)
		{
			return isColliding(object);
		}
		
		return false;
	}
	
	//Returns Angle in Degrees to a point in degrees returns null if point equals center
	private Float angleToPointDeg(float x,float y)
	{
		if(x==pos.x && y==pos.y)
		{
			return null;
		}
		
		float angle= -57.2957795131f*(float)Math.atan2(x-pos.x,y-pos.y)+90f;
		
		if(angle<0)
		{
			return angle+360;
		}
		
		return angle;
	}
	
	//Returns angle to a point in radian
	private float angleToPointRad(float x, float y)
	{
		return (-1f)*(float)Math.atan2(x-pos.x,y-pos.y)+1.57f;
	}
	
	//Check if a point is in range of light
	private boolean isInRange(float x, float y)
	{
		return Vector2.dst(pos.x,pos.y,x,y)<range;
	}
	

	//Draw Line to Point using shape
	private void drawLine(float x, float y,ShapeRenderer shape)
	{
		float angle = angleToPointRad(x,y);
		float line_x= pos.x+range*(float)Math.cos(angle);
		float line_y = pos.y+range*(float)Math.sin(angle);
			
		shape.line(pos.x,pos.y,line_x,line_y);
	}
}
