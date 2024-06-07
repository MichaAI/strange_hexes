package StrangeHexes.WTFMapGenerator.MapGenerators;

import arc.func.Cons;
import mindustry.world.Tiles;

public abstract class BaseMapGenerator implements Cons<Tiles> {
    @Override
    public abstract void get(Tiles tiles);
    public abstract void setBlock(int x, int y);
    public abstract void setFloor(int x, int y);
}
