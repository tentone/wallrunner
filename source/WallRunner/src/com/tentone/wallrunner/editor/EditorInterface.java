package com.tentone.wallrunner.editor;

import com.badlogic.gdx.Gdx;
import com.tentone.wallrunner.Global;
import com.tentone.wallrunner.MainLevelEditor;
import com.tentone.wallrunner.gameobject.Background;
import com.tentone.wallrunner.gameobject.Decoration;
import com.tentone.wallrunner.gameobject.Enemy;
import com.tentone.wallrunner.gameobject.Particle;
import com.tentone.wallrunner.gameobject.ScriptedObject;
import com.tentone.wallrunner.gameobject.Wall;
import com.tentone.wallrunner.gameobject.Light;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditorInterface extends JFrame
{
    public EditorInterface()
    {
        initComponents();
    }
    //Ini Components
    public static void ini()
    {
        //Set Look and Feel
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            
            System.exit(1);
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new EditorInterface().setVisible(true);
            }
        });
    }
    
    //Called after Ini and after Editor Window is ready
    public static void start()
    {
        updateTextureList();
        updateInterfaceElements();
    }
    
    public void editorSettingsFrameUpdateInterface()
    {
        editor_settings_zoom.setSelected(MainLevelEditor.reset_zoom_file);
        editor_settings_fps.setSelectedIndex(MainLevelEditor.fps_lock);
    }
    
    public void updateLayerList()
    {
        int i=0;
        working_layer_selector.removeAllItems();
        while(i<MainLevelEditor.working_layer_name.length)
        {
            working_layer_selector.addItem(MainLevelEditor.working_layer_name[i]);
            i++;
        }
    }
    
    public static void updateTextureList()
    {
        int i=0;
        
        wall_texture_combobox.removeAllItems();
        enemy_texture_combobox.removeAllItems();
        decoration_texture_combobox.removeAllItems();
        part_texture_combobox.removeAllItems();
        object_texture_combobox.removeAllItems();
        
        while(i<Global.texture_file.length)
        {
            wall_texture_combobox.addItem(Global.texture_file[i]);
            enemy_texture_combobox.addItem(Global.texture_file[i]);
            decoration_texture_combobox.addItem(Global.texture_file[i]);
            part_texture_combobox.addItem(Global.texture_file[i]);
            background_texture_combobox.addItem(Global.texture_file[i]);
            object_texture_combobox.addItem(Global.texture_file[i]);
            i++;
        }
    }
    
    public static void updateMenuBar()
    {
        if(MainLevelEditor.grid_on)
        {
            menu_show_grid.setText("Hide Grid");
        }
        else
        {
            menu_show_grid.setText("Show Grid");
        }

        if(MainLevelEditor.draw_level_limit)
        {
            menu_show_level_limit.setText("Hide Level Limit");
        }
        else
        {
            menu_show_level_limit.setText("Show Level Limit");
        }

        if(MainLevelEditor.draw_col_box)
        {
            menu_show_col_box.setText("Hide Colision Box");
        }
        else
        {
            menu_show_col_box.setText("Show Colision Box");
        }

        if(MainLevelEditor.draw_object_cursor)
        {
            menu_show_cursor.setText("Hide Object Cursor");
        }
        else
        {
            menu_show_cursor.setText("Show Object Cursor");
        }

        if(MainLevelEditor.draw_trajectory_lines)
        {
            menu_show_trajectory.setText("Hide Trajectory Lines");
        }
        else
        {
            menu_show_trajectory.setText("Show Trajectory Lines");
        }

        if(MainLevelEditor.draw_particle_emiter)
        {
            menu_show_particle_emiter.setText("Hide Particle Emiter");
        }
        else
        {
            menu_show_particle_emiter.setText("Show Particle Emiter");
        }

        //Menu Editor Text
        if(MainLevelEditor.freeze_level)
        {
            menu_level_freeze.setText("Unfreeze Level");
        }
        else
        {
            menu_level_freeze.setText("Freeze Level");
        }
        if(MainLevelEditor.snap_to_grid)
        {
            menu_editor_snap.setText("Disable Snap to Grid");
        }
        else
        {
            menu_editor_snap.setText("Enable Snap to Grid");
        }
        if(MainLevelEditor.avoid_overlaping)
        {
            menu_editor_avoid_overlaping.setText("Disable Avoid Overlaping");
        }
        else
        {
            menu_editor_avoid_overlaping.setText("Enable Avoid Overlaping");
        }
        if(MainLevelEditor.single_click_mode)
        {
            menu_editor_click.setText("Disable Single Click Mode");
        }
        else
        {
            menu_editor_click.setText("Enable Single Click Mode");
        }
    }
    
    public static void updateScriptList()
    {
        object_script.removeAllItems();
        int i = 0;
        while (i <ScriptedObject.script_list.length)
        {
            object_script.addItem(ScriptedObject.script_list[i]);
            i++;
        }
    }
    
    public static void updateObjectList()
    {
        DefaultListModel model;
        int i=0;
        model = new DefaultListModel();
        while(i<Global.object.length)
        {
            model.addElement("["+i+"] "+ScriptedObject.script_list[Global.object[i].script]+" | (ID:"+Global.object[i].id+") | ("+Global.object[i].pos.x+","+Global.object[i].pos.y+")");
            i++;
        }
        object_list.setModel(model);
    }
    
    public static void updateParticleList()
    {
        DefaultListModel model;
        int i=0;
        model = new DefaultListModel();
        while(i<Global.particle.length)
        {
                        model.addElement("["+i+"] "+Global.texture_file[Global.particle[i].texture]+" | ("+Global.particle[i].pos.x+","+Global.particle[i].pos.y+")");
                        i++;
        }
        part_list.setModel(model);
    }
    
    public static void updateBackgroundList()
    {
        DefaultListModel model;
        int i=0;
        model = new DefaultListModel();
        while(i<Global.background.length)
        {
            model.addElement("["+i+"] "+Global.texture_file[Global.background[i].texture]+") | "+Global.background[i].repeat_mode);
            i++;
        }
        back_list.setModel(model);
    }
    
    public static void updateWallElements()
    {
        wall_texture_combobox.setSelectedIndex(MainLevelEditor.temp_wall.texture);
        updateWallElementsSize();
        wall_ori_x.setText(MainLevelEditor.temp_wall.ori.x+"");
        wall_ori_y.setText(MainLevelEditor.temp_wall.ori.y+"");
        wall_move_mode.setSelectedIndex(MainLevelEditor.temp_wall.move_mode);
        wall_move_limit.setText(MainLevelEditor.temp_wall.move_limit+"");
        wall_move_speed.setText(MainLevelEditor.temp_wall.move_speed+"");
        wall_angular_speed.setText(MainLevelEditor.temp_wall.angular_speed+"");
    }
    
    public static void updateWallElementsSize()
    {
        wall_size_x.setText(MainLevelEditor.temp_wall.sprite.getWidth()+"");
        wall_size_y.setText(MainLevelEditor.temp_wall.sprite.getHeight()+"");
        wall_col_box_x.setText(MainLevelEditor.temp_wall.col_box.x+"");
        wall_col_box_y.setText(MainLevelEditor.temp_wall.col_box.y+"");
    }
    
    public static void updateEnemyElements()
    {
        enemy_texture_combobox.setSelectedIndex(MainLevelEditor.temp_enemy.texture);
        updateEnemyElementsSize();
        enemy_ori_x.setText(MainLevelEditor.temp_enemy.ori.x+"");
        enemy_ori_y.setText(MainLevelEditor.temp_enemy.ori.y+"");
        enemy_col_box_type.setSelectedIndex(MainLevelEditor.temp_enemy.colision_box_type);
        enemy_move_mode.setSelectedIndex(MainLevelEditor.temp_enemy.move_mode);
        enemy_move_limit.setText(MainLevelEditor.temp_enemy.move_limit+"");
        enemy_move_speed.setText(MainLevelEditor.temp_enemy.move_speed+"");
        enemy_angular_speed.setText(MainLevelEditor.temp_enemy.angular_speed+"");
    }
    
    public static void updateEnemyElementsSize()
    {
        enemy_size_x.setText(MainLevelEditor.temp_enemy.sprite.getWidth()+"");
        enemy_size_y.setText(MainLevelEditor.temp_enemy.sprite.getHeight()+"");
        enemy_col_box_x.setText(MainLevelEditor.temp_enemy.col_box.x+"");
        enemy_col_box_y.setText(MainLevelEditor.temp_enemy.col_box.y+"");
    }
    
    public static void updateDecorationElements()
    {
        decoration_texture_combobox.setSelectedIndex(MainLevelEditor.temp_decoration.texture);
        updateDecorationElementsSize();
        decoration_ori_x.setText(MainLevelEditor.temp_decoration.ori.x+"");
        decoration_ori_y.setText(MainLevelEditor.temp_decoration.ori.y+"");
        decoration_move_mode.setSelectedIndex(MainLevelEditor.temp_decoration.move_mode);
        decoration_move_limit.setText(MainLevelEditor.temp_decoration.move_limit+"");
        decoration_move_speed.setText(MainLevelEditor.temp_decoration.move_speed+"");
        decoration_angular_speed.setText(MainLevelEditor.temp_decoration.angular_speed+"");
    }
    
    public static void updateDecorationElementsSize()
    {
        decoration_size_x.setText(MainLevelEditor.temp_decoration.sprite.getWidth()+"");
        decoration_size_y.setText(MainLevelEditor.temp_decoration.sprite.getHeight()+"");
    }
    
    public static void updateObjectElements()
    {
        object_texture_combobox.setSelectedIndex(MainLevelEditor.temp_object.texture);
        updateObjectElementsSize();
        object_ori_x.setText(MainLevelEditor.temp_object.ori.x+"");
        object_ori_y.setText(MainLevelEditor.temp_object.ori.y+"");
        object_script.setSelectedIndex(MainLevelEditor.temp_object.script);
        object_id.setText(MainLevelEditor.temp_object.id);
    }

    public static void updateObjectElementsSize()
    {
        object_size_x.setText(MainLevelEditor.temp_object.sprite.getWidth()+"");
        object_size_y.setText(MainLevelEditor.temp_object.sprite.getHeight()+"");
        object_col_box_x.setText(MainLevelEditor.temp_object.col_box.x+"");
        object_col_box_y.setText(MainLevelEditor.temp_object.col_box.y+"");
    }
    
    public static void updatePlayerSpawn()
    {
        player_spawn_x.setText(Global.player_spawn.x+"");
        player_spawn_y.setText(Global.player_spawn.y+"");
    }
    
    public static void updateLevelSize()
    {
        level_ori_x.setText(Global.level_ori.x+"");
        level_ori_y.setText(Global.level_ori.y+"");
        level_size_x.setText(Global.level_size.x+"");
        level_size_y.setText(Global.level_size.y+"");
    }
    
    public static void updateLevelZoom()
    {
        camera_zoom.setText(Global.camera.getZoom()+"");
    }
    
    public static void updateLevelElements()
    {
        updatePlayerSpawn();
        updateLevelSize();
        camera_follow_player.setSelected(Global.camera.camera_follow_player);
        camera_limited_level.setSelected(Global.camera.limited_level_borders);
        updateLevelZoom();
        camera_border_x.setValue((int)(Global.camera.margin_percent.x*100));
        camera_border_y.setValue((int)(Global.camera.margin_percent.y*100));
        player_destroy_outside_level.setSelected(Global.player.die_out_of_level);
    }

    
    public static void updateWorkingLayer()
    {
        working_layer_selector.setSelectedIndex(MainLevelEditor.working_layer);
    }
    
     public static void updateInterfaceElements()
     {
        DefaultListModel model;
        int i;
        
        try
        {            
            //Menu View Options Text
            updateMenuBar();
            
            //Scripts List
            updateScriptList();
            
            //Object List
            updateObjectList();
            
            //Particle List
            updateParticleList();
            
            //BackGround List
            updateBackgroundList();
            
            //Wall
            updateWallElements();
            
            //Enemy
            updateEnemyElements();

            //Decoration
            updateDecorationElements();
            
            //Object
            updateObjectElements();
            
            //Level and Camera Tab
            updateLevelElements();
            
            //Working Layer
            updateWorkingLayer();
        }
        catch(Exception e){}
     }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        editor_settings_frame = new javax.swing.JFrame();
        editor_settings_okay = new javax.swing.JButton();
        editor_settings_zoom = new javax.swing.JCheckBox();
        editor_settings_fps = new javax.swing.JComboBox();
        editor_settings_text_fps_lock = new javax.swing.JLabel();
        container = new java.awt.Panel();
        side_panel = new javax.swing.JPanel();
        working_layer_selector_text = new javax.swing.JLabel();
        working_layer_selector = new javax.swing.JComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        editor_mode_selector = new javax.swing.JTabbedPane();
        editor_object_edit_tab = new javax.swing.JPanel();
        side_panel_tab = new javax.swing.JTabbedPane();
        wall_tab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        wall_texture_combobox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        wall_size_x = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        wall_size_y = new javax.swing.JTextField();
        wall_ori_x = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        wall_ori_y = new javax.swing.JTextField();
        wall_col_box_x = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        wall_col_box_y = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        wall_move_mode = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        wall_move_limit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        wall_move_speed = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        wall_angular_speed = new javax.swing.JTextField();
        wall_set_object = new javax.swing.JButton();
        enemy_tab = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        enemy_angular_speed = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        enemy_move_limit = new javax.swing.JTextField();
        enemy_move_speed = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        enemy_move_mode = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        enemy_col_box_x = new javax.swing.JTextField();
        enemy_col_box_y = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        enemy_ori_x = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        enemy_ori_y = new javax.swing.JTextField();
        enemy_size_y = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        enemy_size_x = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        enemy_texture_combobox = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        enemy_col_box_type = new javax.swing.JComboBox();
        enemy_set_object = new javax.swing.JButton();
        decoration_tab = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        decoration_angular_speed = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        decoration_move_limit = new javax.swing.JTextField();
        decoration_move_speed = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        decoration_move_mode = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        decoration_ori_x = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        decoration_ori_y = new javax.swing.JTextField();
        decoration_size_y = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        decoration_size_x = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        decoration_texture_combobox = new javax.swing.JComboBox();
        decoration_set_object = new javax.swing.JButton();
        object_tab = new javax.swing.JPanel();
        object_texture_combobox = new javax.swing.JComboBox();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        object_size_x = new javax.swing.JTextField();
        object_ori_x = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        object_ori_y = new javax.swing.JTextField();
        object_size_y = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        object_col_box_x = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        object_col_box_y = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        object_script = new javax.swing.JComboBox();
        object_set = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        object_id = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel85 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        object_list = new javax.swing.JList();
        object_delete = new javax.swing.JButton();
        object_get = new javax.swing.JButton();
        particles_tab = new javax.swing.JPanel();
        part_texture_combobox = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        part_size_x = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        part_size_y = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        part_emiter_type = new javax.swing.JComboBox();
        part_pos_x = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        part_pos_y = new javax.swing.JTextField();
        part_emiter_box_x = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        part_emiter_box_y = new javax.swing.JTextField();
        part_direction = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        part_direction_var = new javax.swing.JTextField();
        part_speed = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        part_speed_var = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        part_lifetime = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        part_lifetime_var = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        part_count = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        part_angular_speed = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        part_add = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        part_delete = new javax.swing.JButton();
        part_get = new javax.swing.JButton();
        part_aditive = new javax.swing.JCheckBox();
        part_one_burst = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        part_list = new javax.swing.JList();
        part_set = new javax.swing.JButton();
        light_tab = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        light_intensity = new javax.swing.JSlider();
        jLabel87 = new javax.swing.JLabel();
        light_range = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        light_color_r = new javax.swing.JSlider();
        light_color_g = new javax.swing.JSlider();
        light_set = new javax.swing.JButton();
        light_color_b = new javax.swing.JSlider();
        light_shadow_wall = new javax.swing.JCheckBox();
        light_shadow_player = new javax.swing.JCheckBox();
        light_shadow_enemy = new javax.swing.JCheckBox();
        light_shadow_decor_back = new javax.swing.JCheckBox();
        jLabel92 = new javax.swing.JLabel();
        light_shadow_decor_front = new javax.swing.JCheckBox();
        editor_background_edit_tab = new javax.swing.JPanel();
        background_texture_combobox = new javax.swing.JComboBox();
        jLabel56 = new javax.swing.JLabel();
        back_size_y = new javax.swing.JTextField();
        back_pos_y = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        back_size_x = new javax.swing.JTextField();
        back_pos_x = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        back_repeat = new javax.swing.JComboBox();
        jLabel69 = new javax.swing.JLabel();
        back_parallax_x = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        back_parallax_y = new javax.swing.JTextField();
        back_add = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel71 = new javax.swing.JLabel();
        back_get = new javax.swing.JButton();
        back_delete = new javax.swing.JButton();
        canvas1 = new java.awt.Canvas();
        back_list_up = new javax.swing.JButton();
        back_list_down = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        back_list = new javax.swing.JList();
        jLabel93 = new javax.swing.JLabel();
        editor_level_editor_tab = new javax.swing.JPanel();
        level_set_but = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel73 = new javax.swing.JLabel();
        player_spawn_y = new javax.swing.JTextField();
        player_destroy_outside_level = new javax.swing.JCheckBox();
        player_spawn_x = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        camera_border_y = new javax.swing.JSlider();
        jLabel63 = new javax.swing.JLabel();
        camera_border_x = new javax.swing.JSlider();
        jLabel62 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        camera_zoom = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        camera_limited_level = new javax.swing.JCheckBox();
        camera_follow_player = new javax.swing.JCheckBox();
        jLabel59 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        level_ori_x = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        level_ori_y = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        level_size_y = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        level_size_x = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        menu_bar = new javax.swing.JMenuBar();
        menu_file = new javax.swing.JMenu();
        menu_file_new = new javax.swing.JMenuItem();
        menu_file_save = new javax.swing.JMenuItem();
        menu_file_load = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menu_file_exit = new javax.swing.JMenuItem();
        menu_editor = new javax.swing.JMenu();
        menu_editor_click = new javax.swing.JMenuItem();
        menu_editor_snap = new javax.swing.JMenuItem();
        menu_editor_avoid_overlaping = new javax.swing.JMenuItem();
        menu_editor_grid_size = new javax.swing.JMenuItem();
        menu_editor_grid_offset = new javax.swing.JMenuItem();
        menu_level = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menu_level_freeze = new javax.swing.JMenuItem();
        menu_level_reset = new javax.swing.JMenuItem();
        menu_view = new javax.swing.JMenu();
        menu_show_col_box = new javax.swing.JMenuItem();
        menu_show_particle_emiter = new javax.swing.JMenuItem();
        menu_show_trajectory = new javax.swing.JMenuItem();
        menu_show_cursor = new javax.swing.JMenuItem();
        menu_show_level_limit = new javax.swing.JMenuItem();
        menu_show_grid = new javax.swing.JMenuItem();

        editor_settings_frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        editor_settings_frame.setTitle("Editor Settings");
        editor_settings_frame.setAlwaysOnTop(true);
        editor_settings_frame.setMinimumSize(new java.awt.Dimension(350, 150));
        editor_settings_frame.setType(java.awt.Window.Type.UTILITY);

        editor_settings_okay.setText("Ok");
        editor_settings_okay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor_settings_okayActionPerformed(evt);
            }
        });

        editor_settings_zoom.setSelected(true);
        editor_settings_zoom.setText("Reset Level Zoom to Save File");
        editor_settings_zoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor_settings_zoomActionPerformed(evt);
            }
        });

        editor_settings_fps.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Off", "60", "120" }));
        editor_settings_fps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor_settings_fpsActionPerformed(evt);
            }
        });

        editor_settings_text_fps_lock.setText("Editor FPS Lock:");

        javax.swing.GroupLayout editor_settings_frameLayout = new javax.swing.GroupLayout(editor_settings_frame.getContentPane());
        editor_settings_frame.getContentPane().setLayout(editor_settings_frameLayout);
        editor_settings_frameLayout.setHorizontalGroup(
            editor_settings_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editor_settings_frameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editor_settings_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editor_settings_frameLayout.createSequentialGroup()
                        .addGroup(editor_settings_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_settings_frameLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editor_settings_okay))
                            .addGroup(editor_settings_frameLayout.createSequentialGroup()
                                .addComponent(editor_settings_zoom)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_settings_frameLayout.createSequentialGroup()
                        .addComponent(editor_settings_text_fps_lock)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editor_settings_fps, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
        );
        editor_settings_frameLayout.setVerticalGroup(
            editor_settings_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_settings_frameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editor_settings_zoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_settings_frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editor_settings_fps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editor_settings_text_fps_lock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editor_settings_okay)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Wallrunner Editor V"+Global.version_editor);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setIconImages(null);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(900, 550));
        setName("editor"); // NOI18N
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        container.setPreferredSize(new java.awt.Dimension(423, 600));
        container.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                containerComponentResized(evt);
            }
        });

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        working_layer_selector_text.setText("Working Layer");

        working_layer_selector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wall", "Enemy", "Decoration Front", "Decoration Back", "Object", "Particles", "Light" }));
        working_layer_selector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                working_layer_selectorMouseClicked(evt);
            }
        });
        working_layer_selector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                working_layer_selectorActionPerformed(evt);
            }
        });
        working_layer_selector.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                working_layer_selectorPropertyChange(evt);
            }
        });

        editor_mode_selector.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        side_panel_tab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                side_panel_tabStateChanged(evt);
            }
        });

        wall_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                wall_tabComponentShown(evt);
            }
        });

        jLabel1.setText("Texture");

        wall_texture_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                wall_texture_comboboxItemStateChanged(evt);
            }
        });

        jLabel2.setText("Size");

        wall_size_x.setText("32");
        wall_size_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wall_size_xActionPerformed(evt);
            }
        });

        jLabel3.setText("x");

        wall_size_y.setText("32");

        wall_ori_x.setText("0");

        jLabel4.setText("Ori");

        jLabel5.setText("x");

        wall_ori_y.setText("0");

        wall_col_box_x.setText("32");

        jLabel6.setText("Colision Box");

        jLabel7.setText("x");

        wall_col_box_y.setText("32");

        jLabel8.setText("Move Mode");

        wall_move_mode.setModel(new javax.swing.DefaultComboBoxModel(Wall.move_mode_list));
        wall_move_mode.setToolTipText("");

        jLabel9.setText("Move Limit");

        wall_move_limit.setText("0");

        jLabel10.setText("Move Speed");

        wall_move_speed.setText("0");

        jLabel12.setText("Angular Speed");

        wall_angular_speed.setText("0");

        wall_set_object.setText("Set");
        wall_set_object.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wall_set_objectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wall_tabLayout = new javax.swing.GroupLayout(wall_tab);
        wall_tab.setLayout(wall_tabLayout);
        wall_tabLayout.setHorizontalGroup(
            wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wall_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wall_tabLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wall_texture_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(wall_tabLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wall_move_mode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(wall_tabLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wall_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wall_move_speed))
                    .addGroup(wall_tabLayout.createSequentialGroup()
                        .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(wall_tabLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(wall_tabLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(wall_tabLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(wall_tabLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(wall_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 104, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wall_tabLayout.createSequentialGroup()
                        .addGap(0, 251, Short.MAX_VALUE)
                        .addComponent(wall_set_object)))
                .addContainerGap())
        );
        wall_tabLayout.setVerticalGroup(
            wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wall_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(wall_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(wall_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(wall_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(wall_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(wall_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(wall_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(wall_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(wall_move_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(wall_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(wall_move_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wall_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(wall_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wall_set_object)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        side_panel_tab.addTab("Wall", wall_tab);

        enemy_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                enemy_tabComponentShown(evt);
            }
        });

        jLabel13.setText("Angular Speed");

        enemy_angular_speed.setText("0");

        jLabel14.setText("Move Limit");

        enemy_move_limit.setText("0");

        enemy_move_speed.setText("0");

        jLabel15.setText("Move Speed");

        enemy_move_mode.setModel(new javax.swing.DefaultComboBoxModel(Enemy.move_mode_list));
        enemy_move_mode.setToolTipText("");
        enemy_move_mode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemy_move_modeActionPerformed(evt);
            }
        });

        jLabel16.setText("Move Mode");

        jLabel17.setText("Colision Box");

        enemy_col_box_x.setText("32");

        enemy_col_box_y.setText("32");
        enemy_col_box_y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemy_col_box_yActionPerformed(evt);
            }
        });

        jLabel18.setText("Ori");

        enemy_ori_x.setText("0");

        jLabel19.setText("x");

        enemy_ori_y.setText("0");

        enemy_size_y.setText("32");

        jLabel20.setText("x");

        enemy_size_x.setText("32");

        jLabel21.setText("Size");

        jLabel22.setText("Texture");

        enemy_texture_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                enemy_texture_comboboxItemStateChanged(evt);
            }
        });
        enemy_texture_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemy_texture_comboboxActionPerformed(evt);
            }
        });

        jLabel27.setText("x");

        jLabel60.setText("Colision Box Type");

        enemy_col_box_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Box", "Circular" }));
        enemy_col_box_type.setToolTipText("");

        enemy_set_object.setText("Set");
        enemy_set_object.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enemy_set_objectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout enemy_tabLayout = new javax.swing.GroupLayout(enemy_tab);
        enemy_tab.setLayout(enemy_tabLayout);
        enemy_tabLayout.setHorizontalGroup(
            enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enemy_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(enemy_tabLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemy_texture_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(enemy_tabLayout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemy_col_box_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(enemy_tabLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemy_move_mode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(enemy_tabLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemy_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemy_move_speed))
                    .addGroup(enemy_tabLayout.createSequentialGroup()
                        .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(enemy_tabLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(enemy_tabLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(enemy_tabLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(enemy_tabLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enemy_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enemy_tabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(enemy_set_object)))
                .addContainerGap())
        );
        enemy_tabLayout.setVerticalGroup(
            enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enemy_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(enemy_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(enemy_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(enemy_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(enemy_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(enemy_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(enemy_col_box_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(enemy_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enemy_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(enemy_move_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(enemy_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(enemy_move_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enemy_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(enemy_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enemy_set_object)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        side_panel_tab.addTab("Enemy", enemy_tab);

        decoration_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                decoration_tabComponentShown(evt);
            }
        });

        jLabel23.setText("Angular Speed");

        decoration_angular_speed.setText("0");

        jLabel24.setText("Move Limit");

        decoration_move_limit.setText("0");
        decoration_move_limit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decoration_move_limitActionPerformed(evt);
            }
        });

        decoration_move_speed.setText("0");

        jLabel25.setText("Move Speed");

        decoration_move_mode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Static", "Horizontal Move", "Vertical Move", "Circular Trajectory" }));
        decoration_move_mode.setToolTipText("");

        jLabel26.setText("Move Mode");

        jLabel28.setText("Ori");

        decoration_ori_x.setText("0");

        jLabel29.setText("x");

        decoration_ori_y.setText("0");

        decoration_size_y.setText("32");

        jLabel30.setText("x");

        decoration_size_x.setText("32");
        decoration_size_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decoration_size_xActionPerformed(evt);
            }
        });

        jLabel31.setText("Size");

        jLabel32.setText("Texture");

        decoration_texture_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                decoration_texture_comboboxItemStateChanged(evt);
            }
        });
        decoration_texture_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decoration_texture_comboboxActionPerformed(evt);
            }
        });

        decoration_set_object.setText("Set");
        decoration_set_object.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decoration_set_objectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout decoration_tabLayout = new javax.swing.GroupLayout(decoration_tab);
        decoration_tab.setLayout(decoration_tabLayout);
        decoration_tabLayout.setHorizontalGroup(
            decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(decoration_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(decoration_tabLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(decoration_texture_combobox, 0, 258, Short.MAX_VALUE))
                    .addGroup(decoration_tabLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(decoration_move_mode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(decoration_tabLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(decoration_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(decoration_move_speed, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                    .addGroup(decoration_tabLayout.createSequentialGroup()
                        .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(decoration_tabLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(decoration_tabLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(decoration_tabLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(decoration_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, decoration_tabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(decoration_set_object)))
                .addContainerGap())
        );
        decoration_tabLayout.setVerticalGroup(
            decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(decoration_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(decoration_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(decoration_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(decoration_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(decoration_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(decoration_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(decoration_move_mode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(decoration_move_limit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(decoration_move_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(decoration_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(decoration_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decoration_set_object)
                .addContainerGap(289, Short.MAX_VALUE))
        );

        side_panel_tab.addTab("Decoration", decoration_tab);

        object_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                object_tabComponentShown(evt);
            }
        });

        object_texture_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                object_texture_comboboxItemStateChanged(evt);
            }
        });
        object_texture_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_texture_comboboxActionPerformed(evt);
            }
        });

        jLabel74.setText("Texture");

        jLabel75.setText("Size");

        object_size_x.setText("32");
        object_size_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_size_xActionPerformed(evt);
            }
        });

        object_ori_x.setText("0");

        jLabel76.setText("Ori");

        object_ori_y.setText("0");

        object_size_y.setText("32");

        jLabel77.setText("x");

        jLabel78.setText("x");

        jLabel79.setText("Colision Box");

        object_col_box_x.setText("32");

        jLabel80.setText("x");

        object_col_box_y.setText("32");
        object_col_box_y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_col_box_yActionPerformed(evt);
            }
        });

        jLabel81.setText("Script");

        object_script.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_scriptActionPerformed(evt);
            }
        });

        object_set.setText("Set");
        object_set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_setActionPerformed(evt);
            }
        });

        jLabel82.setText("ID");

        object_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_idActionPerformed(evt);
            }
        });

        jLabel85.setText("Object List");

        object_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(object_list);

        object_delete.setText("Delete");
        object_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_deleteActionPerformed(evt);
            }
        });

        object_get.setText("Get Values");
        object_get.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                object_getActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout object_tabLayout = new javax.swing.GroupLayout(object_tab);
        object_tab.setLayout(object_tabLayout);
        object_tabLayout.setHorizontalGroup(
            object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5)
            .addGroup(object_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(object_tabLayout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(object_texture_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(object_tabLayout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(object_script, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(object_tabLayout.createSequentialGroup()
                        .addComponent(jLabel82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(object_id))
                    .addGroup(object_tabLayout.createSequentialGroup()
                        .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(object_tabLayout.createSequentialGroup()
                                .addComponent(jLabel75)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel77)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(object_tabLayout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(object_tabLayout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel80)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel85))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, object_tabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(object_set, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, object_tabLayout.createSequentialGroup()
                                .addComponent(object_get)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(object_delete)))))
                .addContainerGap())
        );
        object_tabLayout.setVerticalGroup(
            object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(object_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(object_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(object_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(object_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(object_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78)
                    .addComponent(object_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(object_col_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(object_col_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(object_script, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel82)
                    .addComponent(object_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(object_set)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(object_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(object_delete)
                    .addComponent(object_get))
                .addGap(15, 15, 15))
        );

        side_panel_tab.addTab("Object", object_tab);

        particles_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                particles_tabComponentShown(evt);
            }
        });

        part_texture_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                part_texture_comboboxItemStateChanged(evt);
            }
        });
        part_texture_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_texture_comboboxActionPerformed(evt);
            }
        });

        jLabel33.setText("Texture");

        jLabel34.setText("Size");

        part_size_x.setText("32");

        jLabel35.setText("x");

        part_size_y.setText("32");

        jLabel36.setText("Emiter Type");

        part_emiter_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Point", "Rectangular" }));
        part_emiter_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_emiter_typeActionPerformed(evt);
            }
        });

        part_pos_x.setText("0");
        part_pos_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_pos_xActionPerformed(evt);
            }
        });

        jLabel37.setText("Pos");

        jLabel38.setText("x");

        part_pos_y.setText("0");

        part_emiter_box_x.setText("0");
        part_emiter_box_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_emiter_box_xActionPerformed(evt);
            }
        });

        jLabel39.setText("Emiter Box");

        jLabel40.setText("x");

        part_emiter_box_y.setText("0");

        part_direction.setText("0");

        jLabel41.setText("Particle Direction");

        jLabel42.setText("+");

        part_direction_var.setText("0");

        part_speed.setText("0");

        jLabel43.setText("Particle Speed");

        jLabel44.setText("+/-");

        part_speed_var.setText("0");

        jLabel45.setText("Particle Lifetime");

        part_lifetime.setText("0");

        jLabel46.setText("+/-");

        part_lifetime_var.setText("0");
        part_lifetime_var.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_lifetime_varActionPerformed(evt);
            }
        });

        jLabel47.setText("Particle Count");

        part_count.setText("0");

        jLabel48.setText("Angular Speed");

        part_angular_speed.setText("0");

        part_add.setText("Add New");
        part_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_addActionPerformed(evt);
            }
        });

        jLabel55.setText("Particle List");

        part_delete.setText("Delete");
        part_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_deleteActionPerformed(evt);
            }
        });

        part_get.setText("Get");
        part_get.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_getActionPerformed(evt);
            }
        });

        part_aditive.setText("Aditive Mode");

        part_one_burst.setText("One Burst");

        part_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(part_list);

        part_set.setText("Set");
        part_set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                part_setActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout particles_tabLayout = new javax.swing.GroupLayout(particles_tab);
        particles_tab.setLayout(particles_tabLayout);
        particles_tabLayout.setHorizontalGroup(
            particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(particles_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, particles_tabLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_texture_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(particles_tabLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_emiter_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(particles_tabLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(part_aditive))
                    .addGroup(particles_tabLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_pos_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(part_pos_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(part_one_burst))
                    .addGroup(particles_tabLayout.createSequentialGroup()
                        .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(particles_tabLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_emiter_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_emiter_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(particles_tabLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_direction, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_direction_var, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(particles_tabLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_speed_var, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(particles_tabLayout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_lifetime, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_lifetime_var, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(particles_tabLayout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_count, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel55)
                            .addComponent(jLabel49))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, particles_tabLayout.createSequentialGroup()
                        .addGap(0, 170, Short.MAX_VALUE)
                        .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, particles_tabLayout.createSequentialGroup()
                                .addComponent(part_set)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_add))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, particles_tabLayout.createSequentialGroup()
                                .addComponent(part_get)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(part_delete)))))
                .addContainerGap())
        );
        particles_tabLayout.setVerticalGroup(
            particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(particles_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(part_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(part_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(part_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(part_aditive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(part_pos_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38)
                        .addComponent(part_pos_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(part_one_burst))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(part_emiter_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(part_emiter_box_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(part_emiter_box_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(part_direction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(part_direction_var, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(part_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(part_speed_var, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(part_lifetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(part_lifetime_var, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(part_count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(part_angular_speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(part_add)
                    .addComponent(part_set))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(particles_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(part_delete)
                    .addComponent(part_get))
                .addContainerGap())
        );

        side_panel_tab.addTab("Particles", particles_tab);

        jLabel86.setText("Intensity:");

        light_intensity.setSnapToTicks(true);
        light_intensity.setValue(20);

        jLabel87.setText("Range:");

        light_range.setText("500");

        jLabel88.setText("Color:");

        jLabel89.setText("R");

        jLabel90.setText("G");

        jLabel91.setText("B");

        light_color_r.setValue(100);

        light_color_g.setValue(100);

        light_set.setText("Set");
        light_set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                light_setActionPerformed(evt);
            }
        });

        light_color_b.setValue(100);

        light_shadow_wall.setSelected(true);
        light_shadow_wall.setText("Wall");

        light_shadow_player.setSelected(true);
        light_shadow_player.setText("Player");

        light_shadow_enemy.setSelected(true);
        light_shadow_enemy.setText("Enemy");

        light_shadow_decor_back.setSelected(true);
        light_shadow_decor_back.setText("Decor B");

        jLabel92.setText("Cast Shadow:");

        light_shadow_decor_front.setSelected(true);
        light_shadow_decor_front.setText("Decor F");

        javax.swing.GroupLayout light_tabLayout = new javax.swing.GroupLayout(light_tab);
        light_tab.setLayout(light_tabLayout);
        light_tabLayout.setHorizontalGroup(
            light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(light_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(light_tabLayout.createSequentialGroup()
                        .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel89)
                            .addComponent(jLabel90))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(light_color_r, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(light_color_g, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(light_tabLayout.createSequentialGroup()
                        .addComponent(jLabel91)
                        .addGap(5, 5, 5)
                        .addComponent(light_color_b, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(light_tabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(light_set))
                    .addGroup(light_tabLayout.createSequentialGroup()
                        .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(light_tabLayout.createSequentialGroup()
                                .addComponent(jLabel87)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(light_range, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel88)
                            .addGroup(light_tabLayout.createSequentialGroup()
                                .addComponent(jLabel86)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(light_intensity, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel92)
                            .addGroup(light_tabLayout.createSequentialGroup()
                                .addComponent(light_shadow_wall)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(light_shadow_enemy)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(light_shadow_player)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(light_shadow_decor_back)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(light_shadow_decor_front)))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        light_tabLayout.setVerticalGroup(
            light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(light_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel86)
                    .addComponent(light_intensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(light_range, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(light_color_r, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89))
                .addGap(8, 8, 8)
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel90)
                    .addComponent(light_color_g, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel91)
                    .addComponent(light_color_b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel92)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(light_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(light_shadow_wall)
                    .addComponent(light_shadow_enemy)
                    .addComponent(light_shadow_player)
                    .addComponent(light_shadow_decor_back)
                    .addComponent(light_shadow_decor_front))
                .addGap(20, 20, 20)
                .addComponent(light_set)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        side_panel_tab.addTab("Light", light_tab);

        javax.swing.GroupLayout editor_object_edit_tabLayout = new javax.swing.GroupLayout(editor_object_edit_tab);
        editor_object_edit_tab.setLayout(editor_object_edit_tabLayout);
        editor_object_edit_tabLayout.setHorizontalGroup(
            editor_object_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_panel_tab)
        );
        editor_object_edit_tabLayout.setVerticalGroup(
            editor_object_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_panel_tab)
        );

        editor_mode_selector.addTab("O", editor_object_edit_tab);

        editor_background_edit_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                editor_background_edit_tabComponentShown(evt);
            }
        });

        background_texture_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                background_texture_comboboxActionPerformed(evt);
            }
        });

        jLabel56.setText("Texture");

        back_size_y.setText("32");

        back_pos_y.setText("0");

        jLabel64.setText("x");

        jLabel65.setText("x");

        back_size_x.setText("32");

        back_pos_x.setText("0");
        back_pos_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_pos_xActionPerformed(evt);
            }
        });

        jLabel66.setText("Pos");

        jLabel67.setText("Size");

        jLabel68.setText("Repeat");

        back_repeat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dont Repeat", "Repeat XY", "Repeat X", "Repeat Y" }));
        back_repeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_repeatActionPerformed(evt);
            }
        });

        jLabel69.setText("Parallax Ratio:");

        back_parallax_x.setText("0");
        back_parallax_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_parallax_xActionPerformed(evt);
            }
        });

        jLabel70.setText("x");

        back_parallax_y.setText("0");

        back_add.setText("Add");
        back_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_addActionPerformed(evt);
            }
        });

        jLabel71.setText("Background List");

        back_get.setText("Get");
        back_get.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_getActionPerformed(evt);
            }
        });

        back_delete.setText("Delete");
        back_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_deleteActionPerformed(evt);
            }
        });

        back_list_up.setText("^");
        back_list_up.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        back_list_up.setContentAreaFilled(false);
        back_list_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_list_upActionPerformed(evt);
            }
        });

        back_list_down.setText("v");
        back_list_down.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        back_list_down.setContentAreaFilled(false);
        back_list_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_list_downActionPerformed(evt);
            }
        });

        back_list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(back_list);

        jLabel93.setText("Background");

        javax.swing.GroupLayout editor_background_edit_tabLayout = new javax.swing.GroupLayout(editor_background_edit_tab);
        editor_background_edit_tab.setLayout(editor_background_edit_tabLayout);
        editor_background_edit_tabLayout.setHorizontalGroup(
            editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_background_edit_tabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(back_add))
                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_background_edit_tabLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(back_list_down, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(back_list_up, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_background_edit_tabLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(back_delete)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(back_get))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_background_edit_tabLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(background_texture_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(back_repeat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel71)
                                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel67)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel65)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel66)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_pos_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel64)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_pos_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel69)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_parallax_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel70)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(back_parallax_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel93)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editor_background_edit_tabLayout.setVerticalGroup(
            editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(background_texture_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(back_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(back_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(back_pos_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(back_pos_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(back_repeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back_parallax_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(back_parallax_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(back_add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addGap(0, 0, 0)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editor_background_edit_tabLayout.createSequentialGroup()
                        .addComponent(back_list_up, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(back_list_down, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_background_edit_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(back_get)
                    .addComponent(back_delete))
                .addContainerGap())
        );

        editor_mode_selector.addTab("B", editor_background_edit_tab);

        editor_level_editor_tab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                editor_level_editor_tabComponentShown(evt);
            }
        });

        level_set_but.setText("Set");
        level_set_but.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level_set_butActionPerformed(evt);
            }
        });

        jLabel11.setText("Texture Set");

        jLabel73.setText("(CTRL+Mouse L)");

        player_spawn_y.setText("0");

        player_destroy_outside_level.setText("Destroy Player if out of Level");
        player_destroy_outside_level.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        player_destroy_outside_level.setMargin(new java.awt.Insets(0, 0, 0, 0));
        player_destroy_outside_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                player_destroy_outside_levelActionPerformed(evt);
            }
        });

        player_spawn_x.setText("0");

        jLabel58.setText("x");

        jLabel57.setText("Player Spawn");

        jLabel72.setText("Player");

        camera_border_y.setMajorTickSpacing(10);
        camera_border_y.setMaximum(50);
        camera_border_y.setMinorTickSpacing(1);
        camera_border_y.setPaintLabels(true);
        camera_border_y.setSnapToTicks(true);

        jLabel63.setText("Camera Border Y (%)");

        camera_border_x.setMajorTickSpacing(10);
        camera_border_x.setMaximum(50);
        camera_border_x.setMinorTickSpacing(1);
        camera_border_x.setPaintLabels(true);
        camera_border_x.setSnapToTicks(true);
        camera_border_x.setValue(40);

        jLabel62.setText("Camera Border X (%)");

        jButton1.setText("Set to 1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        camera_zoom.setText("0");

        jLabel61.setText("Camera Zoom");

        camera_limited_level.setText("Camera Limited Level");
        camera_limited_level.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        camera_limited_level.setMargin(new java.awt.Insets(0, 0, 0, 0));
        camera_limited_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camera_limited_levelActionPerformed(evt);
            }
        });

        camera_follow_player.setText("Camera Follow Player");
        camera_follow_player.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        camera_follow_player.setMargin(new java.awt.Insets(0, 0, 0, 0));
        camera_follow_player.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camera_follow_playerActionPerformed(evt);
            }
        });

        jLabel59.setText("Camera");

        level_ori_x.setText("0");

        jLabel53.setText("Ori");

        jLabel51.setText("x");

        level_ori_y.setText("0");

        jLabel84.setText("(ALT + Mouse L)");

        jLabel83.setText("(ALT + Mouse R)");

        level_size_y.setText("0");
        level_size_y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level_size_yActionPerformed(evt);
            }
        });

        jLabel52.setText("x");

        level_size_x.setText("0");

        jLabel54.setText("Size");

        jLabel50.setText("Level");

        javax.swing.GroupLayout editor_level_editor_tabLayout = new javax.swing.GroupLayout(editor_level_editor_tab);
        editor_level_editor_tab.setLayout(editor_level_editor_tabLayout);
        editor_level_editor_tabLayout.setHorizontalGroup(
            editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addComponent(jSeparator6)
            .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player_spawn_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player_spawn_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                        .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(camera_limited_level)
                                    .addComponent(camera_follow_player)
                                    .addComponent(jLabel59)
                                    .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editor_level_editor_tabLayout.createSequentialGroup()
                                            .addComponent(jLabel54)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(level_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel52)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(level_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel83))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editor_level_editor_tabLayout.createSequentialGroup()
                                            .addComponent(jLabel53)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(level_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel51)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(level_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel84)))
                                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel61)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(camera_zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))
                                    .addComponent(jLabel72)
                                    .addComponent(player_destroy_outside_level)
                                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel62)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(camera_border_x, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(camera_border_y, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editor_level_editor_tabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(level_set_but)
                .addContainerGap())
        );
        editor_level_editor_tabLayout.setVerticalGroup(
            editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(level_size_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52)
                    .addComponent(level_size_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(level_ori_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(level_ori_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editor_level_editor_tabLayout.createSequentialGroup()
                        .addComponent(camera_follow_player)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(camera_limited_level)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(camera_zoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(camera_border_x, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(camera_border_y, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(player_spawn_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(player_spawn_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player_destroy_outside_level)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editor_level_editor_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(level_set_but)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        editor_mode_selector.addTab("L", editor_level_editor_tab);

        javax.swing.GroupLayout side_panelLayout = new javax.swing.GroupLayout(side_panel);
        side_panel.setLayout(side_panelLayout);
        side_panelLayout.setHorizontalGroup(
            side_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator7)
            .addGroup(side_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(working_layer_selector_text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(working_layer_selector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(editor_mode_selector, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        side_panelLayout.setVerticalGroup(
            side_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(side_panelLayout.createSequentialGroup()
                .addComponent(editor_mode_selector)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(side_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(working_layer_selector_text)
                    .addComponent(working_layer_selector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        menu_bar.setBorderPainted(false);
        menu_bar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                menu_barFocusGained(evt);
            }
        });
        menu_bar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_barMouseClicked(evt);
            }
        });
        menu_bar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                menu_barPropertyChange(evt);
            }
        });

        menu_file.setText("File");

        menu_file_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menu_file_new.setText("New Level");
        menu_file_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_file_newActionPerformed(evt);
            }
        });
        menu_file.add(menu_file_new);

        menu_file_save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menu_file_save.setText("Save Level");
        menu_file_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_file_saveActionPerformed(evt);
            }
        });
        menu_file.add(menu_file_save);

        menu_file_load.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menu_file_load.setText("Load Level");
        menu_file_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_file_loadActionPerformed(evt);
            }
        });
        menu_file.add(menu_file_load);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem2.setText("Settings");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menu_file.add(jMenuItem2);

        menu_file_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menu_file_exit.setText("Exit");
        menu_file_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_file_exitActionPerformed(evt);
            }
        });
        menu_file.add(menu_file_exit);

        menu_bar.add(menu_file);

        menu_editor.setText("Editor");
        menu_editor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                menu_editorStateChanged(evt);
            }
        });

        menu_editor_click.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menu_editor_click.setText("Toggle Single Click Mode");
        menu_editor_click.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_editor_clickActionPerformed(evt);
            }
        });
        menu_editor.add(menu_editor_click);

        menu_editor_snap.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_editor_snap.setText("Toggle Snap to Grid");
        menu_editor_snap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_editor_snapActionPerformed(evt);
            }
        });
        menu_editor.add(menu_editor_snap);

        menu_editor_avoid_overlaping.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_editor_avoid_overlaping.setText("Toggle Avoid Overlaping");
        menu_editor_avoid_overlaping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_editor_avoid_overlapingActionPerformed(evt);
            }
        });
        menu_editor.add(menu_editor_avoid_overlaping);

        menu_editor_grid_size.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menu_editor_grid_size.setText("Set Grid Size");
        menu_editor_grid_size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_editor_grid_sizeActionPerformed(evt);
            }
        });
        menu_editor.add(menu_editor_grid_size);

        menu_editor_grid_offset.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_editor_grid_offset.setText("Set Grid Offset");
        menu_editor_grid_offset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_editor_grid_offsetActionPerformed(evt);
            }
        });
        menu_editor.add(menu_editor_grid_offset);

        menu_bar.add(menu_editor);

        menu_level.setText("Level");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem1.setText("Start Test Mode");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_level.add(jMenuItem1);

        menu_level_freeze.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        menu_level_freeze.setText("Freeze Level");
        menu_level_freeze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_level_freezeActionPerformed(evt);
            }
        });
        menu_level.add(menu_level_freeze);

        menu_level_reset.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        menu_level_reset.setText("Reset Positions");
        menu_level_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_level_resetActionPerformed(evt);
            }
        });
        menu_level.add(menu_level_reset);

        menu_bar.add(menu_level);

        menu_view.setText("View");
        menu_view.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                menu_viewStateChanged(evt);
            }
        });

        menu_show_col_box.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_show_col_box.setText("Show Colision Box");
        menu_show_col_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_col_boxActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_col_box);

        menu_show_particle_emiter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_show_particle_emiter.setText("Show Particle Emiter");
        menu_show_particle_emiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_particle_emiterActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_particle_emiter);

        menu_show_trajectory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_show_trajectory.setText("Show Trajectory Lines");
        menu_show_trajectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_trajectoryActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_trajectory);

        menu_show_cursor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_show_cursor.setText("Show Object Mouse Cursor");
        menu_show_cursor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_cursorActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_cursor);

        menu_show_level_limit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menu_show_level_limit.setText("Show Level Limit");
        menu_show_level_limit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_level_limitActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_level_limit);

        menu_show_grid.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        menu_show_grid.setText("Show Grid");
        menu_show_grid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_show_gridActionPerformed(evt);
            }
        });
        menu_view.add(menu_show_grid);

        menu_bar.add(menu_view);

        setJMenuBar(menu_bar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(side_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(side_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void menu_file_exitActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int n = JOptionPane.showConfirmDialog(null,"Exit?" , "Exit", JOptionPane.YES_NO_OPTION);
        if(n==0)
        {
            try
            {
                Gdx.app.exit();
                System.exit(1);
            }
            catch(Exception e){}
        }
    }                                              

    private void menu_editor_snapActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        MainLevelEditor.snap_to_grid=!MainLevelEditor.snap_to_grid;
    }                                                

    private void menu_file_loadActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int n = JOptionPane.showConfirmDialog(null,"Any unsaved work will be lost? Load Level?" , "Load", JOptionPane.YES_NO_OPTION);
        if(n==0)
        {
            try
            {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                chooser.setDialogTitle("Open File");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.showOpenDialog(null);
                MainLevelEditor.is_file_open=true;
                MainLevelEditor.file_open=chooser.getSelectedFile().getAbsolutePath();
                Global.loadLevel(MainLevelEditor.file_open);
                setTitle("Wallrunner Level Editor V"+Global.version_editor +"("+MainLevelEditor.file_open+")");
            }
            catch(Exception e)
            {
                MainLevelEditor.is_file_open=false;
                MainLevelEditor.file_open="";
                setTitle("Wallrunner Level Editor V"+Global.version_editor);
                JOptionPane.showMessageDialog(null, "Failed to Load File\n("+e+")");
            }
        }

    }                                              

    private void menu_show_col_boxActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        MainLevelEditor.draw_col_box=!MainLevelEditor.draw_col_box;
    }                                                 

    private void menu_show_level_limitActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        MainLevelEditor.draw_level_limit=!MainLevelEditor.draw_level_limit;
    }                                                     

    private void working_layer_selectorActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
    }                                                      

    private void enemy_texture_comboboxActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        // TODO add your handling code here:
    }                                                      

    private void decoration_texture_comboboxActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        // TODO add your handling code here:
    }                                                           

    private void menu_show_gridActionPerformed(java.awt.event.ActionEvent evt) {                                               
        MainLevelEditor.grid_on=!MainLevelEditor.grid_on;
    }                                              

    private void menu_editor_avoid_overlapingActionPerformed(java.awt.event.ActionEvent evt) {                                                             
        MainLevelEditor.avoid_overlaping=!MainLevelEditor.avoid_overlaping;
    }                                                            

    private void working_layer_selectorMouseClicked(java.awt.event.MouseEvent evt) {                                                    
        
    }                                                   

    private void working_layer_selectorPropertyChange(java.beans.PropertyChangeEvent evt) {                                                      
        MainLevelEditor.working_layer=working_layer_selector.getSelectedIndex();
    }                                                     

    private void menu_file_saveActionPerformed(java.awt.event.ActionEvent evt) {                                               
        try
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setDialogTitle("Open File");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.showOpenDialog(null);
            
            MainLevelEditor.is_file_open=true;
            MainLevelEditor.file_open=chooser.getSelectedFile().getAbsolutePath();
            Global.saveLevel(MainLevelEditor.file_open);
            
            JOptionPane.showMessageDialog(null, chooser.getSelectedFile().getAbsolutePath() +" file Saved!");
            
            setTitle("Wallrunner Level Editor V"+Global.version_editor +"("+MainLevelEditor.file_open+")");
        }
        catch(Exception e)
        {
            MainLevelEditor.is_file_open=false;
            MainLevelEditor.file_open="";
            setTitle("Wallrunner Level Editor V"+Global.version_editor);
            JOptionPane.showMessageDialog(null, "Failed to Save File");
        }
    }                                              

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        //Exit Editor
        int n = JOptionPane.showConfirmDialog(null,"Exit?" , "Exit", JOptionPane.YES_NO_OPTION);
        if(n==0)
        {
            try
            {
                Gdx.app.exit();
                System.exit(1);
            }
            catch(Exception e){}
        }
    }                                  

    private void menu_editor_grid_sizeActionPerformed(java.awt.event.ActionEvent evt) {                                                      
       //Set new Grid Size
       try
       {
           int size=Integer.parseInt(JOptionPane.showInputDialog("Grid Size:"));

           if(size<=0)
           {
               JOptionPane.showMessageDialog(null, "Grid Size must be => 0 ");
           }
           else
           {
               MainLevelEditor.grid_size.y=size;
               MainLevelEditor.grid_size.x=size;
           }
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Grid Size is a Numeric Integer Value");
       }
    }                                                     

    private void menu_show_cursorActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        MainLevelEditor.draw_object_cursor=!MainLevelEditor.draw_object_cursor;
    }                                                

    private void wall_size_xActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void enemy_col_box_yActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void wall_set_objectActionPerformed(java.awt.event.ActionEvent evt) {                                                
        //Set Wall temp Object
        try
        {
            MainLevelEditor.temp_wall = new Wall(0f,0f,wall_texture_combobox.getSelectedIndex(), Float.parseFloat(wall_size_x.getText()), Float.parseFloat(wall_size_y.getText()),Float.parseFloat(wall_col_box_x.getText()),Float.parseFloat(wall_col_box_y.getText()), Float.parseFloat(wall_ori_x.getText()), Float.parseFloat(wall_ori_y.getText()), Float.parseFloat(wall_angular_speed.getText()),wall_move_mode.getSelectedIndex(),Float.parseFloat(wall_move_limit.getText()),Float.parseFloat(wall_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                               

    private void decoration_size_xActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void decoration_move_limitActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
    }                                                     

    private void level_size_yActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void enemy_move_modeActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void enemy_set_objectActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        try
        {
            MainLevelEditor.temp_enemy = new Enemy(0f,0f,enemy_texture_combobox.getSelectedIndex(), Float.parseFloat(enemy_size_x.getText()), Float.parseFloat(enemy_size_y.getText()),enemy_col_box_type.getSelectedIndex(), Float.parseFloat(enemy_col_box_x.getText()),Float.parseFloat(enemy_col_box_y.getText()), Float.parseFloat(enemy_ori_x.getText()), Float.parseFloat(enemy_ori_y.getText()), Float.parseFloat(enemy_angular_speed.getText()),enemy_move_mode.getSelectedIndex(),Float.parseFloat(enemy_move_limit.getText()),Float.parseFloat(enemy_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                                

    private void decoration_set_objectActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        try
        {
            MainLevelEditor.temp_decoration = new Decoration(0f,0f,decoration_texture_combobox.getSelectedIndex(),Float.parseFloat(decoration_size_x.getText()),Float.parseFloat(decoration_size_y.getText()), Float.parseFloat(decoration_ori_x.getText()),Float.parseFloat(decoration_ori_y.getText()),Float.parseFloat(decoration_angular_speed.getText()),decoration_move_mode.getSelectedIndex(),Float.parseFloat(decoration_move_limit.getText()),Float.parseFloat(decoration_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                                     

    private void level_set_butActionPerformed(java.awt.event.ActionEvent evt) {                                              
        try
        {
            Global.player_spawn.x=Float.parseFloat(player_spawn_x.getText());
            Global.player_spawn.y=Float.parseFloat(player_spawn_y.getText());
            Global.level_ori.x=Float.parseFloat(level_ori_x.getText());
            Global.level_ori.y=Float.parseFloat(level_ori_y.getText());
            Global.level_size.x=Float.parseFloat(level_size_x.getText());
            Global.level_size.y=Float.parseFloat(level_size_y.getText());
            Global.camera.setZoom(Float.parseFloat(camera_zoom.getText()));
            Global.camera.margin_percent.x=camera_border_x.getValue()/100f;
            Global.camera.margin_percent.y=camera_border_y.getValue()/100f;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                             

    private void side_panel_tabStateChanged(javax.swing.event.ChangeEvent evt) {                                            

    }                                           

    private void menu_file_newActionPerformed(java.awt.event.ActionEvent evt) {                                              
        int n = JOptionPane.showConfirmDialog(null,"Any unsaved work will be lost? Create new Level?" , "New", JOptionPane.YES_NO_OPTION);
        if(n==0)
        {
            Global.newLevel();
            MainLevelEditor.is_file_open=false;
            MainLevelEditor.file_open="";
            setTitle("Wallrunner Level Editor V"+Global.version_editor);
        }
    }                                             

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {                                       
        updateInterfaceElements();
    }                                      

    private void menu_barPropertyChange(java.beans.PropertyChangeEvent evt) {                                        

    }                                       

    private void menu_barFocusGained(java.awt.event.FocusEvent evt) {                                     

    }                                    

    private void menu_barMouseClicked(java.awt.event.MouseEvent evt) {                                      

    }                                     

    private void menu_viewStateChanged(javax.swing.event.ChangeEvent evt) {                                       
        updateInterfaceElements();
    }                                      

    private void menu_editor_grid_offsetActionPerformed(java.awt.event.ActionEvent evt) {                                                        
       //Grid Offset
       try
       {
            MainLevelEditor.grid_offset.set(Float.parseFloat(JOptionPane.showInputDialog("Grid Offset X:")),Float.parseFloat(JOptionPane.showInputDialog("Grid Offset Y:")));
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Grid Offset is a Numeric Value");
       }
    }                                                       

    private void menu_level_freezeActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        //Toggle Freeze Level
        MainLevelEditor.freeze_level=!MainLevelEditor.freeze_level;
    }                                                 

    private void menu_editorStateChanged(javax.swing.event.ChangeEvent evt) {                                         
        updateInterfaceElements();
    }                                        

    private void menu_show_trajectoryActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        MainLevelEditor.draw_trajectory_lines=!MainLevelEditor.draw_trajectory_lines;
    }                                                    

    private void menu_level_resetActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        MainLevelEditor.resetPosition();
    }                                                

    private void menu_show_particle_emiterActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        MainLevelEditor.draw_particle_emiter=!MainLevelEditor.draw_particle_emiter;
    }                                                         

    private void object_texture_comboboxActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        // TODO add your handling code here:
    }                                                       

    private void object_size_xActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void object_col_box_yActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void object_scriptActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void object_idActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void menu_editor_clickActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        MainLevelEditor.single_click_mode=!MainLevelEditor.single_click_mode;
    }                                                 

    private void object_setActionPerformed(java.awt.event.ActionEvent evt) {                                           
        try
        {
            if(object_id.getText().equals(""))
            {
                object_id.setText("none");
            }
            MainLevelEditor.temp_object = new ScriptedObject(0f,0f,object_texture_combobox.getSelectedIndex(),Float.parseFloat(object_size_x.getText()),Float.parseFloat(object_size_y.getText()),Float.parseFloat(object_ori_x.getText()),Float.parseFloat(object_ori_y.getText()),Float.parseFloat(object_col_box_x.getText()),Float.parseFloat(object_col_box_y.getText()),object_script.getSelectedIndex(),object_id.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                          

    private void player_destroy_outside_levelActionPerformed(java.awt.event.ActionEvent evt) {                                                             
        Global.player.die_out_of_level=player_destroy_outside_level.isSelected();
    }                                                            

    private void part_getActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Get Values from Selected Particle
        try
        {
            part_texture_combobox.setSelectedIndex(Global.particle[part_list.getSelectedIndex()].texture);
            part_size_x.setText(Global.particle[part_list.getSelectedIndex()].particle_size.x+"");
            part_size_y.setText(Global.particle[part_list.getSelectedIndex()].particle_size.y+"");
            part_pos_x.setText(Global.particle[part_list.getSelectedIndex()].pos.x+"");
            part_pos_y.setText(Global.particle[part_list.getSelectedIndex()].pos.y+"");
            part_emiter_type.setSelectedIndex(Global.particle[part_list.getSelectedIndex()].type.ordinal());
            part_emiter_box_x.setText(Global.particle[part_list.getSelectedIndex()].col_box.x+"");
            part_emiter_box_y.setText(Global.particle[part_list.getSelectedIndex()].col_box.y+"");
            part_direction.setText(Global.particle[part_list.getSelectedIndex()].particle_direction+"");
            part_direction_var.setText(Global.particle[part_list.getSelectedIndex()].particle_direction_var+"");
            part_speed.setText(Global.particle[part_list.getSelectedIndex()].particle_speed+"");
            part_speed_var.setText(Global.particle[part_list.getSelectedIndex()].particle_speed_variation+"");
            part_lifetime.setText(Global.particle[part_list.getSelectedIndex()].particle_life_time+"");
            part_lifetime_var.setText(Global.particle[part_list.getSelectedIndex()].particle_life_time_variation+"");
            part_count.setText(Global.particle[part_list.getSelectedIndex()].particle_count+"");
            part_angular_speed.setText(Global.particle[part_list.getSelectedIndex()].particle_rotation_speed+"");
            part_aditive.setSelected(Global.particle[part_list.getSelectedIndex()].aditive_mode);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Select Object First");
        }
    }                                        

    private void part_deleteActionPerformed(java.awt.event.ActionEvent evt) {                                            
        //Remove a Particle
        try
        {
            Global.removeParticle(part_list.getSelectedIndex());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Select Object First");
        }
        updateInterfaceElements();
    }                                           

    private void part_addActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Add new Particle to Game
        try
        {
            Global.addGamePart(new Particle(part_texture_combobox.getSelectedIndex(),Float.parseFloat(part_pos_x.getText()),Float.parseFloat(part_pos_y.getText()),Float.parseFloat(part_emiter_box_x.getText()),Float.parseFloat(part_emiter_box_y.getText()),Float.parseFloat(part_size_x.getText()),Float.parseFloat(part_size_y.getText()),Float.parseFloat(part_direction.getText()),Float.parseFloat(part_direction_var.getText()),Integer.parseInt(part_count.getText()),Float.parseFloat(part_lifetime.getText()),Float.parseFloat(part_lifetime_var.getText()),Float.parseFloat(part_speed.getText()),Float.parseFloat(part_speed_var.getText()),Float.parseFloat(part_angular_speed.getText()),part_emiter_type.getSelectedIndex(),part_one_burst.isSelected(),part_aditive.isSelected()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
        updateInterfaceElements();
    }                                        

    private void part_lifetime_varActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void part_emiter_box_xActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void part_pos_xActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void part_emiter_typeActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void part_texture_comboboxActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
    }                                                     

    private void back_list_downActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int i=back_list.getSelectedIndex();
        Background temp;

        if(i==-1)
        {
            JOptionPane.showMessageDialog(null, "Choose element first!");
        }
        else if(i<Global.background.length && Global.background.length>1)
        {
            temp=Global.background[i];
            Global.background[i]=Global.background[i+1];
            Global.background[i+1]=temp;
            updateBackgroundList();
            back_list.setSelectedIndex(i+1);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Alredy on First Position");
        }
    }                                              

    private void back_list_upActionPerformed(java.awt.event.ActionEvent evt) {                                             
        //Moves Backgroud UP on Layer List
        int i=back_list.getSelectedIndex();
        Background temp;

        if(i==-1)
        {
            JOptionPane.showMessageDialog(null, "Choose element first!");
        }
        else if(i>0 && Global.background.length>1)
        {
            temp=Global.background[i];
            Global.background[i]=Global.background[i-1];
            Global.background[i-1]=temp;
            updateBackgroundList();
            back_list.setSelectedIndex(i-1);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Alredy on First Position");
        }
        
    }                                            

    private void back_deleteActionPerformed(java.awt.event.ActionEvent evt) {                                            
        //Remove Background
        try
        {
            Background[] temp = new Background[Global.background.length-1];
            int i=0;

            while(i<Global.background.length)
            {
                if(i<back_list.getSelectedIndex())
                {
                    temp[i]=Global.background[i];
                }
                else if(i>back_list.getSelectedIndex())
                {
                    temp[i-1]=Global.background[i];
                }
                i++;
            }

            Global.background=temp;
        }
        catch(Exception e){}
        updateInterfaceElements();
    }                                           

    private void back_getActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Get Values From Actual BackGround
        if(back_list.getSelectedIndex()!=-1)
        {
            background_texture_combobox.setSelectedIndex(Global.background[back_list.getSelectedIndex()].texture);
            back_size_x.setText(Global.background[back_list.getSelectedIndex()].size.x+"");
            back_size_y.setText(Global.background[back_list.getSelectedIndex()].size.y+"");
            back_pos_x.setText(Global.background[back_list.getSelectedIndex()].pos.x+"");
            back_pos_y.setText(Global.background[back_list.getSelectedIndex()].pos.y+"");
            back_parallax_x.setText(Global.background[back_list.getSelectedIndex()].parallax.x+"");
            back_parallax_y.setText(Global.background[back_list.getSelectedIndex()].parallax.y+"");
            back_repeat.setSelectedIndex(Global.background[back_list.getSelectedIndex()].repeat_mode);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Choose element first!");
        }
    }                                        

    private void back_addActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Add new Background
        try
        {
            Global.addGameBackground(new Background(background_texture_combobox.getSelectedIndex(),Float.parseFloat(back_pos_x.getText()),Float.parseFloat(back_pos_y.getText()),Float.parseFloat(back_size_x.getText()),Float.parseFloat(back_size_y.getText()),Float.parseFloat(back_parallax_x.getText()),Float.parseFloat(back_parallax_y.getText()),back_repeat.getSelectedIndex()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
        updateInterfaceElements();
    }                                        

    private void back_repeatActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void back_pos_xActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void background_texture_comboboxActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        // TODO add your handling code here:
    }                                                           

    private void object_deleteActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if(object_list.getSelectedIndex()!=-1)
        {
            Global.removeObject(object_list.getSelectedIndex());
            updateInterfaceElements();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Select Object First");
        }
    }                                             

    private void object_getActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if(object_list.getSelectedIndex()!=-1)
        {
            object_texture_combobox.setSelectedIndex(Global.object[object_list.getSelectedIndex()].texture);
            object_size_x.setText(Global.object[object_list.getSelectedIndex()].sprite.getHeight()+"");
            object_size_y.setText(Global.object[object_list.getSelectedIndex()].sprite.getWidth()+"");
            object_ori_x.setText(Global.object[object_list.getSelectedIndex()].ori.x+"");
            object_ori_y.setText(Global.object[object_list.getSelectedIndex()].ori.y+"");
            object_col_box_x.setText(Global.object[object_list.getSelectedIndex()].col_box.x+"");
            object_col_box_y.setText(Global.object[object_list.getSelectedIndex()].col_box.y+"");
            object_script.setSelectedIndex(Global.object[object_list.getSelectedIndex()].script);
            object_id.setText(Global.object[object_list.getSelectedIndex()].id);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Select Object First");
        }
    }                                          

    private void containerComponentResized(java.awt.event.ComponentEvent evt) {                                           
        //Resize canvas
        try
        {
            MainLevelEditor.updateSize();
        }
        catch(Exception e){}
    }                                          

    private void part_setActionPerformed(java.awt.event.ActionEvent evt) {                                         
        try
        {
            MainLevelEditor.temp_particle = new Particle(part_texture_combobox.getSelectedIndex(),0f,0f,Float.parseFloat(part_emiter_box_x.getText()),Float.parseFloat(part_emiter_box_y.getText()),Float.parseFloat(part_size_x.getText()),Float.parseFloat(part_size_y.getText()),Float.parseFloat(part_direction.getText()),Float.parseFloat(part_direction_var.getText()),Integer.parseInt(part_count.getText()),Float.parseFloat(part_lifetime.getText()),Float.parseFloat(part_lifetime_var.getText()),Float.parseFloat(part_speed.getText()),Float.parseFloat(part_speed_var.getText()),Float.parseFloat(part_angular_speed.getText()),part_emiter_type.getSelectedIndex(),part_one_burst.isSelected(),part_aditive.isSelected());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                        

    private void wall_texture_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {                                                       
        try
        {
            MainLevelEditor.temp_wall = new Wall(0f,0f,wall_texture_combobox.getSelectedIndex(), Float.parseFloat(wall_size_x.getText()), Float.parseFloat(wall_size_y.getText()),Float.parseFloat(wall_col_box_x.getText()),Float.parseFloat(wall_col_box_y.getText()), Float.parseFloat(wall_ori_x.getText()), Float.parseFloat(wall_ori_y.getText()), Float.parseFloat(wall_angular_speed.getText()),wall_move_mode.getSelectedIndex(),Float.parseFloat(wall_move_limit.getText()),Float.parseFloat(wall_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error trying to change obj Texture");
        }
    }                                                      

    private void enemy_texture_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {                                                        
        try
        {
            MainLevelEditor.temp_enemy = new Enemy(0f,0f,enemy_texture_combobox.getSelectedIndex(), Float.parseFloat(enemy_size_x.getText()), Float.parseFloat(enemy_size_y.getText()),enemy_col_box_type.getSelectedIndex(), Float.parseFloat(enemy_col_box_x.getText()),Float.parseFloat(enemy_col_box_y.getText()), Float.parseFloat(enemy_ori_x.getText()), Float.parseFloat(enemy_ori_y.getText()), Float.parseFloat(enemy_angular_speed.getText()),enemy_move_mode.getSelectedIndex(),Float.parseFloat(enemy_move_limit.getText()),Float.parseFloat(enemy_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error trying to change obj Texture");
        }
    }                                                       

    private void decoration_texture_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {                                                             
        try
        {
            MainLevelEditor.temp_decoration = new Decoration(0f,0f,decoration_texture_combobox.getSelectedIndex(),Float.parseFloat(decoration_size_x.getText()),Float.parseFloat(decoration_size_y.getText()), Float.parseFloat(decoration_ori_x.getText()),Float.parseFloat(decoration_ori_y.getText()),Float.parseFloat(decoration_angular_speed.getText()),decoration_move_mode.getSelectedIndex(),Float.parseFloat(decoration_move_limit.getText()),Float.parseFloat(decoration_move_speed.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error trying to change obj Texture");
        }
    }                                                            

    private void object_texture_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {                                                         
        try
        {
            MainLevelEditor.temp_object = new ScriptedObject(0f,0f,object_texture_combobox.getSelectedIndex(),Float.parseFloat(object_size_x.getText()),Float.parseFloat(object_size_y.getText()),Float.parseFloat(object_ori_x.getText()),Float.parseFloat(object_ori_y.getText()),Float.parseFloat(object_col_box_x.getText()),Float.parseFloat(object_col_box_y.getText()),object_script.getSelectedIndex(),object_id.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error trying to change obj Texture");
        }
    }                                                        

    private void part_texture_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {                                                       
        try
        {
            MainLevelEditor.temp_particle = new Particle(part_texture_combobox.getSelectedIndex(),0f,0f,Float.parseFloat(part_emiter_box_x.getText()),Float.parseFloat(part_emiter_box_y.getText()),Float.parseFloat(part_size_x.getText()),Float.parseFloat(part_size_y.getText()),Float.parseFloat(part_direction.getText()),Float.parseFloat(part_direction_var.getText()),Integer.parseInt(part_count.getText()),Float.parseFloat(part_lifetime.getText()),Float.parseFloat(part_lifetime_var.getText()),Float.parseFloat(part_speed.getText()),Float.parseFloat(part_speed_var.getText()),Float.parseFloat(part_angular_speed.getText()),part_emiter_type.getSelectedIndex(),part_one_burst.isSelected(),part_aditive.isSelected());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error trying to change obj Texture");
        }
    }                                                      

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       //Set Camera Zoom to 1
       camera_zoom.setText("1");
       Global.camera.setZoom(Float.parseFloat(camera_zoom.getText()));
       
    }                                        

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //Toggle Between test mode and edit mode
        if(MainLevelEditor.test_mode)
        {
            MainLevelEditor.resetPosition();
            MainLevelEditor.freeze_level=true;
        }
        else
        {
            MainLevelEditor.freeze_level=false;
        }
        MainLevelEditor.test_mode=!MainLevelEditor.test_mode;
    }                                          

    private void light_setActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //Light Set Button
        try
        {
            MainLevelEditor.temp_light = new Light(0f,0f,Float.parseFloat(light_range.getText()),light_shadow_player.isSelected(),light_shadow_wall.isSelected(),light_shadow_enemy.isSelected(),light_shadow_decor_back.isSelected(),light_shadow_decor_front.isSelected(),light_color_r.getValue()/100f,light_color_g.getValue()/100f,light_color_b.getValue()/100f,light_intensity.getValue()/100f);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Check Values");
        }
    }                                         

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        editorSettingsFrameUpdateInterface();
        editor_settings_frame.setVisible(true);
    }                                          

    private void editor_settings_okayActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        editor_settings_frame.setVisible(false);
    }                                                    

    private void editor_settings_zoomActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        MainLevelEditor.reset_zoom_file=editor_settings_zoom.isSelected();
    }                                                    

    private void camera_follow_playerActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        Global.camera.camera_follow_player=camera_follow_player.isSelected();
    }                                                    

    private void camera_limited_levelActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        Global.camera.limited_level_borders=camera_limited_level.isSelected();
    }                                                    

    private void editor_settings_fpsActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        MainLevelEditor.fps_lock=editor_settings_fps.getSelectedIndex();
    }                                                   

    private void back_parallax_xActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void enemy_tabComponentShown(java.awt.event.ComponentEvent evt) {                                         
        //Enemy Tab Shown Event
        try
        {
            updateEnemyElements();
        }
        catch(Exception e){}
    }                                        

    private void wall_tabComponentShown(java.awt.event.ComponentEvent evt) {                                        
        //Wall Tab Show Event
        try
        {
            updateWallElements();
        }
        catch(Exception e){}
    }                                       

    private void decoration_tabComponentShown(java.awt.event.ComponentEvent evt) {                                              
        //Decoration Tab Shown Event
        try
        {
            updateDecorationElements();
        }
        catch(Exception e){}
    }                                             

    private void particles_tabComponentShown(java.awt.event.ComponentEvent evt) {                                             
        //Paricle Tab Shown Event
        try
        {
            updateParticleList();
        }
        catch(Exception e){}
    }                                            

    private void editor_level_editor_tabComponentShown(java.awt.event.ComponentEvent evt) {                                                       
        //Level Elements Tab
        updateLevelElements();
    }                                                      

    private void object_tabComponentShown(java.awt.event.ComponentEvent evt) {                                          
        //Object Elements Tab
        updateObjectElements();
        updateObjectList();
        updateScriptList();
    }                                         

    private void editor_background_edit_tabComponentShown(java.awt.event.ComponentEvent evt) {                                                          
        //BackGround Elements Tab
        updateBackgroundList();
    }                                                         

    // Variables declaration - do not modify                     
    public static javax.swing.JButton back_add;
    public static javax.swing.JButton back_delete;
    public static javax.swing.JButton back_get;
    public static javax.swing.JList back_list;
    public static javax.swing.JButton back_list_down;
    public static javax.swing.JButton back_list_up;
    public static javax.swing.JTextField back_parallax_x;
    public static javax.swing.JTextField back_parallax_y;
    public static javax.swing.JTextField back_pos_x;
    public static javax.swing.JTextField back_pos_y;
    public static javax.swing.JComboBox back_repeat;
    public static javax.swing.JTextField back_size_x;
    public static javax.swing.JTextField back_size_y;
    public static javax.swing.JComboBox background_texture_combobox;
    public static javax.swing.JSlider camera_border_x;
    public static javax.swing.JSlider camera_border_y;
    public static javax.swing.JCheckBox camera_follow_player;
    public static javax.swing.JCheckBox camera_limited_level;
    public static javax.swing.JTextField camera_zoom;
    public static java.awt.Canvas canvas1;
    public static java.awt.Panel container;
    public static javax.swing.JTextField decoration_angular_speed;
    public static javax.swing.JTextField decoration_move_limit;
    public static javax.swing.JComboBox decoration_move_mode;
    public static javax.swing.JTextField decoration_move_speed;
    public static javax.swing.JTextField decoration_ori_x;
    public static javax.swing.JTextField decoration_ori_y;
    public static javax.swing.JButton decoration_set_object;
    public static javax.swing.JTextField decoration_size_x;
    public static javax.swing.JTextField decoration_size_y;
    public static javax.swing.JPanel decoration_tab;
    public static javax.swing.JComboBox decoration_texture_combobox;
    public static javax.swing.JPanel editor_background_edit_tab;
    private javax.swing.JPanel editor_level_editor_tab;
    private javax.swing.JTabbedPane editor_mode_selector;
    private javax.swing.JPanel editor_object_edit_tab;
    private javax.swing.JComboBox editor_settings_fps;
    private javax.swing.JFrame editor_settings_frame;
    private javax.swing.JButton editor_settings_okay;
    private javax.swing.JLabel editor_settings_text_fps_lock;
    private javax.swing.JCheckBox editor_settings_zoom;
    public static javax.swing.JTextField enemy_angular_speed;
    public static javax.swing.JComboBox enemy_col_box_type;
    public static javax.swing.JTextField enemy_col_box_x;
    public static javax.swing.JTextField enemy_col_box_y;
    public static javax.swing.JTextField enemy_move_limit;
    public static javax.swing.JComboBox enemy_move_mode;
    public static javax.swing.JTextField enemy_move_speed;
    public static javax.swing.JTextField enemy_ori_x;
    public static javax.swing.JTextField enemy_ori_y;
    public static javax.swing.JButton enemy_set_object;
    public static javax.swing.JTextField enemy_size_x;
    public static javax.swing.JTextField enemy_size_y;
    public static javax.swing.JPanel enemy_tab;
    public static javax.swing.JComboBox enemy_texture_combobox;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel16;
    public static javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel18;
    public static javax.swing.JLabel jLabel19;
    public static javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel20;
    public static javax.swing.JLabel jLabel21;
    public static javax.swing.JLabel jLabel22;
    public static javax.swing.JLabel jLabel23;
    public static javax.swing.JLabel jLabel24;
    public static javax.swing.JLabel jLabel25;
    public static javax.swing.JLabel jLabel26;
    public static javax.swing.JLabel jLabel27;
    public static javax.swing.JLabel jLabel28;
    public static javax.swing.JLabel jLabel29;
    public static javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel30;
    public static javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel32;
    public static javax.swing.JLabel jLabel33;
    public static javax.swing.JLabel jLabel34;
    public static javax.swing.JLabel jLabel35;
    public static javax.swing.JLabel jLabel36;
    public static javax.swing.JLabel jLabel37;
    public static javax.swing.JLabel jLabel38;
    public static javax.swing.JLabel jLabel39;
    public static javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel40;
    public static javax.swing.JLabel jLabel41;
    public static javax.swing.JLabel jLabel42;
    public static javax.swing.JLabel jLabel43;
    public static javax.swing.JLabel jLabel44;
    public static javax.swing.JLabel jLabel45;
    public static javax.swing.JLabel jLabel46;
    public static javax.swing.JLabel jLabel47;
    public static javax.swing.JLabel jLabel48;
    public static javax.swing.JLabel jLabel49;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel50;
    public static javax.swing.JLabel jLabel51;
    public static javax.swing.JLabel jLabel52;
    public static javax.swing.JLabel jLabel53;
    public static javax.swing.JLabel jLabel54;
    public static javax.swing.JLabel jLabel55;
    public static javax.swing.JLabel jLabel56;
    public static javax.swing.JLabel jLabel57;
    public static javax.swing.JLabel jLabel58;
    public static javax.swing.JLabel jLabel59;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel60;
    public static javax.swing.JLabel jLabel61;
    public static javax.swing.JLabel jLabel62;
    public static javax.swing.JLabel jLabel63;
    public static javax.swing.JLabel jLabel64;
    public static javax.swing.JLabel jLabel65;
    public static javax.swing.JLabel jLabel66;
    public static javax.swing.JLabel jLabel67;
    public static javax.swing.JLabel jLabel68;
    public static javax.swing.JLabel jLabel69;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel70;
    public static javax.swing.JLabel jLabel71;
    public static javax.swing.JLabel jLabel72;
    public static javax.swing.JLabel jLabel73;
    public static javax.swing.JLabel jLabel74;
    public static javax.swing.JLabel jLabel75;
    public static javax.swing.JLabel jLabel76;
    public static javax.swing.JLabel jLabel77;
    public static javax.swing.JLabel jLabel78;
    public static javax.swing.JLabel jLabel79;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel80;
    public static javax.swing.JLabel jLabel81;
    public static javax.swing.JLabel jLabel82;
    public static javax.swing.JLabel jLabel83;
    public static javax.swing.JLabel jLabel84;
    public static javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JSeparator jSeparator1;
    public static javax.swing.JSeparator jSeparator2;
    public static javax.swing.JSeparator jSeparator3;
    public static javax.swing.JSeparator jSeparator4;
    public static javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    public static javax.swing.JTextField level_ori_x;
    public static javax.swing.JTextField level_ori_y;
    public static javax.swing.JButton level_set_but;
    public static javax.swing.JTextField level_size_x;
    public static javax.swing.JTextField level_size_y;
    private javax.swing.JSlider light_color_b;
    private javax.swing.JSlider light_color_g;
    private javax.swing.JSlider light_color_r;
    private javax.swing.JSlider light_intensity;
    private javax.swing.JTextField light_range;
    private javax.swing.JButton light_set;
    private javax.swing.JCheckBox light_shadow_decor_back;
    private javax.swing.JCheckBox light_shadow_decor_front;
    private javax.swing.JCheckBox light_shadow_enemy;
    private javax.swing.JCheckBox light_shadow_player;
    private javax.swing.JCheckBox light_shadow_wall;
    private javax.swing.JPanel light_tab;
    public static javax.swing.JMenuBar menu_bar;
    public static javax.swing.JMenu menu_editor;
    public static javax.swing.JMenuItem menu_editor_avoid_overlaping;
    public static javax.swing.JMenuItem menu_editor_click;
    public static javax.swing.JMenuItem menu_editor_grid_offset;
    public static javax.swing.JMenuItem menu_editor_grid_size;
    public static javax.swing.JMenuItem menu_editor_snap;
    public static javax.swing.JMenu menu_file;
    public static javax.swing.JMenuItem menu_file_exit;
    public static javax.swing.JMenuItem menu_file_load;
    public static javax.swing.JMenuItem menu_file_new;
    public static javax.swing.JMenuItem menu_file_save;
    private javax.swing.JMenu menu_level;
    public static javax.swing.JMenuItem menu_level_freeze;
    public static javax.swing.JMenuItem menu_level_reset;
    public static javax.swing.JMenuItem menu_show_col_box;
    public static javax.swing.JMenuItem menu_show_cursor;
    public static javax.swing.JMenuItem menu_show_grid;
    public static javax.swing.JMenuItem menu_show_level_limit;
    public static javax.swing.JMenuItem menu_show_particle_emiter;
    public static javax.swing.JMenuItem menu_show_trajectory;
    public static javax.swing.JMenu menu_view;
    public static javax.swing.JTextField object_col_box_x;
    public static javax.swing.JTextField object_col_box_y;
    public static javax.swing.JButton object_delete;
    public static javax.swing.JButton object_get;
    public static javax.swing.JTextField object_id;
    public static javax.swing.JList object_list;
    public static javax.swing.JTextField object_ori_x;
    public static javax.swing.JTextField object_ori_y;
    public static javax.swing.JComboBox object_script;
    public static javax.swing.JButton object_set;
    public static javax.swing.JTextField object_size_x;
    public static javax.swing.JTextField object_size_y;
    public static javax.swing.JPanel object_tab;
    public static javax.swing.JComboBox object_texture_combobox;
    public static javax.swing.JButton part_add;
    public static javax.swing.JCheckBox part_aditive;
    public static javax.swing.JTextField part_angular_speed;
    public static javax.swing.JTextField part_count;
    public static javax.swing.JButton part_delete;
    public static javax.swing.JTextField part_direction;
    public static javax.swing.JTextField part_direction_var;
    public static javax.swing.JTextField part_emiter_box_x;
    public static javax.swing.JTextField part_emiter_box_y;
    public static javax.swing.JComboBox part_emiter_type;
    public static javax.swing.JButton part_get;
    public static javax.swing.JTextField part_lifetime;
    public static javax.swing.JTextField part_lifetime_var;
    public static javax.swing.JList part_list;
    public static javax.swing.JCheckBox part_one_burst;
    public static javax.swing.JTextField part_pos_x;
    public static javax.swing.JTextField part_pos_y;
    public static javax.swing.JButton part_set;
    public static javax.swing.JTextField part_size_x;
    public static javax.swing.JTextField part_size_y;
    public static javax.swing.JTextField part_speed;
    public static javax.swing.JTextField part_speed_var;
    public static javax.swing.JComboBox part_texture_combobox;
    public static javax.swing.JPanel particles_tab;
    public static javax.swing.JCheckBox player_destroy_outside_level;
    public static javax.swing.JTextField player_spawn_x;
    public static javax.swing.JTextField player_spawn_y;
    private javax.swing.JPanel side_panel;
    public static javax.swing.JTabbedPane side_panel_tab;
    public static javax.swing.JTextField wall_angular_speed;
    public static javax.swing.JTextField wall_col_box_x;
    public static javax.swing.JTextField wall_col_box_y;
    public static javax.swing.JTextField wall_move_limit;
    public static javax.swing.JComboBox wall_move_mode;
    public static javax.swing.JTextField wall_move_speed;
    public static javax.swing.JTextField wall_ori_x;
    public static javax.swing.JTextField wall_ori_y;
    public static javax.swing.JButton wall_set_object;
    public static javax.swing.JTextField wall_size_x;
    public static javax.swing.JTextField wall_size_y;
    public static javax.swing.JPanel wall_tab;
    public static javax.swing.JComboBox wall_texture_combobox;
    public static javax.swing.JComboBox working_layer_selector;
    public static javax.swing.JLabel working_layer_selector_text;
    // End of variables declaration                   
}
