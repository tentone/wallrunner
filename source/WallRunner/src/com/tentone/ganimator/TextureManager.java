package com.tentone.ganimator;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureManager
{
	private String texture_path; //Bone Textures Folder Path
	private HashMap<String,Texture> texture; //Texture repository
	private Texture default_texture;
	
	//Texture Manager Constructor
	public TextureManager(String texture_path) throws Exception
	{
		this.texture_path=texture_path;
		
		//Add slash in order to acess textures by path later
		if(!(texture_path.endsWith("/") || texture_path.endsWith("\\")))
		{
			this.texture_path=this.texture_path+"/";
		}
		
		default_texture=new Texture(new Pixmap(1,1,Pixmap.Format.RGB888));
		loadTextures();
	}
	
	//Loads Texture from texture_list
	public void loadTextures() throws Exception
	{
		ArrayList<String> texture_list = new ArrayList<String>();
		FileHandle path=Gdx.files.internal(texture_path);
		FileHandle[] files = path.list();
		int i=0;
		
		//If no files found and running on desktop try to get folder as relative path
		if(files.length==0 && Gdx.app.getType()!=ApplicationType.Android)
		{
			this.texture_path="./bin/"+this.texture_path;
			path=Gdx.files.internal(texture_path);
			files = path.list();
		}
		
		if(files!=null)
		{
			while(i<files.length)
			{
				if(files[i].name().contains(".png") || files[i].name().contains(".bmp") || files[i].name().contains(".jpg"))
				{
					texture_list.add(files[i].name());
				}
				i++;
			}
			
			texture= new HashMap<String,Texture>();
			i=0;
			while(i<texture_list.size())
			{
				FileHandle file=Gdx.files.internal(texture_path+texture_list.get(i));
				Texture temp=new Texture(file);
				temp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				texture.put(texture_list.get(i),temp);
				i++;
			}
		}
	}
	
	//Returns texture list
	public String[] getTextureList()
	{
		return texture.keySet().toArray(new String[texture.size()]);
	}
	
	//Returns true if a texture exists
	public boolean existsTexture(String file_name)
	{
		if(texture.containsKey(file_name))
		{
			return true;
		}
		return false;
	}
	
	//Returns Texture if available, if not available returns null
	public Texture getTexture(String file_name)
	{
		if(texture.containsKey(file_name))
		{
			return texture.get(file_name);
		}
		return default_texture;
	}
	
	//Add external texture
	public void addTexture(Texture t,String fn)
	{
		texture.put(fn,t);
	}
	
	//Set new Texture Location
	public void setTexturePath(String texture_path) throws Exception
	{
		this.texture_path=texture_path;
	}
	
	//Returns Texture Path
	public String getTexturePath()
	{
		return texture_path;
	}
}
