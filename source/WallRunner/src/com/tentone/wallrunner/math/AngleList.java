package com.tentone.wallrunner.math;

import java.util.Iterator;

//Angle with list of angles sorted
public class AngleList
{	
	//Node Pointers and List Size
	public AngleNode first,last;
	public int size;
	
	//List Mode
	private boolean inverse_mode;
	
	public AngleList(boolean inverse_mode)
	{
		this.inverse_mode=inverse_mode;
		
		if(inverse_mode)
		{
			AngleNode temp = new AngleNode(new Angle(0,360));
			first=temp;
			last=temp;
			size=1;
		}
		else
		{
			first=null;
			last=null;
			size=0;
		}
	}

	//Add new element to list
	public void add(Angle a)
	{
		//Node(s) to insert in the list (or not), if angle bigger than 360 split into 2 separate angles
		AngleNode[] temp;
		int i=0;
		
		//Check if angle greater than 360 and split into 2 angles
		if(a.max>360)
		{
			temp = new AngleNode[2];
			temp[0] = new AngleNode(new Angle(0,a.max-360));
			temp[1] = new AngleNode(new Angle(a.min,360));
		}
		else
		{
			temp = new AngleNode[1];
			temp[0] = new AngleNode(a);
		}
		
		while(i<temp.length)
		{
			if(inverse_mode) //Remove Angle range From List
			{
				//If full angle remove everything from list
				if(temp[i].angle.min==0f && temp[i].angle.max==360f)
				{
					size=0;
					first=null;
					last=null;
					break;
				}
				
				AngleNode actual = first;
				AngleNode before = null;
									
				while(actual!=null)
				{
					//If inside another angle split that angle
					if(temp[i].angle.min>=actual.angle.min && temp[i].angle.max<=actual.angle.max)
					{
						float m=actual.angle.max;
						actual.angle.set(actual.angle.min,temp[i].angle.min);
						temp[i].angle.set(temp[i].angle.max,m);
						
						//Insert in the list
						temp[i].next=actual.next;
						actual.next=temp[i];
						
						size++;
						break;
					}
					else if(temp[i].angle.max>actual.angle.min && temp[i].angle.max<actual.angle.max && temp[i].angle.min<=actual.angle.min)
					{
						actual.angle.min=temp[i].angle.max;
					}
					else if(temp[i].angle.max>=actual.angle.max && temp[i].angle.min<actual.angle.max && temp[i].angle.min>=actual.angle.min)
					{
						actual.angle.max=temp[i].angle.min;
					}
					else if(temp[i].angle.min<actual.angle.min && temp[i].angle.max>actual.angle.max)//Object Covers full angle
					{
						if(before!=null)
						{
							before.next=actual.next;
						}
						else
						{
							first=actual.next;
							actual=actual.next;
						}
						size--;
					}
					
					before=actual;
					actual=actual.next;
				}
			}
			else //Normal Mode
			{
				if(size==0)
				{
					first=temp[i];
					last=temp[i];
					size++;
				}
				else
				{
					//Temporary Pointer to list elements
					AngleNode actual = first;
					AngleNode before = null;
					
					//Iterate all elements in the list
					while(actual!=null)
					{
						//Check if element can be merged into another
						if(temp[i].angle.min>=actual.angle.min && temp[i].angle.max<=actual.angle.max) //Angle inside another angle (ignore angle)
						{
							break;
						}
						else if(temp[i].angle.min<actual.angle.min && temp[i].angle.max>actual.angle.max) //Angle contains another angle
						{
							actual.angle.min=temp[i].angle.min;
							actual.angle.max=temp[i].angle.max;
							break;
						}
						else if(temp[i].angle.min<=actual.angle.min && temp[i].angle.max>=actual.angle.min && temp[i].angle.max<=actual.angle.max)
						{
							actual.angle.min=temp[i].angle.min;
							break;
						}
						else if(temp[i].angle.max>=actual.angle.max && temp[i].angle.min<=actual.angle.max && temp[i].angle.min>=actual.angle.min)
						{
							actual.angle.max=temp[i].angle.max;
							break;
						}
						else if(temp[i].angle.min>actual.angle.min && temp[i].angle.max>actual.angle.max)//If angle if bigger than actual angle add here
						{
							if(before==null)
							{
								first=temp[i];
							}
							else
							{
								before.next=temp[i];
							}
							temp[i].next=actual;
							size++;
							break;
						}
						else if(actual.next==null)//if is the end of the list insert there
						{
							actual.next=temp[i];
							last=temp[i];
							size++;
							break;
						}
						
						//Jump to next Element
						before=actual;
						actual=actual.next;
					}
				}
			}
			i++;
		}
	}
	
	//Reset List
	public void clear()
	{
		if(inverse_mode)
		{
			AngleNode temp = new AngleNode(new Angle(0,360));
			first=temp;
			last=temp;
			size=1;
		}
		else
		{
			size=0;
			first=null;
			last=null;
		}
	}
	
	//Iterator
	public AngleListIterator iterator()
	{
		return new AngleListIterator();
	}
	
	@Override
	public String toString()
	{
		AngleNode it=first;
		String s = "[";
		
		while(it!=null)
		{
			s+=it.angle.toString()+"|";
			it=it.next;
		}
		s+="]";
		
		return s;
	}
	
	private class AngleNode
	{
		public AngleNode next;
		public Angle angle;
		
		public AngleNode(Angle angle)
		{
			this.angle=angle;
			next=null;
		}
	}

	private class AngleListIterator implements Iterator<Angle>
	{
		AngleNode node;
		
		public AngleListIterator()
		{
			node=first;
		}
		
		public boolean hasNext()
		{
			return node!=null;
		}

		public Angle next()
		{
			Angle a = node.angle;
			node=node.next;
			return a;
		}
		
		public void remove(){}
	}
}