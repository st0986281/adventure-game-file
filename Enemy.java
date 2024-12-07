public class Enemy {
    private String name;
    private int health;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void attack(Player player, boolean isDefending) {
        int attackRoll = new Roll().roll(); // Enemy attack roll
        System.out.println(name + " attacks! Attack roll: " + attackRoll);

        if (attackRoll <= 2) {
            System.out.println(name + " misses the attack!");
        } else {
            // Player defends
            if (isDefending) {
                System.out.println("You defended! The enemy's attack is ineffective.");
                return;  // Exit early, no damage dealt
            }

            // Not defending
            player.takeDamage(1);
            System.out.println(name + " deals 1 damage to you.");
        }
    }
}