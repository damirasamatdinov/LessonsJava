package uz.asamatdin.game.entities;

import org.lwjgl.util.vector.Vector3f;
import uz.asamatdin.game.model.TextureModel;

/**
 * Created by Damir on 2016-01-31.
 */
public class Entity {
    private TextureModel textureModel;
    private Vector3f position;
    private float rx, ry, rz;
    private float scale;

    public Entity(TextureModel textureModel, Vector3f position, float rx, float ry, float rz, float scale) {
        this.textureModel = textureModel;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float rx, float ry, float rz) {
        this.rx += rx;
        this.ry += ry;
        this.rz += rz;
    }

    public void increaseScale(float scale) {
        this.scale += scale;
    }

    public TextureModel getTextureModel() {
        return textureModel;
    }

    public void setTextureModel(TextureModel textureModel) {
        this.textureModel = textureModel;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRz() {
        return rz;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
