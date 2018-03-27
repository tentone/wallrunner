package com.tentone.wallrunner.input;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tentone.wallrunner.Global;

public class Button
{
	private Sprite sprite;
	
	public Button(int texture,float pos_x,float pos_y,float size_x,float size_y)
	{
		sprite = new Sprite(Global.texture[texture]);
		sprite.setPosition(pos_x, pos_y);
		sprite.setSize(size_x,size_y);
	}
	
	public Button(Sprite sprite,float pos_x,float pos_y,float size_x,float size_y)
	{
		this.sprite=sprite;
		this.sprite.setOrigin(0,0);
		this.sprite.setPosition(pos_x, pos_y);
		this.sprite.setSize(size_x, size_y);
	}
	
	public boolean isPressed()
	{
		float x=Global.camera.pos.x+(Global.touch_x-Global.camera.size_half.x),y=Global.camera.pos.y+(Global.touch_y-Global.camera.size_half.y);
		
		return (y>sprite.getY() && y<(sprite.getY()+sprite.getHeight())) && (x>sprite.getX() && x<(sprite.getX()+sprite.getWidth()));
	}
	
	public boolean isPressed(float x,float y)
	{
		return (y>sprite.getY() && y<(sprite.getY()+sprite.getHeight())) && (x>sprite.getX() && x<(sprite.getX()+sprite.getWidth()));
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}