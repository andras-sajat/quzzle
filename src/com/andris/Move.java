package com.andris;

import lombok.Data;

@Data
public class Move {

    int piece;

    DIRECTIONS direction;

    int distance;

    Move(int piece, DIRECTIONS dir) {
        this.piece = piece;
        this.direction = dir;
    }

    public String toString() {
        return "Shape: "+(piece+1) + " to "+direction;
    }

    public enum DIRECTIONS {
        DIRECTION_LEFT,
        DIRECTION_RIGHT,
        DIRECTION_UP,
        DIRECTION_DOWN,
    }
}
