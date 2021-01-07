import java.util.Random;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int energy;
    private int gold;
    private Room location;
    private Random rnd;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room location)
    {
        // initialise instance variables
        resetEnergy();
        this.location = location;
        gold = 0;
        rnd=new Random();
    }

    public Room getLocation() {
        return location;
    }

    public void leave() {
        location.leave(this);
    }

    public void setLocation(Room newLocation) {
        location = newLocation;
        location.enter(this);
    }

    public void decreaseEnergy() {
        energy--;
    }

    public int getEnergyLevel() {
        return energy;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void resetEnergy() {
        energy = 10;
    }

    public int getGold(){
        return gold;
    }

    public void generateGold(){
        if(getLocation().getShortDescription().equals("mine 1")||
        getLocation().getShortDescription().equals("mine 2")||
        getLocation().getShortDescription().equals("mine 3")||
        getLocation().getShortDescription().equals("mine 4")||
        getLocation().getShortDescription().equals("mine 5")||
        getLocation().getShortDescription().equals("mine 6")){
            int gain = rnd.nextInt(10);
            System.out.println("You stepped into a mine and found "+gain+" golds.");
            gold = gold+gain;
            System.out.println("Now you have "+ gold + " golds.");}}

    public void eat(){
        if(getLocation().getShortDescription().equals("at food place.")){
            if(getGold() < 10){
                System.out.println("You don't have enough golds to purchase foods.");
            } else{resetEnergy();
                gold = gold -10;
                System.out.println("You spent 10 golds and ate something, you have recover your energy now.");
            }}
    }
}
