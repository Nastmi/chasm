package com.gdx.chasm.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.chasm.Chasm;
import com.gdx.chasm.baseClasses.DebugRenderer;
import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.baseClasses.Renderer;
import com.gdx.chasm.baseClasses.Vector2D;
import com.gdx.chasm.entities.*;
import com.gdx.chasm.handlers.CollisionHandler;
import com.gdx.chasm.handlers.EnemySpawn;
import com.gdx.chasm.handlers.InputHandler;
import org.graalvm.compiler.options.ModifiableOptionValues;

import java.util.ArrayList;

public class GameScreen implements Screen {

    final Chasm game;
    final float unitScale = 1/12f;
    OrthographicCamera camera;
    FitViewport viewport;
    Texture enemyTexture;
    Player player;
    InputHandler inputHandler;
    ShapeRenderer shapeRenderer;
    ArrayList<Entity> collisions = new ArrayList<>();
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    Vector2D spawn;
    Vector2D colSpawn;
    public GameScreen(final Chasm game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(32, 18f, camera);
        enemyTexture = new Texture(Gdx.files.internal("temp_enemy.png"));
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        shapeRenderer = new ShapeRenderer();
        map = new TmxMapLoader().load("tiled_maps/maps/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        createCollisions();
        createPlayer();
        collisions.add(player);
        EnemySpawn.returnEnemies(unitScale, map, collisions);
       /* generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 1;
        font = generator.generateFont(parameter);
        generator.dispose();*/

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        removeDead();
    }

    public void update(float delta){
        for(Entity e:collisions){
            if(e instanceof Player)
                ((Player) e).update(delta, inputHandler.held, inputHandler.single, collisions);
            else if(e instanceof ExplodingEnemy)
                ((ExplodingEnemy) e).update(delta, collisions);
            else if(e instanceof MovableEntity)
                ((MovableEntity) e).update(delta, collisions);
        }
        for(Entity e:collisions){
            if(e instanceof Player)
                ((Player) e).moveX(delta);
            else if(e instanceof MovableEntity)
                ((MovableEntity) e).moveX(delta);

        }
        CollisionHandler.handleCollisions(delta, collisions, 0);
        for(Entity e:collisions){
            if(e instanceof Player)
                ((Player) e).moveY(delta);
            else if(e instanceof MovableEntity)
                ((MovableEntity) e).moveY(delta);
        }
        CollisionHandler.handleCollisions(delta, collisions, 1);
        camera.position.set(new Vector3((float)player.getPosition().getX(), (float)player.getPosition().getY(), 0));
    }
    public void draw(){
        ScreenUtils.clear(0, 0, 0, 1);
        drawMap();
        camera.update();
        Renderer.render(game.batch, collisions, camera);
        DebugRenderer.render(shapeRenderer, collisions, camera);
    }
    public void removeDead(){
        ArrayList<Integer> rmIndex = new ArrayList<>();
        for(int i = 0; i < collisions.size(); i++){
            if(collisions.get(i) instanceof MovableEntity){
                if(((MovableEntity) collisions.get(i)).isDead){
                    if(collisions.get(i) instanceof Player){
                        player.setPosition(new Vector2D(spawn));
                        player.getCollisionBox().setPosition(new Vector2D(colSpawn));
                        player.isDead = false;
                    }
                    else{
                        rmIndex.add(i);
                    }
                }
            }
        }
        System.out.println(rmIndex.size());
        for(Integer i:rmIndex){
            collisions.remove((int)i);
        }
    }

    public void drawMap(){
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void createCollisions(){
        MapLayer collision_layer = map.getLayers().get("collision_layer");
        MapObjects collisions_objects = collision_layer.getObjects();
        for(MapObject o:collisions_objects){
            if(o instanceof RectangleMapObject){
                RectangleMapObject rec = (RectangleMapObject) o;
                collisions.add(new CollisionEntity(rec.getRectangle().getX()*unitScale, rec.getRectangle().getY()*unitScale, rec.getRectangle().getWidth()*unitScale, rec.getRectangle().getHeight()*unitScale));
            }
        }
    }

    public void createPlayer(){
        MapLayer spawn_layer = map.getLayers().get("spawn");
        MapObjects spawn_objects = spawn_layer.getObjects();
        TextureMapObject player_spawn = (TextureMapObject)spawn_objects.get("spawn_point");
        player = new Player(1, 1, player_spawn.getX()*unitScale, player_spawn.getY()*unitScale, player_spawn.getX()*unitScale+0.33, player_spawn.getY()*unitScale, 0.5, 1, new Texture(Gdx.files.internal("player_idle.png")));
        spawn = new Vector2D(player_spawn.getX()*unitScale, player_spawn.getY()*unitScale);
        colSpawn = new Vector2D(player_spawn.getX()*unitScale+0.33, player_spawn.getY()*unitScale);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
