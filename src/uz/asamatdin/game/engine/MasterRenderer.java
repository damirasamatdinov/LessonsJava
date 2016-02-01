package uz.asamatdin.game.engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import uz.asamatdin.game.entities.Camera;
import uz.asamatdin.game.entities.Entity;
import uz.asamatdin.game.entities.Light;
import uz.asamatdin.game.model.TextureModel;
import uz.asamatdin.game.shader.StaticShader;
import uz.asamatdin.game.shader.TerrainShader;
import uz.asamatdin.game.terrains.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Damir on 2016-01-31.
 */
public class MasterRenderer {


    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private static final float RED = 0.5f;
    private static final float GREEN = 0.5f;
    private static final float BLUE = 0.5f;
    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();
    private Matrix4f mProjectionMatrix;

    public MasterRenderer() {
        enableCulling();
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, mProjectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, mProjectionMatrix);
    }

    private Map<TextureModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Light sun, Camera camera) {
        prepare();
        shader.start();
        shader.loadSkyColor(RED, GREEN, BLUE);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public void processTerrains(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(Entity entity) {
        TextureModel model = entity.getTextureModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> entityList = new ArrayList<>();
            entityList.add(entity);
            entities.put(model, entityList);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }

    private void createProjectionMatrix() {
        float ratio = (float) Display.getWidth() / ((float) Display.getHeight());
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * ratio);
        float x_scale = y_scale / ratio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        mProjectionMatrix = new Matrix4f();
        mProjectionMatrix.m00 = x_scale;
        mProjectionMatrix.m11 = y_scale;
        mProjectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        mProjectionMatrix.m23 = -1;
        mProjectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        mProjectionMatrix.m33 = 0;
    }

}
