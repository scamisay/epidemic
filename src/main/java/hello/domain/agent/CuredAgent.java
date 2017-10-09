package hello.domain.agent;

import hello.domain.Direction;
import hello.domain.Point;

import java.util.Map;

public class CuredAgent extends HumanAgent{

    public CuredAgent(AgentContainer body, Map<Action, Double> actionsProbabilities) {
        super(body, actionsProbabilities);
    }

    @Override
    public void _do() {
        if (decide(Action.MOVE)) move();
    }

    @Override
    public void receiveMessage(Agent sender, String message) {
        if(decide(Action.RECEIVE_MESSAGE)){
            //DO SOMETHING
        }
    }

    @Override
    protected AgentType getAgentType() {
        return AgentType.cured;
    }

    @Override
    protected void move() {
        Integer directionIndex = new Double( Math.random()* Direction.values().length).intValue();
        Direction direction = Direction.values()[directionIndex];

        Point newPosition = getBody().getEnvironment().move(
                getBody().getCurrentPosition(),
                direction);

        getBody().changeCurrentPosition(newPosition);
    }
}
