package com.CSV.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidMsg {
    Long rowNumber;
    String field;
    String message;
}
