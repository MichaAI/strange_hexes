package StrangeHexes.WTFMapGenerator.HexGenerators;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import arc.struct.Seq;
import mindustry.world.Tiles;

public abstract class BaseClusterGenerator {
    public Seq<BaseShape> shapes;

    public static float x_iterator_x = 0f;
    public static float x_iterator_y = 0f;
    public static float y_iterator_x = 0f;
    public static float y_iterator_y = 0f;

    public abstract Seq<GeneratedShape> buildClusters(Tiles tiles, int x_iterations, int y_iterations);
}
