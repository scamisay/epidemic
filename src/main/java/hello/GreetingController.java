package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.Direction;
import hello.domain.Point;
import hello.domain.World;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by scamisay on 30/09/17.
 */
@Controller
public class GreetingController {

    private World environment;


    public GreetingController() {
        this.environment = new World(40,40);
        initAgents(800);//PARAMETRIZABLE
    }

    private void initAgents(Integer numberOfAgents) {
        for(int i = 0 ; i < numberOfAgents; i++){
            Runnable runnable = () ->{
                Point position = environment.getSomeAvailablePosition();
                while(true){
                    try{
                        //dormir
                        Thread.sleep(2000);//PARAMETRIZABLE

                        //moverse
                        Integer directionIndex = new Double( Math.random()*Direction.values().length).intValue();
                        Direction direction = Direction.values()[directionIndex];
                        position = environment.move(position,direction);
                    }catch (Exception e){
                        System.out.print("ups");
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting(new ObjectMapper().writeValueAsString(environment.getCurrentPositions()));
    }

}