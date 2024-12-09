package com.linktic.app.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Class<?> type, Object id) {
        super("Entity of type "+type.getSimpleName()+" with id "+getId(id)+" could not be found");
    }

    public EntityNotFoundException(Class<?> type) {
        super("Entity of type "+type.getSimpleName()+" could not be found");
    }

    private static String getId(Object object){
        if(object==null){
            return "null";
        }else{
            return object.toString();
        }
    }
}
