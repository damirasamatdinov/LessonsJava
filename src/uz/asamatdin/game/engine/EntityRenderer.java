package uz.asamatdin.game.engine;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import uz.asamatdin.game.entities.Entity;
import uz.asamatdin.game.model.RawModel;
import uz.asamatdin.game.model.TextureModel;
import uz.asamatdin.game.shader.StaticShader;
import uz.asamatdin.game.textures.ModelTexture;
import uz.asamatdin.game.toolboxs.Maths;

import java.util.List;
import java.util.Map;

/**
 * Created by Damir on 2016-01-30.
 */
public class EntityRenderer {


    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f mProjectionMatrix) {
        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(mProjectionMatrix);
        shader.stop();
    }


    public void render(Map<TextureModel, List<Entity>> entities) {
        for (TextureModel model : entities.keySet()) {
            prepareTextureModel(model);

            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            unbindTextureModel();
        }
    }

    public void prepareTextureModel(TextureModel textureModel) {
        RawModel model = textureModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = textureModel.getTexture();
        if (texture.isHasTransparency()) {
            MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureModel.getTexture().getTextureId());
    }

    private void unbindTextureModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
                entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

    }


}
