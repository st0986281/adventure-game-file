public class Player {
    private int health;
    private final int maxHealth;

    public Player(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal() {
        health = maxHealth;
    }
}
