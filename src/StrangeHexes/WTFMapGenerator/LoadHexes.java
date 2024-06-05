package StrangeHexes.WTFMapGenerator;

import StrangeHexes.WTFMapGenerator.MapGenerators.TestMapGenerator;
import arc.util.Log;
import mindustry.game.Rules;

import static mindustry.Vars.*;

public class LoadHexes {
    private static final Rules rules = new Rules();

    public static void init() {
        rules.pvp = true;
        rules.tags.put("hexed", "true");
        rules.canGameOver = false;
        rules.polygonCoreProtection = true;

        logic.reset();
        Log.info("Generating map...");
        TestMapGenerator generator = new TestMapGenerator();
        world.loadGenerator(512, 512, generator);
        //data.initHexes(generator.getHex());
        Log.info("Map generated.");
        state.rules = rules.copy();
        logic.play();
        netServer.openServer();
    }
}
