package elevator;
import java.util.*;
public class ElevatorController {
   private Vector elevators;
   private Vector floors;
   private Logger log;
   public ElevatorController(Vector floors, Vector elevators){
      this.floors = floors;
      this.elevators = elevators;
      if(Simulator.debug) log = new Logger("Controller");
   }
   public int getNumberWaitingUp(int floorNumber){
      return getFloor(floorNumber).getNumberWaitingUp();
   }
   public int getNumberWaitingDown(int floorNumber){
      return getFloor(floorNumber).getNumberWaitingDown();
   }
   public ElevatorState getElevatorState(int elevatorNumber){
      return ((Elevator)elevators.get(elevatorNumber-1)).getState();
   }
   public void stopElevators(){
       for(int i = 0; i < elevators.size(); i++){
         ((Elevator)elevators.get(i)).setStopRunning();
      }
      for(int i = 0; i < floors.size(); i++){
         ((Floor)floors.get(i)).closeLog();
      }
      if(Simulator.debug) log.close(); 
   }
   public void summonElevatorUp(int floorNumber, Person person) {
      Elevator e = null;
      int counter = 0;
      while ( e == null && person.getKeepRunning()){  
         if(Simulator.debug) log.write("" + ++counter + " tries to summon up elevator to floor " + floorNumber); 
         e = getSameFloorElevator(floorNumber);
         if(e != null){
            if(Simulator.debug) log.write("Setting up destination for elevator " + e.getElevatorNumber() + " same floor " + floorNumber);
               try{
                  e.summonDestination(floorNumber);
               }catch(ElevatorMovingException mx){
                  if(Simulator.debug) log.write("Moving Exception up1: floor " + floorNumber + " on elevator " + e.getState()); 
                  e = null;
                  continue;
               }
         }else{
             if(floorNumber > 1){ 
               if(Simulator.debug) log.write("looking for one moving up from below to floor " + floorNumber);
               e = getNearestElevator(floorNumber, Elevator.MOVING_UP);
             }
            if(e != null){
               if(Simulator.debug) log.write("Setting destination for elevator " + e.getElevatorNumber() + " from  below floor " + floorNumber);
                try{
                  e.summonDestination(floorNumber);
               }catch(ElevatorMovingException mx){
                  if(Simulator.debug) log.write("Moving Exception up2: floor " + floorNumber + " on elevator " + e.getState());
                  e = null;
                  continue;
               }
            }else{
               if(Simulator.debug) log.write("Looking for closest stopped elevator for up floor " + floorNumber);
               e = getNearestElevator(floorNumber, Elevator.NO_DIRECTION);
               if(e != null){
                  if(Simulator.debug) log.write("Setting destination for stopped elevator " + e.getElevatorNumber() + " for up floor " + floorNumber);
                  try{
                     e.summonDestination(floorNumber);
                  }catch(ElevatorMovingException mx){
                     if(Simulator.debug) log.write("Moving Exception up3: floor " + floorNumber + " on elevator " + e.getState());
                     e = null;
                     continue;
                  }
               }else{
                   if(Simulator.debug) log.write("Looking for elevator coming down " + floorNumber);
                  e = getElevator(floorNumber, Elevator.MOVING_DOWN);
                   if(e != null){
                     if(Simulator.debug) log.write("Setting destination for moving down elevator " + e.getElevatorNumber() + " for floor " + floorNumber);
                     try{
                        e.summonDestination(floorNumber);
                     }catch(ElevatorMovingException mx){
                        if(Simulator.debug) log.write("Moving Exception up4: floor " + floorNumber + " on elevator " + e.getState());
                        e = null;
                        continue;
                     }
                  }
               }
            } 
         }
      }
   }
   public void summonElevatorDown(int floorNumber, Person person) {        
      Elevator e = null;
      int counter = 0;
      while ( e == null && person.getKeepRunning()){  
         if(Simulator.debug) log.write("" + ++counter + " tries to summon down elevator to floor " + floorNumber); 
         e = getSameFloorElevator(floorNumber);
         if(e != null){
            if(Simulator.debug) log.write("Setting down destination for elevator " + e.getElevatorNumber() + " same floor " + floorNumber);
             try{
                  e.summonDestination(floorNumber);
               }catch(ElevatorMovingException mx){
                  if(Simulator.debug) log.write("Moving Exception down1: floor " + floorNumber + " on elevator " + e.getState());
                  e = null;
                  continue;
               }
         }else{
            if(floorNumber != Building.MAX_FLOORS){  
               if(Simulator.debug) log.write("looking for one moving down from above to floor " + floorNumber);
               e = getNearestElevator(floorNumber, Elevator.MOVING_DOWN);
            }
            if(e != null){
               if(Simulator.debug) log.write("Setting destination for elevator " + e.getElevatorNumber() + "from  above floor " + floorNumber);
                try{
                  e.summonDestination(floorNumber);
               }catch(ElevatorMovingException mx){
                  if(Simulator.debug) log.write("Moving Exception down2: floor " + floorNumber + " on elevator " + e.getState());
                  e = null;
                  continue;
               }
            }else{
               if(Simulator.debug) log.write("Looking for closest stopped elevator for down floor " + floorNumber);
               e = getNearestElevator(floorNumber, Elevator.NO_DIRECTION);
               if(e != null){
                  if(Simulator.debug) log.write("Setting destination for stopped elevator " + e.getElevatorNumber() + " for down floor " + floorNumber);
                   try{
                     e.summonDestination(floorNumber);
                  }catch(ElevatorMovingException mx){
                      if(Simulator.debug) log.write("Moving Exception down3: floor " + floorNumber + " on elevator " + e.getState());
                     e = null;
                     continue;
                  }
               }else{
                   if(Simulator.debug) log.write("Looking for elevator coming up " + floorNumber);
                  e = getElevator(floorNumber, Elevator.MOVING_UP);
                   if(e != null){
                     if(Simulator.debug) log.write("Setting destination for moving up elevator " + e.getElevatorNumber() + " for floor " + floorNumber);
                     try{
                        e.summonDestination(floorNumber);
                     }catch(ElevatorMovingException mx){
                        if(Simulator.debug) log.write("Moving Exception down4: floor " + floorNumber + " on elevator " + e.getState());
                        e = null;
                        continue;
                     }
                  }
               }
            }
         } 
      }
   }
   public Floor getFloor(int floorNumber) {        
        return (Floor)floors.get(floorNumber-1);
   }       
   public void startElevators(){
      for(int i = 0; i < elevators.size(); i++){
         ((Elevator)elevators.get(i)).start();
      }
   }
   public void elevatorArrived(int floorNumber, Elevator elevator){
      int direction = elevator.getState().motionDirection;
      Floor floor = getFloor(floorNumber);
      if(direction == Elevator.MOVING_UP && floor.isSummonUp()){
         floor.elevatorArrivedUp(elevator);
      }else if(direction == Elevator.MOVING_DOWN && floor.isSummonDown()){
         floor.elevatorArrivedDown(elevator);
      }else if(floor.isSummonUp()){
         floor.elevatorArrivedUp(elevator);
      }else if(floor.isSummonDown()){
         floor.elevatorArrivedDown(elevator);
      }else if(floor.getNumberWaitingUp() > floor.getNumberWaitingDown()){ 
         floor.elevatorArrivedUp(elevator);
      }else{ 
         floor.elevatorArrivedDown(elevator);
      }
   }
   private Elevator getSameFloorElevator(int floorNumber){
      Elevator e = null;
      ElevatorState state = null;
      for(int i = 0; i < elevators.size(); i++){
         e = (Elevator)elevators.get(i);
         state = e.getState();
         if(e.getCurrentFloorNumber() == floorNumber && state.motionState == Elevator.STOPPED && state.numberRiders == 0 ){
            if(Simulator.debug) log.write("Summoned elevator " + e.getElevatorNumber());
            break;
         }else
            e = null;
      }
      return e;
   }
   private Elevator getNearestElevator(int floorNumber, int direction){
      Elevator closestElevator = null;
      int closestFloor = 0;
      int closest = Building.MAX_FLOORS + 1;
      Elevator currentElevator = null;
      int currentFloorNumber = 0;
      for(int i = 0; i < elevators.size(); i++){
         currentElevator = (Elevator)elevators.get(i);
         currentFloorNumber = currentElevator.getCurrentFloorNumber();
         if( direction == Elevator.MOVING_UP){ 
            if(currentFloorNumber > closestFloor && currentFloorNumber < floorNumber){
               closestElevator = currentElevator;
               closestFloor = currentFloorNumber;
            }
         }else if(direction == Elevator.MOVING_DOWN){ 
            if(currentFloorNumber < closestFloor && currentFloorNumber > floorNumber){
               closestElevator = currentElevator;
               closestFloor = currentFloorNumber;
            }
         }else if(direction == Elevator.NO_DIRECTION){ 
            if(currentFloorNumber != floorNumber && Math.abs(currentFloorNumber - floorNumber) < closest){
               closestElevator = currentElevator;
               closest = Math.abs(currentFloorNumber - floorNumber);
            }
         }
      }
      return closestElevator;
   }
    private Elevator getElevator(int floorNumber, int direction){
      Elevator closestElevator = null;
      int closestFloor = 0;
      int closest = Building.MAX_FLOORS + 1;
      Elevator currentElevator = null;
      int currentFloorNumber = 0;
      for(int i = 0; i < elevators.size(); i++){
         currentElevator = (Elevator)elevators.get(i);
         currentFloorNumber = currentElevator.getCurrentFloorNumber();
         if( direction == Elevator.MOVING_UP){ 
            if(currentFloorNumber > closestFloor && currentFloorNumber < floorNumber){
               closestElevator = currentElevator;
               closestFloor = currentFloorNumber;
            }
         }else if(direction == Elevator.MOVING_DOWN){ 
            if(currentFloorNumber < closestFloor && currentFloorNumber > floorNumber){
               closestElevator = currentElevator;
               closestFloor = currentFloorNumber;
            }
         }
      }
      return closestElevator;
   }
 } 
