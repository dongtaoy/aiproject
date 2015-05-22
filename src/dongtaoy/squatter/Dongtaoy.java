package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;
import aiproj.squatter.Player;
import javafx.util.Pair;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class Dongtaoy implements Player {

    private int piece;
    private Board board;
    static final int depth = 3;
    private ArrayList<Double> coefficients;


    public int init(int n, int piece) {
        this.board = new Board(n);
        this.piece = piece;
        this.coefficients = new ArrayList<Double>(){{
            add(100.0);
            add(50.0);
            add(20.0);
            add(10.0);
            add(5.0);
            add(-100.0);
            add(-50.0);
            add(-20.0);
            add(-10.0);
            add(-5.0);
        }};
        return 0;
    }

    public int init(int n, int piece, ArrayList<Double> coefficients){
        this.board = new Board(n);
        this.piece = piece;
        //[capture, connectivity, chain, safety cell, centralise]
        this.coefficients = coefficients;
        return 0;
    }

    public int getPiece() {
        return this.piece;
    }

    public int getOpponentPiece() {
        return piece == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
    }

    public ArrayList<Double> getCoefficients(){
        return this.coefficients;
    }

    public Move makeMove() {
        Cell cell = minimax(board, null, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true).getValue();
        Move move = new Move(this.piece, cell.getRow(), cell.getCol());
        board.placeCell(move);
        return move;
    }

    public int opponentMove(Move m) {
        return board.placeCell(m);
    }

    public int getWinner() {
        return this.board.testWin(this);
    }


    public void printBoard(PrintStream output) {
        output.print(board);
    }

    public Pair<Double, Cell> minimax(Board board, Cell move, int depth, Double alpha, Double beta, boolean isMax) {
        ArrayList<Cell> symmetricCells = board.getSymmetricCells();
        if (depth == 0 || symmetricCells.size() == 0) {
            return new Pair<>(board.evaluate(this), move);
        }
        if (isMax) {
            Pair<Double, Cell> bestValue = new Pair<>(Double.NEGATIVE_INFINITY, move);
            for (Cell cell : symmetricCells) {
                Board newBoard = new Board(board, cell, this.piece);
                Pair<Double, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, false);
                if (value.getKey() > bestValue.getKey()) {
                    bestValue = new Pair<>(value.getKey(), cell);
                    if (bestValue.getKey() > alpha)
                        alpha = bestValue.getKey();
                    if (beta <= alpha)
                        break;
                }
            }
            return bestValue;
        } else {
            Pair<Double, Cell> bestValue = new Pair<>(Double.POSITIVE_INFINITY, move);
            for (Cell cell : symmetricCells) {
                Board newBoard = new Board(board, cell, this.getOpponentPiece());
                Pair<Double, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, true);
                if (value.getKey() < bestValue.getKey()) {
                    bestValue = new Pair<>(value.getKey(), cell);
                    if (bestValue.getKey() < beta)
                        beta = bestValue.getKey();
                    if (beta <= alpha)
                        break;
                }
            }
            return bestValue;
        }
    }


    public String toString() {
        return new StringBuilder()
                .append("PLAYER: ")
                .append(piece)
                .append('\n').toString();
    }
}
