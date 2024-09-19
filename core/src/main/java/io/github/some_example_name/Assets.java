package io.github.some_example_name;

public class Assets
{
    public static SpriteSheet PLAYER_SPRITE_SHEET;

    public static void init()
    {
        PLAYER_SPRITE_SHEET = new SpriteSheet("player_sprite.png", 32, 32, 4, 4);
    }

    public static void dispose()
    {
        PLAYER_SPRITE_SHEET.dispose();
    }
}
