package hello.domain;

import hello.domain.agent.Agent;
import hello.domain.agent.AgentContainer;
import hello.domain.agent.AgentType;
import hello.helper.MapWalker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by scamisay on 03/10/17.
 */
public class World {

    private Set<Point> currentPositions;
    private Integer rows;
    private Integer cols;
    private Set<AgentContainer> agentBodies;

    private World(){}

    public World(Integer rows, Integer cols) {
        new DomainHelper().checkPositive(rows, cols);
        this.rows = rows;
        this.cols = cols;

        currentPositions = new HashSet<>();
        agentBodies = new HashSet<>();
    }

    public Set<Point> getCurrentPositions() {
        return currentPositions;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }

    private boolean isValidRow(Integer i){
        return 0 <= i && i < rows;
    }

    private boolean isValidCol(Integer j){
        return 0 <= j && j < cols;
    }

    private Point logicalMove(Point position, Direction direction){
        Point dest = null;
        switch (direction){
            case UP: if(isValidRow(position.getI()+1)){
                dest = position.setIClonning(position.getI()+1);
            }
                break;
            case DOWN:if(isValidRow(position.getI()-1)){
                dest = position.setIClonning(position.getI()-1);
            }
                break;

            case LEFT:if(isValidCol(position.getJ()-1)){
                dest = position.setJClonning(position.getJ()-1);
            }
                break;
            case RIGHT:if(isValidCol(position.getJ()+1)){
                dest = position.setJClonning(position.getJ()+1);
            }
                break;

            case RIGHT_UP:if(isValidCol(position.getJ()+1) && isValidRow(position.getI()+1)){
                dest = position.setJClonning(position.getJ()+1).setIClonning(position.getI()+1);
            }
                break;

            case RIGHT_DOWN:if(isValidCol(position.getJ()+1) && isValidRow(position.getI()-1)){
                dest = position.setJClonning(position.getJ()+1).setIClonning(position.getI()-1);
            }
                break;

            case LEFT_UP:if(isValidCol(position.getJ()-1)&& isValidRow(position.getI()+1)){
                dest = position.setJClonning(position.getJ()-1).setIClonning(position.getI()+1);
            }
                break;

            case LEFT_DOWN:if(isValidCol(position.getJ()-1) && isValidRow(position.getI()-1)){
                dest = position.setJClonning(position.getJ()-1).setIClonning(position.getI()-1);
            }
                break;


        }
        return dest;
    }

    public Point move(Point position, Direction direction) {
        Point dest = logicalMove(position, direction);

        if(dest != null && !dest.equals(position)){
            try{
                movePosition(position, dest);
                return dest;
            }catch (RuntimeException re){
                return position;
            }
        }else{
            return position;
        }
    }

    private synchronized void movePosition(Point source, Point dest) {
        if(currentPositions.contains(dest)){
            throw new RuntimeException("Posicion ocupada");
        }
        currentPositions.remove(source);
        currentPositions.add(dest);
    }

    public Point getSomeAvailablePosition() {
        Point randomPosition = null;
        do{
            randomPosition = getRandomPosition();
        }while (currentPositions.contains(randomPosition));
        return randomPosition;
    }

    private Point getRandomPosition(){
        Integer i = new Double( Math.random()*rows).intValue();
        Integer j = new Double( Math.random()*cols).intValue();
        return new Point(i, j);
    }

    public void createAgents(Map configuration) {
        MapWalker walker = new MapWalker(configuration);
        Integer agentId = 1;
        for(AgentType agentType: AgentType.values()){
            Map agentConfiguration = (Map)walker.walk("agents."+agentType.name());
            MapWalker agentWalker = new MapWalker(agentConfiguration);
            Integer quantity = agentWalker.walkInteger("quantity");
            for(int i = 0 ; i<quantity ; i++, agentId++){
                String agentName = agentId.toString();

                Point startingPosition = getSomeAvailablePosition();
                startingPosition.getData().put("agentType", agentType.name());
                startingPosition.getData().put("name", agentName);

                AgentContainer agentBody = AgentContainer.createAgent(
                        agentType,
                        agentName,
                        this,
                        startingPosition
                        );
                agentBodies.add(agentBody);
            }

        }

        //ahora inicializo cada agent
        agentBodies.forEach( agent -> agent.start());
    }

    public Set<Agent> findNeighbours(Point currentPosition) {
        Set<Point> neighboursPointsWithAgents =
                findNeighbourPoints(currentPosition).stream()
                .filter(point -> currentPositions.contains(point))
                .collect(Collectors.toSet());

        return agentBodies.stream()
                .filter( body -> neighboursPointsWithAgents.contains(body.getCurrentPosition()))
                .map( body -> body.getAgent())
                .collect(Collectors.toSet());
    }

    private Set<Point> findNeighbourPoints(Point currentPosition) {
        return Arrays.stream(Direction.values()).map(direction -> logicalMove(currentPosition, direction)).collect(Collectors.toSet());
    }

    public synchronized void updatePosition(Point currentPosition) {
        currentPositions.remove(currentPosition);
        currentPositions.add(currentPosition);
    }
}
