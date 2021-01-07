/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game 
{
    private Parser parser;
    //private Room currentRoom;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();

        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room town, food, mine1, mine2, mine3,mine4,mine5,mine6;

        // create the rooms
        town = new Room("at the center of the town, access either food or mine");
        food = new Room("at food place.");

        mine1 = new Room("mine 1");
        mine2 = new Room("mine 2");
        mine3 = new Room("mine 3");
        mine4 = new Room("mine 4");
        mine5 = new Room("mine 5");
        mine6 = new Room("mine 6");

        // initialise room exits
        town.setExit("food", food);
        town.setExit("mine1", mine1);

        food.setExit("town", town);

        mine1.setExit("town", town);
        mine1.setExit("mine2", mine2);
        mine1.setExit("mine3", mine3);

        mine2.setExit("mine1", mine1);
        mine2.setExit("mine4", mine4);

        mine3.setExit("mine1", mine1);
        mine3.setExit("mine4", mine4);
        mine3.setExit("mine6", mine6);

        mine4.setExit("mine2", mine2);
        mine4.setExit("mine3", mine3);
        mine4.setExit("mine5", mine5);

        mine5.setExit("mine4", mine4);
        mine5.setExit("mine6", mine6);

        mine6.setExit("mine3", mine3);
        mine6.setExit("mine5", mine5);

        //currentRoom = town;  // start game town
        player = new Player(town); // start game town

    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(player.getLocation().getShortDescription().equals("mine 1")||
            player.getLocation().getShortDescription().equals("mine 2")||
            player.getLocation().getShortDescription().equals("mine 3")||
            player.getLocation().getShortDescription().equals("mine 4")||
            player.getLocation().getShortDescription().equals("mine 5")||
            player.getLocation().getShortDescription().equals("mine 6"))
            { player.decreaseEnergy();}
            System.out.println("Your energy level is " + player.getEnergyLevel());
            System.out.println("You have "+player.getGold() +" golds");
            player.eat();
            player.generateGold();
            if(player.getGold()>99){
                System.out.println("Congrats.You collect 100 golds and got out of this town.");
                finished = true;
            }
            if (player.isAlive() == false) {
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You woke up and found yourself at a mysterious town;");
        System.out.println("you asked citizens and they will help you to get back to your town if you pay them 100 golds;");
        System.out.println("there is a big gold mine where you can find some golds.");
        System.out.println("You will lose energy when you are moving within the gold mines;");
        System.out.println("you will not lose energy when you are moving between town and food place;");
        System.out.println("you will die if your energy drops to zero.");
        System.out.println();
        System.out.println(player.getLocation().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wonder around the town.");
        System.out.println("You can go food to recover energy when you are at the town.");
        System.out.println("You can also go to mines to collect golds.");
        System.out.println("Plan your journey properly so you won't die in the mines because of starving.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getLocation().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setLocation(nextRoom);
            System.out.println(player.getLocation().getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
