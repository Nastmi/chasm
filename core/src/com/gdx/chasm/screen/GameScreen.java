package com.gdx.chasm.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.gdx.chasm.baseClasses.Rectangle;
import com.gdx.chasm.handlers.CollisionHandler;
import com.gdx.chasm.handlers.InputHandler;
import com.gdx.chasm.entities.Player;

import java.util.ArrayList;

public class GameScreen implements Screen {

    final Chasm game;
    final float unitScale = 1/12f;
    OrthographicCamera camera;
    FitViewport viewport;
    Texture test;
    Player player;
    InputHandler inputHandler;
    ShapeRenderer shapeRenderer;

    ArrayList<Rectangle> collisions = new ArrayList<>();
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    public GameScreen(final Chasm game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(32, 18, camera);
        test = new Texture(Gdx.files.internal("player_idle.png"));
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        shapeRenderer = new ShapeRenderer();
        map = new TmxMapLoader().load("tiled_maps/maps/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        createCollisions();
        createPlayer();
        collisions.add(new Rectangle(1, 1, 1, 80));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        player.setStates(inputHandler.held, inputHandler.single);
        player.setSpeed();
        player.update(delta, collisions);
        camera.position.set(new Vector3((float)player.getPosition().getX(), (float)player.getPosition().getY(), 0));
        draw();
    }

    public void draw(){
        ScreenUtils.clear(0, 0, 0, 1);
        drawMap();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(test, (float) player.getPosition().getX(),(float) player.getPosition().getY(), 1f, 1f);
        game.batch.end();
        drawCollisions();
    }

    public void drawMap(){
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void drawCollisions(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for(Rectangle r:collisions){
            shapeRenderer.rect(5, 10, 2, 2);
            shapeRenderer.rect((float)r.getPosition().getX(), (float)r.getPosition().getY(),(float) r.getWidth(),(float) r.getHeight());
        }
        shapeRenderer.rect((float)player.getCollisionBox().getPosition().getX(), (float)player.getCollisionBox().getPosition().getY(), (float)player.getCollisionBox().getWidth(), (float)player.getCollisionBox().getHeight());
        shapeRenderer.end();
    }

    public void createCollisions(){
        MapLayer collision_layer = map.getLayers().get("collision_layer");
        MapObjects collisions_objects = collision_layer.getObjects();
        for(MapObject o:collisions_objects){
            if(o instanceof RectangleMapObject){
                RectangleMapObject rec = (RectangleMapObject) o;
                collisions.add(new Rectangle(rec.getRectangle().getWidth()*unitScale, rec.getRectangle().getHeight()*unitScale, rec.getRectangle().getX()*unitScale, rec.getRectangle().getY()*unitScale));
            }
        }
    }

    public void createPlayer(){
        MapLayer spawn_layer = map.getLayers().get("spawn");
        MapObjects spawn_objects = spawn_layer.getObjects();
        TextureMapObject player_spawn = (TextureMapObject)spawn_objects.get("spawn_point");
        System.out.println(player_spawn);
        player = new Player(1, 1, player_spawn.getX()*unitScale, player_spawn.getY()*unitScale, player_spawn.getX()*unitScale+0.33, player_spawn.getY()*unitScale, 0.5, 1);
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
