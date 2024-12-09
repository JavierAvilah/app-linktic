package com.linktic.app.util;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    private ModelMapper modelMapper;

    public Converter(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    public  <T> T map(Object sourceObject, Class<T> mapType){
        return modelMapper.map(sourceObject, mapType);
    }

    public  <T> List<T> mapList(List<?> sourceList, Class<T> mapType){
        return sourceList
                .stream()
                .map(element -> modelMapper.map(element, mapType))
                .collect(Collectors.toList());
    }

    public <T> Page<T> mapPage(Page<?> sourcePage, Class<T> mapType) {
        List<T> mappedList = sourcePage
                .getContent()
                .stream()
                .map(element -> modelMapper.map(element, mapType))
                .collect(Collectors.toList());

        return new PageImpl<>(mappedList, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
