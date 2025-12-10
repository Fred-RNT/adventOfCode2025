package com.adis.advent.d10;

import com.adis.advent.Utils;

import java.util.List;

public class D10 {
    List<Machine> machines;

    public D10(String nom) {
        machines = Utils.parseInputFile(nom, Machine::new);
    }

    public static void main(String[] args) {
        D10 puzzle = new D10("d10.input");
        puzzle.resolve();
    }

    private void resolve() {
        System.out.println(machines.stream().mapToInt(Machine::mini).sum());
    }
}
