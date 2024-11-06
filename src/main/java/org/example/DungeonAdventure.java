package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DungeonAdventure {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int health = 100;
        int maxHealth = 110;
        int treasure = 0;
        int dungeonDepth = 1;
        List<String> inventory = new ArrayList<>();

        System.out.println("🏰 Welcome to the Advanced Dungeon Adventure! 🏰");
        System.out.println("Survive through the dungeon, collect treasure, and prepare for the final boss!");

        boolean isGameOver = false;
        while (!isGameOver) {
            System.out.println("\n🌑 Dungeon Depth: " + dungeonDepth);
            System.out.println("❤️ Health: " + health + " | 💰 Treasure: " + treasure + " | 🎒 Inventory: " + inventory);
            System.out.println("You enter a mysterious room...");

            // Randomize room type
            int roomType = random.nextInt(4);

            switch (roomType) {
                case 0:
                    // Treasure Room
                    int foundTreasure = random.nextInt(50) + 20;
                    treasure += foundTreasure;
                    System.out.println("💎 You found a hidden treasure chest containing " + foundTreasure + " treasure!");
                    break;

                case 1:
                    // Trap Room
                    System.out.println("Oh no! You've triggered a trap!");
                    int trapDamage = random.nextInt(20) + 10;
                    health -= trapDamage;
                    System.out.println("You took " + trapDamage + " damage from the trap!");

                    if (health <= 0) {
                        System.out.println("You succumbed to your injuries. Game Over!");
                        isGameOver = true;
                    }
                    break;

                case 2:
                    // Monster Room
                    isGameOver = encounterMonster(scanner, random, inventory, dungeonDepth);
                    health = Math.max(0, health); // Ensure health is not negative
                    break;

                case 3:
                    // Healing Room
                    System.out.println("You found a mystical healing fountain.");
                    int healthRecovered = random.nextInt(30) + 20;
                    health = Math.min(health + healthRecovered, maxHealth);
                    System.out.println("🛏️ You recovered " + healthRecovered + " health.");
                    break;
            }

            // Random chance to find an item
            if (random.nextInt(100) < 25) { // 25% chance to find an item
                String item = findRandomItem(random);
                inventory.add(item);
                System.out.println("🎒 You found an item: " + item);
                applyItemEffect(item, random);
            }

            dungeonDepth++;
            if (dungeonDepth % 10 == 0 && !isGameOver) {
                System.out.println("🔥 You've reached dungeon depth " + dungeonDepth + ". Prepare for the final boss fight!");
                isGameOver = finalBossEncounter(scanner, random, inventory, health);
            }

            if (health <= 0) {
                System.out.println("You have fallen in the dungeon...");
                isGameOver = true;
            }

            if (isGameOver) {
                System.out.println("🏆 Final Score: " + treasure + " treasure collected at depth " + dungeonDepth);
            }
        }

        System.out.println("Thank you for playing Dungeon Adventure!");
        scanner.close();
    }

    // Function to find random item
    private static String findRandomItem(Random random) {
        String[] items = {"Healing Potion", "Attack Boost", "Shield", "Magic Amulet"};
        return items[random.nextInt(items.length)];
    }

    // Function to apply item effects
    private static void applyItemEffect(String item, Random random) {
        switch (item) {
            case "Healing Potion":
                System.out.println("💖 The Healing Potion will heal you in times of need.");
                break;
            case "Attack Boost":
                System.out.println("⚔️ The Attack Boost will increase your damage against monsters.");
                break;
            case "Shield":
                System.out.println("🛡️ The Shield provides additional protection from traps and monster attacks.");
                break;
            case "Magic Amulet":
                System.out.println("🔮 The Magic Amulet increases your maximum health by 10.");
                break;
        }
    }

    // Monster encounter function
    private static boolean encounterMonster(Scanner scanner, Random random, List<String> inventory, int dungeonDepth) {
        System.out.println("A monster appears!");
        int monsterHealth = random.nextInt(40) + dungeonDepth * 5;
        int monsterAttack = random.nextInt(15) + dungeonDepth;

        while (monsterHealth > 0) {
            System.out.println("Monster's health: " + monsterHealth);
            System.out.println("Choose your action: ");
            System.out.println("1. Attack the monster");
            System.out.println("2. Use an item");
            System.out.println("3. Flee");

            int choice = scanner.nextInt();

            if (choice == 1) {
                // Attack the monster
                int damageDealt = random.nextInt(20) + 10;
                if (inventory.contains("Attack Boost")) {
                    damageDealt += 10;
                }
                monsterHealth -= damageDealt;
                System.out.println("⚔️ You hit the monster for " + damageDealt + " damage.");

                // Monster attacks back
                int damageTaken = monsterAttack;
                if (inventory.contains("Shield")) {
                    damageTaken = Math.max(damageTaken - 5, 0);
                }
                System.out.println("The monster hits you for " + damageTaken + " damage.");
            } else if (choice == 2) {
                // Use an item
                System.out.println("Choose an item to use: ");
                for (int i = 0; i < inventory.size(); i++) {
                    System.out.println((i + 1) + ". " + inventory.get(i));
                }
                int itemChoice = scanner.nextInt() - 1;
                if (itemChoice >= 0 && itemChoice < inventory.size()) {
                    String item = inventory.remove(itemChoice);
                    applyItemEffect(item, random);
                } else {
                    System.out.println("Invalid choice.");
                }
            } else if (choice == 3) {
                // Flee from the monster
                System.out.println("You attempt to flee...");
                if (random.nextBoolean()) {
                    System.out.println("You successfully fled from the monster!");
                    return false;
                } else {
                    System.out.println("Failed to flee! The monster attacks as you try to escape.");
                }
            }
        }
        return false;
    }

    private static boolean finalBossEncounter(Scanner scanner, Random random, List<String> inventory, int health) {
        System.out.println("⚔️ Final Boss Encounter ⚔️");
        int bossHealth = 200;
        int bossAttack = 25;

        while (bossHealth > 0 && health > 0) {
            System.out.println("Boss's health: " + bossHealth);
            System.out.println("Your health: " + health);
            System.out.println("Choose your action: ");
            System.out.println("1. Attack the boss");
            System.out.println("2. Use an item");
            System.out.println("3. Attempt to flee (not recommended)");

            int choice = scanner.nextInt();

            if (choice == 1) {
                int damageDealt = random.nextInt(25) + 15;
                bossHealth -= damageDealt;
                System.out.println("⚔️ You dealt " + damageDealt + " damage to the boss.");
                health -= bossAttack;
            } else if (choice == 2) {
                System.out.println("Choose an item to use: ");
                for (int i = 0; i < inventory.size(); i++) {
                    System.out.println((i + 1) + ". " + inventory.get(i));
                }
                int itemChoice = scanner.nextInt() - 1;
                if (itemChoice >= 0 && itemChoice < inventory.size()) {
                    String item = inventory.remove(itemChoice);
                    applyItemEffect(item, random);
                } else {
                    System.out.println("Invalid choice.");
                }
            } else if (choice == 3) {
                System.out.println("You cannot flee from the boss!");
            }
        }
        return health <= 0;
    }
}
