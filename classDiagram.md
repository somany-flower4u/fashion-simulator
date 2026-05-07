# 코디네이터 - 클래스 다이어그램 & 순차도 (간소화)

## 1. 클래스 다이어그램

```mermaid
classDiagram
    direction TB

    %% Enums
    class Category {
        <<enumeration>>
        TOP / BOTTOM / OUTER
    }
    class Weather {
        <<enumeration>>
        SUNNY / RAINY / SNOWY / COLD / HOT / WINDY
    }
    class Schedule {
        <<enumeration>>
        BUSINESS / CASUAL / SPORTS / DATE
    }

    %% Abstract
    class ClothingItem {
        <<abstract>>
        -name, warmth, waterResistance, formality, comfort
        +calculateScore(Situation) int
        +getDisplayName() String
    }

    %% Subclasses
    class Top
    class Bottom
    class Outer

    %% Core Classes
    class ClothingProperty {
        -warmthBonus, waterBonus, formalityBonus, comfortBonus
        +calculateBonus(item, situation) int
    }
    class Situation {
        -weather, schedule, temperature
        +getWarmthWeight() double
        +getWaterResistanceWeight() double
        +getFormalityWeight() double
        +getComfortWeight() double
    }
    class Outfit {
        -top, bottom, outer
        +calculateTotalScore(Situation) int
        +printOutfit() void
    }
    class Wardrobe {
        -tops[], bottoms[], outers[]
        +getTops() ClothingItem[]
        +getBottoms() ClothingItem[]
        +getOuters() ClothingItem[]
    }

    %% Strategy
    class RecommendStrategy {
        <<interface>>
        +recommend(Wardrobe, Situation) Outfit
    }
    class BasicRecommendStrategy {
        +recommend(Wardrobe, Situation) Outfit
    }

    %% Entry & Factory
    class Main {
        +main(args)$ void
    }
    class ClothingFactory {
        +createAllTops()$ ClothingItem[]
        +createAllBottoms()$ ClothingItem[]
        +createAllOuters()$ ClothingItem[]
    }
    class OutfitRecommender {
        +startRecommendation() void
    }

    %% Relationships
    ClothingItem <|-- Top
    ClothingItem <|-- Bottom
    ClothingItem <|-- Outer
    RecommendStrategy <|.. BasicRecommendStrategy

    ClothingItem --> Category
    ClothingItem *-- ClothingProperty : 0..4
    Outfit *-- ClothingItem : 3
    Wardrobe *-- ClothingItem : arrays
    Situation --> Weather
    Situation --> Schedule

    Main --> ClothingFactory : creates items
    Main --> Wardrobe : creates
    Main --> OutfitRecommender : creates
    OutfitRecommender --> RecommendStrategy : uses
    OutfitRecommender --> Situation : creates
    BasicRecommendStrategy --> Wardrobe : reads
    BasicRecommendStrategy --> Outfit : creates
```

## 2. 순차도

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Main
    participant ClothingFactory
    participant Wardrobe
    participant OutfitRecommender
    participant Situation
    participant RecommendStrategy
    participant Outfit

    User->>Main: 프로그램 실행
    Main->>ClothingFactory: createAllTops/Bottoms/Outers()
    ClothingFactory-->>Main: ClothingItem[]
    Main->>Wardrobe: new Wardrobe(items...)
    Wardrobe-->>User: 옷장 목록 출력

    Main->>OutfitRecommender: new OutfitRecommender(wardrobe, strategy)
    OutfitRecommender-->>User: 날씨/일정/기온 입력 요청
    User->>OutfitRecommender: 상황 입력
    OutfitRecommender->>Situation: new Situation(weather, schedule, temp)
    Situation-->>OutfitRecommender: Situation 객체

    OutfitRecommender->>RecommendStrategy: recommend(wardrobe, situation)
    RecommendStrategy->>Wardrobe: getTops/Bottoms/Outers()
    Wardrobe-->>RecommendStrategy: 아이템 배열

    Note over RecommendStrategy: 모든 조합 탐색<br/>(상의 × 하의 × 아우터)
    loop 최적 조합 탐색
        RecommendStrategy->>Outfit: new Outfit(t, b, o)
        Outfit->>Outfit: calculateTotalScore(situation)
        Outfit-->>RecommendStrategy: 점수
    end
    RecommendStrategy-->>OutfitRecommender: 최적 Outfit

    OutfitRecommender->>Outfit: calculateTotalScore(situation)
    Outfit-->>OutfitRecommender: 최종 점수
    OutfitRecommender-->>User: 추천 코디 + 적합도 출력

    User->>Main: 재시도 여부 (Y/N)
```

## 3. 핵심 요약

| 구성요소 | 역할 |
|---------|------|
| **ClothingItem** | 옷의 기본 능력치 (보온/방수/격식/편안) |
| **Situation** | 오늘 상황 (날씨+일정+기온) → 가중치 계산 |
| **Outfit** | 상의+하의+아우터 조합 → 총점 계산 |
| **RecommendStrategy** | 추천 알고리즘 인터페이스 |
| **BasicRecommendStrategy** | 모든 조합을 탐색해 최고 점수 코디 선택 |
| **OutfitRecommender** | 사용자 입력 받고 추천 결과 출력 |
| **ClothingFactory** | 팩토리 패턴으로 옷 아이템 생성 |

---

*상세 버전은 `classDiagram_detailed.md`를 참조하세요.*
