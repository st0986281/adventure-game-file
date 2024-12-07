import java.util.Random;

public class CombatEncounter {

    // Method to create a random enemy
    public static Enemy createRandomEnemy() {
        String[] enemyTypes = {"Wolf", "Bandit", "Slime"};
        int index = new Random().nextInt(enemyTypes.length);
        return new Enemy(enemyTypes[index], 10); // random enemies have 10 HP
    }

    // Method to create a boss based on the level
    public static Enemy createBoss(int level) {
        if (level == 4) {
            return new Bear();
        } else if (level == 8) {
            return new DarkKnight();
        } else if (level == 12) {
            return new Dragon();
        }
        return null; // returns null if this method is called on a wrong level (shouldn't happen)
    }
}
