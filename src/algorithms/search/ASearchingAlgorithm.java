package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    private int number_of_nodes;

    public ASearchingAlgorithm(){
        this.number_of_nodes = 0;
    }

    public void setNumber_of_nodes(int number_of_nodes) {
        this.number_of_nodes = number_of_nodes;
    }

    public int getNumberOfNodesEvaluated(){
        return this.number_of_nodes;
    }


}
