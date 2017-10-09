package hello.domain.agent;

import hello.domain.Point;
import hello.domain.World;

import java.util.Map;

/**
 * Created by scamisay on 04/10/17.
 */
public abstract class HumanAgent extends Agent {

    public HumanAgent(AgentContainer body, Map<Action, Double> actionsProbabilities) {
        super(body, actionsProbabilities);
    }

    protected abstract void move();
}
