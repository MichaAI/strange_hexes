package StrangeHexes.WTFMapGenerator.MapGenerators;

import StrangeHexes.WTFMapGenerator.GeneratedShape;
import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.ThreeUniformTilings.DodecagonSquareTriangle;
import StrangeHexes.WTFMapGenerator.LoadHexes;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.maps.filters.GenerateFilter;
import mindustry.maps.filters.OreFilter;
import mindustry.world.Tile;
import mindustry.world.Tiles;

import static arc.math.Mathf.sign;
import static arc.math.geom.Intersector.isInPolygon;
import static mindustry.Vars.maps;

public class TestMapGenerator2 extends BaseMapGenerator implements Cons<Tiles> {
    public BaseClusterGenerator generator;
    public Seq<GeneratedShape> shapes;
    public Tiles tiles;
    public Seq<GenerateFilter> ores;

    int seed1 = 0;
    int seed2 = 0;

    @Override
    public void get(Tiles tiles) {

        this.tiles = tiles;

        seed1 = Mathf.random(10000);
        seed2 = Mathf.random(10000);

        ores = new Seq<>();
        maps.addDefaultOres(ores);
        ores.each(o -> ((OreFilter)o).threshold -= 0.05f);
        ores.insert(0, new OreFilter(){{
            ore = Blocks.oreScrap;
            scl += 2 / 2.1F;
        }});
        ores.each(GenerateFilter::randomize);
        GenerateFilter.GenerateInput in = new GenerateFilter.GenerateInput();

        for (int i = 0; i<600; i++) {
            for (int j = 0; j<600;j++) {
                setFloor(i, j);
            }
        }

        generator = new DodecagonSquareTriangle();
        shapes = generator.buildClusters(tiles,4,4);

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
        tiles.set(x, y, new Tile(x, y, Blocks.darksand, Blocks.air, Blocks.stoneWall));
    }

    @Override
    public void setFloor(int x, int y) {
        tiles.set(x, y, new Tile(x, y, Blocks.darksand, Blocks.air, Blocks.air));
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
        dx = x2 - x1;
        dy = y2 - y1;
        incx = sign(dx);
        incy = sign(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx > dy) {
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }

        x = x1;
        y = y1;
        err = el / 2;
        LoadHexes.map_generator.setBlock(x, y);
        tiles.getn(x, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x+3, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x+2, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x+1, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x-1, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x-2, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x-3, y).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y+3).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y+2).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y+1).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y-1).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y-2).setFloor(Blocks.sand.asFloor());
        tiles.getn(x, y-3).setFloor(Blocks.sand.asFloor());
        for (int t = 0; t < el; t++) {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;
                y += incy;
            } else {
                x += pdx;
                y += pdy;
            }

            LoadHexes.map_generator.setBlock(x, y);
            tiles.getn(x, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x+3, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x+2, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x+1, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x-1, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x-2, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x-3, y).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y+3).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y+2).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y+1).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y-1).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y-2).setFloor(Blocks.sand.asFloor());
            tiles.getn(x, y-3).setFloor(Blocks.sand.asFloor());
        }
    }

}
