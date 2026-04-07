/**
 * AI의 포켓몬 전투 행동 결정 전략
 */
public interface AIStrategy {
    /**
     * AI의 행동을 결정 (공격 1 or 교체 2)
     * @return 1: 공격, 2: 교체
     */
    int decideTurn(Trainer aiTrainer, Trainer opponent);

    /**
     * 교체할 포켓몬 인덱스 반환
     */
    int chooseSwitchTarget(Trainer aiTrainer, Pokemon opponentPokemon);
}
