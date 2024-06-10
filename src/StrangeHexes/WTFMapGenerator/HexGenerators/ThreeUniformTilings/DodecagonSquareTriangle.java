package StrangeHexes.WTFMapGenerator.HexGenerators.ThreeUniformTilings;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.Shapes.BaseShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Dodecagon;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Hexagon;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Square;
import StrangeHexes.WTFMapGenerator.Shapes.Polygons.Triangle;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class DodecagonSquareTriangle extends BaseClusterGenerator {
    public static float side_size = 15;
    public static float x_iterator_x = 28*2+15*3;
    public static float y_iterator_y = 28+13*4+7.5f;
    public static float y_iterator_x = 28+15+7.5f;

    public static Seq<BaseShape> shapes = Seq.with(
            new Dodecagon(0, 0, side_size, 0),
            new Square(0, 28+7d, side_size, 0),
            new Square(0, 28+15+7d, side_size, 0),
            new Square(0, 28+15*2+7d, side_size, 0),
            new Hexagon(30.0, 28+13, side_size, 0),
            new Hexagon(28+15*2+7.5f, 7.5f+13, side_size, 0),
            new Hexagon(28+15+7.5f, 13*3+7.5f, side_size, 0),
            new Triangle(28+15+7.5f, 7.5f+4.3f, side_size, 0),
            new Triangle(28+15+7.5f, 7.5f+13+6.6f, side_size, 180),
            new Triangle(28+7.5f, 7.5f+13*2+4.3f, side_size, 0),
            new Triangle(28+15*2+7.5f, 7.5f+13*2+4.3f, side_size, 0),
            new Square(60, 28+7.5, side_size, 60),
            new Square(60, 28+15+7.5, side_size, 60),
            new Square(60, 28+15*2+7.5, side_size, 60),
            new Hexagon(90.0, 28+13, side_size, 0),
            new Hexagon(14, 28+13*3, side_size,0),
            new Hexagon(-14, 28+13*3, side_size,0),
            new Triangle(0, 28+13*2+4.3f, side_size, 0),
            new Triangle(0, 30+13*3+6.6f, side_size, 180),
            new Triangle(14, 28+14+6.6f, side_size, 180),
            new Triangle(-14, 28+14+6.6f, side_size, 180),
            new Square(120, 28+7.5, side_size, 30),
            new Square(120, 28+15+7.5, side_size, 30),
            new Square(120, 28+15*2+7.5, side_size, 30)
    );

    @Override
    public Seq<GeneratedShape> buildClusters(Tiles tiles, int x_iterations, int y_iterations) {
        Seq<GeneratedShape> generated_shapes = new Seq<GeneratedShape>();
        for (int i = 0; i < y_iterations; i++) {
            for (int j = 0; j < x_iterations; j++) {
                for (int k = 0; k < shapes.size; k++) {
                    generated_shapes.add(shapes.get(k).GenerateAndPlace(
                            (j * x_iterator_x + i % 2 * y_iterator_x) + 50,
                            (j * x_iterator_y + i * y_iterator_y) + 50,
                            tiles));
                }
            }
        }
        return generated_shapes;
    }
}

