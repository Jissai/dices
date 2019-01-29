package ant.dices.dal;

import io.jsondb.JsonDBTemplate;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DbAccess {

    static String BASE_SCAN_PACKAGE = "ant.dices.dal.schema";

    public static JsonDBTemplate test() {
        String userHome = System.getProperty("user.home");
        Path db = Paths.get(userHome, "test");
        JsonDBTemplate template = new JsonDBTemplate(db.toString(), DbAccess.BASE_SCAN_PACKAGE);
        return template;
    }

    public static JsonDBTemplate instance() {
        String userHome = System.getProperty("user.home");
        Path db = Paths.get(userHome, "prod");
        JsonDBTemplate template = new JsonDBTemplate(db.toString(), DbAccess.BASE_SCAN_PACKAGE);
        return template;
    }
}
