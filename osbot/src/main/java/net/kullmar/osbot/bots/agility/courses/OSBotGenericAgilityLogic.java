package net.kullmar.osbot.bots.agility.courses;

import net.kullmar.rsbots.api.agility.AgilityLogic;
import net.kullmar.rsbots.api.agility.CourseInfo;
import org.osbot.rs07.api.filter.ActionFilter;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OSBotGenericAgilityLogic extends AgilityLogic<RS2Object> {
    private MethodProvider methodProvider;
    private Collection<Filter<RS2Object>> reachableObstacleFilter;

    public OSBotGenericAgilityLogic(CourseInfo courseInfo, MethodProvider methodProvider) {
        super(courseInfo);
        this.methodProvider = methodProvider;
        reachableObstacleFilter = getReachableObstacleFilter();
    }

    @Override
    public RS2Object getNextObstacle() {
        return methodProvider.getObjects().closest(reachableObstacleFilter.toArray(new Filter[0]));
    }

    private Collection<Filter<RS2Object>> getReachableObstacleFilter() {
        List<Filter<RS2Object>> filters = new ArrayList<>();
        filters.add(getActionFilter());
        filters.add(getNameFilter());
        filters.add(object -> object.equals(lastObstacle));
        filters.add(object -> methodProvider.getMap().canReach(object));
        return filters;
    }

    private NameFilter<RS2Object> getNameFilter() {
        return new NameFilter<>(courseInfo.getObstaclesNames());
    }

    private ActionFilter<RS2Object> getActionFilter() {
        return new ActionFilter<>(courseInfo.getObstacleActions());
    }
}
