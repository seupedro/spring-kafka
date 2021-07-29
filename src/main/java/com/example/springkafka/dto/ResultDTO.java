package com.example.springkafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultDTO {

    List<String> result;

}
