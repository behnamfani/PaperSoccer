import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bot {
    // Goal coordinates (opponent's goal)
    private final double jGoal = (PaperSoccer.m1Goal + PaperSoccer.m2Goal) / 2.0;
    private final int iGoal = 0; // Top row is the opponent's goal
    private final int iToop = PaperSoccer.itoop; // Ball's current row
    private final int jToop = PaperSoccer.jtoop; // Ball's current column
    private final Dot[][] map = PaperSoccer.map; // Game board

    public static int action = 0; // Stores the best move
    public static int minMaxCount = 0; // Limits recursion depth

    // Helper function to calculate squared distance
    private static double power(double a) {
        return a * a;
    }

    // Calculates the distance from the ball to the opponent's goal
    private double distance(int iBall, int jBall) {
        return power(iBall - iGoal) + power(jBall - jGoal);
    }

    /**
     * Minimax algorithm to determine the best move.
     * @param location Current position of the ball.
     * @param player Current player ("B" for bot, "A" for opponent).
     * @param iBall Row index of the ball.
     * @param jBall Column index of the ball.
     * @return Utility value of the best move.
     */
    public double miniMax(Dot location, String player, int iBall, int jBall) {
        minMaxCount++; // Increment recursion counter

        // Base case: Prevent infinite recursion
        if (minMaxCount > 30) {
            return distance(iBall, jBall);
        }

        // Base case: Ball reached the goal
        if (iBall == 0 || iBall == map.length - 1) {
            return 0;
        }

        // Initialize arrays to track forbidden moves, visited nodes, and distances
        int[] forbiddenNeighbors = new int[8];
        boolean[] visiteds = new boolean[8];
        double[] dists = new double[8];

        // Initialize arrays
        for (int i = 0; i < 8; i++) {
            forbiddenNeighbors[i] = 0;
            visiteds[i] = false;
            dists[i] = (player.equals("B")) ? 1e9 : -1e9; // MAX or MIN initialization
        }

        // Update forbidden neighbors based on available moves
        for (String move : TextArea.available) {
            switch (move) {
                case "1": forbiddenNeighbors[0] = 1; break; // Up-left
                case "2": forbiddenNeighbors[1] = 1; break; // Up
                case "3": forbiddenNeighbors[2] = 1; break; // Up-right
                case "4": forbiddenNeighbors[3] = 1; break; // Left
                case "5": forbiddenNeighbors[4] = 1; break; // Right
                case "6": forbiddenNeighbors[5] = 1; break; // Down-left
                case "7": forbiddenNeighbors[6] = 1; break; // Down
                case "8": forbiddenNeighbors[7] = 1; break; // Down-right
            }
        }

        // Check for a single available move
        int check = 0, checkA = 0;
        for (int w = 0; w < 8; w++) {
            if (forbiddenNeighbors[w] == 1) {
                check++;
                checkA = w;
            }
        }
        // If only one move is available, take it
        if (check == 1) {
            action = checkA;
            return distance(iBall, jBall);
        }

        // Edge case: Ball is on the side of the board
        if (jBall == 0 || jBall == map[0].length - 1) {
            for (int w = 0; w < 8; w++) {
                if (forbiddenNeighbors[w] == 1) {
                    action = w;
                    break;
                }
            }
            return distance(iBall, jBall);
        }

        // MAX turn (AI's turn): Minimize distance to goal
        if (player.equals("B")) {
            return maxTurn(forbiddenNeighbors, visiteds, dists, iBall, jBall);
        }
        // MIN turn (Player's turn): Maximize distance to goal
        else {
            return minTurn(forbiddenNeighbors, visiteds, dists, iBall, jBall);
        }
    }

    /**
     * Handles the AI's turn (MAX player).
     */
    private double maxTurn(int[] forbiddenNeighbors, boolean[] visiteds, double[] dists, int iBall, int jBall) {
        boolean meetOthers = true;
        int visitedCount = 0;

        // Check upper nodes
        for (int j = 0; j < 3; j++) {
            if (forbiddenNeighbors[j] == 1) {
                meetOthers = false;
                visiteds[j] = map[iBall - 1][jBall + j - 1].visited;
                if (visiteds[j]) visitedCount++;
                dists[j] = miniMax(map[iBall - 1][jBall + j - 1], "A", iBall - 1, jBall + j - 1);
            }
        }

        // If upper nodes are available, evaluate them
        if (!meetOthers) {
            return handleUpperNodesMax(dists, visiteds, visitedCount);
        }

        // Check side nodes (left and right)
        meetOthers = true;
        if (forbiddenNeighbors[3] == 1 || forbiddenNeighbors[4] == 1) {
            meetOthers = false;
            return handleSideNodesMax(forbiddenNeighbors, visiteds, iBall, jBall);
        }

        // Check down nodes
        if (meetOthers) {
            return handleDownNodesMax(forbiddenNeighbors, visiteds, iBall, jBall);
        }

        return distance(iBall, jBall);
    }

    /**
     * Handles the player's turn (MIN player).
     */
    private double minTurn(int[] forbiddenNeighbors, boolean[] visiteds, double[] dists, int iBall, int jBall) {
        boolean meetOthers = true;
        int visitedCount = 0;

        // Check upper nodes
        for (int j = 0; j < 3; j++) {
            if (forbiddenNeighbors[j] == 1) {
                meetOthers = false;
                visiteds[j] = map[iBall - 1][jBall + j - 1].visited;
                if (visiteds[j]) visitedCount++;
                dists[j] = miniMax(map[iBall - 1][jBall + j - 1], "B", iBall - 1, jBall + j - 1);
            }
        }

        // If upper nodes are available, evaluate them
        if (!meetOthers) {
            return handleUpperNodesMin(dists, visiteds, visitedCount);
        }

        // Check side nodes (left and right)
        meetOthers = true;
        if (forbiddenNeighbors[3] == 1 || forbiddenNeighbors[4] == 1) {
            meetOthers = false;
            return handleSideNodesMin(forbiddenNeighbors, visiteds, iBall, jBall);
        }

        // Check down nodes
        if (meetOthers) {
            return handleDownNodesMin(forbiddenNeighbors, visiteds, iBall, jBall);
        }

        return distance(iBall, jBall);
    }

    /**
     * Handles upper nodes for MAX player.
     */
    private double handleUpperNodesMax(double[] dists, boolean[] visiteds, int visitedCount) {
        if (visitedCount == 3 || visitedCount == 0) {
            // Choose the move with the minimum distance
            double minimum = 1e9;
            for (int k = 0; k < 3; k++) {
                if (dists[k] < minimum) {
                    minimum = dists[k];
                    action = k;
                }
            }
            return minimum;
        } else if (visitedCount == 1) {
            // Choose the visited node
            for (int k = 0; k <= 2; k++) {
                if (visiteds[k]) {
                    action = k;
                    return dists[k];
                }
            }
        } else if (visitedCount == 2) {
            // Handle cases where two nodes are visited
            return handleTwoVisitedMax(dists, visiteds);
        }
        return 0;
    }

    /**
     * Handles side nodes for MAX player.
     */
    private double handleSideNodesMax(int[] forbiddenNeighbors, boolean[] visiteds, int iBall, int jBall) {
        if (forbiddenNeighbors[3] == 1 && forbiddenNeighbors[4] == 1) {
            if (visiteds[3] && visiteds[4] || !visiteds[3] && !visiteds[4]) {
                // Compare left and right moves
                double v1 = miniMax(map[iBall][jBall + 1], "A", iBall, jBall + 1);
                double v2 = miniMax(map[iBall][jBall - 1], "A", iBall, jBall - 1);
                if (v1 < v2) {
                    action = 4;
                    return v1;
                } else {
                    action = 3;
                    return v2;
                }
            } else if (visiteds[3] && !visiteds[4]) {
                // Choose left move
                action = 3;
                return miniMax(map[iBall][jBall - 1], "A", iBall, jBall - 1);
            } else if (!visiteds[3] && visiteds[4]) {
                // Choose right move
                action = 4;
                return miniMax(map[iBall][jBall + 1], "A", iBall, jBall + 1);
            }
        }
        return distance(iBall, jBall);
    }

    /**
     * Handles down nodes for MAX player.
     */
    private double handleDownNodesMax(int[] forbiddenNeighbors, boolean[] visiteds, int iBall, int jBall) {
        if (forbiddenNeighbors[5] == 0 && forbiddenNeighbors[6] == 0 && forbiddenNeighbors[7] == 0) {
            // Default move: down-left
            action = 5;
            return distance(iBall - 1, jBall - 1);
        } else {
            // Choose the first available down move
            for (int k = 5; k < 8; k++) {
                if (forbiddenNeighbors[k] == 1 && visiteds[k]) {
                    action = k;
                    return distance(iBall + 1, jBall + 1);
                }
            }
            for (int k = 5; k < 8; k++) {
                if (forbiddenNeighbors[k] == 1) {
                    action = k;
                    return distance(iBall + 1, jBall + 1);
                }
            }
        }
        return distance(iBall, jBall);
    }

    /**
     * Handles upper nodes for MIN player.
     */
    private double handleUpperNodesMin(double[] dists, boolean[] visiteds, int visitedCount) {
        if (visitedCount == 3 || visitedCount == 0) {
            // Choose the move with the maximum distance
            double maximum = -1e9;
            for (int k = 0; k < 3; k++) {
                if (dists[k] > maximum) {
                    maximum = dists[k];
                    action = k;
                }
            }
            return maximum;
        } else if (visitedCount == 1) {
            // Choose the visited node
            for (int k = 0; k <= 2; k++) {
                if (visiteds[k]) {
                    action = k;
                    return dists[k];
                }
            }
        } else if (visitedCount == 2) {
            // Handle cases where two nodes are visited
            return handleTwoVisitedMin(dists, visiteds);
        }
        return 0;
    }

    /**
     * Handles side nodes for MIN player.
     */
    private double handleSideNodesMin(int[] forbiddenNeighbors, boolean[] visiteds, int iBall, int jBall) {
        if (forbiddenNeighbors[3] == 1 && forbiddenNeighbors[4] == 1) {
            if (visiteds[3] && visiteds[4] || !visiteds[3] && !visiteds[4]) {
                // Compare left and right moves
                double v1 = miniMax(map[iBall][jBall + 1], "B", iBall, jBall + 1);
                double v2 = miniMax(map[iBall][jBall - 1], "B", iBall, jBall - 1);
                if (v1 > v2) {
                    action = 4;
                    return v1;
                } else {
                    action = 3;
                    return v2;
                }
            } else if (visiteds[3] && !visiteds[4]) {
                // Choose left move
                action = 3;
                return miniMax(map[iBall][jBall - 1], "B", iBall, jBall - 1);
            } else if (!visiteds[3] && visiteds[4]) {
                // Choose right move
                action = 4;
                return miniMax(map[iBall][jBall + 1], "B", iBall, jBall + 1);
            }
        }
        return distance(iBall, jBall);
    }

    /**
     * Handles down nodes for MIN player.
     */
    private double handleDownNodesMin(int[] forbiddenNeighbors, boolean[] visiteds, int iBall, int jBall) {
        if (forbiddenNeighbors[5] == 0 && forbiddenNeighbors[6] == 0 && forbiddenNeighbors[7] == 0) {
            // Default move: down-left
            action = 5;
            return distance(iBall - 1, jBall - 1);
        } else {
            // Choose the first available down move
            for (int k = 5; k < 8; k++) {
                if (forbiddenNeighbors[k] == 1 && visiteds[k]) {
                    action = k;
                    return distance(iBall + 1, jBall + 1);
                }
            }
            for (int k = 5; k < 8; k++) {
                if (forbiddenNeighbors[k] == 1) {
                    action = k;
                    return distance(iBall + 1, jBall + 1);
                }
            }
        }
        return distance(iBall, jBall);
    }

    /**
     * Handles cases where two upper nodes are visited for MAX player.
     */
    private double handleTwoVisitedMax(double[] dists, boolean[] visiteds) {
        if (visiteds[0] && visiteds[1] && !visiteds[2]) {
            if (dists[0] < dists[1]) {
                action = 0;
                return dists[0];
            } else {
                action = 1;
                return dists[1];
            }
        } else if (!visiteds[0] && visiteds[1] && visiteds[2]) {
            if (dists[1] < dists[2]) {
                action = 1;
                return dists[1];
            } else {
                action = 2;
                return dists[2];
            }
        } else if (visiteds[0] && !visiteds[1] && visiteds[2]) {
            if (dists[0] < dists[2]) {
                action = 0;
                return dists[0];
            } else {
                action = 2;
                return dists[2];
            }
        }
        return 0;
    }

    /**
     * Handles cases where two upper nodes are visited for MIN player.
     */
    private double handleTwoVisitedMin(double[] dists, boolean[] visiteds) {
        if (visiteds[0] && visiteds[1] && !visiteds[2]) {
            if (dists[0] > dists[1]) {
                action = 0;
                return dists[0];
            } else {
                action = 1;
                return dists[1];
            }
        } else if (!visiteds[0] && visiteds[1] && visiteds[2]) {
            if (dists[1] > dists[2]) {
                action = 1;
                return dists[1];
            } else {
                action = 2;
                return dists[2];
            }
        } else if (visiteds[0] && !visiteds[1] && visiteds[2]) {
            if (dists[0] > dists[2]) {
                action = 0;
                return dists[0];
            } else {
                action = 2;
                return dists[2];
            }
        }
        return 0;
    }
}
