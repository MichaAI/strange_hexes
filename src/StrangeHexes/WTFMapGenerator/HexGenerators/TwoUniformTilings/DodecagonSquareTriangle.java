package StrangeHexes.WTFMapGenerator.HexGenerators.TwoUniformTilings;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Dodecagon;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Square;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Triangle;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class DodecagonSquareTriangle extends BaseClusterGenerator {
    public static float side_size = 15;
    public static float x_iterator_x = 28*2;
    public static float y_iterator_y = 28*2;
    public static float y_iterator_x = 28+15+7.5f;

    public static Seq<BaseShape> shapes = Seq.with(
            new Dodecagon(0, 0, side_size, 0),
            new Triangle(30.0, 28 + 4, side_size, 180),
            new Triangle(60.0, 28 + 4, side_size, 90),
            new Square(45.0, 29 + 11, side_size, 0),
            new Triangle(28f, 29 + 7 + 4.3f, side_size, 0),
            new Triangle(29 + 7 + 4.3f, 28, side_size, 270)
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

