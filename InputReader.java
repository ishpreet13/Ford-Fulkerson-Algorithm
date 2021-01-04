import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * InputReader is a class with static methods that reads the input data and creates
 * the residual graph for running Ford-Fulkerson algorithm using the shortest augmenting paths
 * method for bipartite matching
 *
 * @author Ishpreet Talwar
 */
public class InputReader {

    /**
     * @param fileName Name of the file that contains the program input data.
     *                 The file should be present in the same folder as the code directory
     * @return An object of type ResidualGraph which represents the graph using adjacency matrix
     * @throws FileNotFoundException
     */
    public static ResidualGraph getResidualGraph(String fileName) throws FileNotFoundException {
        List<String> fileAsListOfString = readFileAsListOfString(fileName);
        // Getting number of vertices
        int numberOfVertices = Integer.parseInt(fileAsListOfString.get(0));

        // Initializing the residual graph
        ResidualGraph residualGraph = new ResidualGraph(numberOfVertices);

        // Storing nodes names in the residual graph class
        for (int i = 0; i < numberOfVertices; i++) {
            residualGraph.addNode(fileAsListOfString.get(i + 1).strip(), i + 1);
        }

        // Getting number of edges
        int numEdges = Integer.parseInt(fileAsListOfString.get(numberOfVertices + 1));

        // Adding edges to the residualGraph
        for (int i = 0; i < numEdges; i++) {
            String[] edgeVertices = fileAsListOfString.get(i + numberOfVertices + 2).strip().split("\\s* \\s*");
            int start = Integer.parseInt(edgeVertices[0]);
            int end = Integer.parseInt(edgeVertices[1]);
            residualGraph.addEdge(start, end);
        }

        // Connecting source to vertices on left and vertices on right to sink
        residualGraph.connectSourceAndSink();

        // Returning the residual graph
        return residualGraph;
    }

    /**
     * @param fileName Name of the file that contains the program input data.
     * @return The data in the file "filename" as list of strings
     * @throws FileNotFoundException
     */
    private static List<String> readFileAsListOfString(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        // Initializing the list to store lines of the input file
        List<String> fileAsListOfString = new ArrayList<String>();
        Scanner scan = new Scanner(file);
        // Reading each line of the file
        while (scan.hasNextLine()) {
            fileAsListOfString.add(scan.nextLine());
        }
        return fileAsListOfString;
    }
}
