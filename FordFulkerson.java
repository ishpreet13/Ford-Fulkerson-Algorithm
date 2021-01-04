import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * FordFulkerson is a class that contains the static methods which drive the Ford-Fulkerson algorithm
 * using the shortest augmenting paths method for bipartite matching - Dinitz algorithm
 */
public class FordFulkerson {
    /**
     * Public static method called from the main class that runs the Dinitz algorithm on the residual
     * graph in phases. In each phase, level graph is created using the residual graph containing paths from source to sink.
     * The algorithm is complete when there doesn't exists any path from source to sink in the residual graph.
     *
     * @param residualGraph An object of type ResidualGraph which represents the residual graph using adjacency matrix
     */
    public static void runAlgorithm(ResidualGraph residualGraph) {

        // Iterating over different phases until there exists a path from source to sink
        while (true) {
            // Creating a level graph using BFS on residual graph. Returns null when no path exists from source to sink
            LevelGraph levelGraph = residualGraph.createLevelGraph();

            // When level graph is null, it means no path exists from source to sink. Hence the loop
            // should break and the result should be printed
            if (levelGraph == null) {
                break;
            }

            // Initializing location to source node and path to an empty list.
            int currentLocation = residualGraph.getSource();
            LinkedList<Integer> path = new LinkedList<>();

            // Recursively traversing on current level graph until there exists a path from source to sink.
            traverse(currentLocation, path, residualGraph, levelGraph);
        }

        // Printing bipartite matching result
        residualGraph.printMatching();

    }

    /**
     * Recursive DFS in the level graph until there is a path out of source. Once a path from source to
     * sink is found, the flow is augmented and the level and residual graphs are updated.
     * If the current node is not sink, traversal advances when there is a path forward from the current node
     * and retreats in case there is no path forward. In case of retreating, the level graph is updated by deleting the node.
     *
     * @param currentNode   : Current node in the recursive traversal
     * @param currentPath   : LinkedList of previously visited nodes (in order) to get to the current path
     * @param residualGraph : Current object of residual graph
     * @param levelGraph    : Current object of Level graph created using BFS on residual graph
     */
    private static void traverse(int currentNode, LinkedList<Integer> currentPath, ResidualGraph residualGraph, LevelGraph levelGraph) {

        // Assigning source and sink locally to reuse in this method
        int source = residualGraph.getSource();
        int sink = residualGraph.getSink();

        // Remove this later
        //System.out.println(currentPath + "...>" + currentNode);

        // Adding current node to the path as it is being visited
        currentPath.add(currentNode);

        // If the location is sink, path needs to be augmented with updates in residual graph and level graph
        if (currentNode == sink) {
            // Augmenting flow with path: Updating the bipartite matching
            residualGraph.augmentPath(currentPath);

            // Updating residual graph by reversing the edges in the path
            residualGraph.reverseEdgesOnAPath(currentPath);

            // Deleting edges from level graph in the path
            levelGraph.deleteEdgesOnAPath(currentPath);

            // Setting location to source and resetting the path to an empty list
            traverse(source, new LinkedList<>(), residualGraph, levelGraph);
        }
        // If current location is not sink
        else {
            // Getting neighbours from the current node
            List<Integer> neighbours = levelGraph.getNeighbours(currentNode);

            // Breaking the traversal phase if there is no path out of source
            if (currentNode == source && neighbours.size() == 0) {
                return;
            }

            // Retreating, when the traversal is stuck at a node
            else if (neighbours.size() == 0) {
                // Deleting current node and incoming edges from the level graph
                levelGraph.deleteNode(currentNode);

                // Deleting the last node from path
                currentPath.removeLast(); // Takes O(1) time

                // Start traversing again from the previous node in the current path
                int previousNode = currentPath.removeLast();
                traverse(previousNode, currentPath, residualGraph, levelGraph);
            }
            // Advancing along the first neighbour in the level graph from the current location
            else {
                traverse(neighbours.get(0), currentPath, residualGraph, levelGraph);
            }
        }
    }
}



