package StrangeHexes.WTFMapGenerator.Shapes.Polygons;

import StrangeHexes.WTFMapGenerator.Drawers;
import StrangeHexes.WTFMapGenerator.Shapes.Polygon;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import mindustry.world.Tiles;

public class Square extends Polygon {
    public final int n = 4;
    public final FloatSeq rotations = FloatSeq.with(45f, 135f, 225f, 315f);
    public Square(float offset_x, float offset_y, float side_length, float rotation) {
        super(offset_x, offset_y, side_length, rotation);
    }

    @Override
    public void GenerateAndPlace(float global_offset_x, float global_offset_y, Tiles tiles) {

        for (int i = 0; i < rotations.size; i++) {
            rotations.set(i, rotations.get(i) + rotation);
        }

        Seq<Point2> points = new Seq<>();
        for (int i = 0; i < rotations.size; i++) {
            points.add(new Point2(
                    (int) ((Mathf.cosDeg(rotations.get(i)) * (side_length / (2 * Mathf.sinDeg(180f / n)))) + global_offset_x),
                    (int) ((Mathf.sinDeg(rotations.get(i)) * (side_length / (2 * Mathf.sinDeg(180f / n)))) + global_offset_y)
            ));
        }

        for (int i = 0; i < rotations.size; i++) {
            Drawers.drawBresenhamLine(points.get(i).x, points.get(i).y,
                    points.get((i + 1) % rotations.size).x, points.get((i + 1) % rotations.size).y,
                    tiles
            );
        }
    }
}
