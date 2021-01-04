import java.io.FileNotFoundException;

/**
 * The main class of Program3 to run Ford-Fulkerson algorithm using the shortest augmenting paths method for bipartite matching
 * @author Ishpreet Talwar
 */
public class Program3 {
    public static void main(String[] args) throws FileNotFoundException {
        // Reading input and creating a residual graph using the program3data.text file
        ResidualGraph residualGraph = InputReader.getResidualGraph("program3data4.txt");
        // Running Ford-Fulkerson algorithm on the residual graph above to find the maximum bipartite matching
        FordFulkerson.runAlgorithm(residualGraph);
    }
}
