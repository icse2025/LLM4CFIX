package elevator;
import java.util.*;
public class Person implements Runnable{
   public static final int WAITING = 1; 
   public static final int TAKING_STAIRS = 2; 
   public static final int WORKING = 3;
   public static final int WALKING_OUTSIDE = 4;
   public static final int RIDING = 5;
   public static final int GOING_NOWHERE = -1;
   public static final int OUTSIDE = -1;
   public static final int IN_ELEVATOR = 0;
   private static Building building;
   private int personID; 
   private int destination;
   private int location; 
   private int activity; 
   private Elevator elevator; 
   private Floor floor;
   private Thread activePerson;
   private Logger log;
   private volatile boolean keepRunning;
   public static void setBuilding(Building theBuilding){
      building = theBuilding;
   }
   public Person(int personID){
      this.personID = personID;
   }
   public void setStopRunning(){
      keepRunning = false;
   }
   public boolean getKeepRunning(){
      return keepRunning;
   }
   public synchronized void attention(){
      activePerson.interrupt();
   }
   public synchronized void elevatorArrived(Elevator elevator) {        
      this.elevator = elevator;
   }
   public synchronized PersonState getState() {        
       PersonState state = new PersonState();
       state.personNumber = personID;
       state.activity = activity;
       state.destination = destination;
       state.location = location;
       if(elevator != null)
         state.elevatorNumber = elevator.getElevatorNumber();
       return state;
   }
   public int getPersonNumber(){
      return personID;
   }
   public void start(){
      destination = GOING_NOWHERE;
      activity = WALKING_OUTSIDE;
      if(Simulator.debug) log = new Logger("Person" + personID);
      keepRunning = true;
      if(activePerson == null){
         activePerson = new Thread(this);
         activePerson.start();
      }
   }
   public void run(){
      while(keepRunning){
         switch(activity){
            case WALKING_OUTSIDE:
               if(wantsToEnter()){
                  building.peopleOutside--;
                  setDestination();
                  floor = building.enterBuilding();
                  location = floor.getFloorNumber();
                  if(destination > location){ 
                     activity = WAITING;
                     setWaitTime();
                     floor.summonElevatorUp(this);
                     action();
                  }else{ 
                     building.peopleWorking++;
                     activity = WORKING;
                     destination = GOING_NOWHERE;
                     setWorkingTime();
                     action();
                  }
               }else{ 
                  destination = GOING_NOWHERE;
                  location = OUTSIDE;
                  activity = WALKING_OUTSIDE;
                  setWorkingTime();
                  action();
               }
               break;
            case TAKING_STAIRS:
               if(location == destination){
                  building.peopleTakingStairs--;
                  building.peopleWorking++;
                  activity = WORKING;
                  floor = building.getFloor(location);
                  destination = GOING_NOWHERE;
                  setWorkingTime();
                  action();
               }else if(destination > location){
                  climbUp();
               }else{
                  climbDown();
               }
               break;
            case WAITING:
               if(elevator != null){
                  enterElevator();
               }else{ 
                  if(wantsToTakeStairs()){
                     floor.stopWaiting(this);
                     building.peopleTakingStairs++;
                     activity = TAKING_STAIRS;
                     if(destination > location){
                        climbUp();
                     }else{
                        climbDown();
                     }
                  }else{
                     setWaitTime();
                     action();
                  }
               }
               break;
            case WORKING:
               if(location == 1){
                  if(wantsToLeave()){
                     building.peopleOutside++;
                     building.peopleWorking--;
                     destination = GOING_NOWHERE;
                     location = OUTSIDE;
                     activity = WALKING_OUTSIDE;
                     setWorkingTime();
                     action();
                  }else{ 
                     setDestination();
                     if(destination > location){ 
                        building.peopleWorking--;
                        activity = WAITING;
                        setWaitTime();
                        floor.summonElevatorUp(this);
                        action();
                     }else{ 
                        activity = WORKING;
                        destination = GOING_NOWHERE;
                        setWorkingTime();
                        action();
                     }
                  }
               }else{ 
                  setDestination();
                  if(destination > location){ 
                     building.peopleWorking--;
                     activity = WAITING;
                     setWaitTime();
                     floor.summonElevatorUp(this);
                     action();
                  }else if(destination < location){
                     building.peopleWorking--;
                     activity = WAITING;
                     setWaitTime();
                     floor.summonElevatorDown(this);
                     action();
                  }else{ 
                     activity = WORKING;
                     destination = GOING_NOWHERE;
                     setWorkingTime();
                     action();
                  }
               }
               break;
            case RIDING:
               if(elevator.getCurrentFloorNumber() == destination){
                  leaveElevator();
               }else
                  setWaitTime();
                  action();
               break;
         }
         if(Simulator.debug) log.write(getState().toString());
      }
      if(Simulator.debug) log.close();
   }
   private boolean wantsToEnter(){
                return new Random().nextBoolean();
	}
   private boolean wantsToTakeStairs(){
                return new Random().nextBoolean();
	}
	private void setDestination() {
                destination = 1+new Random().nextInt(building.MAX_FLOORS);
	}
	private void setWaitTime() {
	}
	private void setWorkingTime() {
	}
	private boolean wantsToLeave() {
                return new Random().nextBoolean();
	}
   private void action(){
   }
   private void climbUp(){
      if(location != Building.MAX_FLOORS){
         action();
         ++location;
      }
   }
   private void climbDown(){
      if(location != 1){
         action();
         --location;
      }
   }
   private  void enterElevator(){
      try{
         elevator.enterElevator(this);
         elevator.setDestination(destination);
         floor.stopWaiting(this);
         floor = null;
         location = IN_ELEVATOR;
         activity = RIDING;
         action();
      }catch(ElevatorFullException fx){
         resetWaitForElevator();
      }catch(DoorClosedException cx){
        try{
            elevator.requestOpenDoor();
         }catch(ElevatorMovingException mx){
            resetWaitForElevator(); 
         }
      }
   }
   private void leaveElevator(){
      try{
         elevator.leaveElevator(this);
         floor = building.getFloor(destination);
         location = destination;
         destination = GOING_NOWHERE;
         activity = WORKING;
         building.peopleWorking++;
         setWorkingTime();
         action();
      }catch(DoorClosedException dx){
         try{
            elevator.requestOpenDoor();
         }catch(ElevatorMovingException mx){
            elevator.setDestination(destination);
         }
      }
   }
   private void resetWaitForElevator(){
      if(Simulator.debug) log.write("Person " + personID + " missed elevator " + elevator.getElevatorNumber());
      floor.stopWaiting(this);
      elevator = null;
      setWaitTime();
      action();
      if(destination > location)
         floor.summonElevatorUp(this);
      else
         floor.summonElevatorDown(this);
   }
} 
