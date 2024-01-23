import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = new int[]{270, 260, 250, 200, 180, 220};
    public static int[] heroesDamage = new int[]{10, 15, 20, 0, 5, 15};
    public static String[] heroesAttackType = new String[]{"Physical", "Magical", "Kinetic", "Medic", "Witcher", "Lucky"};
    public static int roundNumber;

    public Main() {
    }

    public static void main(String[] args) {
        showStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        } else {
            boolean allHeroesDead = true;

            for (int i = 0; i < heroesHealth.length; ++i) {
                if (heroesHealth[i] > 0) {
                    allHeroesDead = false;
                    break;
                }
            }

            if (allHeroesDead) {
                System.out.println("Boss won!!!");
            }

            return allHeroesDead;
        }
    }

    public static void playRound() {
        ++roundNumber;
        bossAttacks();
        chooseBossDefence();
        heroesAttack();
        medicHeal();
        witcherRevive();
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; ++i) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];

                if (heroesAttackType[i].equals("Lucky")) {
                    Random random = new Random();
                    int dodgeChance = random.nextInt(2);
                    if (dodgeChance == 1) {
                        System.out.println("----> " + heroesAttackType[i] + " dodged the boss's attack!");
                        continue;
                    }
                }

                // Обычный удар
                if (bossDefence.equals(heroesAttackType[i])) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("----> Critical damage: " + damage);
                }

                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void medicHeal() {
        int medicIndex = 3;

        int healingAmount = 30;
        for (int i = 0; i < heroesHealth.length; ++i) {
            if (i != medicIndex && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                heroesHealth[i] += healingAmount;
                System.out.println("Medic healed " + heroesAttackType[i] + " for " + healingAmount + " health.");
                break;
            }
        }
    }

    public static void witcherRevive() {
        int witcherIndex = 4;

        for (int i = 0; i < heroesHealth.length; ++i) {
            if (i != witcherIndex && heroesHealth[i] <= 0) {
                Random random = new Random();
                int reviveChance = random.nextInt(2);
                if (reviveChance == 1) {
                    heroesHealth[i] = 1;
                    System.out.println("Witcher revived " + heroesAttackType[i] + " sacrificing himself.");
                    heroesHealth[witcherIndex] = 0;
                    break;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; ++i) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void showStatistics() {
        int var10001 = roundNumber;
        System.out.println("ROUND " + var10001 + " ----------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " + (bossDefence == null ? "No defence" : bossDefence));

        for (int i = 0; i < heroesHealth.length; ++i) {
            String var1 = heroesAttackType[i];
            System.out.println(var1 + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}