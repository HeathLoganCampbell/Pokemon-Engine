package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    private Texture spriteSheet;
    public TextureRegion[] sprites;
    private final int spriteWidth;
    private final int spriteHeight;
    private final int sheetColumns;
    private final int sheetRows;

    public SpriteSheet(String path, int spriteWidth, int spriteHeight, int sheetColumns, int sheetRows) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.sheetColumns = sheetColumns;
        this.sheetRows = sheetRows;

        spriteSheet = new Texture(Gdx.files.internal(path), true);
        spriteSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sprites = new TextureRegion[sheetColumns * sheetRows];
        initializeSprites();
    }

    private void initializeSprites() {
        for (int row = 0; row < sheetRows; row++) {
            for (int col = 0; col < sheetColumns; col++) {
                int index = row * sheetColumns + col;
                sprites[index] = new TextureRegion(
                    spriteSheet,
                    col * spriteWidth,
                    row * spriteHeight,
                    spriteWidth,
                    spriteHeight
                );
            }
        }
    }

    public void drawSprite(SpriteBatch batch, int index, float x, float y) {
        batch.draw(sprites[index % sprites.length], x, y);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
