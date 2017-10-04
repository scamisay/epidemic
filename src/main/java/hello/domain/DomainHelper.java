package hello.domain;

import java.util.Collection;

/**
 * Created by scamisay on 03/10/17.
 */
public class DomainHelper {

    public void checkNotNull(Object ... args){
        for(Object andArg : args){
            if(andArg == null){
                throw new RuntimeException("Todos los paramentros son obligatorios");
            }
        }
    }

    public void checkNotEmpty(String ... args){
        for(String andArg : args){
            if(andArg == null || andArg.isEmpty()){
                throw new RuntimeException("Todos los paramentros son obligatorios");
            }
        }
    }

    public void checkNotEmpty(Collection... args){
        for(Collection aCollection : args){
            if(aCollection == null || aCollection.isEmpty()){
                throw new RuntimeException("Todos los paramentros son obligatorios");
            }
        }
    }

    public void checkPositive(Number ... numbers){
        for(Number aNumber: numbers){
            if(aNumber == null || aNumber.intValue() < 0){
                throw new RuntimeException("La cantidad debe ser positiva");
            }
        }
    }
}
