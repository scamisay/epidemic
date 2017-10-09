package hello.domain.agent;

import hello.domain.DomainHelper;
import hello.domain.Point;
import hello.domain.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by scamisay on 04/10/17.
 */
public abstract class Agent {

    protected AgentContainer body;
    protected Map<Action,Double> actionsProbabilities;

    public Agent(AgentContainer body, Map<Action, Double> actionsProbabilities) {
        new DomainHelper().checkNotNull(body);
        this.body = body;
        this.actionsProbabilities = (actionsProbabilities == null)? new HashMap<>(): actionsProbabilities;
    }

    protected abstract void _do();

    public void sendMessage(Agent receiver, String message){
        if(decide(Action.SEND_MESSAGE))
            receiver.receiveMessage(this, message);
    }

    public void receiveMessage(Agent sender, String message){
        if(decide(Action.RECEIVE_MESSAGE)){
            //DO SOMETHING
        }
    }

    protected boolean decide(Action action){
        Double chance = Math.random();
        return chance <= findActionProbability(action);
    }

    private final Double DEFAULT_PROBABILITY = 0.5;

    protected Double findActionProbability(Action action){
        Double prob = null;
        try{
            prob = actionsProbabilities.get(action);
            if(prob == null){
                return DEFAULT_PROBABILITY;
            }else {
                return prob;
            }
        }catch (Exception e){
            return DEFAULT_PROBABILITY;
        }
    }

    protected AgentContainer getBody(){
        return body;
    }
}
