package uz.asamatdin.game.model;

import uz.asamatdin.game.textures.ModelTexture;

/**
 * Created by Damir on 2016-01-30.
 */
public class TextureModel {
    private RawModel rawModel;
    private ModelTexture texture;

    public TextureModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
