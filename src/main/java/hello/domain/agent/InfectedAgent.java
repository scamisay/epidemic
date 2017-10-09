package hello.domain.agent;

import hello.domain.Direction;
import hello.domain.Point;

import java.util.Map;
import java.util.Set;

public class InfectedAgent extends HumanAgent{

    public InfectedAgent(AgentContainer body, Map<Action, Double> actionsProbabilities) {
        super(body, actionsProbabilities);
    }

    @Override
    public void _do() {
        if (decide(Action.MOVE)) move();
        if (decide(Action.INFECT)) infect();
    }

    @Override
    public void receiveMessage(Agent sender, String message) {
        if(decide(Action.RECEIVE_MESSAGE)){
            //DO SOMETHING
        }
    }

    @Override
    protected AgentType getAgentType() {
        return AgentType.infected;
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

    private void infect(){
        Set<Agent> neighbours = getBody().getEnvironment().findNeighbours(getBody().getCurrentPosition());
        neighbours.forEach( neighbour -> sendMessage(neighbour, Action.INFECT.name()));
    }
}
