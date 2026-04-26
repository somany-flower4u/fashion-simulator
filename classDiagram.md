classDiagram
    direction LR

    class Main {
        +main(String[] args)
        -printTitle()
        -selectPlayerParty(Pokemon[] pokedex) Pokemon[]
        -selectRivalParty(Pokemon[] pokedex) Pokemon[]
    }

    class GameUtils {
        -static Scanner scanner
        +GameUtils(Scanner s)
        +getValidInput(int min, int max) int
        +pause(int ms) void
        +getRandomOrder(int n) int[]
    }

    class Type {
        <<enum>>
        FIRE
        WATER
        GRASS
        NORMAL
        +getEmoji() String
        +getEffectiveness(Type defender) double
    }

    class Skill {
        -String name
        -Type type
        -int power
        -int accuracy
        -int maxPP
        -int currentPP
        -boolean isPriority
        +calculateDamage(Pokemon attacker, Pokemon defender) int
        +tryHit() boolean
        +usePP() boolean
        +canUse() boolean
    }

    class Pokemon {
        <<abstract>>
        -String name
        -Type type
        -int level
        -int maxHp
        -int currentHp
        -int attack
        -int defense
        -int speed
        -Skill[] skills
        -int killCount
        +getTypeEmoji() String
        +setSkills(Skill s1, Skill s2, Skill s3, Skill s4) void
        +takeDamage(int damage) void
        +isAlive() boolean
        +getHpBar() String
        +getDisplayName() String
    }

    class FirePokemon {
        +FirePokemon(String name, int maxHp, int attack, int defense, int speed)
    }

    class WaterPokemon {
        +WaterPokemon(String name, int maxHp, int attack, int defense, int speed)
    }

    class GrassPokemon {
        +GrassPokemon(String name, int maxHp, int attack, int defense, int speed)
    }

    class PokemonFactory {
        +createAllPokemon() Pokemon[]
        +clonePokemon(Pokemon original) Pokemon
        +printPokedex(Pokemon[] pokemons) void
    }

    class AIStrategy {
        <<interface>>
        +decideTurn(Trainer aiTrainer, Trainer opponent) int
        +chooseSwitchTarget(Trainer aiTrainer, Pokemon opponentPokemon) int
    }

    class BasicAIStrategy {
        +decideTurn(Trainer aiTrainer, Trainer opponent) int
        +chooseSwitchTarget(Trainer aiTrainer, Pokemon opponentPokemon) int
    }

    class Trainer {
        -String name
        -Pokemon[] party
        -int activePokemonIndex
        -boolean isPlayer
        -AIStrategy aiStrategy
        +getActivePokemon() Pokemon
        +hasAlivePokemon() boolean
        +getAliveCount() int
        +switchPokemon(int index) boolean
        +chooseBestSwitch(Pokemon opponentPokemon) int
        +chooseBestSkill(Pokemon opponent) int
        +forceSwitch(Scanner scanner, Pokemon opponentPokemon) void
        +getMVP() Pokemon
    }

    class BattleManager {
        -Trainer player
        -Trainer rival
        -int turnCount
        -Scanner scanner
        +BattleManager(Trainer player, Trainer rival, Scanner scanner)
        +startBattle() void
    }

    Main ..> GameUtils
    Main ..> PokemonFactory
    Main ..> Trainer
    Main ..> BattleManager

    Pokemon <|-- FirePokemon
    Pokemon <|-- WaterPokemon
    Pokemon <|-- GrassPokemon

    Pokemon *-- Skill
    Pokemon --> Type
    Skill --> Type
    Skill --> Pokemon

    PokemonFactory ..> Pokemon
    PokemonFactory ..> FirePokemon
    PokemonFactory ..> WaterPokemon
    PokemonFactory ..> GrassPokemon
    PokemonFactory ..> Skill
    PokemonFactory ..> Type

    AIStrategy <|.. BasicAIStrategy
    Trainer o-- Pokemon
    Trainer --> AIStrategy

    BattleManager --> Trainer
    BattleManager --> GameUtils
    BattleManager --> Skill