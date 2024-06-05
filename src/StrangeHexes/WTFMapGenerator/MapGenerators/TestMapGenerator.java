package StrangeHexes.WTFMapGenerator.MapGenerators;

import StrangeHexes.WTFMapGenerator.HexGenerators.BaseClusterGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.RegularTilings.HexagonGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.RegularTilings.SquareGenerator;
import StrangeHexes.WTFMapGenerator.HexGenerators.RegularTilings.TriangleGenerator;
import arc.func.Cons;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.Tiles;

public class TestMapGenerator extends BaseMapGenerator implements Cons<Tiles> {
    @Override
    public void get(Tiles tiles) {
        for (int i = 0; i<512; i++) {
            for (int j = 0; j<512;j++) {
                tiles.set(i, j, new Tile(i, j, Blocks.sand, Blocks.air, Blocks.air));
            }
        }
        BaseClusterGenerator hex_generator = new HexagonGenerator();
        hex_generator.buildClusters(tiles,10,10);
    }
}
