public class EdgeData<NodeType,EdgeType extends Number>{
    public NodeType from;
    public NodeType to;
    public EdgeType weight;
    public EdgeData(NodeType from, NodeType to, EdgeType weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

