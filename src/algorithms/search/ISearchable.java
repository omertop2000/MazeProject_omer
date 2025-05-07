package algorithms.search;

public interface ISearchable {

    public AState get_start_point();

    public AState get_goal_point();

    public int get_diagonal_value();

    public void set_diagonal_value(int diagonal_value);

    public ISearchable get_copy();
}
