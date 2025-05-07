package algorithms.search;
import java.util.ArrayList;
import java.util.Stack;
public class DepthFirstSearch extends ASearchingAlgorithm{

    public Solution solve(ISearchable domain) {
        if (domain == null)
            throw new IllegalArgumentException ("domain is null");
        domain = domain.get_copy();
        AState start_point = domain.get_start_point();
        AState goal_point = domain.get_goal_point();
        Stack<AState> pathStack = new Stack<>();
        pathStack.push(start_point);
        start_point.setColor("grey");
        boolean not_single_white;
        int nodes_evaluated = 1;
        AState cur_point = start_point;
        while(!cur_point.equals(goal_point)){
            not_single_white = true;
            ArrayList<AState> neighbours = cur_point.getNeighbours();
            for(AState neighbour : neighbours){
                if(neighbour.getColor().equals("white")){
                    not_single_white = false;
                    neighbour.setColor("grey");
                    pathStack.push(neighbour);
                    nodes_evaluated++;
                    cur_point = pathStack.peek();
                    break;
                }
            }
            if(not_single_white){
                cur_point.setColor("black");
                pathStack.pop();
                cur_point = pathStack.peek();
            }
        }
        this.setNumber_of_nodes(nodes_evaluated);
        return new Solution(pathStack);
    }

    public String getName(){
        return "DepthFirstSearch";
    }
}
