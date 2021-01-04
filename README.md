# Network flow for bipartite matching

This program implements the Ford-Fulkerson algorithm using the shortest augmenting paths method for bipartite matching.While noting that the problem is specifically for bipartite matching (each edge and reverse edge will have capacity 0 or 1). Output the pairs matched in some maximal matching of the input (there could be more than one, but you just need to output one). 
The input file will be “program3data.txt”. 
The first line will hold the number of nodes n, which will be even. The next n/2 lines will store the names of items to be matched in the “left” set. Then, n/2 further lines will store the names of items to be matching in the “right” set. Next, the number of edges will be stored and following that will be the edges (one per line) indexing the nodes using 1-based indexing.

Here is a summary of the algorithm:

Read data from file into Graph G.
Create residual graph Gf by adding source and sink nodes and edges from source and to sink.
Initialize flow f to zero along each edge.
While not done:
	Construct level graph LG from Gf using breadth-first search (delete back and cross edges).
	If no path exists from source to sink (i.e., sink not found during BFS), output matching, done.
	Initialize location to source node, path to empty.
	While not stuck at source:
		If location is sink:
			Augment flow with path.
			Update Gf.
			Delete edges in path from LG.
			Set location to source.
			Clear path.
		Else:
			If stuck, retreat:
				Delete current node and incoming edges from LG.
				Delete last edge from path.
			Else:
				Advance along some edge in LG that leaves current location.
				Update current path.
