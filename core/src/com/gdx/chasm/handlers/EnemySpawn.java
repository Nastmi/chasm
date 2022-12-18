package com.gdx.chasm.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.gdx.chasm.entities.BouncerEnemy;
import com.gdx.chasm.entities.Entity;
import com.gdx.chasm.entities.ExplodingEnemy;

import java.util.ArrayList;

public class EnemySpawn {

    public static void returnEnemies(double unitScale, TiledMap map, ArrayList<Entity> collisions){
        MapLayer enemy_layer = map.getLayers().get("enemy_layer");
        MapObjects enemy_objects = enemy_layer.getObjects();
        for(MapObject o:enemy_objects){
            if(o instanceof TextureMapObject){
                MapProperties prop = o.getProperties();
                String type = (String)prop.get("type");
                TextureMapObject enemy = (TextureMapObject) o;
                if(type.equals("bouncer"))
                    collisions.add(new BouncerEnemy(2, 2, enemy.getX()*unitScale, enemy.getY()*unitScale, enemy.getX()*unitScale, enemy.getY()*unitScale, 2, 2, 1, new Texture(Gdx.files.internal("temp_enemy.png"))));
                if(type.equals("exploding"))
                    collisions.add(new ExplodingEnemy(2, 2, enemy.getX()*unitScale, enemy.getY()*unitScale, enemy.getX()*unitScale, enemy.getY()*unitScale, 2, 2, new Texture(Gdx.files.internal("temp_enemy_2.png"))));
            }
        }
    }
//new Texture(Gdx.files.internal("temp_enemy.png")
}
/*
public BouncerEnemy(     double width,
    double height,
    double x,
    double y,
    double colX,
    double colY,
    double collisionWidth,
    double collisionHeight,
    double maxTime,
    Texture texture )
 */
