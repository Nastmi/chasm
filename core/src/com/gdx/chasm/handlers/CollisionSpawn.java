package com.gdx.chasm.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.gdx.chasm.entities.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CollisionSpawn {

    public static void createCollisions(TiledMap map, ArrayList<Entity> collisions, double unitScale, TextureAtlas entityAtlas) {
        MapLayer collision_layer = map.getLayers().get("collision_layer");
        MapObjects collisions_objects = collision_layer.getObjects();
        MapLayer checkpoint_layer = map.getLayers().get("checkpoints");
        MapObjects checkpoint_objects = checkpoint_layer.getObjects();
        MapLayer dash_layer = map.getLayers().get("dash_restore");
        MapObjects dash_objects = dash_layer.getObjects();
        for (MapObject o : collisions_objects) {
            if (o instanceof RectangleMapObject) {
                RectangleMapObject rec = (RectangleMapObject) o;
                collisions.add(new CollisionEntity(rec.getRectangle().getX() * unitScale, rec.getRectangle().getY() * unitScale, rec.getRectangle().getWidth() * unitScale, rec.getRectangle().getHeight() * unitScale));
            }
        }
        for (MapObject o : checkpoint_objects) {
            if (o instanceof RectangleMapObject) {
                RectangleMapObject rec = (RectangleMapObject) o;
                MapProperties prop = o.getProperties();
                int number = (int) prop.get("number");
                collisions.add(new Checkpoint(1, 1, (double) rec.getRectangle().getX() * unitScale, (double) rec.getRectangle().getY() * unitScale, (double) rec.getRectangle().getX() * unitScale, (double) rec.getRectangle().getY() * unitScale, (double) rec.getRectangle().getWidth() * unitScale, (double) rec.getRectangle().getHeight() * unitScale, new Texture(Gdx.files.internal("player_idle.png")), number, entityAtlas));
            }
        }
        for (MapObject o : dash_objects) {
            if (o instanceof TextureMapObject) {
                TextureMapObject enemy = (TextureMapObject) o;
                collisions.add(new DashRestore(1, 1, enemy.getX() * unitScale, enemy.getY() * unitScale, enemy.getX() * unitScale, enemy.getY() * unitScale, 1, 1, new Texture(Gdx.files.internal("temp_enemy.png")), entityAtlas));
            }
        }
    }
}
