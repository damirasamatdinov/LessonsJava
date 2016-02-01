package uz.asamatdin.game.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import uz.asamatdin.game.model.RawModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damir on 2016-01-30.
 */
public class Loader {

    List<Integer> vaoIds = new ArrayList<Integer>();
    List<Integer> vboIds = new ArrayList<Integer>();
    List<Integer> textureIds = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoId = createVAO();
        bindIndicesVBO(indices);
        storeDataInAttirbuteList(0, 3, positions);
        storeDataInAttirbuteList(1, 2, textureCoords);
        storeDataInAttirbuteList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoId, indices.length);

    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays();
        vaoIds.add(vaoId);
        GL30.glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeDataInAttirbuteList(int attributeNumber, int coordinateSize, float[] data) {
        int vboId = GL15.glGenBuffers();
        vboIds.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void cleanUp() {
        for (int vao : vaoIds) {
            GL30.glDeleteVertexArrays(vao);
        }

        for (int vbo : vboIds) {
            GL15.glDeleteBuffers(vbo);
        }

        for (int texture : textureIds) {
            GL11.glDeleteTextures(texture);
        }
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }


    public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureId = texture.getTextureID();
        textureIds.add(textureId);
        return textureId;
    }

    private void bindIndicesVBO(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vboIds.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
