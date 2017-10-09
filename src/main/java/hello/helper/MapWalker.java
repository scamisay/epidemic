package hello.helper;

import java.util.Map;

public class MapWalker {

    private Map map;

    private MapWalker(){}

    public MapWalker(Map map) {
        this.map = map;
    }

    public Object walk(String dottedPath){
        try{
            String [] path = dottedPath.split("\\.");
            Object o = map;
            for(String pathElement : path){
                o = ((Map)o).get(pathElement);
            }
            return o;
        }catch (Exception e){
            return null;
        }
    }

    public Integer walkInteger(String dottedPath){
        try{
            Object o = walk(dottedPath);
            return Integer.parseInt((String)o);
        }catch (Exception e){
            return null;
        }
    }

}
