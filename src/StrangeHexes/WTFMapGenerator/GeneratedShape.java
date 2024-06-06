package StrangeHexes.WTFMapGenerator;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.game.Team;

public class GeneratedShape {
    public Seq<Vec2> polygon;
    public Team team;

    public GeneratedShape(Seq<Vec2> polygon, Team team) {
        this.polygon = polygon;
        this.team = team;
    }

    public Seq<Vec2> getPolygon() {
        return polygon;
    }
}
