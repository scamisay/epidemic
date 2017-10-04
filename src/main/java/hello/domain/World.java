package hello.domain;

import javax.swing.text.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by scamisay on 03/10/17.
 */
public class World {

    private Set<Point> currentPositions;
    private Integer rows;
    private Integer cols;

    private World(){}

    public World(Integer rows, Integer cols) {
        new DomainHelper().checkPositive(rows, cols);
        this.rows = rows;
        this.cols = cols;

        currentPositions = new HashSet<>();
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

    public Point move(Point position, Direction direction) {
        Point dest = null;
        switch (direction){
            case UP: if(isValidRow(position.getI()+1)){
                dest = position.setI(position.getI()+1);
                }
                break;
            case DOWN:if(isValidRow(position.getI()-1)){
                dest = position.setI(position.getI()-1);
                }
                break;

            case LEFT:if(isValidCol(position.getI()-1)){
                dest = position.setJ(position.getI()-1);
                }
                break;
            case RIGHT:if(isValidCol(position.getI()+1)){
                dest = position.setJ(position.getI()+1);
                }
                break;
        }

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
}
