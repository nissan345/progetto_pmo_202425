/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

/**
 *
 * @author OS
 */
public class LevelRequirement {

    private final int minLvl; 

    public LevelRequirement(int minLvl) {
        if (minLvl < 1) throw  new IllegalArgumentException("minLvl >= 1"); 
        this.minLvl = minLvl; 
    }

    public boolean isSatisfiedBy(Character c) {
        return c.getLvl() >= this.minLvl; 
    }
}
