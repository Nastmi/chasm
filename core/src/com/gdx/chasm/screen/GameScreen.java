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
import com.badlogic.gdx.maps.MapProperties;
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
import com.sun.tools.javac.comp.Check;
import org.graalvm.compiler.options.ModifiableOptionValues;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class GameScreen implements Screen {

    final Chasm game;
    final float unitScale = 1/12f;
    OrthographicCamera camera;
    OrthographicCamera guiCamera;
    FitViewport viewport;
    FitViewport guiViewport;
    Texture enemyTexture;
    Player player;
    InputHandler inputHandler;
    ShapeRenderer shapeRenderer;
    ArrayList<Entity> collisions = new ArrayList<>();
    TiledMap map;
    int worldWidth;
    int worldHeight;
    OrthogonalTiledMapRenderer mapRenderer;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    BitmapFont pauseFont;
    Vector2D spawn;
    Vector2D colSpawn;
    double timer;
    Rectangle menuButton;
    Rectangle resumeButton;
    Rectangle restartButton;
    Texture pauseMenuTexture;
    boolean paused;
    public GameScreen(final Chasm game){
        this.game = game;
        camera = new OrthographicCamera();
        guiCamera = new OrthographicCamera();
        viewport = new FitViewport(32f, 18f, camera);
        guiViewport = new FitViewport(1920, 1080, guiCamera);
        inputHandler = new InputHandler();
        pauseMenuTexture = new Texture(Gdx.files.internal("pause.png"));
        Gdx.input.setInputProcessor(inputHandler);
        shapeRenderer = new ShapeRenderer();
        createMap();
        createCollisions();
        createPlayer();
        collisions.add(player);
        EnemySpawn.returnEnemies(unitScale, map, collisions);
        createGui();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/04B_30__.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        parameter.size = 60;
        pauseFont = generator.generateFont(parameter);
        generator.dispose();
        paused = false;
    }

    private void createGui() {
        restartButton = new Rectangle(6.4, 2.3, -100, -100);
        menuButton = new Rectangle(6.4, 2.3, -100, -100);
        resumeButton = new Rectangle(6.4, 2.3, -100, -100);
        timer = 0;
    }

    private void createMap() {
        map = new TmxMapLoader().load("tiled_maps/maps/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        MapProperties prop = map.getProperties();
        worldWidth = prop.get("width", Integer.class);
        worldHeight = prop.get("height", Integer.class);
        System.out.println(worldWidth);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        removeDead();
        setPause();
    }

    private void setPause() {
        if(inputHandler.single.get("pause")){
            paused = !paused;
            inputHandler.single.put("pause", false);
        }
    }

    public void update(float delta){
        if(paused)
            checkGui();
        if(!paused){
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
            clampCamera();
            timer += delta;
            setCheckpoint();
            resumeButton.setPosition(new Vector2D(camera.position.x-3.2, camera.position.y + 2.75));
            menuButton.setPosition(new Vector2D(camera.position.x-3.2, camera.position.y - 1));
            restartButton.setPosition(new Vector2D(camera.position.x-3.2, camera.position.y - 5));
        }
    }

    private void checkGui() {
        if(inputHandler.mousePressed){
            double[] mousePos = inputHandler.mousePos;
            mousePos[0] -= viewport.getLeftGutterWidth();
            mousePos[1] -= viewport.getBottomGutterHeight();
            mousePos[0] = mousePos[0]/viewport.getScreenWidth() * viewport.getWorldWidth() + (camera.position.x - viewport.getWorldWidth()/2);
            mousePos[1] = viewport.getWorldHeight() - mousePos[1]/viewport.getScreenHeight() * viewport.getWorldHeight() + (camera.position.y - viewport.getWorldHeight()/2);
            inputHandler.mousePressed = false;
            if(CollisionHandler.pointRectangleCollision(new Vector2D(mousePos[0], mousePos[1]), resumeButton)){
                paused = false;
            }
            else if(CollisionHandler.pointRectangleCollision(new Vector2D(mousePos[0], mousePos[1]), menuButton)){

            }
            else if(CollisionHandler.pointRectangleCollision(new Vector2D(mousePos[0], mousePos[1]), restartButton)){
                paused = false;
                player.isDead = true;
            }
        }
    }

    public void clampCamera(){
        double posX = player.getPosition().getX();
        double posY = player.getPosition().getY();
        if(player.getPosition().getX() >= worldWidth - viewport.getWorldWidth()/2){
            posX = worldWidth - viewport.getWorldWidth()/2;
        }
        if(player.getPosition().getY() >= worldHeight- viewport.getWorldHeight()/2){
            posY = worldHeight- viewport.getWorldHeight()/2;
        }
        if(player.getPosition().getX() <= viewport.getWorldWidth()/2){
            posX = viewport.getWorldWidth()/2;
        }
        if(player.getPosition().getY() <= viewport.getWorldHeight()/2){
            posY = viewport.getWorldHeight()/2;
        }
        camera.position.set(new Vector3((float)posX, (float)posY, 0));
        guiCamera.position.set(new Vector3((float)posX*60, (float)posY*60, 0));
    }
    public void draw(){
        ScreenUtils.clear(0, 0, 0, 1);
        drawMap();
        camera.update();
        Renderer.render(game.batch, collisions, camera);
        ArrayList<Rectangle> rest = new ArrayList<>();
        if(paused){
            rest.add(menuButton);
            rest.add(restartButton);
            rest.add(resumeButton);
        }
        drawGui();
        //DebugRenderer.render(shapeRenderer, collisions, camera, rest);
    }

    public void drawGui(){
        int cutTime = (int)timer;
        int minutes = (cutTime % 3600) / 60;
        int seconds = cutTime % 60;
        String timeString = minutes + ":" + seconds;
        game.batch.begin();
        game.batch.setProjectionMatrix(guiCamera.combined);
        font.draw(game.batch, timeString, 20, 1060);
        if(paused){
            game.batch.draw(pauseMenuTexture, guiViewport.getWorldWidth()/2-315, guiViewport.getWorldHeight()/2-450, 630, 900);
            pauseFont.draw(game.batch, "Resume", (float)812, (float)800);
            pauseFont.draw(game.batch, "Menu", (float)860, (float)570);
            pauseFont.draw(game.batch, "Restart", (float)790, (float)335);
        }
        game.batch.end();
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
        MapLayer checkpoint_layer = map.getLayers().get("checkpoints");
        MapObjects checkpoint_objects = checkpoint_layer.getObjects();
        for(MapObject o:collisions_objects){
            if(o instanceof RectangleMapObject){
                RectangleMapObject rec = (RectangleMapObject) o;
                collisions.add(new CollisionEntity(rec.getRectangle().getX()*unitScale, rec.getRectangle().getY()*unitScale, rec.getRectangle().getWidth()*unitScale, rec.getRectangle().getHeight()*unitScale));
            }
        }
        for(MapObject o:checkpoint_objects){
            if(o instanceof RectangleMapObject){
                RectangleMapObject rec = (RectangleMapObject) o;
                MapProperties prop = o.getProperties();
                int number = (int)prop.get("number");
                collisions.add(new Checkpoint((double)rec.getRectangle().getX()*unitScale, (double)rec.getRectangle().getY()*unitScale, (double)rec.getRectangle().getWidth()*unitScale, (double)rec.getRectangle().getHeight()*unitScale, new Texture(Gdx.files.internal("player_idle.png")), number));
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

    public void setCheckpoint(){
        for(Entity e:collisions){
            if(e instanceof Checkpoint){
                Checkpoint c = (Checkpoint) e;
                int num = c.getNumber();
                if(num == player.getCheckpointNumber()){
                    spawn = new Vector2D(c.getCollisionBox().getPosition().getX(), c.getCollisionBox().getPosition().getY());
                    colSpawn = new Vector2D(c.getCollisionBox().getPosition().getX()+0.33, c.getCollisionBox().getPosition().getY());
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        guiViewport.update(width, height, true);
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
