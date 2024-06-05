package StrangeHexes.WTFMapGenerator.Shapes;

import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.world.Tiles;

public abstract class BaseShape {
    public float rotation;
    public float offset_x;
    public float offset_y;

    protected BaseShape(float rotation, float offset_x, float offset_y) {
        this.rotation = rotation;
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }

    public abstract void GenerateAndPlace(float base_offset_x, float base_offset_y, Tiles tiles);


}