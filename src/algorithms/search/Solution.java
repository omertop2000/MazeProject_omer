package algorithms.search;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Solution implements Serializable {
    private final ArrayList<AState> state_path;

    public Solution(Stack<AState> stackpath){
        this.state_path = new ArrayList<>();
        while(!stackpath.empty())
            state_path.add(stackpath.pop());
        Collections.reverse(state_path);
    }
    public Solution(Solution sol){
        this.state_path = new ArrayList<>(sol.state_path);
    }

    public ArrayList<AState> getSolutionPath(){
        return state_path;
    }
}
