package com.tentone.ganimator;

import java.util.Arrays;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bone
{
	//Animation Pointer
	private Animation animation;
	
	//Texture Files
	private Texture[] texture;
	public String[] texture_file;
	
	//Data Variables
	public Sprite sprite;
	public Frame[] key_frame;
	private Frame buffer_frame;

	//File Data Variables
	public String label;
	public boolean loop;
	public float alpha,rotation;
	public Vector2 pos,size,ori;
	
	//Animation Flow Variables
	private float actual_time;
	private int actual_frame;
	private boolean active;
	
	//Constructor
	public Bone(Animation animation,String[] texture_file,String label,float pos_x, float pos_y,float size_x, float size_y,float ori_x,float ori_y, float rotation, boolean loop,float alpha)
	{
		//Initialize Object Variables
		active=true;
		this.label=label;
		this.loop=loop;
		this.texture_file=texture_file;
		this.alpha=alpha;
		this.rotation=rotation;
		
		//Get Associated Animation
		this.animation=animation;
		
		pos = new Vector2(pos_x,pos_y);
		size = new Vector2(size_x,size_y);
		ori = new Vector2(ori_x,ori_y);
		
		//Gets Texture from Animation Class
		texture = new Texture[texture_file.length];
		int i=0;
		while(i<texture_file.length)
		{
			texture[i]=animation.getTextureManager().getTexture(texture_file[i]);
			i++;
		}	
		
		//Use first texture as default
		sprite = new Sprite(texture[0]);
		
		//Initialize Frame
		key_frame = new Frame[0];
		
		//Add First Frame and ini Buffer Frame
		addKeyFrame(0,pos_x,pos_y,size_x,size_y,ori_x,ori_y,rotation,alpha,0);
		buffer_frame = new Frame(0,pos_x,pos_y,size_x,size_y,ori_x,ori_y,rotation,alpha,0);
		
		updateSprite();
	}
	
	//Updates Bone Animation State based on defined time frame
	public void update(float time_base)
	{
		//Check if animation is active and have at least 2 key frames
		if(active && key_frame.length>1)
		{
			float remaining_time=time_base;
			
			while(remaining_time>=key_frame[actual_frame+1].time-actual_time) //Skip Frames abobe time_base
			{
				//Calculate new remaining time
				remaining_time-=key_frame[actual_frame+1].time-actual_time;
				
				if(actual_frame>=key_frame.length-2)//Check if its last frame if so check if loop mode is enabled and restart animation
				{
					if(loop)
					{
						buffer_frame.pos.set(key_frame[0].pos);
						buffer_frame.ori.set(key_frame[0].ori);
						buffer_frame.size.set(key_frame[0].size);
						buffer_frame.alpha=key_frame[0].alpha;
						buffer_frame.rotation=key_frame[0].rotation;
						actual_time=key_frame[0].time;
						sprite.setTexture(texture[key_frame[0].texture]);
						actual_frame=0;
					}
					else
					{
						active=false;
					}
				}
				else //if its not last frame just jump to next frame and update buffer
				{
					actual_frame++;
					buffer_frame.pos.set(key_frame[actual_frame].pos);
					buffer_frame.ori.set(key_frame[actual_frame].ori);
					buffer_frame.size.set(key_frame[actual_frame].size);
					buffer_frame.alpha=key_frame[actual_frame].alpha;
					buffer_frame.rotation=key_frame[actual_frame].rotation;
					sprite.setTexture(texture[key_frame[actual_frame].texture]);
					actual_time=key_frame[actual_frame].time;
				}
			}
			
			//Calculate Update Ratio and update buffer with new frame
			float update_ratio=remaining_time/(key_frame[actual_frame+1].time-key_frame[actual_frame].time);
			buffer_frame.pos.x+=update_ratio*(key_frame[actual_frame+1].pos.x-key_frame[actual_frame].pos.x);
			buffer_frame.pos.y+=update_ratio*(key_frame[actual_frame+1].pos.y-key_frame[actual_frame].pos.y);
			buffer_frame.ori.x+=update_ratio*(key_frame[actual_frame+1].ori.x-key_frame[actual_frame].ori.x);
			buffer_frame.ori.y+=update_ratio*(key_frame[actual_frame+1].ori.y-key_frame[actual_frame].ori.y);
			buffer_frame.size.x+=update_ratio*(key_frame[actual_frame+1].size.x-key_frame[actual_frame].size.x);
			buffer_frame.size.y+=update_ratio*(key_frame[actual_frame+1].size.y-key_frame[actual_frame].size.y);
			buffer_frame.rotation+=update_ratio*(key_frame[actual_frame+1].rotation-key_frame[actual_frame].rotation);
			buffer_frame.alpha+=update_ratio*(key_frame[actual_frame+1].alpha-key_frame[actual_frame].alpha);
			actual_time+=remaining_time;
			
			//Correct Buffer alpha
			if(buffer_frame.alpha<0f)
			{
				buffer_frame.alpha=0f;
			}
			
			//Check if ready for next frame
			if(actual_time>key_frame[actual_frame+1].time)
			{
				actual_frame++;
			}
			
			//If was last frame restart of deactivate animation
			if(actual_frame>=key_frame.length-1)
			{
				if(loop)
				{
					buffer_frame.pos.set(key_frame[0].pos);
					buffer_frame.ori.set(key_frame[0].ori);
					buffer_frame.size.set(key_frame[0].size);
					buffer_frame.alpha=key_frame[0].alpha;
					buffer_frame.rotation=key_frame[0].rotation;
					sprite.setTexture(texture[key_frame[0].texture]);
					actual_time=0;
					actual_frame=0;
				}
				else
				{
					active=false;
				}
			}
		}
		else if(!active && loop)//If its disabled but loop mode is active set enabled again
		{
			active=true;
		}	
	}
	
	//Updates sprite based on actual frame data
	public void updateSprite()
	{
		sprite.setPosition(buffer_frame.pos.x*animation.getScale().x+animation.getPosition().x, buffer_frame.pos.y*animation.getScale().y+animation.getPosition().y);
		sprite.setSize(buffer_frame.size.x*animation.getScale().x,buffer_frame.size.y*animation.getScale().y);
		sprite.setOrigin(buffer_frame.ori.x, buffer_frame.ori.y);
		sprite.setAlpha(buffer_frame.alpha);
		sprite.setRotation(buffer_frame.rotation);
	}
	
	//Reset Animation to first frame
	public void resetBoneAnimation()
	{
		actual_time=0;
		actual_frame=0;
		buffer_frame.pos.set(key_frame[0].pos);
		buffer_frame.ori.set(key_frame[0].ori);
		buffer_frame.size.set(key_frame[0].size);
		buffer_frame.alpha=key_frame[0].alpha;
		buffer_frame.rotation=key_frame[0].rotation;
		sprite.setTexture(texture[key_frame[0].texture]);
	}
	
	//Delete Key Frame from Bone
	public boolean deleteKeyFrame(int index)
	{
		if(index<=0 || index>=key_frame.length)
		{
			return false;
		}
		
		Frame[] temp = new Frame[key_frame.length-1];
		int i=0;
		
		while(i<key_frame.length)
		{
			if(i<index)
			{
				temp[i]=key_frame[i];
			}
			else if(i>index)
			{
				temp[i-1]=key_frame[i];
			}
			i++;
		}
		
		key_frame=temp;
		animation.updateAnimationDuration();
		
		return true;
	}
	
	//Add new Key Frame to Bone
	public boolean addKeyFrame(Frame frame)
	{
		if(frame.time<0)
		{
			return false;
		}
		
		Frame[] temp = new Frame[key_frame.length+1];
		
		int i=0;
		while(i<key_frame.length)
		{
			temp[i]=key_frame[i];
			i++;
		}
		temp[i] = frame;
		
		Arrays.sort(temp);
		key_frame=temp;
		
		animation.updateAnimationDuration();
		
		return true;
	}
	
	//Add new KeyFrame to Bone
	public boolean addKeyFrame(String frame)
	{
		String[] temp=frame.split("\\|");
		
		//Check if frame time if valid
		if(Float.parseFloat(temp[0])<0)
		{
			return false;
		}
		
		Frame[] tempa = new Frame[key_frame.length+1];
		int i=0;
		while(i<key_frame.length)
		{
			tempa[i]=key_frame[i];
			i++;
		}
		tempa[i] = new Frame(Float.parseFloat(temp[0]), Float.parseFloat(temp[1]), Float.parseFloat(temp[2]),Float.parseFloat(temp[3]),Float.parseFloat(temp[4]),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),Float.parseFloat(temp[7]),Float.parseFloat(temp[8]),Integer.parseInt(temp[9]));
		
		Arrays.sort(temp);
		key_frame=tempa;
		
		animation.updateAnimationDuration();
		return true;
	}
	
	//Add new Key Frame to Bone
	public boolean addKeyFrame(float time, float pos_x, float pos_y, float size_x, float size_y, float ori_x, float ori_y, float rotation, float alpha,int texture)
	{
		if(time<0)
		{
			return false;
		}
		
		Frame[] temp = new Frame[key_frame.length+1];
		int i=0;
		while(i<key_frame.length)
		{
			temp[i]=key_frame[i];
			i++;
		}
		temp[i] = new Frame(time,pos_x,pos_y,size_x,size_y,ori_x,ori_y,rotation,alpha,texture);
		
		Arrays.sort(temp);
		key_frame=temp;
		
		animation.updateAnimationDuration();
		
		return true;
	}
	
	//Set Bone Time to t and updates BufferFrame
	public void setTime(float t)
	{
		resetBoneAnimation();
		update(t);
	}
	
	public String[] getKeyFrameDescription()
	{
		String[] temp = new String[key_frame.length];
		int i=0;
		
		while(i<temp.length)
		{
			temp[i]=key_frame[i].time+"s  pos("+key_frame[i].pos.x+","+key_frame[i].pos.y+") size("+key_frame[i].size.x+","+key_frame[i].size.y+") alpha("+key_frame[i].alpha+") rot("+key_frame[i].rotation+")";
			i++;
		}
		
		return temp;
	}
	
	//Return actual Frame
	public Frame getActualFrame()
	{
		return buffer_frame;
	}
	
	//Returns frame for time t in no frame for that time defined returns null
	public int getFrame(float t)
	{
		int i=0;
		while(i<key_frame.length)
		{
			if(key_frame[i].time==t)
			{
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	//Return Key Frame Array
	public Frame[] getKeyFrame()
	{
		return key_frame;
	}
	
	public float getActualTime()
	{
		return actual_time;
	}
	
	public float getMaxTime()
	{
		if(key_frame.length==0)
		{
			return 0;
		}
		return key_frame[key_frame.length-1].time;
	}
	
	public boolean setTexture(String text_file,int frame)
	{
		boolean texture_found=false;
		int texture_index=0;
		
		//Check if frame is in range
		if(frame<0 || frame>=key_frame.length)
		{
			return false;
		}
		
		//Check if texture already on texture list
		int i=0;
		while(i<texture_file.length)
		{
			if(texture_file[i].equals(text_file))
			{
				texture_index=i;
				texture_found=true;
				break;
			}
			i++;
		}
		
		//If the texture was not found add to the list
		if(!texture_found)
		{	
			//Check if texture file exits in the texture manager
			if(!animation.getTextureManager().existsTexture(text_file))
			{
				return false;
			}
			
			String[] temp = new String[texture_file.length+1];
			Texture[] tempt = new Texture[texture.length+1];
			i=0;
			
			//Add Texture to texture list
			while(i<texture_file.length)
			{
				//If already exists on bone dont add nothing
	
				temp[i]=texture_file[i];
				tempt[i]=texture[i];
				i++;
			}
			temp[i]=text_file;
			tempt[i]=animation.getTextureManager().getTexture(text_file);
			texture_index=i;
			
			texture_file=temp;
			texture=tempt;
		}
		
		key_frame[frame].texture=texture_index;
		
		return true;
	}
}
