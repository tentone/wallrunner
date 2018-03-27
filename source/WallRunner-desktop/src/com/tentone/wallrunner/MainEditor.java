package com.tentone.wallrunner;

import java.awt.BorderLayout;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.tentone.wallrunner.editor.EditorInterface;

public class MainEditor
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{	
		EditorInterface.ini();
		
		if(args.length!=0)
		{
			MainLevelEditor.canvas = new LwjglAWTCanvas(new MainLevelEditor(args[0]));
		}
		else
		{
			MainLevelEditor.canvas = new LwjglAWTCanvas(new MainLevelEditor(""));
		}
		
		
		while(true)
		{
			try
			{
				MainLevelEditor.canvas.getCanvas().setSize(EditorInterface.container.size().width,EditorInterface.container.size().height);
				EditorInterface.container.add(MainLevelEditor.canvas.getCanvas(),BorderLayout.CENTER);
				break;
			}
			catch(Exception e){}
		}
		
	}
}
