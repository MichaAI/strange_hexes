package StrangeHexes.WTFMapGenerator.MapGenerators;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.SemiregularTiligs.DodecagonSquareHex;
import arc.func.Cons;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.Tiles;

import static arc.math.geom.Intersector.isInPolygon;

public class TestMapGenerator extends BaseMapGenerator implements Cons<Tiles> {
    public BaseClusterGenerator generator;
    public Seq<GeneratedShape> shapes;
    @Override
    public void get(Tiles tiles) {
        for (int i = 0; i<512; i++) {
            for (int j = 0; j<512;j++) {
                tiles.set(i, j, new Tile(i, j, Blocks.sand, Blocks.air, Blocks.air));
            }
        }
        generator = new DodecagonSquareHex();
        shapes = generator.buildClusters(tiles,6,7);

        boolean continue_flag;
        for (int i = 0; i<512; i++) {
            for (int j = 0; j<512;j++) {
                continue_flag = false;
                for (int k = 0; k < shapes.size; k++) {
                    if (isInPolygon(shapes.get(k).getPolygon(), new Vec2(i, j))) {
                        continue_flag = true;
                        break;
                    }
                }
                if (continue_flag) continue;
                tiles.set(i, j, new Tile(i, j, Blocks.sand, Blocks.air, Blocks.stoneWall));
            }
        }
    }
}
