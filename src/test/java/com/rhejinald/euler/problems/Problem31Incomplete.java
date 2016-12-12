package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In England the currency is made up of pound, £, and pence, p, and there are eight coins in general circulation:
 * <p>
 * 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).
 * It is possible to make £2 in the following way:
 * <p>
 * 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
 * How many different ways can £2 be made using any number of coins?
 * <p>
 * -------------------------
 * My notes:
 * Factors of 200
 * 200 -> [1, 2, 4, 5, 8, 10, 20, 25, 40, 50, 100, 200]
 * <p>
 * All the ways to make 10:
 * 10
 * 5,5
 * 5,2,2,1
 * 5,2,1,1,1
 * 5,1,1,1,1,1
 * 2,2,2,2,2
 * 2,2,2,2,1,1
 * 2,2,2,1,1,1,1
 * 2,2,1,1,1,1,1,1
 * 2,1,1,1,1,1,1,1,1
 * 1,1,1,1,1,1,1,1,1,1
 * <p>
 * Using coins:
 * 5
 * |...,..,
 * 2   2  1
 * |.,
 * 1 1
 * <p>
 * This tree thing might be useful for "coin-factors"
 * But how to reliably build the full derivative tree of possible outcomes?
 * <p>
 * Each combination can be written as the sum:
 * a*200p + b*100p + c*50p + d*20p + e*10p + f*5p + g*2p + h*1p
 * <p>
 * We might be able to get away with some iterate over every a..f combination where f(a..f) < 200
 * and pad with ones and twos.
 * So it would look like:
 * 1f; 2f; 3f; 4f; 5f .. 40f (200p)
 * ... nah still ugly efficiency-wise. Maybe still easier to model as a tree
 * <p>
 * Because we're adding, factors may not be the best way to think about it
 * For our base denominators, 1,2,5 (everything bigger is in nice multiples):
 * 10 can be made in the ways above... but we can learn this best with a tree I think
 * <p>
 * Everything can be built using the following relationships. 6 is special to facilitate the multiples of 2
 * 1 -> 1                           // 1
 * 2 -> {1,1}, 2                    // 2
 * 5 -> {2,2,1}, 5                  // 4 -> 5, 2+2+1, 2+1+1+1, 1+1+1+1+1
 * 10 -> {2,2,2,2,2}, {5,5}, 10     // 1 + 1 + 2*5-tree + 1 = 11
 * 20 -> {10,10}, 20                // 2 + 2*tree = 24
 * 50 -> {20,20,10}, 50             // 2 + 6*tree = 146
 * 100 -> {50,50}, 100              // 2 + 2*tree = 294
 * 200 -> {100,100}, 200            // 2 + 2*tree = 590
 * <p>
 * 162 -> factors: 1, 2, 3, 6, 9, 18, 27, 54, 81, 162
 * -> For a more complex example like that, we need to see if we can build it using the smaller constituent parts
 * 162 = 100+50+10+2 - that's our first combo. Then for each, we need to
 */
@Ignore("It doesn't work yet :(")
public class Problem31Incomplete {
    public static String countString(Collection<CoinNode> nodes) {
        HashMap<Integer, Integer> coinCountMap = Maps.newHashMap();
        coinCountMap.put(1, 0);
        coinCountMap.put(2, 0);
        coinCountMap.put(5, 0);
        coinCountMap.put(10, 0);
        coinCountMap.put(20, 0);
        coinCountMap.put(50, 0);
        coinCountMap.put(100, 0);
        coinCountMap.put(200, 0);

        for (CoinNode node : nodes) {
            Integer coin = node.getCoinValue();
            coinCountMap.put(coin, coinCountMap.get(coin) + 1);
        }

        return String.format("%d*£2 %d*£1 %d*50p %d*20p %d*10p %d*5p %d*2p %d*1p",
                coinCountMap.get(200),
                coinCountMap.get(100),
                coinCountMap.get(50),
                coinCountMap.get(20),
                coinCountMap.get(10),
                coinCountMap.get(5),
                coinCountMap.get(2),
                coinCountMap.get(1));
    }

    public CoinMap buildMap() {
        HashMap<Integer, CoinNode> coinMap = Maps.newHashMapWithExpectedSize(8);
        coinMap.put(1, new CoinNode(1));
        coinMap.put(2, new CoinNode(2, new CoinList(1, 1)));
        coinMap.put(5, new CoinNode(5, new CoinList(2, 2, 1)));
        coinMap.put(10, new CoinNode(10, new CoinList(2, 2, 2, 2, 2), new CoinList(5, 5)));
        coinMap.put(20, new CoinNode(20, new CoinList(10, 10)));
        coinMap.put(50, new CoinNode(50, new CoinList(20, 20, 10)));
        coinMap.put(100, new CoinNode(100, new CoinList(50, 50)));
        coinMap.put(200, new CoinNode(200, new CoinList(100, 100)));
        return new CoinMap(coinMap);
    }

    @Test
    public void basicTraverse() throws Exception {
        CoinMap coinMap = buildMap();
        assertThat(getAllPermutationsStart(coinMap, coinMap.get(2))).containsExactly(new CoinList(1, 1), new CoinList(2));

    }

    private Set<CoinList> getAllPermutationsStart(final CoinMap coinMap, final CoinNode root) {
        Set<CoinList> permutations = Sets.newHashSet();
        List<CoinNode> nodesToTraverse = Lists.newArrayList();
        nodesToTraverse.add(root);

        while (!nodesToTraverse.isEmpty()) {
            permutations.add(getCoinSetFromCurrentCoins(nodesToTraverse));

            for (CoinNode coinNode : nodesToTraverse) {
                CoinNode node = nodesToTraverse.remove(0);
            }


// I have no idea what I was doing here...
//            if (node.getCoinValue() > 1) {
//                Set<CoinList> coinLists = node.getCoinLists();
//                for (CoinList coinList : coinLists) {
//                    for (Integer coin : coinList.getList()) {
//                        nodesToTraverse.add(coinMap.get(coin));
//                    }
//                }
//            }
        }
        return permutations;
    }

    @Test
    public void testGetCoinSetFromCurrentCoins() throws Exception {
        ArrayList<CoinNode> coinNodes = Lists.newArrayList(
                new CoinNode(1, new CoinList(1), new CoinList(2)),
                new CoinNode(1, new CoinList(2)),
                new CoinNode(2, new CoinList(1, 1)));
        assertThat(getCoinSetFromCurrentCoins(coinNodes).getList()).containsExactly(1,1,2);

        ArrayList<CoinNode> coinNodes1 = Lists.newArrayList(
                new CoinNode(1, new CoinList(2)),
                new CoinNode(2, new CoinList(1), new CoinList(2)),
                new CoinNode(1, new CoinList(1, 1)));
        assertThat(getCoinSetFromCurrentCoins(coinNodes1).getList()).containsExactly(1,1,2);

    }

    private CoinList getCoinSetFromCurrentCoins(List<CoinNode> nodesToTraverse) {
        List<Integer> permutationCoinSet = Lists.newArrayList();
        for (CoinNode coinNode : nodesToTraverse) {
            permutationCoinSet.add(coinNode.getCoinValue());
        }
        return new CoinList(permutationCoinSet);
    }

    @Test
    public void testCountCointsOutputsCorrectString() throws Exception {
        CoinNode coinNode1 = new CoinNode(5);
        CoinNode coinNode2 = new CoinNode(5);
        CoinNode coinNode3 = new CoinNode(10);
        ArrayList<CoinNode> coinNodes = Lists.newArrayList(coinNode1, coinNode2, coinNode3);

        assertThat(countString(coinNodes)).isEqualTo("0*£2 0*£1 0*50p 0*20p 1*10p 2*5p 0*2p 0*1p");
    }


    public class CoinNode {

        private final int value;
        private final Set<CoinList> coinLists;

        public CoinNode(int value, CoinList... coinLists) {
            this.value = value;
            this.coinLists = Sets.newHashSet(coinLists);
        }

        public Set<CoinList> getCoinLists() {
            return coinLists;
        }

        public Integer getCoinValue() {
            return value;
        }
    }

    public class CoinList {
        private final List<Integer> valueSet;

        public CoinList(Collection<Integer> valueSet) {
            this.valueSet = Lists.newArrayList(valueSet);
            Collections.sort(this.valueSet);
        }

        public CoinList(Integer... valueSet) {
            this(Lists.newArrayList(valueSet));
        }

        public List<Integer> getList() {
            return valueSet;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CoinList coinList = (CoinList) o;

            return valueSet != null ? valueSet.equals(coinList.valueSet) : coinList.valueSet == null;

        }

        @Override
        public int hashCode() {
            return valueSet != null ? valueSet.hashCode() : 0;
        }
    }

    private class CoinMap {
        private Map<Integer, CoinNode> coinMap;

        public CoinMap(Map<Integer, CoinNode> coinMap) {
            this.coinMap = coinMap;
        }

        public Map<Integer, CoinNode> getCoinMap() {
            return coinMap;
        }

        public CoinNode get(int i) {
            return coinMap.get(i);
        }
    }
}
