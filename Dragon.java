public class Dragon extends Enemy {
    private int turnCounter;

    public Dragon() {
        super("Dragon", 30);
        this.turnCounter = 0;
    }

    //Dragon is unique final boss with different attack pattern that the player must react to.
    @Override
    public void attack(Player player, boolean isDefending) {
        turnCounter++;
        if (turnCounter == 4) {
            System.out.println("The dragon breathes fire!");
            if (isDefending) {
             System.out.println("You blocked the dragons fire with your shield!");
            } else {
                System.out.println("It really burns! You take 5 damage!");
                player.takeDamage(5);
            }
            turnCounter = 0;
        } else if (turnCounter == 3) {
            System.out.println("The dragon takes a deep breath...");
        } else {
            super.attack(player, isDefending);
        }
    }
}
