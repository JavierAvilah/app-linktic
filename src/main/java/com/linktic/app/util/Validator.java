package com.linktic.app.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;


import java.util.Arrays;
import java.util.Set;


@Component
public class Validator {
    private final jakarta.validation.Validator javaxValidator;

    public Validator() {
        this.javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> T validate(T t){
        if(t!=null){
            Set<ConstraintViolation<T>> violations = javaxValidator.validate(t);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            return t;
        }
        return null;
    }

    public interface NotPresentFoundCallback{
        void notPresentFound(String attrName);
    }

    public void validatePresent(Class<?> clazz, Object object, NotPresentFoundCallback notPresentFoundCallback, String... attrs){
        int mode;

        if(Arrays.asList(attrs).contains("*")){
            mode = 1;
        }else{
            mode = 2;
        }

        ReflectionUtils.doWithFields(clazz, field -> {
            field.setAccessible(true);
            if(mode==1){
                Object o1 = field.get(object);
                if(o1==null){
                    notPresentFoundCallback.notPresentFound(field.getName());
                }else{
                    if(o1 instanceof JsonNullable && !((JsonNullable<?>)o1).isPresent()){
                        notPresentFoundCallback.notPresentFound(field.getName());
                    }
                }
            }else{
                if(Arrays.stream(attrs).anyMatch(a -> a.equals(field.getName()))){
                    Object o2 = field.get(object);
                    if(o2==null){
                        notPresentFoundCallback.notPresentFound(field.getName());
                    }else{
                        if(o2 instanceof JsonNullable && !((JsonNullable<?>)o2).isPresent()){
                            notPresentFoundCallback.notPresentFound(field.getName());
                        }
                    }
                }
            }
        });
    }
}
