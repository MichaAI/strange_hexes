package StrangeHexes.WTFMapGenerator;

import StrangeHexes.WTFMapGenerator.MapGenerators.BaseMapGenerator;
import StrangeHexes.WTFMapGenerator.MapGenerators.TestMapGenerator2;
import arc.util.Log;
import mindustry.game.Rules;

import static mindustry.Vars.*;

public class LoadHexes {
    private static final Rules rules = new Rules();
    public static BaseMapGenerator map_generator;

    public static void init() {
        rules.pvp = true;
        rules.tags.put("hexed", "true");
        rules.canGameOver = false;
        rules.polygonCoreProtection = true;

        logic.reset();
        Log.info("Generating map...");
        map_generator = new TestMapGenerator2();
        world.loadGenerator(600, 600, map_generator);
        //data.initHexes(generator.getHex());
        Log.info("Map generated.");
        state.rules = rules.copy();
        logic.play();
        netServer.openServer();
    }
}
