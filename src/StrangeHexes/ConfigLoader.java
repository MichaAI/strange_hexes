package StrangeHexes;

import arc.files.Fi;

import static mindustry.Vars.dataDirectory;

import org.json.JSONObject;

public class ConfigLoader {

    public static JSONObject config;

    public static void load() {
        Fi cfg = dataDirectory.child("config.json");
        config = new JSONObject(cfg.readString());
    }
}
