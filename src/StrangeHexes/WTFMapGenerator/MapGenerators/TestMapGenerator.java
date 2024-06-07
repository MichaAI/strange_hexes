package StrangeHexes.WTFMapGenerator.MapGenerators;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.SemiregularTiligs.DodecagonSquareHex;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.Tiles;

import static arc.math.geom.Intersector.isInPolygon;

public class TestMapGenerator extends BaseMapGenerator implements Cons<Tiles> {
    public BaseClusterGenerator generator;
    public Seq<GeneratedShape> shapes;
    public Tiles tiles;
    int seed1 = 0;
    int seed2 = 0;
    Block[][] floors = {
            {Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.grass},
            {Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.grass, Blocks.grass},
            {Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.grass, Blocks.shale},
            {Blocks.darksandTaintedWater, Blocks.darksandTaintedWater, Blocks.moss, Blocks.moss, Blocks.sporeMoss, Blocks.stone},
            {Blocks.ice, Blocks.iceSnow, Blocks.snow, Blocks.dacite, Blocks.hotrock, Blocks.salt}
    };

    Block[][] blocks = {
            {Blocks.stoneWall, Blocks.stoneWall, Blocks.sandWall, Blocks.sandWall, Blocks.pine, Blocks.pine},
            {Blocks.stoneWall, Blocks.stoneWall, Blocks.duneWall, Blocks.duneWall, Blocks.pine, Blocks.pine},
            {Blocks.stoneWall, Blocks.stoneWall, Blocks.duneWall, Blocks.duneWall, Blocks.pine, Blocks.pine},
            {Blocks.sporeWall, Blocks.sporeWall, Blocks.sporeWall, Blocks.sporeWall, Blocks.sporeWall, Blocks.stoneWall},
            {Blocks.iceWall, Blocks.snowWall, Blocks.snowWall, Blocks.snowWall, Blocks.stoneWall, Blocks.saltWall}
    };

    @Override
    public void get(Tiles tiles) {
        this.tiles = tiles;
        for (int i = 0; i<512; i++) {
            for (int j = 0; j<512;j++) {
                setFloor(i, j);
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
                setBlock(i, j);
            }
        }
    }

    @Override
    public void setBlock(int x, int y) {
        int temp = Mathf.clamp((int)((Simplex.noise2d(seed1, 12, 0.6, 1.0 / 400, x, y) - 0.5) * 10 * blocks.length), 0, blocks.length-1);
        int elev = Mathf.clamp((int)(((Simplex.noise2d(seed2, 12, 0.6, 1.0 / 700, x, y) - 0.5) * 10 + 0.15f) * blocks[0].length), 0, blocks[0].length-1);
        tiles.set(x, y, new Tile(x, y,
                floors[temp][elev], Blocks.air, blocks[temp][elev]));
    }

    @Override
    public void setFloor(int x, int y) {
        int temp = Mathf.clamp((int)((Simplex.noise2d(seed1, 12, 0.6, 1.0 / 400, x, y) - 0.5) * 10 * blocks.length), 0, blocks.length-1);
        int elev = Mathf.clamp((int)(((Simplex.noise2d(seed2, 12, 0.6, 1.0 / 700, x, y) - 0.5) * 10 + 0.15f) * blocks[0].length), 0, blocks[0].length-1);
        tiles.set(x, y, new Tile(x, y,
                floors[temp][elev], Blocks.air, Blocks.air));
    }
}
