package hello.domain.agent;

import hello.domain.Direction;
import hello.domain.Point;

import java.util.HashMap;
import java.util.Map;

public class TherapistAgent extends HumanAgent{

    public TherapistAgent(AgentContainer body, Map<Action, Double> actionsProbabilities) {
        super(body, actionsProbabilities);
    }

    @Override
    public void _do() {
        if (decide(Action.MOVE)) move();
    }

    @Override
    public void receiveMessage(Agent sender, String message) {
        if(decide(Action.RECEIVE_MESSAGE)){
            if(message.equals(Action.INFECT.name())){
                body.setAgent(new InfectedAgent(body, new HashMap<>()));
            }
        }
    }

    @Override
    protected AgentType getAgentType() {
        return AgentType.therapist;
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
