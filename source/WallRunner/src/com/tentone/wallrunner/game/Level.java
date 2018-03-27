package com.tentone.wallrunner.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.MainLevelEditor;
import com.tentone.wallrunner.gameobject.Background;
import com.tentone.wallrunner.gameobject.Decoration;
import com.tentone.wallrunner.gameobject.Enemy;
import com.tentone.wallrunner.gameobject.Light;
import com.tentone.wallrunner.gameobject.Particle;
import com.tentone.wallrunner.gameobject.Player;
import com.tentone.wallrunner.gameobject.ScriptedObject;
import com.tentone.wallrunner.gameobject.Wall;

//This class is being used as and extension of the main data pool cause im lazy
public class Level
{	
	//Level Objects
	public static Player player;
	public static Background[] background;
	public static Decoration[] decoration_front, decoration_back;
	public static Wall[] wall;
	public static Enemy[] enemy;
	public static Particle[] particle;
	public static ScriptedObject[] object;
	public static Light[] light;
	public static ShaderProgram[] shader;

	//Game Level Propieties
	public static int level_texture_set;
	public static Float level_zoom;
	public static Vector2 level_ori,level_size,player_spawn;
	
	//Level Render
	public static void draw()
	{
		int i=0;
		
		//Start Frame
		Global.camera.clearScreen();
		Global.camera.startFrame();
		
		//BackGround
		i=0;
		while(i<Global.background.length)
		{
			Global.background[i].draw();
			i++;
		}
		
		//Decoration Back
		i=0;
		while(i<Global.decoration_back.length)
		{
			Global.decoration_back[i].draw();
			i++;
		}
		
		//Walls
		i=0;
		while(i<Global.wall.length)
		{
			Global.wall[i].draw();
			i++;
		}
		
		//Objects
		i=0;
		while(i<Global.object.length)
		{
			Global.object[i].draw();
			i++;
		}
		
		//Enemys
		i=0;
		while(i<Global.enemy.length)
		{
			Global.enemy[i].draw();
			i++;
		}

		//Player
		Global.player.draw();

		//Decoration Front
		i=0;
		while(i<Global.decoration_front.length)
		{
			Global.decoration_front[i].draw();
			i++;
		}

		//Particles
		if(Global.def_graphics_particles)
		{
			i=0;
			while(i<Global.particle.length)
			{
				Global.particle[i].draw();
				i++;
			}
		}
		
		//TODO Remove this code
		/*i=0;
		while(i<animation.length)
		{
			animation[i].update(Gdx.graphics.getDeltaTime());
			animation[i].draw(Global.camera.batch);
			i++;
		}*/
		
		Global.camera.endFrame();
		
		//Light
		i=0;
		while(i<Global.light.length)
		{
			Global.light[i].draw();
			i++;
		}
	}
	
	//Add Game Elements Functions
	//Add Light to Array
	public static void addLight(Light a)
	{
		Light[] temp = new Light[Global.light.length+1];
		int i=0;

		while(i<Global.light.length)
		{
			temp[i]=Global.light[i];
			i++;
		}
		temp[i]=a;
		Global.light=temp;
	}
	
	//Add BackGround to Game Array
	public static void addGameBackground(Background a)
	{
		Background[] temp = new Background[Global.background.length+1];
		int i=0;

		while(i<Global.background.length)
		{
			temp[i]=Global.background[i];
			i++;
		}
		temp[i]=a;
		Global.background=temp;
	}

	//Add Decoration Elemnets to Decoration Front Array
	public static void addGameDecorFront(Decoration a)
	{
		Decoration[] wallt = new Decoration[Global.decoration_front.length+1];
		int i=0;

		while(i<Global.decoration_front.length)
		{
			wallt[i]=Global.decoration_front[i];
			i++;
		}

		wallt[i] = a;
		Global.decoration_front=wallt;
	}

	//Add Decoration Element to Decoration Back Array
	public static void addGameDecorBack(Decoration a)
	{
		Decoration[] wallt = new Decoration[Global.decoration_back.length+1];
		int i=0;

		while(i<Global.decoration_back.length)
		{
			wallt[i]=Global.decoration_back[i];
			i++;
		}

		wallt[i] = a;
		Global.decoration_back=wallt;
	}

	//Add Wall to Game Array
	public static void addGameWall(Wall a)
	{
		Wall[] wallt = new Wall[Global.wall.length+1];
		int i=0;

		while(i<Global.wall.length)
		{
			wallt[i]=Global.wall[i];
			i++;
		}

		wallt[i] = a;
		Global.wall=wallt;
	}

	//Add Enemy to Game Array
	public static void addGameEnemy(Enemy a)
	{
		Enemy[] wallt = new Enemy[Global.enemy.length+1];
		int i=0;

		while(i<Global.enemy.length)
		{
			wallt[i]=Global.enemy[i];
			i++;
		}

		wallt[i] = a;
		Global.enemy=wallt;
	}

	public static void addGamePart(Particle a)
	{
		Particle[] b = new Particle[Global.particle.length+1];
		int i=0;

		while(i<Global.particle.length)
		{
			b[i]=Global.particle[i];
			i++;
		}
		b[i]=a;
		Global.particle=b;
	}

	public static void addGameObject(ScriptedObject a)
	{
		ScriptedObject[] b = new ScriptedObject[Global.object.length+1];
		int i=0;

		while(i<Global.object.length)
		{
			b[i]=Global.object[i];
			i++;
		}
		b[i]=a;
		Global.object=b;
	}

	public static void removeDecorBack(int index)
	{
		Decoration[] tmp= new Decoration[Global.decoration_back.length-1];
		int j=0;
		while(j<tmp.length)
		{
			if(j<index)
			{
				tmp[j]=Global.decoration_back[j];
			}
			else
			{
				tmp[j]=Global.decoration_back[j+1];
			}
			j++;
		}
		Global.decoration_back=tmp;
	}

	public static void removeDecorFront(int index)
	{
		Decoration[] tmp= new Decoration[Global.decoration_front.length-1];
		int j=0;
		while(j<tmp.length)
		{
			if(j<index)
			{
				tmp[j]=Global.decoration_front[j];
			}
			else
			{
				tmp[j]=Global.decoration_front[j+1];
			}
			j++;
		}
		Global.decoration_front=tmp;
	}

	//Remove Light from Global.light array
	public static void removeLight(int index)
	{
		Light[] tmp= new Light[Global.light.length-1];
		int j=0;
		while(j<tmp.length)
		{
			if(j<index)
			{
				tmp[j]=Global.light[j];
			}
			else
			{
				tmp[j]=Global.light[j+1];
			}
			j++;
		}
		Global.light=tmp;
	}
	
	//Remove Wall from Global.wall array
	public static void removeWall(int index)
	{
		Wall[] tmp= new Wall[Global.wall.length-1];
		int j=0;
		while(j<tmp.length)
		{
			if(j<index)
			{
				tmp[j]=Global.wall[j];
			}
			else
			{
				tmp[j]=Global.wall[j+1];
			}
			j++;
		}
		Global.wall=tmp;
	}

	//Remove Enemr ffrom Global.enemy array
	public static void removeEnemy(int index)
	{
		Enemy[] tmp= new Enemy[Global.enemy.length-1];
		int j=0;
		while(j<tmp.length)
		{
			if(j<index)
			{
				tmp[j]=Global.enemy[j];
			}
			else
			{
				tmp[j]=Global.enemy[j+1];
			}
			j++;
		}
		Global.enemy=tmp;
	}	

	//Remove Object from Global.object Array
	public static void removeObject(int index)
	{
		int i=0;
		ScriptedObject[] tmp = new ScriptedObject[Global.object.length-1];

		while(i<tmp.length)
		{
			if(i<index)
			{
				tmp[i]=Global.object[i];
			}
			else
			{
				tmp[i]=Global.object[i+1];
			}
			i++;
		}

		Global.object=tmp;
	}

	//Remove Particle form Global.particle array
	public static void removeParticle(int index)
	{
		int i=0;
		Particle[] temp = new Particle[Global.particle.length-1];

		while(i<Global.particle.length)
		{
			if(i<index)
			{
				temp[i]=Global.particle[i];
			}
			else if(i>index)
			{
				temp[i-1]=Global.particle[i];
			}
			i++;
		}

		Global.particle=temp;
	}

	//Returns a list of id of the ScriptedObjects that use the same id "id"
	public static Integer[] getObjectIndex(String id)
	{
		Stack<Integer> a = new Stack<Integer>();

		int i=0;
		while(i<Global.object.length)
		{
			if(Global.object[i].id.equals(id))
			{
				a.push(i);
			}
			i++;
		}

		Integer[] r = new Integer[a.size()];
		a.toArray(r);

		return r;
	}
	//Creates new Level
	public static void newLevel()
	{
		Global.texture_set=0;
		Global.level_texture_set=0;
		Global.loadTextureSetFile(Global.texture_set_file[0]);
		Global.time=0;
		Global.player_spawn = new Vector2(0,0);
		Global.level_ori = new Vector2(0,0);
		Global.level_size = new Vector2(0,0);
		Global.camera.setZoom(1f);
		Global.camera.pos.set(0f,0f);
		Global.player= new Player(Global.player_spawn.x,Global.player_spawn.y,false);
		Global.background = new Background[0];
		Global.decoration_back = new Decoration[0];
		Global.decoration_front = new Decoration[0];
		Global.wall = new Wall[0];
		Global.enemy = new Enemy[0];
		Global.particle = new Particle[0];
		Global.object = new ScriptedObject[0];
		Global.light = new Light[0];
	}

	//Load Level from File(BufferedReader) to use in game
	public static void loadLevel(String file_name) throws Exception
	{
		BufferedReader reader=null;
		String[] temp;
		int i,j;

		reader = new BufferedReader(new InputStreamReader(Gdx.files.internal(file_name).read()));

		//Texture Set (Load only if isn't already loaded)
		level_texture_set=Integer.parseInt(reader.readLine());
		
		//If not running on editor load new texture set
		if(!Global.editor_mode && level_texture_set!=Global.texture_set)
		{
			Global.loadTextureSetFile(Global.texture_set_file[level_texture_set]);
			Global.texture_set=level_texture_set;
		}
		
		//Level
		player_spawn = new Vector2(Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()));
		level_ori = new Vector2(Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()));
		level_size = new Vector2(Float.parseFloat(reader.readLine()),Float.parseFloat(reader.readLine()));
		Global.time=0;

		//Camera
		temp=reader.readLine().split("\\|");
		try
		{
			Global.camera.size.y=Float.parseFloat(temp[0]);
			Global.camera.aspect_ratio=Global.def_graphics_aspect_ratio;
			Global.camera.pos.x=Float.parseFloat(temp[1]);
			Global.camera.pos.y=Float.parseFloat(temp[2]);
			Global.level_zoom=Float.parseFloat(temp[3]);
			Global.camera.setZoom(Global.level_zoom);
			Global.camera.camera_follow_player=temp[4].equals("true");
			Global.camera.margin_percent.x=Float.parseFloat(temp[5]);
			Global.camera.margin_percent.y=Float.parseFloat(temp[6]);
			Global.camera.limited_level_borders=temp[7].equals("true");
			Global.camera.updateInternalAtributes();
		}
		catch(Exception e)
		{
			if(Global.camera!=null)
			{
				Global.camera.dispose();
			}
			Global.camera = new Camera(Float.parseFloat(temp[0]),Global.def_graphics_aspect_ratio,Float.parseFloat(temp[1]),Float.parseFloat(temp[2]),Global.level_zoom=Float.parseFloat(temp[3]),temp[4].equals("true"),Float.parseFloat(temp[5]),Float.parseFloat(temp[6]),temp[7].equals("true"));
		}

		//Player
		player = Player.createFromDataString(reader.readLine());

		//Particles
		i=0;
		j=Integer.parseInt(reader.readLine());
		particle = new Particle[j];
		while(i<j)
		{
			particle[i] = Particle.createFromDataString(reader.readLine());
			i++;
		}

		//Wall
		i=0;
		j=Integer.parseInt(reader.readLine());
		wall= new Wall[j];
		while(i<j)
		{
			wall[i]= Wall.createFromDataString(reader.readLine());
			i++;
		}

		//Enemy
		i=0;
		j=Integer.parseInt(reader.readLine());
		enemy= new Enemy[j];
		while(i<j)
		{
			enemy[i]= Enemy.createFromDataString(reader.readLine());
			i++;
		}

		//Decoration Back
		i=0;
		j=Integer.parseInt(reader.readLine());
		decoration_back = new Decoration[j];
		while(i<j)
		{
			decoration_back[i]= Decoration.createFromDataString(reader.readLine());
			i++;
		}

		//Decoration Front
		i=0;
		j=Integer.parseInt(reader.readLine());
		decoration_front = new Decoration[j];
		while(i<j)
		{
			decoration_front[i]= Decoration.createFromDataString(reader.readLine());
			i++;
		}

		//Background
		i=0;
		j=Integer.parseInt(reader.readLine());
		background = new Background[j];
		while(i<j)
		{
			background[i]= Background.createFromDataString(reader.readLine());
			i++;
		}

		//Object
		i=0;
		j=Integer.parseInt(reader.readLine());
		object = new ScriptedObject[j];
		while(i<j)
		{
			object[i] = ScriptedObject.createFromDataString(reader.readLine());
			i++;
		}
		
		//Light
		i=0;
		j=Integer.parseInt(reader.readLine());
		light = new Light[j];
		while(i<j)
		{
			light[i] = Light.createFromDataString(reader.readLine());
			i++;
		}
	}
	
	//Save Level to File (File.io)
	public static void saveLevel(String file_name) throws Exception
	{
		PrintWriter pw;
		int i;

		pw = new PrintWriter(new File(file_name));

		//Texture Set
		pw.println(level_texture_set);

		//Level
		pw.println(player_spawn.x+"");
		pw.println(player_spawn.y+"");	
		pw.println(level_ori.x+"");
		pw.println(level_ori.y+"");
		pw.println(level_size.x+"");
		pw.println(level_size.y+"");

		//Set Zoom Value Back to 1 if needed
		if(Global.editor_mode && MainLevelEditor.reset_zoom_file)
		{
			Global.camera.setZoom(1f);
		}
		
		//Camera
		pw.println(Global.camera.size.y+"|"+Global.camera.pos.x+"|"+Global.camera.pos.y+"|"+Global.camera.getZoom()+"|"+Global.camera.camera_follow_player+"|"+Global.camera.margin_percent.x+"|"+Global.camera.margin_percent.y+"|"+Global.camera.limited_level_borders);

		//Player
		pw.println(player.dataString());

		//Particles
		pw.println(particle.length+"");
		i=0;
		while(i<particle.length)
		{
			pw.println(particle[i].dataString());
			pw.flush();
			i++;
		}

		//Wall
		pw.println(wall.length+"");
		i=0;
		while(i<wall.length)
		{
			pw.println(wall[i].dataString());
			pw.flush();
			i++;
		}

		//Enemy
		pw.println(enemy.length+"");
		i=0;
		while(i<enemy.length)
		{
			pw.println(enemy[i].dataString());
			pw.flush();
			i++;
		}

		//Decoration Back
		pw.println(decoration_back.length+"");
		i=0;
		while(i<decoration_back.length)
		{
			pw.println(decoration_back[i].dataString());
			pw.flush();
			i++;
		}

		//Decoration Front
		pw.println(decoration_front.length+"");
		i=0;
		while(i<decoration_front.length)
		{
			pw.println(decoration_front[i].dataString());
			pw.flush();
			i++;
		}

		//BackGround
		pw.println(background.length+"");
		i=0;
		while(i<background.length)
		{
			pw.println(background[i].dataString());
			pw.flush();
			i++;
		}

		//Object
		pw.println(object.length+"");
		i=0;
		while(i<object.length)
		{
			pw.println(object[i].dataString());
			pw.flush();
			i++;
		}
		
		//Light
		pw.println(light.length+"");
		i=0;
		while(i<light.length)
		{
			pw.println(light[i].dataString());
			pw.flush();
			i++;
		}
		pw.close();
	}
}
