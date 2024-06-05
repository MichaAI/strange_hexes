package StrangeHexes.WTFMapGenerator.Shapes.Polygons;

import StrangeHexes.WTFMapGenerator.Drawers;
import StrangeHexes.WTFMapGenerator.Shapes.Polygon;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.world.Tiles;

import java.awt.*;
import java.awt.geom.Point2D;

import static arc.math.Mathf.PI;

public class Triangle extends Polygon {
    public final int n = 3;
    public final FloatSeq rotations = FloatSeq.with(90.0F, 210.0F, 330.0F);

    public Triangle(float offset_x, float offset_y, float side_length, float rotation) {
        super(offset_x, offset_y, side_length, rotation);
    }

    @Override
    public void GenerateAndPlace(float global_offset_x, float global_offset_y, Tiles tiles) {

        for (int i = 0; i < rotations.size; i++) {
            rotations.set(i, rotations.get(i) + rotation);
        }

        for (int i = 0; i < rotations.size; i++) {
            points.add(new Point2(
                    (int) ((Mathf.cosDeg(rotations.get(i)) * (side_length / (2 * Mathf.cos(PI / n)))) + global_offset_x),
                    (int) ((Mathf.cosDeg(rotations.get(i)) * (side_length / (2 * Mathf.sin(PI / n)))) + global_offset_x)
            ));
        }
        Log.info(points);
        for (int i = 0; i < rotations.size; i++) {
            Drawers.drawBresenhamLine(points.get(i).x, points.get(i).y,
                    points.get((i+1)%rotations.size).x, points.get((i+1)%rotations.size).y,
                    tiles
            );
        }
    }
}
