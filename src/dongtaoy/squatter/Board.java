package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by dongtao on 3/25/2015.
 */


public class Board {

    private int dimension;
    private Cell[][] cells;
    private boolean DEBUG = false;

    /**
     * Create Board object
     * ONLY FOR TEST PURPOSE THIS FUNCTION MIGHT CRASH
     * !!! CAPTUREDBY IN CELL IS NOT INITIALIZED
     *
     * @param contents board layout in char[][] format
     */
    public Board(char[][] contents) {
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (contents[i][j] == 'B')
                    cells[i][j] = new Cell(Piece.BLACK, this, i, j);
                else if (contents[i][j] == 'W')
                    cells[i][j] = new Cell(Piece.WHITE, this, i, j);
                else if (contents[i][j] == '+')
                    cells[i][j] = new Cell(Piece.EMPTY, this, i, j);
                else if (contents[i][j] == '-')
                    cells[i][j] = new Cell(Piece.DEAD, this, i, j);
//        this.findCapturedCells();

    }

    /**
     * Create Board object
     * ONLY FOR TESTING SYMMETRIC BOARD OTHERWISE MIGHT CRASH
     * !!! CAPTUREDBY IN CELL IS NOT COPIED
     *
     * @param contents
     */
    public Board(int[][] contents) {
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell(contents[i][j], this, i, j);
//        this.findCapturedCells();
    }

    /**
     * Create a Board object
     * Used in initializing empty board
     *
     * @param dimension dimension of a board
     */
    public Board(int dimension) {
        this.dimension = dimension;
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell(Piece.EMPTY, this, i, j, Cell.CaptureType.NOT_CAPTURED);
    }


    /**
     * Create a deep copy of a Board object
     * Used in minimax algorithm
     *
     * @param board
     * @param cell
     * @param piece
     */
    public Board(Board board, Cell cell, int piece) {
        this.dimension = board.getDimension();
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (board.getCells()[i][j].equals(cell))
                    this.cells[i][j] = new Cell(piece, this, i, j, Cell.CaptureType.NOT_CAPTURED);
                else
                    this.cells[i][j] = new Cell(board.getCells()[i][j].getPiece(), this, i, j, board.getCells()[i][j].getCapturedBy());
            }
        }
        this.findCapturedCells();
    }

    public int placeCell(Move move) {
        cells[move.Row][move.Col] = new Cell(move.P, this, move.Row, move.Col);
        this.findCapturedCells();
        return 1;
    }

    public void findCapturedCells() {
        HashSet<Cell.Direction> validDirection = new HashSet<Cell.Direction>() {{
            add(Cell.Direction.TOPMIDDLE);
            add(Cell.Direction.BOTTOMMIDDLE);
            add(Cell.Direction.MIDDLELEFT);
            add(Cell.Direction.MIDDLERIGHT);
        }};
        for (int i = 1; i < this.dimension - 1; i++) {
            for (int j = 1; j < this.dimension - 1; j++) {
                Cell currentCell = this.cells[i][j];
                if (currentCell.getPiece() != Piece.DEAD) {
                    Integer[] array = new Integer[]{Piece.WHITE, Piece.BLACK, Piece.DEAD, Piece.EMPTY};
                    HashSet<Integer> validList = new HashSet<>(Arrays.asList(array));
                    HashSet<Cell> visited = new HashSet<>();
                    if (DEBUG) {
                        System.out.printf("\n\ncurrent: (%d, %d)\n", currentCell.getRow(), currentCell.getCol());
                    }

                    if (currentCell.getPiece() == Piece.EMPTY) {
                        HashSet<Cell> surroundedCells = currentCell.getCellsBy(new HashSet<Cell>(), validList, validDirection);
                        boolean isBlack = false, isWhite = false;
                        if (DEBUG) {
                            System.out.println("Empty cell surrounded by: " + surroundedCells);
                        }
                        for (Cell cell : surroundedCells) {
                            if (cell.getPiece() == Piece.BLACK)
                                isBlack = true;
                            if (cell.getPiece() == Piece.WHITE)
                                isWhite = true;
                        }
                        if (!(isBlack && isWhite)) {
                            if (isBlack) {
                                validList.remove(Piece.BLACK);
                            }
                            if (isWhite) {
                                validList.remove(Piece.WHITE);
                            }
                            if (!isEnclosed(visited, currentCell, validList, validDirection)) {
                                for (Cell cell : visited) {
                                    if (isBlack) {
                                        cell.capturedBy(Piece.BLACK);
                                    } else {
                                        cell.capturedBy(Piece.WHITE);
                                    }
                                }
                            }

                        }

                    } else {
                        int piece = currentCell.getPiece();
                        switch (this.cells[i][j].getPiece()) {
                            case Piece.BLACK:
                                validList.remove(Piece.WHITE);
                                break;
                            case Piece.WHITE:
                                validList.remove(Piece.BLACK);
                                break;
                        }
                        if (!isEnclosed(visited, currentCell, validList, validDirection)) {
                            for (Cell cell : visited) {
                                if (piece == Piece.WHITE) {
                                    cell.capturedBy(Piece.BLACK);
                                } else {
                                    cell.capturedBy(Piece.WHITE);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private boolean isEnclosed(HashSet<Cell> visited, Cell current, HashSet<Integer> validList, HashSet<Cell.Direction> validDirection) {

        if (current.isOnBorder())
            return true;
        visited.add(current);
        Collection<Cell> surroundedCells = current.getCellsBy(visited, validList, validDirection);
        if (DEBUG) {
            System.out.println("in findPathToBorder");
            System.out.println("\tcurrent" + current.getCoordinates() + ": " + current);
        }

        for (Cell cell : surroundedCells) {
            if (isEnclosed(visited, cell, validList, validDirection))
                return true;
        }
        return false;
    }

    private HashSet<Cell> dfs(HashSet<Cell> visited, Cell current, HashSet<Integer> validList, HashSet<Cell.Direction> validDirection) {
        visited.add(current);
        HashSet<Cell> surroundedCells = current.getCellsBy(visited, validList, validDirection);
        if (DEBUG) {
            System.out.println("in findPathToBorder");
            System.out.println("\tcurrent" + current.getCoordinates() + ": " + current);
            System.out.println("\tvalidList: " + validList);
            System.out.println("\tvalidDirection: " + validDirection);
            System.out.println("\tsurround: " + surroundedCells);
        }
        if (surroundedCells.size() == 0) {
            return visited;
        }
        for (Cell cell : surroundedCells) {
            visited.addAll(dfs(visited, cell, validList, validDirection));
        }
        return visited;
    }

    private boolean isOnBorder(Collection<Cell> cells) {
        for (Cell cell : cells) {
            if (cell.isOnBorder())
                return true;
        }
        return false;
    }


    /**
     * evaluate current board position
     *
     * @param player player
     * @return value
     */
    public double evaluate(Dongtaoy player) {
        int playerCaptured = 0;
        int opponentCaptured = 0;
        HashSet<Cell> visited = new HashSet<>();
        int playerChain = 0;
        int opponentChain = 0;
        HashSet<Cell> playerSafeCell = new HashSet<>();
        HashSet<Cell> opponentSafeCell = new HashSet<>();
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                final Cell cell = this.cells[i][j];
                if (cell.isOnBorder() && cell.isColored()) {
                    HashSet<Cell> temp = dfs(new HashSet<Cell>(),
                            cell,
                            new HashSet<Integer>() {{
                                add(cell.getPiece());
                            }},
                            new HashSet<Cell.Direction>() {{
                                add(Cell.Direction.TOPMIDDLE);
                                add(Cell.Direction.BOTTOMMIDDLE);
                                add(Cell.Direction.MIDDLELEFT);
                                add(Cell.Direction.MIDDLERIGHT);
                            }});
                    if (cell.isPlayerCell(player))
                        playerSafeCell.addAll(temp);
                    else
                        opponentSafeCell.addAll(temp);
                }

                if (!visited.contains(cell)) {
                    HashSet<Cell> temp = dfs(new HashSet<Cell>(),
                            cell,
                            new HashSet<Integer>() {{
                                add(cell.getPiece());
                            }},
                            new HashSet<Cell.Direction>() {{
                                add(Cell.Direction.TOPMIDDLE);
                                add(Cell.Direction.BOTTOMMIDDLE);
                                add(Cell.Direction.MIDDLELEFT);
                                add(Cell.Direction.MIDDLERIGHT);
                                add(Cell.Direction.TOPLEFT);
                                add(Cell.Direction.TOPRIGHT);
                                add(Cell.Direction.BOTTOMLEFT);
                                add(Cell.Direction.BOTTOMRIGHT);
                            }});
                    visited.addAll(temp);
//                    System.out.println(temp);
                    if (cell.isPlayerCell(player))
                        playerChain++;
                    else if (cell.getPiece() == player.getOpponentPiece())
                        opponentChain++;

                }

//

                if (cell.isCaptured(player.getPiece())) {
                    playerCaptured++;
                }
                if (cell.isCaptured(player.getOpponentPiece())) {
                    opponentCaptured++;
                }
            }
        }
        if (DEBUG) {
            System.out.println("player captured: " + playerCaptured);
            System.out.println("opponent captured: " + opponentCaptured);

            System.out.println("player Safe cell: " + playerSafeCell.size());
            System.out.println("opponent Safe cell: " + opponentSafeCell.size());

            System.out.println("player chain: " + playerChain);
            System.out.println("Opponent chain: " + opponentChain);

        }
        double maxChain = Math.pow(Math.ceil(this.dimension / 2), 2);
        return 10 * playerCaptured + playerSafeCell.size() + (maxChain - playerChain)
                - 10 * opponentCaptured - opponentSafeCell.size() - (maxChain - opponentChain);
    }

    private Pair<Integer, Integer> getNumOfSafeCell() {

        return new Pair<>(1, 1);
    }

    /**
     * if board is symmetric return only cells that is different
     *
     * @return cells
     */
    public ArrayList<Cell> getSymmetricCells() {
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

    /**
     * get all empty cell in current Board
     *
     * @return cells
     */
    public ArrayList<Cell> getEmptyCell() {
        ArrayList<Cell> value = new ArrayList<>();
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (cells[i][j].isEmpty())
                    value.add(cells[i][j]);
        return value;
    }


    public Board verticalFlip() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[i][this.dimension - j - 1].getPiece();
        return new Board(contents);
    }

    public Board horizontalFlip() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[this.dimension - i - 1][j].getPiece();
        return new Board(contents);
    }

    public Board transposeDown() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[j][i].getPiece();
        return new Board(contents);
    }

    public Board transposeUp() {
        return this.transposeDown().horizontalFlip().verticalFlip();
    }

    /**
     * getter function for dimension
     *
     * @return this.dimension
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * getter function for cells
     *
     * @return Cell[][] this.cells
     */
    public Cell[][] getCells() {
        return cells;
    }


    /**
     * equals function for Board
     *
     * @param object
     * @return boolean true or false
     */
    public boolean equals(Object object) {
        Board board = (Board) object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (this.cells[i][j].getPiece() != board.getCells()[i][j].getPiece())
                    return false;
        return true;
    }

    /**
     * toString function for Board
     *
     * @return String representation of a board
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("board moves:\n");
        for (Cell[] row : this.cells) {
            for (Cell c : row) {
                stringBuilder.append(c);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }


}
