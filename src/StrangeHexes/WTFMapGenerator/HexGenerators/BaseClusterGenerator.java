package StrangeHexes.WTFMapGenerator.HexGenerators;

import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import arc.struct.Seq;
import mindustry.world.Tiles;

public abstract class BaseClusterGenerator {
    public Seq<BaseShape> shapes;
    public static void buildClusters(Tiles tiles, int x_iterations, int y_iterations) {};
    public float x_iterator_x = 0f;
    public static float x_iterator_y = 0f;
    public float y_iterator_x = 0f;
    public float y_iterator_y = 0f;
}
