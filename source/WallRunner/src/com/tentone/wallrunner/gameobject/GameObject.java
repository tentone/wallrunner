package com.tentone.wallrunner.gameobject;

public interface GameObject
{
	//Basic update() and draw() functions called on update and render loops
	public abstract void draw();
	public abstract void update();
	
	//Return dataString representing all object attributes in a single line
	public abstract String dataString();
	
	//Returns is a gameObject is active
	public boolean isActive();
}
