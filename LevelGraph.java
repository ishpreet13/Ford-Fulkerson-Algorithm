import java.util.*;

/**
 * LevelGraph contains the data that represents the Level graph in each phase of the Dinitz algorithm
 * LevelGraph is represented using list of nodes, Adjacency matrix to show how nodes are connected
 * LevelGraph provides functionality to get neighbours from the current node, delete edges on the path
 * and delete the node
 * @author Ishpreet Talwar
 */
public class LevelGraph {
    /**
     * Number of nodes
     */
    int numberOfNodes;
    /**
     * Adjacency matrix to represent the Level graph
     */
    int[][] adjacencyMatrix;
    /**
     * Initializes the level graph
     * @param inNumberOfNodes: Number of nodes
     * @param inAadjacencyMatrix: Adjacency matrix
     */
    public LevelGraph(int inNumberOfNodes, int[][] inAadjacencyMatrix){
        numberOfNodes = inNumberOfNodes;
        adjacencyMatrix = inAadjacencyMatrix;
    }
    /**
     * Gets all the connected nodes to a node
     * @param node: Number of the node in the level graph, example 1 (represents Anibel)
     * @return List of nodes(integers) connected to the node
     */
    public List<Integer> getNeighbours(int node){
        List<Integer> neighbours = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            if (adjacencyMatrix[node][i] == 1){
                neighbours.add(i);
            }
        }
        return neighbours;
    }
    /**
     * Deleted edges on the path by updating the adjacency matrix
     * @param path: A list of nodes representing a path
     * @pre: Levelgraph containing the edges along the path of nodes in the input - path
     * @post Levelgraph with edges removed along the path of nodes in the input - path
     */

    public void deleteEdgesOnAPath(List<Integer> path) {
        // Iterating over nodes in the path to delete the edges
        for (int i = 0; i < path.size() - 1; i++) {
            int start = path.get(i);
            int end = path.get(i + 1);
            adjacencyMatrix[start][end] = 0;
        }
    }
    /**
     * Deletes a node from the graph by removing all the incoming and outgoing edges
     * @param node : Number of the node in the level graph, example 1 (represents Anibel)
     * @pre: Levelgraph containing the edges to and from the node
     * @post: Levelgraph with removed edges to and from the node
     */
    public void deleteNode(int node) {
        // Remove connectivity to and from the node
        for (int i = 0; i < numberOfNodes; i++) {
            adjacencyMatrix[node][i] = 0;
            adjacencyMatrix[i][node] = 0;
        }
    }

}
