public class Combat {

    private Player player;
    private Enemy enemy;

    public Combat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    // start of combat
    public void startCombat() {
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            displayStatus();
            String action = getPlayerAction();
            handleAction(action);
            //checks if enemy is defeated before they take their turn
            if (enemy.getHealth() <= 0) {
                break;
            }
            enemyTurn();
            System.out.println();
        }
        endCombat();
        System.out.println();
    }

    // Display current status (Player HP and Enemy HP)
    private void displayStatus() {
        System.out.println("Player HP: " + player.getHealth());
        System.out.println(enemy.getName() + " HP: " + enemy.getHealth());
    }

    // Get player action: Attack or Defend
    private String getPlayerAction() {
        String action = "";
        while (true) {  // loops until player atk or def
            System.out.println("What would you like to do? (a/d): ");
            action = new java.util.Scanner(System.in).nextLine().toLowerCase();
            if (action.equals("a") || action.equals("d")) {
                break;  // Exit the loop if a valid action is entered
            } else {
                System.out.println("Invalid action. Choose 'atk' to attack or 'def' to defend.");
            }
        }
        return action;
    }

    // Handle player action
    private void handleAction(String action) {
        if (action.equals("a")) {
            handleAttack();  // Player attacks
        } else if (action.equals("d")) {
            handleDefend();  // Player defends
        }
    }

    private boolean isDefending = false; // Flag for defend option


    // Handle attack action
    private void handleAttack() {
        int attackRoll = new Roll().roll();  // Roll for attack
        System.out.println("Attack roll: " + attackRoll);

        if (attackRoll == 1) {
            // Miss
            System.out.println("You missed the attack!");
        } else if (attackRoll == 10) {
            // Critical hit
            System.out.println("Critical hit! You deal 6 damage.");
            enemy.takeDamage(6);  // Crits don't roll for damage
        } else {
            // Roll for damage
            int damageRoll = new Roll().roll();  // Roll for damage
            System.out.println("Damage roll: " + damageRoll);

            int damage = 0;
            if (damageRoll >= 1 && damageRoll <= 3) {
                damage = 1;
            } else if (damageRoll >= 4 && damageRoll <= 7) {
                damage = 2;
            } else if (damageRoll >= 8 && damageRoll <= 10) {
                damage = 3;
            }

            System.out.println("You deal " + damage + " damage.");
            enemy.takeDamage(damage);  // damage dealt
        }
    }

    // Handle defend action
    private void handleDefend() {
        isDefending = true;
        // Take no damage on defend
    }

    // Enemy attacks then defend flag is reset
    private void enemyTurn() {
        enemy.attack(player, isDefending);
        isDefending = false;
    }

    // End combat, display result
    private void endCombat() {
        if (player.getHealth() <= 0) {
            System.out.println("You have been defeated. GAME OVER.");
        } else if (enemy.getHealth() <= 0) {
            System.out.println("You defeated the " + enemy.getName() + "!");
        }
    }
}
