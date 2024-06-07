package StrangeHexes.WTFMapGenerator.Shapes.Polygons;

import StrangeHexes.WTFMapGenerator.Drawers;
import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.Shapes.Polygon;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import mindustry.game.Team;
import mindustry.world.Tiles;

public class Dodecagon extends Polygon {
    public final int n = 12;
    public final FloatSeq rotations = FloatSeq.with(15, 45, 75, 105, 135, 165, 195, 225, 255, 285, 315, 345);

    public Dodecagon(float offset_x, float offset_y, float side_length, float rotation) {
        super(offset_x, offset_y, side_length, rotation);
    }

    @Override
    public GeneratedShape GenerateAndPlace(float global_offset_x, float global_offset_y, Tiles tiles) {

        FloatSeq rotations = new FloatSeq();
        for (int i = 0; i < this.rotations.size; i++) {
            rotations.add(this.rotations.get(i) + rotation);
        }

        Seq<Vec2> points = new Seq<>();
        for (int i = 0; i < rotations.size; i++) {
            points.add(new Vec2(
                    (float) ((Mathf.cosDeg(rotations.get(i)) * (side_length / (2 * Mathf.sinDeg(180f / n)))) + global_offset_x + offset_x),
                    (float) ((Mathf.sinDeg(rotations.get(i)) * (side_length / (2 * Mathf.sinDeg(180f / n)))) + global_offset_y + offset_y)
            ));
        }

        for (int i = 0; i < rotations.size; i++) {
            Drawers.drawBresenhamLine((int) (points.get(i).x), (int) (points.get(i).y),
                    (int) points.get((i + 1) % rotations.size).x, (int) points.get((i + 1) % rotations.size).y
            );
        }

        return new GeneratedShape(points, Team.derelict);
    }
}
