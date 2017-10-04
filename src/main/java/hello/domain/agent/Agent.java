package hello.domain.agent;

import hello.domain.DomainHelper;
import hello.domain.Point;
import hello.domain.World;

import java.util.Map;

/**
 * Created by scamisay on 04/10/17.
 */
public abstract class Agent {

    protected Point currentPosition;
    protected World environment;
    protected Map<Action,Double> actionsProbabilities;

    public Agent(Point currentPosition, World environment, Map<Action, Double> actionsProbabilities) {
        new DomainHelper().checkNotNull(currentPosition, environment, actionsProbabilities);
        this.currentPosition = currentPosition;
        this.environment = environment;
        this.actionsProbabilities = actionsProbabilities;
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

    protected Double findActionProbability(Action action){
        try{
            return actionsProbabilities.get(action);
        }catch (Exception e){
            return 0.5;
        }
    }
}
