package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class InfinitePlane {
    private List<ModelInstance> planes;
    private Texture texture;
    private float planeSize;
    private int numberOfPlanes;

    public InfinitePlane( float planeSize, int numberOfPlanes) {
        this.planeSize = planeSize;
        this.numberOfPlanes = numberOfPlanes;
        this.planes = new ArrayList<>();

        createPlanes();
    }

    private void createPlanes() {
        Texture planeTexture = new Texture(Gdx.files.internal("floor.png")); // Replace with your texture file
        planeTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); // Set to repeat

        Material material = new Material(TextureAttribute.createDiffuse(planeTexture));
        ModelBuilder modelBuilder = new ModelBuilder();
        Model model = modelBuilder.createRect(
            -planeSize / 2, 0, -planeSize / 2,
            -planeSize / 2, 0, planeSize / 2,
            planeSize / 2, 0, planeSize / 2,
            planeSize / 2, 0, -planeSize / 2,
            0f,0f,0f,
            material,
            VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates
        );

        for (int i = -numberOfPlanes / 2; i < numberOfPlanes /2; i++) {
            for (int j = -numberOfPlanes / 2; j < numberOfPlanes / 2; j++) {
                ModelInstance instance = new ModelInstance(model);
                instance.transform.setTranslation(i * planeSize, 0, j * planeSize);
                planes.add(instance);
            }
        }
    }

    public void update() {
        // Update plane positions based on camera position if needed
    }

    public void render(ModelBatch modelBatch, Environment environment) {
        for (ModelInstance plane : planes) {
            modelBatch.render(plane, environment);
        }
    }
}
