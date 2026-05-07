# 날씨 및 목적 기반 외출 코디네이터 - 클래스 다이어그램 및 순차도

## 1. 클래스 다이어그램 (Class Diagram)

```mermaid
classDiagram
    direction TB

    %% ==================== Enums ====================
    class Category {
        <<enumeration>>
        +TOP
        +BOTTOM
        +OUTER
        -String korName
        -String emoji
        +getKorName() String
        +getEmoji() String
    }

    class Weather {
        <<enumeration>>
        +SUNNY
        +RAINY
        +SNOWY
        +COLD
        +HOT
        +WINDY
        -String korName
        -String emoji
        +getKorName() String
        +getEmoji() String
    }

    class Schedule {
        <<enumeration>>
        +BUSINESS
        +CASUAL
        +SPORTS
        +DATE
        -String korName
        -String emoji
        +getKorName() String
        +getEmoji() String
    }

    %% ==================== Abstract & Interface ====================
    class ClothingItem {
        <<abstract>>
        -String name
        -Category category
        -int warmth
        -int waterResistance
        -int formality
        -int comfort
        -ClothingProperty[] properties
        +ClothingItem(String, Category, int, int, int, int)
        +setProperties(ClothingProperty, ClothingProperty, ClothingProperty, ClothingProperty) void
        +getCategoryEmoji()* String
        +calculateScore(Situation) int
        +getName() String
        +getCategory() Category
        +getWarmth() int
        +getWaterResistance() int
        +getFormality() int
        +getComfort() int
        +getProperties() ClothingProperty[]
        +getProperty(int) ClothingProperty
        +getDisplayName() String
    }

    class RecommendStrategy {
        <<interface>>
        +recommend(Wardrobe, Situation) Outfit
    }

    %% ==================== Concrete Classes ====================
    class Top {
        +Top(String, int, int, int, int)
        +getCategoryEmoji() String
    }

    class Bottom {
        +Bottom(String, int, int, int, int)
        +getCategoryEmoji() String
    }

    class Outer {
        +Outer(String, int, int, int, int)
        +getCategoryEmoji() String
    }

    class ClothingProperty {
        -String name
        -int warmthBonus
        -int waterBonus
        -int formalityBonus
        -int comfortBonus
        +ClothingProperty(String, int, int, int, int)
        +calculateBonus(ClothingItem, Situation) int
        +getName() String
        +getWarmthBonus() int
        +getWaterBonus() int
        +getFormalityBonus() int
        +getComfortBonus() int
        +getInfo() String
    }

    class Situation {
        -Weather weather
        -Schedule schedule
        -int temperature
        +Situation(Weather, Schedule, int)
        +getWarmthWeight() double
        +getWaterResistanceWeight() double
        +getFormalityWeight() double
        +getComfortWeight() double
        +getWeather() Weather
        +getSchedule() Schedule
        +getTemperature() int
        +getDisplay() String
    }

    class Outfit {
        -ClothingItem top
        -ClothingItem bottom
        -ClothingItem outer
        +Outfit(ClothingItem, ClothingItem, ClothingItem)
        +calculateTotalScore(Situation) int
        +getTop() ClothingItem
        +getBottom() ClothingItem
        +getOuter() ClothingItem
        +printOutfit() void
    }

    class Wardrobe {
        -String ownerName
        -ClothingItem[] tops
        -ClothingItem[] bottoms
        -ClothingItem[] outers
        +Wardrobe(String, ClothingItem[], ClothingItem[], ClothingItem[])
        +getTops() ClothingItem[]
        +getBottoms() ClothingItem[]
        +getOuters() ClothingItem[]
        +getOwnerName() String
        +printWardrobe() void
        -printItem(ClothingItem) void
    }

    class ClothingFactory {
        +createAllTops()$ ClothingItem[]
        +createAllBottoms()$ ClothingItem[]
        +createAllOuters()$ ClothingItem[]
        +createTShirt()$ ClothingItem
        +createShirt()$ ClothingItem
        +createJeans()$ ClothingItem
        +createSlacks()$ ClothingItem
        +createHoodie()$ ClothingItem
        +createTrenchCoat()$ ClothingItem
        +cloneClothing(ClothingItem)$ ClothingItem
        -copyProperty(ClothingProperty)$ ClothingProperty
    }

    class BasicRecommendStrategy {
        +recommend(Wardrobe, Situation) Outfit
    }

    class OutfitRecommender {
        -Wardrobe wardrobe
        -RecommendStrategy strategy
        -Scanner scanner
        +OutfitRecommender(Wardrobe, RecommendStrategy, Scanner)
        +startRecommendation() void
        -printIntro() void
        -inputSituation() Situation
        -printResult(Outfit, Situation, int) void
    }

    class ConsoleUtils {
        -Scanner scanner
        +ConsoleUtils(Scanner)
        +getValidInput(int, int)$ int
        +pause(int)$ void
        +getRandomOrder(int)$ int[]
    }

    class Main {
        -Scanner scanner
        +main(String[])$ void
        -printTitle()$ void
    }

    %% ==================== Relationships ====================
    ClothingItem <|-- Top : extends
    ClothingItem <|-- Bottom : extends
    ClothingItem <|-- Outer : extends

    RecommendStrategy <|.. BasicRecommendStrategy : implements

    ClothingItem --> Category : uses
    ClothingItem --> ClothingProperty : composition (4)
    ClothingItem ..> Situation : depends on

    ClothingProperty ..> ClothingItem : depends on
    ClothingProperty ..> Situation : depends on

    Situation --> Weather : composition
    Situation --> Schedule : composition

    Outfit --> ClothingItem : composition (3)
    Outfit ..> Situation : depends on

    Wardrobe --> ClothingItem : composition (arrays)

    ClothingFactory ..> ClothingItem : creates
    ClothingFactory ..> ClothingProperty : creates
    ClothingFactory ..> Top : creates
    ClothingFactory ..> Bottom : creates
    ClothingFactory ..> Outer : creates

    BasicRecommendStrategy --> Wardrobe : uses
    BasicRecommendStrategy --> Situation : uses
    BasicRecommendStrategy --> Outfit : creates

    OutfitRecommender --> Wardrobe : uses
    OutfitRecommender --> RecommendStrategy : uses
    OutfitRecommender --> Situation : creates & uses
    OutfitRecommender --> Outfit : uses
    OutfitRecommender ..> ConsoleUtils : depends on
    OutfitRecommender ..> Weather : depends on
    OutfitRecommender ..> Schedule : depends on

    Main --> ClothingFactory : uses
    Main --> Wardrobe : creates
    Main --> OutfitRecommender : creates
    Main --> BasicRecommendStrategy : creates
    Main ..> ConsoleUtils : depends on
```

---

## 2. 순차도 (Sequence Diagram)

### 2.1 전체 프로그램 실행 흐름

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Main
    participant ConsoleUtils
    participant ClothingFactory
    participant Wardrobe
    participant OutfitRecommender
    participant Situation
    participant RecommendStrategy
    participant BasicRecommendStrategy
    participant Outfit
    participant ClothingItem
    participant Weather
    participant Schedule

    %% ========== Initialization ==========
    User->>Main: java Main
    Main->>ConsoleUtils: new ConsoleUtils(scanner)
    ConsoleUtils-->>Main: ConsoleUtils instance
    Main->>Main: printTitle()

    %% ========== Main Loop ==========
    loop repeat until user exits
        %% ========== Create Wardrobe ==========
        Main->>ClothingFactory: createAllTops()
        ClothingFactory->>ClothingFactory: createTShirt()
        ClothingFactory->>Top: new Top("반팔티", 20, 10, 10, 80)
        Top-->>ClothingFactory: Top instance
        ClothingFactory->>ClothingFactory: createShirt()
        ClothingFactory->>Top: new Top("셔츠", 30, 20, 70, 50)
        Top-->>ClothingFactory: Top instance
        ClothingFactory-->>Main: ClothingItem[2] (tops)

        Main->>ClothingFactory: createAllBottoms()
        ClothingFactory->>ClothingFactory: createJeans()
        ClothingFactory->>Bottom: new Bottom("청바지", 40, 30, 40, 70)
        Bottom-->>ClothingFactory: Bottom instance
        ClothingFactory->>ClothingFactory: createSlacks()
        ClothingFactory->>Bottom: new Bottom("슬랙스", 35, 25, 80, 50)
        Bottom-->>ClothingFactory: Bottom instance
        ClothingFactory-->>Main: ClothingItem[2] (bottoms)

        Main->>ClothingFactory: createAllOuters()
        ClothingFactory->>ClothingFactory: createHoodie()
        ClothingFactory->>Outer: new Outer("후드집업", 60, 30, 20, 80)
        Outer-->>ClothingFactory: Outer instance
        ClothingFactory->>ClothingFactory: createTrenchCoat()
        ClothingFactory->>Outer: new Outer("트렌치코트", 50, 60, 80, 40)
        Outer-->>ClothingFactory: Outer instance
        ClothingFactory-->>Main: ClothingItem[2] (outers)

        Main->>Wardrobe: new Wardrobe("사용자", tops, bottoms, outers)
        Wardrobe-->>Main: Wardrobe instance

        Main->>Wardrobe: printWardrobe()
        Wardrobe-->>User: 옷장 목록 출력

        %% ========== Create Recommender ==========
        Main->>BasicRecommendStrategy: new BasicRecommendStrategy()
        BasicRecommendStrategy-->>Main: BasicRecommendStrategy instance
        Main->>OutfitRecommender: new OutfitRecommender(wardrobe, strategy, scanner)
        OutfitRecommender-->>Main: OutfitRecommender instance

        %% ========== Start Recommendation ==========
        Main->>OutfitRecommender: startRecommendation()

        %% --- Step 1: Print Intro ---
        OutfitRecommender->>OutfitRecommender: printIntro()
        OutfitRecommender-->>User: "코디 추천 시뮬레이터" 타이틀 출력

        %% --- Step 2: Input Weather ---
        OutfitRecommender->>OutfitRecommender: inputSituation()
        OutfitRecommender->>Weather: Weather.values()
        Weather-->>OutfitRecommender: Weather[]
        OutfitRecommender-->>User: 날씨 선택 목록 출력
        User->>OutfitRecommender: 날씨 번호 입력
        OutfitRecommender->>ConsoleUtils: getValidInput(1, 6)
        ConsoleUtils-->>OutfitRecommender: valid choice (int)

        %% --- Step 3: Input Schedule ---
        OutfitRecommender->>Schedule: Schedule.values()
        Schedule-->>OutfitRecommender: Schedule[]
        OutfitRecommender-->>User: 일정 선택 목록 출력
        User->>OutfitRecommender: 일정 번호 입력
        OutfitRecommender->>ConsoleUtils: getValidInput(1, 4)
        ConsoleUtils-->>OutfitRecommender: valid choice (int)

        %% --- Step 4: Input Temperature ---
        OutfitRecommender-->>User: 기온 입력 요청
        User->>OutfitRecommender: 기온 입력
        OutfitRecommender->>ConsoleUtils: getValidInput(-10, 40)
        ConsoleUtils-->>OutfitRecommender: valid temperature (int)

        %% --- Step 5: Create Situation ---
        OutfitRecommender->>Situation: new Situation(weather, schedule, temp)
        Situation-->>OutfitRecommender: Situation instance

        %% --- Step 6: Pause for effect ---
        OutfitRecommender->>ConsoleUtils: pause(1000)
        ConsoleUtils-->>OutfitRecommender: (1초 대기)

        %% ========== Recommendation Logic ==========
        OutfitRecommender->>RecommendStrategy: recommend(wardrobe, situation)
        RecommendStrategy->>BasicRecommendStrategy: recommend(wardrobe, situation)

        BasicRecommendStrategy->>Wardrobe: getTops()
        Wardrobe-->>BasicRecommendStrategy: ClothingItem[] tops
        BasicRecommendStrategy->>Wardrobe: getBottoms()
        Wardrobe-->>BasicRecommendStrategy: ClothingItem[] bottoms
        BasicRecommendStrategy->>Wardrobe: getOuters()
        Wardrobe-->>BasicRecommendStrategy: ClothingItem[] outers

        %% --- Brute-force search all combinations ---
        BasicRecommendStrategy->>BasicRecommendStrategy: bestScore = -1, bestOutfit = null
        loop for each top in tops
            loop for each bottom in bottoms
                loop for each outer in outers
                    BasicRecommendStrategy->>Outfit: new Outfit(top, bottom, outer)
                    Outfit-->>BasicRecommendStrategy: Outfit instance

                    BasicRecommendStrategy->>Outfit: calculateTotalScore(situation)
                    Outfit->>ClothingItem: calculateScore(situation)
                    ClothingItem->>Situation: getWarmthWeight()
                    Situation-->>ClothingItem: double
                    ClothingItem->>Situation: getWaterResistanceWeight()
                    Situation-->>ClothingItem: double
                    ClothingItem->>Situation: getFormalityWeight()
                    Situation-->>ClothingItem: double
                    ClothingItem->>Situation: getComfortWeight()
                    Situation-->>ClothingItem: double
                    ClothingItem->>ClothingProperty: calculateBonus(item, situation)
                    ClothingProperty->>Situation: getWarmthWeight()
                    Situation-->>ClothingProperty: double
                    ClothingProperty->>Situation: getWaterResistanceWeight()
                    Situation-->>ClothingProperty: double
                    ClothingProperty->>Situation: getFormalityWeight()
                    Situation-->>ClothingProperty: double
                    ClothingProperty->>Situation: getComfortWeight()
                    Situation-->>ClothingProperty: double
                    ClothingProperty-->>ClothingItem: int bonus
                    ClothingItem-->>Outfit: int score

                    Outfit->>ClothingItem: calculateScore(situation)
                    ClothingItem-->>Outfit: int score

                    Outfit->>ClothingItem: calculateScore(situation)
                    ClothingItem-->>Outfit: int score

                    Outfit-->>BasicRecommendStrategy: int totalScore

                    alt if totalScore > bestScore
                        BasicRecommendStrategy->>BasicRecommendStrategy: bestScore = totalScore
                        BasicRecommendStrategy->>BasicRecommendStrategy: bestOutfit = current
                    end
                end
            end
        end

        BasicRecommendStrategy-->>RecommendStrategy: Outfit (best)
        RecommendStrategy-->>OutfitRecommender: Outfit (best)

        %% ========== Calculate Final Score ==========
        OutfitRecommender->>Outfit: calculateTotalScore(situation)
        Outfit-->>OutfitRecommender: int score

        %% ========== Print Result ==========
        OutfitRecommender->>OutfitRecommender: printResult(outfit, situation, score)
        OutfitRecommender->>Situation: getDisplay()
        Situation-->>OutfitRecommender: String display
        OutfitRecommender-->>User: 오늘의 상황 출력

        OutfitRecommender->>Outfit: printOutfit()
        Outfit->>ClothingItem: getDisplayName()
        ClothingItem-->>Outfit: String
        Outfit-->>User: 추천 코디 출력

        OutfitRecommender-->>User: 적합도 점수 및 평가 출력

        %% ========== Repeat Prompt ==========
        Main-->>User: "다시 추천받으시겠습니까? (Y/N)"
        User->>Main: Y 또는 N 입력
    end

    Main-->>User: "안녕히 가세요"
```

### 2.2 점수 계산 상세 흐름

```mermaid
sequenceDiagram
    autonumber
    participant Outfit
    participant ClothingItem
    participant ClothingProperty
    participant Situation

    Note over Outfit,Situation: calculateTotalScore() 호출 시 각 아이템별 점수 계산

    Outfit->>ClothingItem: calculateScore(situation)
    Note right of ClothingItem: 상의 점수 계산

    ClothingItem->>Situation: getWarmthWeight()
    Situation-->>ClothingItem: weight (기온/날씨 기반)
    ClothingItem->>ClothingItem: warmth * weight

    ClothingItem->>Situation: getWaterResistanceWeight()
    Situation-->>ClothingItem: weight (날씨 기반)
    ClothingItem->>ClothingItem: waterResistance * weight

    ClothingItem->>Situation: getFormalityWeight()
    Situation-->>ClothingItem: weight (일정 기반)
    ClothingItem->>ClothingItem: formality * weight

    ClothingItem->>Situation: getComfortWeight()
    Situation-->>ClothingItem: weight (일정 기반)
    ClothingItem->>ClothingItem: comfort * weight

    loop for each ClothingProperty
        ClothingItem->>ClothingProperty: calculateBonus(item, situation)
        ClothingProperty->>Situation: getWarmthWeight()
        Situation-->>ClothingProperty: weight
        ClothingProperty->>ClothingProperty: warmthBonus * weight

        ClothingProperty->>Situation: getWaterResistanceWeight()
        Situation-->>ClothingProperty: weight
        ClothingProperty->>ClothingProperty: waterBonus * weight

        ClothingProperty->>Situation: getFormalityWeight()
        Situation-->>ClothingProperty: weight
        ClothingProperty->>ClothingProperty: formalityBonus * weight

        ClothingProperty->>Situation: getComfortWeight()
        Situation-->>ClothingProperty: weight
        ClothingProperty->>ClothingProperty: comfortBonus * weight

        ClothingProperty-->>ClothingItem: int bonus
    end

    ClothingItem->>ClothingItem: score = sum of all
    ClothingItem-->>Outfit: int score

    Note over Outfit: (하의, 아우터도 동일한 과정 반복)

    Outfit->>Outfit: total = topScore + bottomScore + outerScore
    Outfit-->>Caller: int totalScore
```

---

## 3. 다이어그램 설명

### 클래스 다이어그램 주요 관계

| 관계 | 설명 |
|------|------|
| **상속** (--\|>) | `ClothingItem`을 `Top`, `Bottom`, `Outer`가 상속 |
| **구현** (..\|>) | `RecommendStrategy`를 `BasicRecommendStrategy`가 구현 |
| **구성** (--*) | `Outfit`은 `ClothingItem` 3개로 구성됨 (상의, 하의, 아우터) |
| **연관** (-->) | `Situation`은 `Weather`와 `Schedule`을 참조 |
| **의존** (..>) | `ClothingItem.calculateScore()`는 `Situation`에 의존 |

### 순차도 주요 흐름

1. **초기화**: 프로그램 실행 → 유틸리티 초기화 → 옷장 생성
2. **입력**: 사용자가 날씨/일정/기온을 입력 → `Situation` 객체 생성
3. **추천**: `BasicRecommendStrategy`가 모든 코디 조합을 탐색하여 최적의 `Outfit` 선택
4. **점수 계산**: 각 아이템의 능력치 × 상황 가중치 + 특성 별볼너스 = 총 적합도
5. **결과 출력**: 추천 코디와 적합도 점수를 사용자에게 표시

---

*본 다이어그램은 프로젝트의 전체 구조와 실행 흐름을 시각화한 것입니다.*
