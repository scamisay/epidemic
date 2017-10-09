package hello.domain.agent;

import hello.domain.Point;
import hello.domain.World;

import java.util.HashMap;

public class AgentContainer extends Thread{

    private static final Integer SLEEP_TIME = 2000;

    private Agent agent;
    private Point currentPosition;
    private World environment;
    private String name;
    private Integer age=0;

    private AgentContainer(){}

    private AgentContainer(String name, World environment, Point initialPosition){
        this.name = name;
        this.environment = environment;
        this.currentPosition = initialPosition;
    }

    public static AgentContainer createAgent(AgentType agentType, String name, World environment, Point initialPosition){
        AgentContainer body = new AgentContainer(name,environment,initialPosition);
        Agent agent = null;
        switch (agentType){
            case cured: agent = new CuredAgent(body, new HashMap<>()); break;
            case healthy: agent = new HealthyAgent(body, new HashMap<>()); break;
            case infected: agent = new InfectedAgent(body, new HashMap<>()); break;
            case therapist: agent = new TherapistAgent(body, new HashMap<>()); break;
        }
        body.setAgent(agent);
        return body;
    }

    public String getAgentName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public World getEnvironment(){
        return environment;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Agent getAgent() {
        return agent;
    }

    protected synchronized void setAgent(Agent agent){
        if(agent != null){
            this.agent = agent;
            getCurrentPosition().getData().put("agentType", agent.getAgentType().name());
            getEnvironment().updatePosition(getCurrentPosition());
        }
    }

    protected void changeCurrentPosition(Point newPosition){
        if(!newPosition.equals(currentPosition)){
            currentPosition = newPosition;
        }
    }

    //TODO: implementar memoria del agente
    //TODO: implementar muerte del agente
    //TODO: implementar cambio del tipo del agente

    @Override
    public void run() {
        while (true){
            try{
                //dormir
                Thread.sleep(SLEEP_TIME);

                //hacer algo
                agent._do();

                //envejecer
                age++;
            }catch (Exception e){

            }
        }
    }
}
