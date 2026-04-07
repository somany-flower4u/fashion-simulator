public class PokemonFactory {

    // 포켓몬 전체 도감 (6마리)
    public static Pokemon[] createAllPokemon() {
        Pokemon[] all = new Pokemon[6];

        // 불 타입
        all[0] = createCharizard();
        all[1] = createFlareon();

        // 물 타입
        all[2] = createBlastoise();
        all[3] = createSamurott();

        // 풀 타입
        all[4] = createVenusaur();
        all[5] = createLeafeon();

        return all;
    }

    // 리자몽: 밸런스 공격형
    public static Pokemon createCharizard() {
        Pokemon p = new FirePokemon("리자몽", 150, 95, 70, 85);
        p.setSkills(
                new Skill("화염방사", Type.FIRE, 90, 100, 15, false),
                new Skill("불대문자", Type.FIRE, 120, 85, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 부스터: 극 공격형 (유리)
    public static Pokemon createFlareon() {
        Pokemon p = new FirePokemon("부스터", 120, 110, 55, 70);
        p.setSkills(
                new Skill("화염방사", Type.FIRE, 90, 100, 15, false),
                new Skill("불대문자", Type.FIRE, 120, 85, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 거북왕: 탱커형
    public static Pokemon createBlastoise() {
        Pokemon p = new WaterPokemon("거북왕", 160, 75, 95, 65);
        p.setSkills(
                new Skill("파도타기", Type.WATER, 90, 100, 15, false),
                new Skill("하이드로펌프", Type.WATER, 120, 80, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 샤미드: 속도형
    public static Pokemon createSamurott() {
        Pokemon p = new WaterPokemon("샤미드", 130, 90, 70, 90);
        p.setSkills(
                new Skill("파도타기", Type.WATER, 90, 100, 15, false),
                new Skill("하이드로펌프", Type.WATER, 120, 80, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 이상해꽃: 밸런스형
    public static Pokemon createVenusaur() {
        Pokemon p = new GrassPokemon("이상해꽃", 155, 80, 85, 75);
        p.setSkills(
                new Skill("에너지볼", Type.GRASS, 90, 100, 15, false),
                new Skill("솔라빔", Type.GRASS, 120, 85, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 리프: 속도 공격형
    public static Pokemon createLeafeon() {
        Pokemon p = new GrassPokemon("리프", 120, 100, 60, 95);
        p.setSkills(
                new Skill("에너지볼", Type.GRASS, 90, 100, 15, false),
                new Skill("솔라빔", Type.GRASS, 120, 85, 5, false),
                new Skill("몸통박치기", Type.NORMAL, 50, 100, 30, false),
                new Skill("전광석화", Type.NORMAL, 40, 100, 30, true)
        );
        return p;
    }

    // 포켓몬 도감 출력
    public static void printPokedex(Pokemon[] pokemons) {
        System.out.println("┌────┬──────────┬──────┬─────┬──────┬──────┬──────┐");
        System.out.println("│ #  │ 이름      │ 속성  │  HP │  공격 │ 방어  │  속도  │");
        System.out.println("├────┼──────────┼──────┼─────┼──────┼──────┼──────┤");
        for (int i = 0; i < pokemons.length; i++) {
            Pokemon p = pokemons[i];
            System.out.printf("│ %-2d │ %-6s   │  %s  │ %-3d │  %-3d │  %-3d │  %-3d │\n",
                    (i + 1), p.getName(), p.getTypeEmoji(),
                    p.getMaxHp(), p.getAttack(), p.getDefense(), p.getSpeed());
        }
        System.out.println("└────┴──────────┴──────┴─────┴──────┴──────┴──────┘");
    }

    // 깊은 복사 (같은 포켓몬을 두 트레이너가 가질 수 있도록)
    public static Pokemon clonePokemon(Pokemon original) {
        Pokemon clone;
        switch (original.getType()) {
            case FIRE:
                clone = new FirePokemon(original.getName(), original.getMaxHp(),
                        original.getAttack(), original.getDefense(), original.getSpeed());
                break;
            case WATER:
                clone = new WaterPokemon(original.getName(), original.getMaxHp(),
                        original.getAttack(), original.getDefense(), original.getSpeed());
                break;
            case GRASS:
                clone = new GrassPokemon(original.getName(), original.getMaxHp(),
                        original.getAttack(), original.getDefense(), original.getSpeed());
                break;
            default:
                return null;
        }

        // 스킬 복사
        Skill[] origSkills = original.getSkills();
        clone.setSkills(
                copySkill(origSkills[0]),
                copySkill(origSkills[1]),
                copySkill(origSkills[2]),
                copySkill(origSkills[3])
        );
        return clone;
    }

    private static Skill copySkill(Skill s) {
        if (s == null) return null;
        return new Skill(s.getName(), s.getType(), s.getPower(),
                s.getAccuracy(), s.getMaxPP(), s.isPriority());
    }
}
