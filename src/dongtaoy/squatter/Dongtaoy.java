package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Player;

import java.io.PrintStream;


public class Dongtaoy implements Player {

    private int dimensions;
    private int piece;
    private int cellContent;


    public int init(int n, int p) {
        this.dimensions = n;
        this.piece = p;
        if(this.piece == 1)
            this.cellContent = 'W';
        else if(this.piece == 2)
            this.cellContent = 'B';
        else
            return -1;
        return 0;
    }

    public Move makeMove() {
        return new Move();
    }

    public int opponentMove(Move m) {
        return 0;
    }

    public int getWinner() {
        return 0;
    }


    public void printBoard(PrintStream output) {

    }
}
