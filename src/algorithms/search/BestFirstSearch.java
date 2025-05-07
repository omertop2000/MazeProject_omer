package algorithms.search;

public class BestFirstSearch extends BreadthFirstSearch{

    public Solution solve(ISearchable domain){
        if (domain == null)
            throw new IllegalArgumentException ("domain is null");
        domain = domain.get_copy();
        domain.set_diagonal_value(15);
        return super.solve(domain);
    }

    public String getName(){
        return "BestFirstSearch";
    }

}

