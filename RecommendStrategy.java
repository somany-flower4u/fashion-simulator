public interface RecommendStrategy {
    Outfit recommend(Wardrobe wardrobe, Situation situation);

    public static class Basic implements RecommendStrategy {
        @Override
        public Outfit recommend(Wardrobe wardrobe, Situation situation) {
            ClothingItem[] tops = wardrobe.getTops();
            ClothingItem[] bottoms = wardrobe.getBottoms();
            ClothingItem[] outers = wardrobe.getOuters();

            Outfit bestOutfit = null;
            int bestScore = -1;

            for (ClothingItem t : tops) {
                for (ClothingItem b : bottoms) {
                    for (ClothingItem o : outers) {
                        Outfit current = new Outfit(t, b, o);
                        int score = current.calculateTotalScore(situation);
                        if (score > bestScore) {
                            bestScore = score;
                            bestOutfit = current;
                        }
                    }
                }
            }
            return bestOutfit;
        }
    }
}
