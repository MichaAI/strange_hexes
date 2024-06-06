package StrangeHexes.WTFMapGenerator.Shapes;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import mindustry.world.Tiles;

public abstract class Polygon extends BaseShape{
    public double side_length;
    public int n;

    protected Polygon(float offset_x, float offset_y, float side_length, float rotation) {
        super(rotation, offset_x, offset_y);
        this.side_length = side_length;
    }

    @Override
    public abstract GeneratedShape GenerateAndPlace(float base_offset_x, float base_offset_y, Tiles tiles);
}
