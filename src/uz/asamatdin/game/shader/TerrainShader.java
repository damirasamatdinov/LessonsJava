package uz.asamatdin.game.shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import uz.asamatdin.game.entities.Camera;
import uz.asamatdin.game.entities.Light;
import uz.asamatdin.game.toolboxs.Maths;

/**
 * Created by Damir on 2016-01-31.
 */
public class TerrainShader extends ShaderProgram {

    public static final String VERTEX_FILE = "shader/terrainVertexShader.txt";
    public static final String FRAGMENT_FILE = "shader/terrainFragmentShader.txt";

    private int mMTransformationHandle;
    private int mMProjectionHandle;
    private int mMVIewHandle;
    private int mLightPositionHandle;
    private int mLightColourHandle;
    private int mShineDamperHandle;
    private int mReflectivityHandle;
    private int mUseFakeLightingHandle;
    private int mSkyColorHandle;
    private int mBackgroundTextureHandle;
    private int mRTextureHandle;
    private int mGTextureHandle;
    private int mBTextureHandle;
    private int mBlendMapTextureHandle;


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        mMTransformationHandle = getUniformLocation("u_MTransformation");
        mMProjectionHandle = getUniformLocation("u_MProjection");
        mMVIewHandle = getUniformLocation("u_MView");
        mLightColourHandle = getUniformLocation("u_LightColour");
        mLightPositionHandle = getUniformLocation("u_LightPosition");
        mShineDamperHandle = getUniformLocation("u_ShineDamper");
        mReflectivityHandle = getUniformLocation("u_Reflectivity");
        mUseFakeLightingHandle = getUniformLocation("u_UseFakeLighting");
        mSkyColorHandle = getUniformLocation("u_SkyColor");
        mBackgroundTextureHandle = getUniformLocation("u_TextureBackground");
        mRTextureHandle = getUniformLocation("u_TextureR");
        mGTextureHandle = getUniformLocation("u_TextureG");
        mBTextureHandle = getUniformLocation("u_TextureB");
        mBlendMapTextureHandle = getUniformLocation("u_TextureBlendMap");
    }

    public void connectTextureUnits() {
        loadInt(mBackgroundTextureHandle, 0);
        loadInt(mRTextureHandle, 1);
        loadInt(mGTextureHandle, 2);
        loadInt(mBTextureHandle, 3);
        loadInt(mBlendMapTextureHandle, 4);
    }

    public void loadSkyColor(float r, float g, float b) {
        loadVector3f(mSkyColorHandle, new Vector3f(r, g, b));
    }

    public void loadFakeLightingVariable(boolean useFake) {
        loadBoolean(mUseFakeLightingHandle, useFake);
    }

    public void loadShineVariables(float gamper, float reflecticity) {
        loadFloat(mShineDamperHandle, gamper);
        loadFloat(mReflectivityHandle, reflecticity);
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        loadMatrix(mMTransformationHandle, matrix4f);
    }

    public void loadLight(Light light) {
        loadVector3f(mLightPositionHandle, light.getPosition());
        loadVector3f(mLightColourHandle, light.getColour());
    }

    public void loadProjectionMatrix(Matrix4f matrix4f) {
        loadMatrix(mMProjectionHandle, matrix4f);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f matrix4f = Maths.createViewMatrix(camera);
        loadMatrix(mMVIewHandle, matrix4f);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
        bindAttribute(2, "normal");
    }
}
