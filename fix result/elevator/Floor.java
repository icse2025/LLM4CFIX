package elevator;
import java.util.*;
public class Floor {
   private int floorNumber;
   private volatile boolean summonUp;
   private volatile boolean summonDown;
   private Vector upWaiting = new Vector(); 
   private Vector downWaiting = new Vector(); 
   private static ElevatorController elevatorController;
   private Logger log;
   public Floor(int floorNumber){
      this.floorNumber = floorNumber;
      if(Simulator.debug) log = new Logger("Floor" + this.floorNumber);
   }
   public void closeLog(){
      if(Simulator.debug) log.close();
   }
   public boolean isSummonUp(){
      return summonUp;
   }
   public boolean isSummonDown(){
      return summonDown;
   }
   public static void setElevatorController(ElevatorController controller){
      elevatorController = controller;
   }
   public int getFloorNumber(){
      return floorNumber;
   }
   public int getNumberWaitingUp(){
      return upWaiting.size();
   }
   public int getNumberWaitingDown(){
      return downWaiting.size();
   }
   public void summonElevatorUp(Person person) {
      if(Simulator.debug) log.write("Summon up for person " + person.getPersonNumber());
      upWaiting.add(person);
      if(!summonUp){
         if(Simulator.debug) log.write("Light off summon UP for person " + person.getPersonNumber());
         elevatorController.summonElevatorUp(floorNumber, person);
         summonUp = true;
      }
   }
   public void summonElevatorDown(Person person) {
      if(Simulator.debug) log.write("Summon down for person " + person.getPersonNumber());
      downWaiting.add(person);
      if(!summonDown){ 
         if(Simulator.debug) log.write("Light off summon DOWN for person " + person.getPersonNumber());
         elevatorController.summonElevatorDown(floorNumber, person);
         summonDown = true;
      }
   }      
   public void elevatorArrivedUp(Elevator elevator) {
      Person p = null;
      summonUp = false;
      for(int i = 0; i < upWaiting.size(); i++){
         p = (Person)upWaiting.get(i);
         p.elevatorArrived(elevator);
         p.attention();
      }
      if(Simulator.debug) log.write("Elevator " + elevator.getElevatorNumber() + " has arrived UP on " + getFloorNumber());
   } 
   public void elevatorArrivedDown(Elevator elevator){
      Person p = null;
      summonDown = false;
      for(int i = 0; i < downWaiting.size(); i++){
         p = (Person)downWaiting.get(i);
         p.elevatorArrived(elevator);
         p.attention();
      }
      if(Simulator.debug) log.write("Elevator " + elevator.getElevatorNumber() + " has arrived DOWN on " + getFloorNumber());
   }
   public void stopWaiting(Person person) {
      if(Simulator.debug) log.write("Person " + person.getPersonNumber() + "  has stopped waiting on " + getFloorNumber());
      upWaiting.remove(person);
      downWaiting.remove(person);
   } 
} 
