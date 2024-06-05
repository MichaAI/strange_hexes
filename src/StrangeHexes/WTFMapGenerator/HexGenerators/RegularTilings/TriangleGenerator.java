package StrangeHexes.WTFMapGenerator.HexGenerators.RegularTilings;

import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Triangle;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class TriangleGenerator extends BaseClusterGenerator {
    public static float x_iterator_x = 15;
    public static float y_iterator_x = 7.5f;
    public static float y_iterator_y = 13;
    public static float side_size = 15;

    public static Seq<BaseShape> shapes = Seq.with(
            new Triangle(0, 0, side_size, 0),
            //new Triangle(7.5f, -4.3f, side_size, 180)
            );

    @Override
    public void buildClusters(Tiles tiles, int x_iterations, int y_iterations) {
        for (int i = 0; i < y_iterations; i++) {
            for (int j = 0; j < x_iterations; j++) {
                for (int k = 0; k < shapes.size; k++) {
                    shapes.get(k).GenerateAndPlace(
                            (j*x_iterator_x+i%2*y_iterator_x)+40,
                            (j*x_iterator_y+i*y_iterator_y)+40,
                            tiles);
                }
            }
        }
    }
}
