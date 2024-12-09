package com.linktic.app.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Iterator;

@Component
public class JsonNullableModelConverter implements ModelConverter {
	@Override
	public Schema<?> resolve(AnnotatedType annotatedType, ModelConverterContext modelConverterContext,
										Iterator<ModelConverter> iterator) {
		if (annotatedType.getType() instanceof SimpleType) {
			SimpleType type = (SimpleType) annotatedType.getType();
			if(type.getRawClass().getName().equals("org.openapitools.jackson.nullable.JsonNullable")){
				JavaType javaType = type.getBindings().getBoundType(0);
				annotatedType = new AnnotatedType(javaType.getRawClass()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).resolveAsRef(false);
				return this.resolve(annotatedType, modelConverterContext, iterator);
			}

			if(type.getBindings().size() !=0 && Object.class.equals(type.getBindings().getBoundType(0).getRawClass())){
				annotatedType = new AnnotatedType(type.getRawClass()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).resolveAsRef(true);
				return this.resolve(annotatedType, modelConverterContext, iterator);
			}
		}
		else if (annotatedType.getType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
			Type type = parameterizedType.getActualTypeArguments()[0];
			if (type instanceof WildcardType) {
				WildcardType wildcardType = (WildcardType) type;
				if (Object.class.equals(wildcardType.getUpperBounds()[0])) {
					annotatedType = new AnnotatedType(parameterizedType.getRawType()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).resolveAsRef(true);
					return this.resolve(annotatedType, modelConverterContext, iterator);
				}
			}
		}
		if (iterator.hasNext()) {
			return iterator.next().resolve(annotatedType, modelConverterContext, iterator);
		}
		else {
			return null;
		}
	}
}