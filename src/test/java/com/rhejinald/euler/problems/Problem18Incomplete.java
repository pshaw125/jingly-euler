package com.rhejinald.euler.problems;


import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Problem18Incomplete {
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
     * Brute force is a pretty easy way. For each junction, try stay and try right
     * <p>
     * We'll need a cleverer method for the larger puzzle - Dijkstra's algorithm! It's been a while!
     * <p>
     * Assign to every node a tentative distance value: set it to zero for our initial node and to infinity for all
     * other nodes. - DONE
     * <p>
     * Set the initial node as current. Mark all other nodes unvisited. Create a set of all the unvisited nodes called
     * the unvisited set.
     * <p>
     * For the current node, consider all of its unvisited neighbors and calculate their tentative distances. Compare
     * the newly calculated tentative distance to the current assigned value and assign the smaller one. For example,
     * if the current node A is marked with a distance of 6, and the edge connecting it with a neighbor B has length 2,
     * then the distance to B (through A) will be 6 + 2 = 8. If B was previously marked with a distance greater than 8
     * then change it to 8. Otherwise, keep the current value.
     * <p>
     * When we are done considering all of the neighbors of the current node, mark the current node as visited and
     * remove it from the unvisited set. A visited node will never be checked again.
     * <p>
     * If the destination node has been marked visited (when planning a route between two specific nodes) or if the
     * smallest tentative distance among the nodes in the unvisited set is infinity (when planning a complete traversal;
     * occurs when there is no connection between the initial node and remaining unvisited nodes), then stop. The
     * algorithm has finished.
     * <p>
     * Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new
     * "current node", and go back to step 3.
     */

    private ArrayList<ArrayList<Integer>> loadSampleData() {
        ArrayList<ArrayList<Integer>> base = Lists.newArrayListWithCapacity(4);
        base.add(Lists.newArrayList(3));
        base.add(Lists.newArrayList(7, 4));
        base.add(Lists.newArrayList(2, 4, 6));
        base.add(Lists.newArrayList(8, 5, 9, 3));
        return base;
    }

    @Test
    public void testAssignWeights() {
        Tree tree = new Tree(loadSampleData());
        assertThat(tree.get(0, 0).getDistanceValue()).isEqualTo(0);
        assertThat(tree.get(1, 1).getDistanceValue()).isEqualTo(Integer.MAX_VALUE);
        assertThat(tree.get(1, 0).getDistanceValue()).isEqualTo(Integer.MAX_VALUE);
        assertThat(tree.get(2, 0).getDistanceValue()).isEqualTo(Integer.MAX_VALUE);

    }


    private static class Tree {
        private ArrayList<ArrayList<Node>> tree;

        public Tree(ArrayList<ArrayList<Integer>> tree) {
            ArrayList<ArrayList<Node>> nodeTree = new ArrayList<ArrayList<Node>>();
            for (ArrayList<Integer> integers : tree) {
                ArrayList<Node> nodesForThisRow = new ArrayList<Node>();
                nodeTree.add(nodesForThisRow);
                for (Integer nodeWeight : integers) {
                    nodesForThisRow.add(new Node(nodeWeight));
                }
            }
            nodeTree.get(0).get(0).setDistanceValue(0);
            this.tree = nodeTree;
        }

        public Node get(int row, int index) {
            return tree.get(row).get(index);
        }
    }

    private static class Node {
        private Integer coreWeight;
        private Integer distanceValue;

        public Node(Integer coreWeight) {
            this.coreWeight = coreWeight;
            this.distanceValue = Integer.MAX_VALUE;
        }


        public Integer getCoreWeight() {
            return coreWeight;
        }

        public Integer getDistanceValue() {
            return distanceValue;
        }

        public void setDistanceValue(Integer distanceValue) {
            this.distanceValue = distanceValue;
        }
    }

}
