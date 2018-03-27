package com.tentone.wallrunner.math;

public class Angle implements Comparable<Angle>
{
	public float min,max;
	
	public Angle(float min, float max)
	{
		this.min=min;
		this.max=max;
	}
	
	public void set(float min, float max)
	{
		this.min=min;
		this.max=max;
	}
	
	@Override
	public String toString()
	{
		return "("+min+","+max+")";
	}
	
	@Override
	public int compareTo(Angle a)
	{
		return (int)(min-a.min);
	}
	
	//Returns Min And Max Angles From a List in Range from 0 to 360
	public static Float[] getMinAndMaxAngle(Float[] list)
	{
		Float[] values = new Float[2]; //0->min | 1->max
		int j=0;
		
		//Ini first element values[0] and values[1]
		if(list[0]!=null)
		{
			values[1]=list[0];
			values[0]=list[0];
		}
		else
		{
			values[1]=list[1];
			values[0]=list[1];
		}
		
		//Compare values[0] and values[1] angle
		j=0;
		while(j<list.length)
		{
			if(list[j]!=null)
			{
				if(list[j]>values[1])
				{
					values[1]=list[j];
				}
				if(list[j]<values[0])
				{
					values[0]=list[j];
				}
			}
			j++;
		}
		
		//If Invalid angle probably angle superior to 360 ... fix
		if(values[1]-values[0]>180)
		{
			float listemp=values[1];
			values[0]+=360;
			values[1]=values[0];
			values[0]=listemp;
			
			j=0;
			while(j<list.length)
			{
				if(list[j]!=null)
				{
					if(list[j]<180)
					{
						if(list[j]+360>values[1])
						{
							values[1]=list[j]+360;
						}
						if(list[j]+360<values[0])
						{
							values[0]=list[j]+360;
						}
					}
					else
					{
						if(list[j]>values[1])
						{
							values[1]=list[j];
						}
						if(list[j]<values[0])
						{
							values[0]=list[j];
						}
					}
				}
				j++;
			}
			
		}
		
		return values;
	}

}