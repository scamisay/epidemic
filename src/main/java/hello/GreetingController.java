package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.domain.Direction;
import hello.domain.Point;
import hello.domain.World;
import hello.helper.MapWalker;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by scamisay on 30/09/17.
 */
@Controller
public class GreetingController {

    private World environment;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Map message) throws Exception {
        return new Greeting(new ObjectMapper().writeValueAsString(environment.getCurrentPositions()));
    }

    private static final String DELIM = ".";
    private static final String ENVIRONMENT = "environment";
    private static final String ENV_WIDTH = ENVIRONMENT+DELIM+"width";
    private static final String ENV_HEIGHT = ENVIRONMENT+DELIM+"height";

    @MessageMapping("/configure")
    public void configure(Map configuration){
        //comienzo simulacion, si configure viene vacio uso valores default
        MapWalker walker = new MapWalker(configuration);
        Integer width = walker.walkInteger(ENV_WIDTH);
        Integer height = walker.walkInteger(ENV_HEIGHT);
        environment = new World(width,height);
        environment.createAgents(configuration);
    }

}