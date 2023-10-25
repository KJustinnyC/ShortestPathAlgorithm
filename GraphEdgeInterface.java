public interface GraphEdgeInterface{
    public GraphNodeInterface getPred();
    public GraphNodeInterface getSucc();
    public void setPred(GraphNodeInterface pred);
    public void setSucc(GraphNodeInterface succ);
    public int getWeight();		  //rename depending on proposal
    public void setWeight(int weight); //rename depending on proposal
    public String toString();
}
    