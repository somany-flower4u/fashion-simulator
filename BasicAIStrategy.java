/**
 * 기본 AI 전략: 상성을 고려한 의사 결정
 */
public class BasicAIStrategy implements AIStrategy {

    @Override
    public int decideTurn(Trainer aiTrainer, Trainer opponent) {
        Pokemon aiActive = aiTrainer.getActivePokemon();
        Pokemon playerActive = opponent.getActivePokemon();

        // HP가 30% 이하이고, 상성이 불리하면 교체 고려
        double hpRatio = (double) aiActive.getCurrentHp() / aiActive.getMaxHp();
        double typeEff = playerActive.getType().getEffectiveness(aiActive.getType());

        if (hpRatio < 0.3 && typeEff >= 2.0 && aiTrainer.getAliveCount() > 1) {
            int bestSwitch = aiTrainer.chooseBestSwitch(playerActive);
            if (bestSwitch >= 0) {
                // 교체 대상이 현재보다 나은지 확인
                Pokemon switchTarget = aiTrainer.getPokemon(bestSwitch);
                double newTypeEff = playerActive.getType().getEffectiveness(switchTarget.getType());
                if (newTypeEff < typeEff) {
                    return 2; // 교체
                }
            }
        }
        return 1; // 공격
    }

    @Override
    public int chooseSwitchTarget(Trainer aiTrainer, Pokemon opponentPokemon) {
        return aiTrainer.chooseBestSwitch(opponentPokemon);
    }
}
