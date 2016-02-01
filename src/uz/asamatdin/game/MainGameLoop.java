package uz.asamatdin.game;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import uz.asamatdin.game.engine.DisplayManager;
import uz.asamatdin.game.engine.Loader;
import uz.asamatdin.game.engine.MasterRenderer;
import uz.asamatdin.game.entities.Camera;
import uz.asamatdin.game.entities.Entity;
import uz.asamatdin.game.entities.Light;
import uz.asamatdin.game.loader.ObjLoader;
import uz.asamatdin.game.model.RawModel;
import uz.asamatdin.game.model.TextureModel;
import uz.asamatdin.game.terrains.Terrain;
import uz.asamatdin.game.textures.ModelTexture;
import uz.asamatdin.game.textures.TerrainTexture;
import uz.asamatdin.game.textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Damir on 2016-01-30.
 */
public class MainGameLoop {


    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        RawModel model = ObjLoader.loadObjModel("palm_tree", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("grass.png"));
        texture.setShineDamper(100);
        texture.setReflectivity(4);
        TextureModel textureModel = new TextureModel(model, texture);

        TextureModel grass_01 = new TextureModel(ObjLoader.loadObjModel("Grass_01", loader),
                new ModelTexture(loader.loadTexture("lawn_tile.png")));

        TextureModel grass_02 = new TextureModel(ObjLoader.loadObjModel("Grass_03", loader),
                new ModelTexture(loader.loadTexture("lawn_tile.png")));

        grass_01.getTexture().setHasTransparency(true);
        grass_01.getTexture().setUseFakeLighting(true);
        grass_02.getTexture().setUseFakeLighting(true);
        grass_02.getTexture().setHasTransparency(true);


        Light light = new Light(new Vector3f(3000, 3000, 3000), new Vector3f(1, 1, 1));

        /*Terrain Entities*/

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("ground.jpg"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("road.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("lawn_tile.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("grass.png"));
        TerrainTexture blendMapTexture = new TerrainTexture(loader.loadTexture("blend.png"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        Terrain terrain = new Terrain(0, 0, loader, terrainTexturePack, blendMapTexture);
        Terrain terrain1 = new Terrain(1, 0, loader, terrainTexturePack, blendMapTexture);

        /*End of Terrain Entities*/


        List<Entity> entityList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 400; i++) {
            entityList.add(new Entity(textureModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, 0, 0, 3));

            entityList.add(new Entity(grass_01, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, 0, 0, 1));

            entityList.add(new Entity(grass_02, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, 0, 0, 0.5f));
        }


        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {

            camera.move();
            for (Entity entity1 : entityList) {
                renderer.processEntity(entity1);
            }
            renderer.processTerrains(terrain);
            renderer.processTerrains(terrain1);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
