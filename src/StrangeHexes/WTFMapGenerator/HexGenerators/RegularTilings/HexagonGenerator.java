package StrangeHexes.WTFMapGenerator.HexGenerators.RegularTilings;

import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Hexagon;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Square;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class HexagonGenerator extends BaseClusterGenerator {
    public static float side_size = 15;
    public static float x_iterator_x = 15 + side_size / (Mathf.sinDeg(180f / 6));
    public static float y_iterator_y = 25; //пж просто не пытайтесь понять как я получил это значение

    public static Seq<BaseShape> shapes = Seq.with(
            new Hexagon(0, 0, side_size, 0),
            new Hexagon(24, 12, side_size, 0)
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
