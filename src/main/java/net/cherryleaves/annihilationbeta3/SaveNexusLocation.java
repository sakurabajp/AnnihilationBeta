package net.cherryleaves.annihilationbeta3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SaveNexusLocation {
    // LocationをStringに変換
    public static String locationToString(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + "," +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch();
    }

    // StringをLocationに変換
    public static Location stringToLocation(String locationString) {
        String[] parts = locationString.split(",");
        if (parts.length == 6) {
            return new Location(
                    Bukkit.getWorld(parts[0]),
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Float.parseFloat(parts[4]),
                    Float.parseFloat(parts[5])
            );
        }
        return null; // 変換失敗時の処理
    }

    // Locationをyamlファイルに保存
    public static void saveLocationToYaml(Location location, File file, String path) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(path, locationToString(location));
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // yamlファイルからLocationを取得
    public static Location loadLocationFromYaml(File file, String path) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String locationString = config.getString(path);
        if (locationString != null) {
            return stringToLocation(locationString);
        }
        return null; // 読み込み失敗時の処理
    }
}
