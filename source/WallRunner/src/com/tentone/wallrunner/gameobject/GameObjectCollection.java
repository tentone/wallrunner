package com.tentone.wallrunner.gameobject;

public class GameObjectCollection implements GameObject
{
	private GameObject[] list;
	
	public GameObjectCollection()
	{
		list = new GameObject[0];
	}
	
	public GameObjectCollection(GameObject[] list)
	{
		this.list=list;
	}
	
	public static GameObject createFromDataString(String data)
	{
		return null;
	}
	
	public String dataString()
	{
		String temp=(list.length+1)+"||";
		int i=0;
		
		while(i<list.length)
		{
			temp+=list[i].dataString()+"||";
			i++;
		}
		
		return temp;
	}
	
	public void add(GameObject a)
	{
		GameObject[] temp = new GameObject[list.length+1];
		int i=0;
		
		while(i<list.length)
		{
			temp[i]=list[i];
			i++;
		}
		temp[i]=a;
		
		list=temp;
	}
	
	@Override
	public void draw()
	{
		int i=0;
		while(i<list.length)
		{
			list[i].draw();
			i++;
		}
	}

	@Override
	public void update()
	{
		int i=0;
		while(i<list.length)
		{
			list[i].update();
			i++;
		}
	}
	
	@Override
	public boolean isActive()
	{
		return true;
	}
}
