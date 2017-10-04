package hello.domain.agent;

import hello.domain.Direction;
import hello.domain.Point;
import hello.domain.World;

import java.util.Map;

/**
 * Created by scamisay on 04/10/17.
 */
public class HealthyAgent extends HumanAgent {


    public HealthyAgent(Point currentPosition, World environment, Map<Action, Double> actionsProbabilities) {
        super(currentPosition, environment, actionsProbabilities);
    }

    @Override
    public void _do() {
        if (decide(Action.MOVE)) move();
    }

    @Override
    protected void move() {
        Integer directionIndex = new Double( Math.random()* Direction.values().length).intValue();
        Direction direction = Direction.values()[directionIndex];
        currentPosition = environment.move(currentPosition,direction);
    }
}
