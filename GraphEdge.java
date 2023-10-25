/**
 * Class representing an edge in an undirected graph
 */
public class GraphEdge implements GraphEdgeInterface {
    GraphNodeInterface pred;
    GraphNodeInterface succ;
    int weight;

    /**
     * Constructor for an edge
     * @param pred  the predecessor node the edge is connected to
     * @param succ  the successor node the edge is connected to
     * @param weight the weight of the edge
     */
    public GraphEdge(GraphNodeInterface pred, GraphNodeInterface succ, int weight){
        this.pred = pred;
        this.succ = succ;
        this.weight = weight;
    }

    /**
     * Getter for pred
     */
    public GraphNodeInterface getPred(){
        return pred;
    }
    
    /**
     * Getter for succ
     */
    public GraphNodeInterface getSucc(){
        return succ;
    }

    /**
     * Setter for pred
     */
    public void setPred(GraphNodeInterface pred){
        this.pred = pred;
    }

    /**
     * Setter for succ
     */
    public void setSucc(GraphNodeInterface succ){
        this.succ = succ;
    }

    /**
     * Getter for weight
     */
    public int getWeight(){
        return weight;
    }

    /**
     * Setter for weight
     */
    public void setWeight(int weight){
        this.weight = weight;
    }

    public String toString(){
        return pred.toString() + " -" + weight + "- " + succ.toString();
    }
}
