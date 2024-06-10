package StrangeHexes.WTFMapGenerator.HexGenerators.SemiregularTiligs;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Octagon;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class OctagonGenerator extends BaseClusterGenerator {
    public static float side_size = 15;
    public static float x_iterator_x = 37;
    public static float y_iterator_y = 37;

    public static Seq<BaseShape> shapes = Seq.with(
            new Octagon(0, 0, side_size, 0)
    );

    @Override
    public Seq<GeneratedShape> buildClusters(Tiles tiles, int x_iterations, int y_iterations) {
        Seq<GeneratedShape> generated_shapes = new Seq<GeneratedShape>();
        for (int i = 0; i < y_iterations; i++) {
            for (int j = 0; j < x_iterations; j++) {
                for (int k = 0; k < shapes.size; k++) {
                    generated_shapes.add(shapes.get(k).GenerateAndPlace(
                            (j * x_iterator_x + i % 2 * y_iterator_x) + 40,
                            (j * x_iterator_y + i * y_iterator_y) + 40,
                            tiles));
                }
            }
        }
        return generated_shapes;
    }
}

