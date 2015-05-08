package dongtaoy.squatter;

import aiproj.squatter.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dongtao on 3/25/2015.
 */


public class Board {

    private int dimension;
    private Cell[][] cells;

    /**
     * Create Board object
     *
     * @param contents board layout in char[][] format
     */
    public Board(char[][] contents) {
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell(contents[i][j], i, j);
    }

    public Board(int dimension) {
        this.dimension = dimension;
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell('0', i, j);
    }

    public Board(Board board, Cell cell, char player) {
        this.dimension = board.getDimension();
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (board.getCells()[i][j].equals(cell))
                    this.cells[i][j] = new Cell(player, i, j);
                else
                    this.cells[i][j] = new Cell(board.getCells()[i][j].getContent(), i, j);
            }
        }
    }

    /**
     * Check win for this board
     */
    public void checkWin() {
        int whiteCaptured = 0;
        int blackCaptured = 0;
        boolean isFinished = true;

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                // if cell is captured
                if (cells[i][j].getContent() == '-') {
                    // if the previous cell is captured
                    if (cells[i][j - 1].getContent() != '-')
                        cells[i][j].setCapturedBy(cells[i][j - 1].getContent());
                    else
                        cells[i][j].setCapturedBy(cells[i][j - 1].getCapturedBy());

                    // increment counter
                    if (cells[i][j].getCapturedBy() == 'W')
                        whiteCaptured++;
                    else
                        blackCaptured++;
                } else if (cells[i][j].getContent() == '+') {
                    // if there is any '+' in cell, game is not finished
                    isFinished = false;
                }
            }
        }
        // if finished print who wins or a draw
        if (isFinished)
            System.out.println(blackCaptured > whiteCaptured ? "Black" : (blackCaptured == whiteCaptured ? "Draw" : "White"));
        else
            System.out.println("None");
        System.out.println(whiteCaptured);
        System.out.println(blackCaptured);

    }

    /**
     * toString function for Board
     *
     * @return String representation of a board
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] row : this.cells) {
            for (Cell c : row) {
                stringBuilder.append(c.getContent());
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int placeCell(Move move) {
        cells[move.Row][move.Col] = new Cell(move.P == 1 ? 'W' : 'B', move.Row, move.Col);
        return 1;
    }


    public ArrayList<Cell> getEmptyCell() {
        ArrayList<Cell> value = new ArrayList<>();
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (cells[i][j].isEmpty())
                    value.add(cells[i][j]);
        return value;
    }

    public Board verticalFlip() {
        char[][] contents = new char[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[i][this.dimension - j - 1].getContent();
        return new Board(contents);
    }

    public Board horizontalFlip() {
        char[][] contents = new char[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[this.dimension - i - 1][j].getContent();
        return new Board(contents);
    }

    public Board transposeDown() {
        char[][] contents = new char[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[j][i].getContent();
        return new Board(contents);
    }

    public Board transposeUp() {
        return this.transposeDown().horizontalFlip().verticalFlip();
    }

    public ArrayList<Cell> getAvaliableCells() {
        ArrayList<Cell> values = this.getEmptyCell();
        ArrayList<Cell> temp = new ArrayList<>();
        int x;
        if (this.dimension % 2 == 0) {
            x = this.dimension / 2;
        } else {
            x = this.dimension / 2 + 1;
        }
        if (this.equals(this.horizontalFlip())) {
            temp.clear();
            for (int i = 0; i < (x); i++)
                for (int j = 0; j < this.dimension; j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.verticalFlip())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j < (x); j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.transposeDown())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j <= i; j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.transposeUp())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j <= (this.dimension - i - 1); j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        return values;
    }


    public boolean equals(Object object) {
        Board board = (Board) object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (this.cells[i][j].getContent() != board.getCells()[i][j].getContent())
                    return false;
        return true;
    }


    public int getDimension() {
        return dimension;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
