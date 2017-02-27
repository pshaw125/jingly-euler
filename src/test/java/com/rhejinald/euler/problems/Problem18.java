package com.rhejinald.euler.problems;


import com.google.common.collect.Lists;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Number Triangle
 * <p>
 * 3
 * 7 4
 * 2 4 6
 * 8 5 9 3
 * <p>
 * Highest score = 3+7+4+9=23
 * Instead of center start and move Left or Right; think of it as remain at current index, or +1 (move right)
 * by left aligning the tree
 * <p>
 * Each path will comprise of a list of indices, each either equal to or 1 greater than the last
 * <p>
 * I'm thinking of a modified DFS for this - each path that currently has the highest total is the one to continue
 * with. It'd be nice not to have to do every possible path; so yeah we do actually want to assign weights.
 * Working from the bottom up we can construct the "strongest" path from each tuple - we have to check every value
 * once which should give us an O(n) solution
 *
 * ======
 * Success! on 12/11/2016
 * [9, 8, 8, 7, 6, 5, 4, 3, 3, 3, 2, 2, 2, 1, 0] with Score: 1074
 *
 * Gonna try this with problem 67...
 */
public class Problem18 {


    private List<List<Integer>> loadData() {
        List<List<Integer>> base = Lists.newArrayListWithCapacity(15);
        base.add(Lists.newArrayList(75));
        base.add(Lists.newArrayList(95, 64));
        base.add(Lists.newArrayList(17, 47, 82));
        base.add(Lists.newArrayList(18, 35, 87, 10));
        base.add(Lists.newArrayList(20, 4, 82, 47, 65));
        base.add(Lists.newArrayList(19, 1, 23, 75, 3, 34));
        base.add(Lists.newArrayList(88, 2, 77, 73, 7, 63, 67));
        base.add(Lists.newArrayList(99, 65, 4, 28, 6, 16, 70, 92));
        base.add(Lists.newArrayList(41, 41, 26, 56, 83, 40, 80, 70, 33));
        base.add(Lists.newArrayList(41, 48, 72, 33, 47, 32, 37, 16, 94, 29));
        base.add(Lists.newArrayList(53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14));
        base.add(Lists.newArrayList(70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57));
        base.add(Lists.newArrayList(91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48));
        base.add(Lists.newArrayList(63, 66, 4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31));
        base.add(Lists.newArrayList(4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 4, 23));
        Collections.reverse(base);
        return base;
    }

    private List<List<Integer>> loadSampleData() {
        List<List<Integer>> base = Lists.newArrayListWithCapacity(4);
        base.add(Lists.newArrayList(3));
        base.add(Lists.newArrayList(7, 4));
        base.add(Lists.newArrayList(2, 4, 6));
        base.add(Lists.newArrayList(8, 5, 9, 3));
        Collections.reverse(base);
        return base;
    }

    @Test
    public void weightIndividualLine() throws Exception {
        List<List<Integer>> data = loadSampleData();
        List<Integer> currentRow = data.get(1);
        List<Integer> firstRow = data.get(0);
        List<WeightedPathNode> newFirstRow = convertFirstRowToWeightedPathNode(firstRow);

        List<WeightedPathNode> weightedRow = buildRow(currentRow, newFirstRow);

        assertThat(weightedRow).hasSize(3);
        assertThat(weightedRow).contains(
                new WeightedPathNode(0, 10, Lists.newArrayList(0, 0)),
                new WeightedPathNode(1, 13, Lists.newArrayList(2, 1)),
                new WeightedPathNode(2, 15, Lists.newArrayList(2, 2))
        );
    }

    @Test
    public void weightSequentialLines() throws Exception {
        List<List<Integer>> data = loadSampleData();
        List<Integer> firstRow = data.get(0);
        List<WeightedPathNode> newFirstRow = convertFirstRowToWeightedPathNode(firstRow);

        List<Integer> secondRow = data.get(1);
        List<WeightedPathNode> weightedRow = buildRow(secondRow, newFirstRow);

        List<Integer> thirdRow = data.get(2);
        List<WeightedPathNode> compiledThirdRow = buildRow(thirdRow, weightedRow);
        assertThat(compiledThirdRow).hasSize(2);
        assertThat(compiledThirdRow).contains(
                new WeightedPathNode(0, 20, Lists.newArrayList(2, 1, 0)),
                new WeightedPathNode(1, 19, Lists.newArrayList(2, 2, 1))
        );
    }

    @Test
    public void weightFullRecursively() throws Exception {
        List<List<Integer>> data = loadSampleData();
        List<Integer> firstRow = data.get(0);
        List<WeightedPathNode> newFirstRow = convertFirstRowToWeightedPathNode(firstRow);


        List<WeightedPathNode> weightedPathNodes = buildRowsRecursively(1, newFirstRow, data);
        assertThat(weightedPathNodes).hasSize(1);
        assertThat(weightedPathNodes).contains(new WeightedPathNode(0, 23, Lists.newArrayList(2, 1, 0, 0)), Index.atIndex(0));

        WeightedPathNode result = weightedPathNodes.get(0);
        System.out.println(result.getPathUpToThisPoint() + " with Score: " + result.getScore());
    }

    private List<WeightedPathNode> buildRowsRecursively(int value, List<WeightedPathNode> nextRow, List<List<Integer>> data) {
        if (value >= data.size()) {
            return nextRow;
        } else {
            List<WeightedPathNode> weightedPathNodes = buildRow(data.get(value), nextRow);
            return buildRowsRecursively(value + 1, weightedPathNodes, data);
        }
    }

    private List<WeightedPathNode> convertFirstRowToWeightedPathNode(List<Integer> firstRow) {
        List<WeightedPathNode> newFirstRow = Lists.newArrayList();
        for (int i = 0; i < firstRow.size(); i++) {
            newFirstRow.add(new WeightedPathNode(i, firstRow.get(i), Lists.<Integer>newArrayList(i)));
        }
        return newFirstRow;
    }

    private List<WeightedPathNode> buildRow(List<Integer> currentRow, List<WeightedPathNode> previousRow) {
        List<WeightedPathNode> updatedRow = Lists.newArrayList();
        for (int currentIndex = 0; currentIndex < currentRow.size(); currentIndex++) {
            WeightedPathNode newNode = getWeightedPathNodeForIndex(currentRow, previousRow, currentIndex);
            updatedRow.add(newNode.getIndex(), newNode);
        }
        return updatedRow;
    }

    private WeightedPathNode getWeightedPathNodeForIndex(List<Integer> currentRow, List<WeightedPathNode> previousRow, int currentIndex) {
        Integer currentIndexScore = currentRow.get(currentIndex);

        int indexOfLeftPath = currentIndex;
        int indexOfRightPath = currentIndex + 1;
        WeightedPathNode leftPath = previousRow.get(indexOfLeftPath);
        WeightedPathNode rightPath = previousRow.get(indexOfRightPath);
        Integer leftBranchScore = leftPath.getScore();
        Integer rightBranchScore = rightPath.getScore();

        int selectedPathScore;
        WeightedPathNode selectedPath;
        if (leftBranchScore >= rightBranchScore) {
            selectedPathScore = leftBranchScore;
            selectedPath = leftPath;
        } else { // rightBranchScore > leftBranchScore
            selectedPathScore = rightBranchScore;
            selectedPath = rightPath;
        }
        ArrayList<Integer> pathUpToThisPoint = Lists.newArrayList(selectedPath.getPathUpToThisPoint());
        pathUpToThisPoint.add(currentIndex);
        return new WeightedPathNode(currentIndex, selectedPathScore + currentIndexScore, pathUpToThisPoint);
    }


    @Test
    public void testProblem18() throws Exception {
        List<List<Integer>> data = loadData();
        List<Integer> firstRow = data.get(0);
        List<WeightedPathNode> newFirstRow = convertFirstRowToWeightedPathNode(firstRow);

        List<WeightedPathNode> weightedPathNodes = buildRowsRecursively(1, newFirstRow, data);
        assertThat(weightedPathNodes).hasSize(1);

        WeightedPathNode result = weightedPathNodes.get(0);
        System.out.println(result.getPathUpToThisPoint() + " with Score: " + result.getScore());
    }


    private class WeightedPathNode {
        private final int index;
        private final int score;
        private final ArrayList<Integer> pathUpToThisPoint;

        public WeightedPathNode(int currentIndex, int newNodeScore, ArrayList<Integer> pathUpToThisPoint) {
            this.index = currentIndex;
            this.score = newNodeScore;
            this.pathUpToThisPoint = pathUpToThisPoint;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeightedPathNode that = (WeightedPathNode) o;

            if (index != that.index) return false;
            if (score != that.score) return false;
            return pathUpToThisPoint != null ? pathUpToThisPoint.equals(that.pathUpToThisPoint) : that.pathUpToThisPoint == null;

        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + score;
            result = 31 * result + (pathUpToThisPoint != null ? pathUpToThisPoint.hashCode() : 0);
            return result;
        }

        public ArrayList<Integer> getPathUpToThisPoint() {
            return pathUpToThisPoint;
        }

        public int getIndex() {
            return index;
        }

        public int getScore() {
            return score;
        }
    }
}
