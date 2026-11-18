# 가중치 기반 패션 시뮬레이터

홍익대학교 **디자인패턴프로그래밍** 수업의 팀 프로젝트입니다. 날씨, 일정, 기온을 입력하면 옷장에서 가장 잘 맞는 상의, 하의, 아우터 조합을 골라 주는 Java 콘솔 프로그램입니다.

옷마다 보온, 방수, 격식, 편안 4가지 능력치가 있고, 입력한 상황에 따라 능력치마다 다른 가중치를 곱해 점수를 냅니다. 모든 조합의 점수를 계산해 가장 높은 조합을 추천합니다.

> English summary: a Java console app from a university design-pattern course team project. Given weather, schedule and temperature, it scores every top, bottom and outer combination by weighting four item attributes (warmth, water resistance, formality, comfort) and recommends the best outfit. It applies Strategy, Factory (nested class), Enum and constructor-based dependency injection.

## 사용한 디자인 패턴

| 패턴 | 위치 | 하는 일 |
|---|---|---|
| Strategy | `RecommendStrategy` (인터페이스), `RecommendStrategy.Basic` | 추천 알고리즘을 인터페이스로 분리합니다. `Basic`은 모든 조합을 돌아 점수가 가장 높은 코디를 고릅니다. |
| Factory | `Wardrobe.Factory` (중첩 클래스) | 반팔티, 셔츠, 청바지 같은 `ClothingItem`을 만들고 속성을 붙입니다. |
| 의존성 주입 | `Main` → `OutfitRecommender` | `Main`이 `Wardrobe`, `RecommendStrategy`, `Scanner`를 만들어 생성자로 넘깁니다. |
| Enum | `Category`, `Weather`, `Schedule` | 분류, 날씨, 일정을 타입 안전한 상수로 두고 한글 이름과 이모지를 함께 가집니다. |

`ClothingItem.cloneItem()`은 속성까지 복사하는 메서드가 있지만, 현재 코드의 다른 곳에서는 호출하지 않습니다.

## 점수 계산

한 벌의 점수는 4가지 능력치에 상황 가중치를 곱해 더한 값입니다. 각 옷에는 특성(`ClothingItem.Property`)이 최대 4개 붙고, 특성도 같은 가중치로 보너스 점수를 더합니다.

```
옷 점수 = 보온×w보온 + 방수×w방수 + 격식×w격식 + 편안×w편안 + 특성 보너스
코디 점수 = 상의 점수 + 하의 점수 + 아우터 점수
```

가중치는 `Situation`이 계산합니다. 기본값은 모두 1.0이고 조건에 따라 더하거나 뺍니다. 방수, 격식, 편안 가중치는 최소 0.1입니다.

| 가중치 | 조건 | 변화 |
|---|---|---|
| 보온 | 기온 5℃ 미만 | +1.5 |
| | 기온 5℃ 이상 15℃ 미만 | +0.5 |
| | 기온 30℃ 초과 | -1.5 |
| | 날씨가 눈 또는 추움 | +0.5 |
| | 날씨가 더움 | -1.0 |
| 방수 | 비 | +2.0 |
| | 눈 | +1.0 |
| 격식 | 정장/회의 | +1.5 |
| | 데이트 | +0.5 |
| | 캐주얼 외출 | -0.3 |
| 편안 | 운동 | +2.0 |
| | 캐주얼 외출 | +0.5 |
| | 정장/회의 | -0.3 |

기온이 30℃를 넘으면 보온 능력치가 있는 아우터는 점수에서 500점을 뺍니다. 더운 날 두꺼운 아우터를 추천하지 않게 하려는 규칙입니다.

결과 화면에는 `적합도 점수: N / 1200`과 한 줄 평이 나옵니다. 한 줄 평은 900점 이상, 700점 이상, 500점 이상, 그 미만으로 나뉩니다. 1200은 화면에 적어 둔 기준값이고, 코드에서 점수를 이 값으로 제한하지는 않습니다.

## 옷장

`Wardrobe.Factory`가 만드는 옷은 8가지입니다. 능력치는 보온, 방수, 격식, 편안 순입니다.

| 분류 | 이름 | 능력치 |
|---|---|---|
| 상의 | 반팔티 | 20, 10, 10, 80 |
| 상의 | 셔츠 | 30, 20, 70, 50 |
| 하의 | 청바지 | 40, 30, 40, 70 |
| 하의 | 슬랙스 | 35, 25, 80, 50 |
| 하의 | 트레이닝 팬츠 | 25, 20, 20, 90 |
| 아우터 | 후드집업 | 60, 30, 20, 80 |
| 아우터 | 트렌치코트 | 50, 60, 80, 40 |
| 아우터 | 아우터 없음 | 0, 0, 0, 100 |

## 폴더 구조

```
Main.java                진입점, 객체 조립, 다시 추천받기 반복
OutfitRecommender.java   입력 받기, 추천 호출, 결과 출력
RecommendStrategy.java   추천 전략 인터페이스와 Basic 구현
Wardrobe.java            옷장, 중첩 Factory
ClothingItem.java        옷 한 벌, 중첩 Property
Outfit.java              상의+하의+아우터 한 벌 조합과 총점
Situation.java           날씨·일정·기온과 가중치 계산
Category.java Weather.java Schedule.java   enum
Diagram/                 클래스 다이어그램, 순차도 (Mermaid)
class/                   컴파일된 .class 파일
run.sh, run.bat          컴파일과 실행 스크립트
```

다이어그램은 [클래스 다이어그램](Diagram/class_diagram_simple.md), [순차도](Diagram/sequence_diagram_simple.md)에 있습니다.

## 설치와 실행

JDK가 필요합니다. 아래 명령은 컴파일해서 `class/` 폴더에 넣고 실행합니다.

```bash
# macOS / Linux
./run.sh

# Windows
run.bat

# 직접 실행
javac -d class *.java
java -cp class Main
```

실행하면 옷장 표가 나오고, 순서대로 날씨(맑음, 비, 눈, 추움, 더움, 바람), 일정(정장/회의, 캐주얼 외출, 운동, 데이트), 기온(-10 ~ 40)을 입력합니다. 결과가 나온 뒤 `Y`를 입력하면 다른 상황으로 다시 추천받을 수 있습니다.

## 팀

홍익대학교 소프트웨어융합학과 김태빈, 이호진, 전지민, 장재경

## 라이선스

코드는 [MIT License](LICENSE)를 따릅니다.
