package com.tentone.ganimator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Animation 
{
	public Bone[] bone; //Bone Array
	private float animation_time,current_time; //Animation Total Time
	private Vector2 pos,scale; //Position and Size Offset Values
	private TextureManager texture_manager;
	
	//Black animation constructor
	public Animation(TextureManager texture_manager)
	{
		this.texture_manager=texture_manager;
		
		bone = new Bone[0];
		this.animation_time=0;
		
		pos = new Vector2(0f,0f);
		scale = new Vector2(1f,1f);
	}
	
	//Get texture Manager
	public TextureManager getTextureManager()
	{
		return texture_manager;
	}
	
	//Set new Position
	public void setPosition(float x,float y)
	{
		this.pos.x=x;
		this.pos.y=y;
	}
	
	//Get Animation Position
	public Vector2 getPosition()
	{
		return pos;
	}
	
	//Returns Animation Scale
	public Vector2 getScale()
	{
		return scale;
	}
	
	public void setScale(float scale)
	{
		this.scale.x=scale;
		this.scale.y=scale;
	}
	
	//Set Scale
	public void setScale(float x,float y)
	{
		this.scale.x=x;
		this.scale.y=y;
	}
	
	//Return Animation Bones Array
	public Bone[] getBones()
	{
		return bone;
	}
	
	//Return Animation Time
	public float getDuration()
	{
		return animation_time;
	}
	
	//Return animation current time
	public float getTime()
	{
		return current_time;
	}
	
	//Set Animation to time point
	public void setTime(float time)
	{
		int i=0;
		while(i<bone.length)
		{
			bone[i].setTime(time);
			i++;
		}
		current_time=time;
	}
	
	//Updates Animation State
	public void update(float time_base)
	{
		int i=0;
		while(i<bone.length)
		{
			bone[i].update(time_base);
			i++;
		}
		
		//Update Animation Time
		current_time+=time_base;
		if(current_time>animation_time)
		{
			current_time=(current_time%animation_time)+time_base;
		}
	}
	
	//Draw Actual Animation Frame
	public void draw(SpriteBatch batch)
	{
		int i=bone.length-1;
		while(i>-1)
		{
			bone[i].updateSprite();
			bone[i].sprite.draw(batch);
			i--;
		}
	}

	//Reset Animation
	public void reset()
	{
		int i=0;
		while(i<bone.length)
		{
			bone[i].resetBoneAnimation();
			i++;
		}
	}
	
	//Add new bone to animation
	public void addBone(String[] texture,String label,float pos_x, float pos_y,float size_x, float size_y,float ori_x, float ori_y, float rotation,boolean loop,float alpha)
	{
		Bone[] temp = new Bone[bone.length+1];
		int i=0;
		
		while(i<bone.length)
		{
			temp[i]=bone[i];
			i++;
		}
		
		temp[i]=new Bone(this,texture,label,pos_x,pos_y,size_x,size_y,ori_x,ori_y,rotation,loop,alpha);
		bone=temp;
		
		updateAnimationDuration();
	}
	
	//Delete Bone from animation
	public void deleteBone(int index)
	{
		Bone[] temp = new Bone[bone.length-1];
		int i=0;

		while(i<bone.length)
		{
			if(i<index)
			{
				temp[i]=bone[i];
			}
			else if(i>index)
			{
				temp[i-1]=bone[i];
			}
			i++;
		}

		bone=temp;

		updateAnimationDuration();
	}

	//Move Bone Down on array
	public void moveBoneDown(int index)
	{
		if(index==0)
		{
			return;
		}
		
		Bone[] temp = new Bone[bone.length];
		int i=0;
		
		while(i<temp.length)
		{
			if(i!=index-1)
			{
				temp[i]=bone[i];
				i++;
			}
			if(i==index-1)
			{
				temp[i]=bone[i+1];
				i++;
				temp[i]=bone[i-1];
				i++;
			}
		}
		
		bone=temp;
	}
	
	//Move Bone Up on array
	public void moveBoneUp(int index)
	{
		if(index==bone.length-1)
		{
			return;
		}
		
		Bone[] temp = new Bone[bone.length];
		int i=0;
		
		while(i<temp.length)
		{
			if(i!=index)
			{
				temp[i]=bone[i];
				i++;
			}
			if(i==index)
			{
				temp[i]=bone[i+1];
				i++;
				temp[i]=bone[i-1];
				i++;
			}
		}
		
		bone=temp;
	}
	
	//Updates Animation Max Time
	public void updateAnimationDuration()
	{
		if(bone.length>0)
		{
			animation_time=bone[0].getMaxTime();
		}
		else
		{
			animation_time=0;
			return;
		}
		
		int i=0;
		while(i<bone.length)
		{
			if(animation_time<bone[i].getMaxTime())
			{
				animation_time=bone[i].getMaxTime();
			}
			i++;
		}
		
	}
	
	//Load Animation File Using BufferedReader (java.io)
	public void loadFile(String file_name) throws Exception
	{	
		BufferedReader reader=null;
		
		//Try to load animation from internal files if it fails load as absolute path
		try
		{
			reader=new BufferedReader(new InputStreamReader(Gdx.files.internal(file_name).read()));
		}
		catch(Exception e)
		{
			reader=new BufferedReader(new InputStreamReader(Gdx.files.absolute(file_name).read()));
		}

		int version=Integer.parseInt(reader.readLine());
		
		if(version==1)
		{
			//Variables
			int i,j,bone_length,frame_length;
			bone=new Bone[0];
			
			bone_length=Integer.parseInt(reader.readLine());
			
			i=0;
			while(i<bone_length)
			{
				String[] a =  new String[1];
				a[0]=reader.readLine();
				
				//Add new Bone
				addBone(a,reader.readLine(),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()),reader.readLine().equals("true"),Float.parseFloat(reader.readLine()));
				
				//Add frames to Bone
				frame_length=Integer.parseInt(reader.readLine());
				j=0;
				while(j<frame_length)
				{
					bone[i].addKeyFrame(reader.readLine()+"|0");
					j++;
				}
				i++;
			}
		}
		else if(version==2)
		{
			//Variables
			int i,j,bone_length;
			String label;
			boolean loop;
			int frame_count;
			String[] text,temp;
			
			bone=new Bone[0];
			
			bone_length=Integer.parseInt(reader.readLine());
			
			i=0;
			while(i<bone_length)
			{
				text=new String[Integer.parseInt(reader.readLine())];
				
				j=0;
				while(j<text.length)
				{
					text[j]=reader.readLine();
					j++;
				}
				
				label=reader.readLine();
				loop=Boolean.parseBoolean(reader.readLine());
				frame_count=Integer.parseInt(reader.readLine());
				temp=reader.readLine().split("\\|");
				
				//Add new Bone
				addBone(text,label,Float.parseFloat(temp[1]), Float.parseFloat(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),loop,Float.parseFloat(temp[8]));
				
				//Add frames to Bone
				j=0;
				while(j<frame_count)
				{
					bone[i].addKeyFrame(reader.readLine());
					j++;
				}
				i++;
			}
		}
		
		reader.close();
	}
	
	//Save Animation to external File using PrintWriter (java.io)
	public void saveFile(String file_name) throws Exception
	{
		PrintWriter pw = new PrintWriter(new File(file_name));;
		int i,j;
		
		//File Version
		pw.println(1);
		
		//Write Animation Data to File
		pw.println(bone.length); //Number of Bones
		i=0;
		while(i<bone.length)
		{
			pw.println(bone[i].texture_file[0]); //Texture File
			pw.println(bone[i].label);
			pw.println(bone[i].key_frame[0].pos.x);
			pw.println(bone[i].key_frame[0].pos.y);
			pw.println(bone[i].key_frame[0].size.x);
			pw.println(bone[i].key_frame[0].size.y);
			pw.println(bone[i].key_frame[0].ori.x);
			pw.println(bone[i].key_frame[0].ori.y);
			pw.println(bone[i].key_frame[0].rotation);
			pw.println(bone[i].loop); //Loop Mode 
			pw.println(bone[i].key_frame[0].alpha); //Initial Alpha
			pw.println(bone[i].key_frame.length-1); //Number of Key Frames
			
			j=1;
			while(j<bone[i].key_frame.length)//Write Key Frames
			{
				pw.println(bone[i].key_frame[j].time+"|"+bone[i].key_frame[j].pos.x+"|"+bone[i].key_frame[j].pos.y+"|"+bone[i].key_frame[j].size.x+"|"+bone[i].key_frame[j].size.y+"|"+bone[i].key_frame[j].ori.x+"|"+bone[i].key_frame[j].ori.y+"|"+bone[i].key_frame[j].rotation+"|"+bone[i].key_frame[j].alpha);
				j++;
			}
			
			pw.flush();
			i++;
		}
		
		pw.close();
	}
	
	//Save Animation (Version 2) with texture swaping Save Animation to external File using PrintWriter (java.io)
	public void saveFileV2(String file_name) throws Exception
	{
		PrintWriter pw = new PrintWriter(new File(file_name));;
		int i,j;
		
		//File Version
		pw.println(2);
		
		//Write Animation Data to File
		pw.println(bone.length); //Number of Bones
		
		i=0;
		while(i<bone.length)
		{
			pw.println(bone[i].texture_file.length); //Number of texture files
			
			j=0;
			while(j<bone[i].texture_file.length)
			{
				pw.println(bone[i].texture_file[j]); //Texture File
				j++;
			}
			
			pw.println(bone[i].label); //Bone Label
			pw.println(bone[i].loop); //Loop Mode 
			pw.println(bone[i].key_frame.length-1); //Number of Key Frames
			
			j=0;
			while(j<bone[i].key_frame.length)//Write All Key Frames
			{
				pw.println(bone[i].key_frame[j].time+"|"+bone[i].key_frame[j].pos.x+"|"+bone[i].key_frame[j].pos.y+"|"+bone[i].key_frame[j].size.x+"|"+bone[i].key_frame[j].size.y+"|"+bone[i].key_frame[j].ori.x+"|"+bone[i].key_frame[j].ori.y+"|"+bone[i].key_frame[j].rotation+"|"+bone[i].key_frame[j].alpha+"|"+bone[i].key_frame[j].texture);
				j++;
			}
			
			pw.flush();
			i++;
		}
		
		pw.close();
	}
}

