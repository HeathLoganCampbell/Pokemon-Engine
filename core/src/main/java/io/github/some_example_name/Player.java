package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;

public class Player
{
    private static final float MOVEMENT_SPEED = 1f;

    private int walkingTicks = 0;
    private float x, y;
    private Direction direction = Direction.SOUTH;
    private Decal playerSprite;

    public Player()
    {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void update(float delta)
    {
        handleInput(delta);
    }

    public void render(DecalBatch batch)
    {
        int index = (((walkingTicks / 14) % 4) + (this.direction.ordinal() * 4)) % Assets.PLAYER_SPRITE_SHEET.sprites.length;
        if(playerSprite == null)
        {
            playerSprite = Decal.newDecal(Assets.PLAYER_SPRITE_SHEET.sprites[index], true);
            playerSprite.setScale(0.016f, 0.016f);
            playerSprite.rotateX(-35f);
        }
        else
        {
            playerSprite.setTextureRegion(Assets.PLAYER_SPRITE_SHEET.sprites[index]);
        }

        playerSprite.setPosition(this.x, 0.25f, this.y);
        batch.add(playerSprite);
    }

    private void handleInput(float deltaTime) {
        float velocityX = 0, velocityY = 0;
        boolean isWalking = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX -= 1;
            direction = Direction.WEST;
            isWalking = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX += 1;
            direction = Direction.EAST;
            isWalking = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY -= 1;
            direction = Direction.NORTH;
            isWalking = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY += 1;
            direction = Direction.SOUTH;
            isWalking = true;
        }

        if(isWalking)
        {
            walkingTicks++;
        }
        else
        {
            walkingTicks = 0;
        }

        float length = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (length > 0) {
            velocityX /= length;
            velocityY /= length;
        }

        velocityX *= MOVEMENT_SPEED;
        velocityY *= MOVEMENT_SPEED;

        // Update position based on velocity
        setX(getX() + velocityX * deltaTime);
        setY(getY() + velocityY * deltaTime);
    }
}
