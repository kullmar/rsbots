package net.kullmar.rsbots.api.agility;

import com.google.gson.Gson;
import net.kullmar.rsbots.api.GsonUtil;
import net.kullmar.rsbots.api.agility.courses.data.AreaData;
import net.kullmar.rsbots.api.agility.courses.data.pyramid.*;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class PyramidJsonTest {

    @Test
    public void testPyramidJsonData() {
        InputStream jsonFile = this.getClass().getResourceAsStream("pyramid.json");
        Gson gson = GsonUtil.getGson();
        PyramidData courseData = gson.fromJson(new InputStreamReader(jsonFile), PyramidData.class);

        AreaData area = courseData.getAreas()[0];
        assert(area != null && area.getBounds() != null && area.getId() != null);

        BoulderData boulder = courseData.getBoulders()[0];
        assert(boulder != null && boulder.getBoulderAreas() != null);
        BoulderAreaData boulderArea = boulder.getBoulderAreas()[0];
        assert(boulderArea != null && boulderArea.getBounds() != null && boulderArea.getId() != null);

        LevelData[] pyramidLevels = courseData.getPyramidLevels();
        assert pyramidLevels != null;
        LevelData pyramidLevel = pyramidLevels[0];
        ObstacleAreaData[] areasByObstacle = pyramidLevel.getAreasByObstacle();
        assert(areasByObstacle != null);
        ObstacleAreaData obstacleAreaData = areasByObstacle[0];

        System.out.println(gson.toJson(courseData));
    }
}
