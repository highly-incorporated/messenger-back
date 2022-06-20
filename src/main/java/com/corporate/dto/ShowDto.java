package com.corporate.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data @Component
public class ShowDto {
    private List<Integer> id;
    private List<String> name;

    public ShowDto() {

    }

    public ShowDto(List<Integer> id, List<String> name) {
        this.id = id;
        this.name = name;
    }
}