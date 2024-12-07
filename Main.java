import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Short adventure game where you advance through levels encountering random enemies and facing bosses.
// There is a turn based combat system. Start the game for more info.

public class Main {
    public static void main(String[] args) {
        Player player = new Player(10);
        Main game = new Main(player);
        game.startGame();
    }

    private Player player;
    private int currentLevel;

    public Main(Player player) {
        this.player = player;
        this.currentLevel = 1;  // Start at level 1
    }

    // Method to start the game
    public void startGame() {
        System.out.println();
        System.out.println("Load previous progress? (y/n): ");
        String input = new Scanner(System.in).nextLine().toLowerCase();
        if (input.equals("y")) {
            loadProgress();
            System.out.println("Player Health: " + player.getHealth());
        } else {
            System.out.println();
            displayIntroduction();
        }

        while (player.getHealth() > 0) {
            // Display player options
            String action = getPlayerAction();
            handleAction(action);
            System.out.println();

            if (currentLevel == 12) {  // Dragon defeated at level 12
                System.out.println("Congratulations, you defeated the Dragon and beat the game!");
                break;
            }

            // Player advances
            if (action.equals("f")) {
                advanceLevel();
                saveProgress();
            }
        }
    }

    // Displays introduction
    private void displayIntroduction() {
        System.out.println();
        System.out.println("Welcome to the adventure! Your goal is to defeat the dragon.");
        System.out.println("You will travel through dangerous lands, facing enemies and bosses.");
        System.out.println("You are armed with a sword and a shield. So you can Attack (a) or Defend (d).");
        System.out.println("To advance you can move forward (f) or if you need to heal you can (r)");
        System.out.println("You are never safe...");
        System.out.println("Good Luck!");
        System.out.println();
    }

    // Player travel options
    private String getPlayerAction() {
        String action = "";
        while (true) {
            System.out.println("Current stage: " + currentLevel);
            System.out.println("No enemies in sight. Continue forward or rest? (f/r): ");
            action = new Scanner(System.in).nextLine().toLowerCase();
            if (action.equals("f") || action.equals("r")) {
                break;
            } else {
                System.out.println("Invalid action. Enter (f) to move forward or (r) to rest.");
            }
        }
        return action;
    }

    // Handle the player's action (either forward or rest)
    private void handleAction(String action) {
        if (action.equals("r")) {
            handleRest();
        }
    }

    // Handle resting action
    private void handleRest() {
        System.out.println("You decide to rest and heal.");

        int roll = new Roll().roll();  // Roll to check if it is safe to heal
        if (roll <= 2) {
            System.out.println("You were ambushed while resting!");
            Enemy enemy = CombatEncounter.createRandomEnemy();
            System.out.println("A " + enemy.getName() + " appears!");
            Combat combat = new Combat(player, enemy);
            combat.startCombat();
        } else {
            System.out.println("You rest and heal to full health.");
            player.heal();  // Heal player
        }
    }

    // Player moves forward to next stage
    public void advanceLevel() {
        if (currentLevel == 12) {
            return; // There are only 12 levels
        }

        currentLevel++;
        System.out.println("You have reached stage " + currentLevel + ".");

        // Check for boss level
        if (currentLevel == 4 || currentLevel == 8 || currentLevel == 12) {
            System.out.println("A fierce foe awaits!");
            Enemy boss = CombatEncounter.createBoss(currentLevel);  // Create the boss based on level
            System.out.println("It's a " + boss.getName() + "!");
            Combat combat = new Combat(player, boss);
            combat.startCombat();
        } else {
            // Roll for encounter
            int encounterRoll = new Roll().roll();  // 1-4 for enemy, 5-9 for normal, 10 for free heal
            if (encounterRoll <= 4) {
                Enemy enemy = CombatEncounter.createRandomEnemy();
                System.out.println("A " + enemy.getName() + " appears!");
                Combat combat = new Combat(player, enemy);
                combat.startCombat();
            } else if (encounterRoll == 10) {
                System.out.println("You found a magical fountain! Your health is fully restored.");
                player.heal();
            }
        }
    }

    // Save current game state to a CSV file
    public void saveProgress() {
        try (FileWriter writer = new FileWriter("game_save.csv")) {
            writer.write("PlayerHealth,CurrentLevel\n"); // Header
            writer.write(player.getHealth() + "," + currentLevel + "\n");
            System.out.println("Game progress saved!");
        } catch (IOException e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }

    // Load progress from the CSV file
    public void loadProgress() {
        try (BufferedReader reader = new BufferedReader(new FileReader("game_save.csv"))) {
            String line = reader.readLine(); // Skip header
            if ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                player = new Player(Integer.parseInt(data[0]));
                currentLevel = Integer.parseInt(data[1]);
                System.out.println("Game progress loaded!");
            }
        } catch (IOException e) {
            System.out.println("Error loading progress: " + e.getMessage());
        }
    }
}
