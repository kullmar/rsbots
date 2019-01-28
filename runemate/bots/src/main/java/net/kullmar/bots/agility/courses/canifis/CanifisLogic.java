package net.kullmar.bots.agility.courses.canifis;

import com.google.gson.Gson;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Resources;
import net.kullmar.bots.legacy.AgilityAction;
import net.kullmar.bots.agility.courses.CourseLogic;
import net.kullmar.bots.agility.AgilityState;
import net.kullmar.rsbots.api.agility.courses.data.AreaData;
import net.kullmar.rsbots.api.agility.courses.data.CanifisData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CanifisLogic implements CourseLogic {
    private static final String DATA_FILE = "canifis.json";

    private static final Map<CanifisArea, Area> areaBounds = new HashMap<>();
    private static final List<String> actions = new ArrayList<>();

    public CanifisLogic() {
        init();
    }

    private void init() {
        initActions();
        // initBounds();
    }

    private void initActions() {
        actions.add(AgilityAction.CLIMB.getAction());
        actions.add(AgilityAction.JUMP.getAction());
        actions.add(AgilityAction.VAULT.getAction());
    }

    @Override
    public AgilityState getCurrentState() {
        return null;
    }

    @Override
    public AgilityState getState(Class<? extends AgilityState> agilityState) {
        return null;
    }

    @Override
    public GameObject getNextObstacle() {
        String[] actionsArray = actions.toArray(new String[0]);
        CanifisArea currentArea = getCurrentArea();
        Area bounds = areaBounds.get(currentArea);
        return GameObjects.newQuery().within(bounds).actions(actionsArray).results().first();
    }

    public void setLastUsedObstacle(GameObject gameObject) {

    }

    @Override
    public void updateState(Class<? extends AgilityState> state) {

    }

    @Override
    public Area getStartingArea() {
        return null;
    }

    private void initBounds() {
        CanifisData courseData = getCourseData();
        AreaData[] areasData = courseData.getAreas();
        CanifisArea[] canifisAreas = CanifisArea.values();

        assert areasData.length == CanifisArea.values().length;

        int i = 0;
        for (AreaData areaData : areasData) {
            // areaBounds.put(canifisAreas[i], fromAreaData(areaData));
            ++i;
        }
    }

    private CanifisData getCourseData() {
        InputStream jsonFile = Resources.getAsStream(DATA_FILE);
        if (jsonFile == null) {
            throw new IllegalStateException("Unable to load " + DATA_FILE);
        }
        return new Gson().fromJson(new InputStreamReader(jsonFile), CanifisData.class);
    }

//    private Area fromAreaData(AreaData areaData) {
//        RectangleData boundsData = areaData.getBounds();
//        Coordinate bottomLeft = boundsData.getBottomLeft();
//        Coordinate topRight = new Coordinate(boundsData.getX() + boundsData.getWidth() - 1,
//                boundsData.getY() + boundsData.getHeight() - 1, areaData.getPlane());
//        return Area.rectangular(bottomLeft, topRight);
//    }

    private CanifisArea getCurrentArea() {
        if (Players.getLocal() == null) {
            return null;
        }
        Coordinate playerLocation = Players.getLocal().getPosition();
        Optional<Map.Entry<CanifisArea, Area>> result = areaBounds.entrySet().stream()
                .filter(entry -> entry.getValue().contains(playerLocation))
                .findAny();
        return result.map(Map.Entry::getKey).orElse(null);
    }
}
