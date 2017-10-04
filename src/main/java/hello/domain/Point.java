package hello.domain;

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

    public Point setI(Integer i) {
        return new Point(i,j);
    }

    public Point setJ(Integer j) {
        return new Point(i,j);
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
