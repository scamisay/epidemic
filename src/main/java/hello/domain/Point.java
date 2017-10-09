package hello.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by scamisay on 03/10/17.
 */
public class Point {
    private Integer i;
    private Integer j;
    private Map data;

    public Point(Integer i, Integer j) {
        new DomainHelper().checkPositive(i,j);
        this.i = i;
        this.j = j;
        this.data = new HashMap();
    }

    public Point(Integer i, Integer j, Map data) {
        new DomainHelper().checkPositive(i,j);
        this.i = i;
        this.j = j;
        this.data = data;
    }

    public Integer getI() {
        return i;
    }

    public Integer getJ() {
        return j;
    }

    public Map getData() {
        return data;
    }

    public Point setIClonning(Integer i) {
        return new Point(i,j,data);
    }

    public Point setJClonning(Integer j) {
        return new Point(i,j,data);
    }

    public Point setData(Map data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (!i.equals(point.i)) return false;
        return j.equals(point.j);
    }

    @Override
    public int hashCode() {
        int result = i.hashCode();
        result = 31 * result + j.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)",i,j);
    }
}
