import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ResidualGraph contains the data that represents the Residual graph
 * ResidualGraph is represented using list of nodes, Adjacency matrix to show how nodes are connected,
 * source and sink nodes and the bipartite matching
 * ResidualGraph provides functionality to generate LevelGraph using BFS, Augment any path and update the matching, print
 * the output matching
 * @author Ishpreet Talwar
 */
public class ResidualGraph {

    /**
     * Number of nodes
     */
    private int numberOfNodes;

    /**
     * List of nodes
     */
    private String[] nodes;

    /**
     * 2d adjacency matrix showing connection between nodes
     */
    private int adjacencyMatrix[][];

    /**
     * Index of source in the list of nodes
     */
    private int source;

    /**
     * Index of sink in the list of the nodes
     */
    private int sink;

    /**
     * Bipartite matching as a hashmap of x->y where x is on left
     * and y is on the right of bipartite matching
     */
    private HashMap<Integer, Integer> matching;

    /**
     * Getter for source
     * @return
     */
    public int getSource() {
        return source;
    }

    /**
     * Getter for sink
     * @return
     */
    public int getSink() {
        return sink;
    }

    /**
     * Prints matching as the output
     * Example:
     * Node1 / Node 2
     * Node3 / Node 4
     * 2 total matches
     */
    public void printMatching() {
        this.matching.forEach((key, value) -> System.out.println(this.getNode(key) + " / " + this.getNode(value)));
        System.out.println(this.matching.size() + " total matches");
    }

    /**
     * Initializes the Residual graph using the number of nodes
     * @param numberOfNodes: number of nodes
     * pre: numberOfNodes, adjacencyMatrix, nodes, source, sink, matching are all uninitialized
     * post: numberOfNodes is initialized as input numberOfNodes + 2
     *       adjacencyMatrix is initialized as 2d array of sizes numberOfNodes * numberOfNodes
     *       nodes is initialized as a String array of size numberOfNodes with 1st element as source and last as sink
     *       sink is initialized as numberOfNodes-1, source is initialized as 0
     *       matching is initialized as an empty hashmap
     */
    public ResidualGraph(int numberOfNodes) {
        // Input nodes + source + sink
        this.numberOfNodes = numberOfNodes + 2;
        this.adjacencyMatrix = new int[this.numberOfNodes][this.numberOfNodes];
        this.nodes = new String[this.numberOfNodes];
        // First node is source
        this.source = 0;
        // Last node is sink
        this.sink = this.numberOfNodes - 1;
        this.nodes[source] = "source";
        this.nodes[sink] = "sink";
        this.matching = new HashMap<>();
    }

    /**
     * Adds a node to the ResidualGraph
     * @param node : Name of the node, example Anibel
     * @param position : Position of the node, example : 1
     */
    public void addNode(String node, int position){
        nodes[position] = node;
    }

    /**
     * Adds an edge to the ResidualGraph by updating its adjacency matrix
     * @param start: Index of the start vertex
     * @param end: Index of the end vertex
     */
    public void addEdge(int start, int end){
        adjacencyMatrix[start][end] = 1;
    }

    /**
     * Connects the source and sink to the graph after the input edges are connected
     * pre: Residual graph with left and right sets of nodes connected. Source and sink are not connected to the graph
     * post: Sink is connected to all the nodes on the left
     *       Right set of nodes are connected to the sink
     */
    public void connectSourceAndSink(){
        // Connecting source to vertices on the left
        for (int i = 1; i < numberOfNodes / 2; i++) {
            adjacencyMatrix[source][i] = 1;
        }
        // Connecting vertices on the right to the sink
        for (int i = numberOfNodes / 2; i < numberOfNodes -1; i++) {
            adjacencyMatrix[i][sink] = 1;
        }
    }

    /**
     * Reverse edges on the Path from source to sink
     * @param path : A list of nodes representing the path from source to sink
     *             The first element is source and last element is sink
     * pre: Residual graph containing the path provided as input
     * post: Residual graph containing the revered input path from sink to source
     */
    public void reverseEdgesOnAPath(List<Integer> path){
        // Reversing the edge by removing current and creating opposite
        for (int i = 0;  i < path.size() - 1; i++) {
            int start = path.get(i);
            int end = path.get(i + 1);
            // Deleting the current edge
            adjacencyMatrix[start][end] = 0;
            // Creating the reverse edge
            adjacencyMatrix[end][start] = 1;
        }
    }

    /**
     * Creates a Level graph using the current state of Residual graph by applying BFS on it to find the
     * shortest path to each node from the source
     * @return
     */
    public LevelGraph createLevelGraph() {
        // Initializing the new adjacency matrix for the level graph
        int [][] lgAdjacencyMatrix = new int [numberOfNodes][numberOfNodes];

        // Initializing an array to store level of each node
        int level[] = new int[numberOfNodes];
        for(int i = 0; i < numberOfNodes; ++i)
            level[i] = -1;

        // Creating a queue, enqueue source vertex and update the level of source to 0
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(source);
        level[source] = 0;

        // Loop to traverse on the residual graph using BFS
        while (queue.size()!=0)
        {
            // Polling from the queue oto get the 1st element
            int current = queue.poll();

            // If current is sink, continue to next iteration
            if (current == sink) {
                continue;
            }

            for (int next = 1; next < numberOfNodes; next++)
            {
                // Visit the next node only if its level is unset (-1) or if it is in equal level as set before
                if ((level[next] == -1 || level[next] >= (level[current] + 1)) && adjacencyMatrix[current][next] > 0)
                {
                    // Adding next to the queue
                    queue.add(next);
                    // Setting the level to next
                    level[next] = level[current] + 1;

                    // Create path from current to next in level graph
                    lgAdjacencyMatrix[current][next] = 1;
                }
            }
        }

        // If level of sink is unset, this means path doesn't exists from source to sink and hence returning null
        if (level[sink] == -1)
               return null;

        // Initializing a new level graph using the nodes and the adjacency matrix
        LevelGraph levelGraph = new LevelGraph(numberOfNodes, lgAdjacencyMatrix);
        // Returning the level graph
        return levelGraph;
    }

    /**
     * Gets the name of the node using the node position
     * @param position
     * @return Name of node as string using the position of it from the list of nodes
     */
    private String getNode(int position) {
        return nodes[position];
    }

    /**
     * Augments the flow in the path. In case of bipartite matching, it creates or deletes the matching using
     * the path provided in the input to augment
     * @param path : List of nodes traversed from source to sink
     */
    public void augmentPath(LinkedList<Integer> path) {
        for (int i = 1;  i < path.size() - 1; i = i + 2) {
            int start = path.get(i);
            int end = path.get(i + 1);

            // Augmenting the flow from start to end which is represented by an entry in the hashmap
            this.matching.put(start, end);
        }
    }
}
