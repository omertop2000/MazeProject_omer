package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void testInheritance() {
        BestFirstSearch bfs = new BestFirstSearch();
        System.out.println("Testing inheritance: Checking if BestFirstSearch is an instance of BreadthFirstSearch");
        assertInstanceOf(BreadthFirstSearch.class, bfs);
        System.out.println("Inheritance test passed: BestFirstSearch is an instance of BreadthFirstSearch");
    }

    @Test
    void testSolveWithNullDomain() {
        BestFirstSearch bfs = new BestFirstSearch();
        System.out.println("Testing null: Checking if it handles null domain");
        assertThrows(IllegalArgumentException.class, () -> bfs.solve(null));
        System.out.println("Testing null passed: test null has passed");

    }

    @Test
    void testSuperclassSolveMethodCall() {
        System.out.println("Testing null after solution: checking if the return is not null after solution");

        Maze maze = new MyMazeGenerator().generate(10,10);
        SearchableMaze domain = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(domain);
        assertNotNull(solution);
        System.out.println("Testing null after solution passed: test passed");
    }

    @Test
    void testEfficiency() {
        System.out.println("Testing efficiency of best: checking if the solution path is shorter");

        Maze maze = new MyMazeGenerator().generate(100, 100);


        SearchableMaze originalDomain = new SearchableMaze(maze);

        BestFirstSearch best = new BestFirstSearch();
        DepthFirstSearch dfs = new DepthFirstSearch();
        BreadthFirstSearch bfs2 = new BreadthFirstSearch();
        Solution solution1 = best.solve(originalDomain);  //using BestFirst
        Solution solution2 = bfs2.solve(originalDomain);  //using BreadthFirst
        Solution solution3 = dfs.solve(originalDomain);

        assertTrue(solution1.getSolutionPath().size() <= solution2.getSolutionPath().size() &&
                solution1.getSolutionPath().size() <= solution3.getSolutionPath().size());
        System.out.println("Testing efficiency of best passed: test passed");
    }

    @Test
    void testUnderMinute() {
        System.out.println("Testing time to solve: checking if the solution takes under a minute");

        Maze maze = new MyMazeGenerator().generate(1000, 1000);
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        SearchableMaze originalDomain = new SearchableMaze(maze);

        long start = System.currentTimeMillis();
        bfs.solve(originalDomain);
        long end = System.currentTimeMillis();

        long duration = end - start;
        System.out.println("Duration: " + duration + " milliseconds");

        assertTrue(duration < 60000, "BreadthFirstSearch should solve the maze within a minute");
        System.out.println("Test Passed");
    }

    @Test
    void testSolveBadArgumentsInMaze() {
        MyMazeGenerator generator = new MyMazeGenerator();
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        // Test negative rows
        Exception exceptionRows = assertThrows(IllegalArgumentException.class, () -> {
            bfs.solve(new SearchableMaze(generator.generate(-1, 100)));
        });
        assertEquals("Invalid maze dimensions", exceptionRows.getMessage());

        // Test negative columns
        Exception exceptionCols = assertThrows(IllegalArgumentException.class, () -> {
            bfs.solve(new SearchableMaze(generator.generate(100, -1)));
        });
        assertEquals("Invalid maze dimensions", exceptionCols.getMessage());

        // Test both dimensions negative
        Exception exceptionBoth = assertThrows(IllegalArgumentException.class, () -> {
            bfs.solve(new SearchableMaze(generator.generate(-1, -1)));
        });
        assertEquals("Invalid maze dimensions", exceptionBoth.getMessage());
        // Test null maze
    }

}

