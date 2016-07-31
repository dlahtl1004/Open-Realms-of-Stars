package org.openRealmOfStars.player.ship;

import java.util.ArrayList;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Ship Design class, design is used to create a ship. Design is also
 * linked to Ship Stat class
 * 
 */
public class ShipDesign {

  public static final String DESIGN_OK = "Design is OK!";
  
  /**
   * Ship Design name
   */
  private String name;
  
  /**
   * Ship's hull
   */
  private ShipHull hull;
  
  /**
   * Ship's component list
   */
  private ArrayList<ShipComponent> components;


  public ShipDesign(ShipHull hull) {
    setHull(hull);
    components = new ArrayList<>();
    name = "Design";
  }
  
  /**
   * Get number of components in ship's component list
   * @return number of components
   */
  public int getNumberOfComponents() {
    return components.size();
  }
  
  /**
   * Get ship component by index. Can return null if index is out list.
   * @param index Component list index
   * @return ShipComponent or null
   */
  public ShipComponent getComponent(int index) {
    if (index >= 0 && index < components.size()) {
      return components.get(index);
    }
    return null;
  }
  
  /**
   * Add new component to list
   * @param comp ShipComponent to add
   */
  public void addComponent(ShipComponent comp) {
    components.add(comp);
  }
  
  /**
   * Remove component from the list
   * @param index Component's index to remove
   */
  public void removeComponent(int index) {
    components.remove(index);
  }
  

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ShipHull getHull() {
    return hull;
  }

  public void setHull(ShipHull hull) {
    if (hull != null) {
      this.hull = hull;
    }
  }

  
  /**
   * Get current energy usage for ship. This is very useful information
   * when ship is being design.
   * @return Energy usage, positive means extra energy and negative
   * that too much energy is used.
   */
  public int getFreeEnergy() {
    int energy = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getEnergyResource() > 0) {
        energy = energy +comp.getEnergyResource();
      }
      if (comp.getEnergyRequirement() > 0) {
        energy = energy -comp.getEnergyRequirement();
      }
    }
    return energy;
  }

  /**
   * Get maximum shield for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum shield
   */
  public int getTotalShield() {
    int shield = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.SHIELD) {
        shield = shield +comp.getDefenseValue();
      }
    }
    return shield;
  }

  /**
   * Get maximum armor for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum armor
   */
  public int getTotalArmor() {
    int armor = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.ARMOR) {
        armor = armor +comp.getDefenseValue();
      }
    }
    return armor;
  }
  
  /**
   * Does ship design have weapons? True if one weapon is in place.
   * @return True if weapon is found, otherwise false
   */
  
  public boolean hasWeapons() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.WEAPON_BEAM ||
          comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO ||
          comp.getType() == ShipComponentType.WEAPON_HE_MISSILE ||
          comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO ||
          comp.getType() == ShipComponentType.WEAPON_RAILGUN) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does ship design have engine? True if engine is in place.
   * @return True if engine is found, otherwise false
   */
  public boolean hasEngine() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get number of free slots
   * @return number of free slots in ship design
   */
  public int getFreeSlots() {
    return hull.getMaxSlot()-components.size();
  }

  /**
   * Get current cost for ship. This is very useful information
   * when ship is being design.
   * @return Cost in production
   */
  public int getCost() {
    int cost = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      cost = cost +comp.getCost();
    }
    cost = cost +hull.getCost();
    return cost;
  }

  /**
   * Get current metal cost for ship. This is very useful information
   * when ship is being design.
   * @return Cost in production
   */
  public int getMetalCost() {
    int cost = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      cost = cost +comp.getMetalCost();
    }
    cost = cost +hull.getMetalCost();
    return cost;
  }
  
  /**
   * Calculate military power of design. Design needs to have at least single
   * weapon to be a military ship
   * @return Military power
   */
  public int getTotalMilitaryPower() {
    int power = 0;
    boolean militaryShip = false;
    power = getHull().getSlotHull()*getHull().getMaxSlot();
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.WEAPON_BEAM || 
          comp.getType() == ShipComponentType.WEAPON_RAILGUN ||
          comp.getType() == ShipComponentType.WEAPON_HE_MISSILE || 
          comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
        militaryShip = true;
        power = power+comp.getDamage();
      }
      if (comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
        power = power+comp.getDamage()/2;
      }
      if (comp.getType() == ShipComponentType.ARMOR ||
          comp.getType() == ShipComponentType.SHIELD) {
        power = power+comp.getDefenseValue();
      }
      if (comp.getType() == ShipComponentType.ENGINE && 
          getHull().getHullType() != ShipHullType.STARBASE) {
        power = power+comp.getTacticSpeed()-1;
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER) {
        power = power+comp.getDamage()/10;
      }
      if (comp.getType() == ShipComponentType.JAMMER) {
        power = power+comp.getDamage()/10;
      }
    }
    if (!militaryShip) {
      power = 0;
    }
    return power;
  }
  
  /**
   * Get ship component list in priority order
   * @return Ship component array
   */
  public ShipComponent[] getComponentList() {
    ShipComponent result[] =  new ShipComponent[getNumberOfComponents()];
    for (int i=0;i<getNumberOfComponents();i++) {
      result[i] = getComponent(i);
    }
    return result;
  }
  
  /**
   * Change component priority order
   * @param index Index which to change
   * @param higher true for higher priority and false for lower
   * @return int new changed index, if change happened
   */
  public int changePriority(int index, boolean higher) {
    if (index >= 0 && index < getNumberOfComponents()) {
      ShipComponent result[] = getComponentList();
      ShipComponent temp = result[index];
      if (higher && index > 0) {
        int target = index -1;
        result[index] = result[target];
        result[target] = temp;
        components.clear();
        for (int i = 0;i<result.length;i++) {
          components.add(result[i]);
        }
        return target;
      }
      if (!higher && index < result.length-1) {
        int target = index +1;
        result[index] = result[target];
        result[target] = temp;
        components.clear();
        for (int i = 0;i<result.length;i++) {
          components.add(result[i]);
        }
        return target;
      }
    }
    return index;
  }
  
  /**
   * Get Design Info, but not the component info
   * @return Design info as a String
   */
  public String getDesignInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(hull.getHullType().toString());
    sb.append("\n");
    sb.append("Energy: ");
    sb.append(getFreeEnergy());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(getTotalShield());
    sb.append(" Armor: ");
    sb.append(getTotalArmor());
    sb.append(" Hull Points: ");
    sb.append(hull.getSlotHull()*hull.getMaxSlot());
    if (getTotalMilitaryPower() > 0) {
      sb.append("\n");
      sb.append("Military power: ");
      sb.append(getTotalMilitaryPower());
    }
    sb.append("\n");
    sb.append("Slots: ");
    sb.append(components.size());
    sb.append("/");
    sb.append(hull.getMaxSlot());
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      sb.append("\n");
      sb.append("Cargo: ");
      sb.append(getFreeSlots()*10);
      sb.append("units or ");
      sb.append(getFreeSlots()*2);
      sb.append(" population");
    }
    return sb.toString();
  }
  
  public String getFlaws() {
    boolean designOk = true;
    StringBuilder sb = new StringBuilder();
    if (!hasEngine()) {
      designOk = false;
      sb.append("Engine is missing!\n");
    }
    if (getFreeSlots()<0) {
      designOk = false;
      sb.append("Too many components!\n");
    }
    if (getFreeSlots()== 0 
        && (hull.getHullType()==ShipHullType.FREIGHTER ||
            hull.getHullType()==ShipHullType.PRIVATEER)) {
      designOk = false;
      sb.append("No cargo space!\n");
    }
    if (hasWeapons() 
        && (hull.getHullType()==ShipHullType.FREIGHTER ||
            hull.getHullType()==ShipHullType.PROBE)) {
      designOk = false;
      sb.append("No weapons allowed in "+hull.getHullType().toString()+"!\n");
    }
    for (int i = 0;i<getNumberOfComponents();i++) {
      ShipComponent comp = getComponent(i);
      if (comp.getType() == ShipComponentType.COLONY_MODULE && hull.getHullType() != ShipHullType.FREIGHTER) {
        designOk = false;
        sb.append("Colonization module in non freighter hull.");
      }
    }
    if (getFreeEnergy()<0) {
      designOk = false;
      sb.append("No enough energy resources!");
    }
    if (designOk) {
      // Return OK text
      sb.append(DESIGN_OK);
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(hull.getHullType().toString());
    sb.append("\n");
    sb.append("Energy: ");
    sb.append(getFreeEnergy());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(getTotalShield());
    sb.append(" Armor: ");
    sb.append(getTotalArmor());
    sb.append(" Hull Points: ");
    sb.append(hull.getSlotHull()*hull.getMaxSlot());
    if (getTotalMilitaryPower() > 0) {
      sb.append("\n");
      sb.append("Military power: ");
      sb.append(getTotalMilitaryPower());
    }
    sb.append("\n\nComponents:\n");
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      sb.append(i+1);
      sb.append(": ");
      sb.append(comp.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
  
  
}