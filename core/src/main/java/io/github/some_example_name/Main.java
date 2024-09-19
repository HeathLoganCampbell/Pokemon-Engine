package io.github.some_example_name;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.utils.Array;

public class Main extends ApplicationAdapter {
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    public boolean loading;
    private Model ship;
    private ModelInstance instance;
    private TextureRegion[][] tileRegions;
    private Plane plane;
    private Texture tileset;

    private DecalBatch decalBatch;

    private Player player;

    private InfinitePlane infinitePlane;

    @Override
    public void create () {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        Assets.init();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 2.4f, 1.2f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        camController.forwardKey = Input.Keys.I;
        camController.backwardKey = Input.Keys.K;
        camController.rotateLeftKey = Input.Keys.J;
        camController.rotateRightKey = Input.Keys.L;
        Gdx.input.setInputProcessor(camController);

        infinitePlane = new InfinitePlane(0.2f, 1); // Adjust dimensions as necessary

        ModelLoader loader = new ObjLoader();
        ObjLoader.ObjLoaderParameters objLoaderParameters = new ObjLoader.ObjLoaderParameters();
        objLoaderParameters.flipV = true;

        ship = loader.loadModel(Gdx.files.internal("models/untitled.obj"), objLoaderParameters);

        tileset = new Texture(Gdx.files.internal("TileSet.png"));

        instance = new ModelInstance(ship);
        instances.add(instance);
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));

        for (Material material : instance.materials) {
            TextureAttribute textureAttr = (TextureAttribute) material.get(TextureAttribute.Diffuse);
            if (textureAttr != null) {
                textureAttr.textureDescription.minFilter = Texture.TextureFilter.Nearest;
                textureAttr.textureDescription.magFilter = Texture.TextureFilter.Nearest;
            }
        }

        player = new Player();
    }

    @Override
    public void render () {
//        camController.update();
        player.update(Gdx.graphics.getDeltaTime());
        infinitePlane.update();
        cam.position.set(player.getX(), 3f,player.getY() + 1.2f);
        cam.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Render the 3D models
        modelBatch.begin(cam);
        infinitePlane.render(modelBatch, environment);
        modelBatch.render(instances, environment);
        modelBatch.end();

        player.render(decalBatch);
        decalBatch.flush();
    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }

    public void resume () {
    }

    public void resize (int width, int height) {
    }

    public void pause () {
    }
}
