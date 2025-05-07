package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm{

    public Solution solve(ISearchable domain){
        if (domain == null)
            throw new IllegalArgumentException ("domain is null");
        domain = domain.get_copy();
        int nodes_evaluated = 1;
        AState start_point = domain.get_start_point();
        AState goal_point = domain.get_goal_point();
        start_point.setColor("grey");
        PriorityQueue<AState> minHeap = new PriorityQueue<>();
        AState cur_point = start_point;
        ArrayList<AState> neighbours;
        int cur_x,cur_y,neighbour_x,neighbour_y;
        boolean diagonal;
        while(!cur_point.equals(goal_point)){
            cur_x = cur_point.getRow_pos();
            cur_y = cur_point.getCol_pos();
            neighbours = cur_point.getNeighbours();
            for(AState neighbour : neighbours){ // traversing on neighbours of current state
                neighbour_x = neighbour.getRow_pos();
                neighbour_y = neighbour.getCol_pos();
                diagonal = is_diagonal(cur_x,cur_y,neighbour_x,neighbour_y);
                if(neighbour.getColor().equals("white")){
                    neighbour.setColor("grey");
                    neighbour.setPrevious(cur_point);
                    if(diagonal)
                        neighbour.setValue(cur_point.getValue()+domain.get_diagonal_value());
                    else
                        neighbour.setValue(cur_point.getValue()+10);
                    minHeap.add(neighbour);
                }
            }
            cur_point = minHeap.poll();
            nodes_evaluated++;
        }
        this.setNumber_of_nodes(nodes_evaluated);
        return new Solution(recreate_path(goal_point));
    }


    public Stack<AState> recreate_path(AState goal_point){
        AState cur_point = goal_point;
        Stack<AState> pathstack = new Stack<>();
        while(cur_point != null){
            pathstack.push(cur_point);
            cur_point = cur_point.getPrevious();
        }
        Collections.reverse(pathstack);
        return pathstack;
    }
    public boolean is_diagonal(int cur_x, int cur_y,int neighbour_x, int neighbour_y){
        int num1 = (cur_x - neighbour_x);
        num1 = num1 * num1;
        int num2 = (cur_y -neighbour_y);
        num2 = num2 * num2;
        return (num2 + num1 > 1);
    }

    public String getName(){
        return "BreadthFirstSearch";
    }
}
