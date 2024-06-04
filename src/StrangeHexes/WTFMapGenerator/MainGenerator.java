package StrangeHexes.WTFMapGenerator;

import arc.util.Log;
import mindustry.game.Rules;

import static mindustry.Vars.*;

public class MainGenerator {
    private final Rules rules = new Rules();

    public void init() {
        rules.pvp = true;
        rules.tags.put("hexed", "true");
        rules.canGameOver = false;
        rules.polygonCoreProtection = true;

        logic.reset();
        Log.info("Generating map...");
        //HexedGenerator generator = new HexedGenerator();
        //world.loadGenerator(Hex.size, Hex.size, generator);
        //data.initHexes(generator.getHex());
        Log.info("Map generated.");
        state.rules = rules.copy();
        logic.play();
        netServer.openServer();
    }
}
