package uz.asamatdin.game.toolboxs;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import uz.asamatdin.game.entities.Camera;

/**
 * Created by Damir on 2016-01-31.
 */
public class Maths {
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        Matrix4f.translate(translation, matrix4f, matrix4f);
        Matrix4f.rotate((float) Math.toDegrees(rx), new Vector3f(1, 0, 0), matrix4f, matrix4f);
        Matrix4f.rotate((float) Math.toDegrees(ry), new Vector3f(0, 1, 0), matrix4f, matrix4f);
        Matrix4f.rotate((float) Math.toDegrees(rz), new Vector3f(0, 0, 1), matrix4f, matrix4f);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix4f, matrix4f);

        return matrix4f;
    }


    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPosition = camera.getPosition();
        Vector3f negactivePosition = new Vector3f(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        Matrix4f.translate(negactivePosition, viewMatrix, viewMatrix);
        return viewMatrix;
    }
}
