package com.andris;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class Shape {

    int vSize;

    int hSize;

    int symbol;

    int id;

    int hPos;

    int vPos;

    Shape copyOf() {
        return new Shape(vSize, hSize, symbol, id, hPos, vPos);
    }
}
