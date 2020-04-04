/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        // FIXME
        for (int r = 0; r < BOARD_SIZE; r += 1) {
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                _board[(r << 3) + c] = contents[r][c];
                sq(c,r)._contains = contents[r][c];
                set(sq(c, r), contents[r][c]);
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        // FIXME
        for (int r = 0; r < BOARD_SIZE; r += 1) {
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                _board[(r << 3) + c] = board.get(sq(c, r));
            }
        }

        _turn = board._turn;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        //Piece setter = get(sq);
        //Piece setter = v;
        _board[sq.index()] = v;
        sq._contains = v;
        //System.out.println("sq " + sq + "to " + v);
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. This function assumes that
     *  MOVE.isCapture() will return false.  If it saves the move for
     *  later retraction, makeMove itself uses MOVE.captureMove() to produce
     *  the capturing move. */
    void makeMove(Move move) {
        assert isLegal(move);
        Square from = move.getFrom();
        Square to = move.getTo();
        if (get(from) != get(to) && get(to) != EMP) {
            Move.mv(from, to, true);
            _moves.add(Move.mv(from, to, true));
            System.out.println("Replaced " + sq(from.col(),from.row()) +" " + sq(to.col(), to.row()));
        } else {
            Move.mv(from, to, false);
            _moves.add(Move.mv(from, to, false));
        }
        set(to, get(from));
        set(from, EMP);
        _turn = turn().opposite();

        // FIXME
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        Move undo = _moves.remove(_moves.size() - 1);
        Square from = undo.getFrom();
        Square to = undo.getTo();
        set(from, get(to));
        if (undo.isCapture()) {
            set(to, get(to).opposite());
        } else {
            set(to, EMP);
        }
        _turn = turn().opposite();
        _subsetsInitialized = false;
        // FIXME
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (!from.isValidMove(to)) {
            //System.out.println("fails !isValidMove");
            return false;
        }
        if ((from == null) || (to == null) || from._contains == EMP)  {
            return false;
        }
        if (from == to) {
            return false;
        }
        if (from._contains == to._contains) {
            System.out.println("samesies");
            return false;
        }
//        int fromR, fromC, toR, toC;
//        fromC = from.col(); fromR = from.row();
//        toC = from.row(); toR = to.row();
        int dist = from.distance(to);
        int dir = from.direction(to);
        int actions = actions(from, dir);
        if (dist != actions) {
            System.out.println(dist + " " + actions);
            System.out.println("Fails dist!= actions");
            return false;
        }
        int blockDist = blocked(from, dir);
        if (blockDist < actions && blockDist != 0) {
            System.out.println("blockDist < actions");
            return false;
        }
        if (from._contains != to._contains) {
            //System.out.println("should be capture");
        }
        return true;   // FIXME
    }
    int blocked(Square from, int direction) {
        assert from._contains != EMP;
        Piece _piece = from._contains;
        Piece blocker = WP;
        if (_piece == WP) {
            blocker = BP;
        }
        //assert from._contains == WP;
        int dirC, dirR, c, r, dist;
        dirC = Square.DIR[direction][0];
        dirR = Square.DIR[direction][1];
        c = from.col(); r = from.row();
        dist = 0;
        while (((c < BOARD_SIZE - 1) && (r < BOARD_SIZE - 1)) && ((c >= 0) && (r >= 0))) {
            c += dirC; r += dirR;
            if (c < 0 || r < 0) {
                break;
            }
            dist += 1;
            if (sq(c,r)._contains == blocker) {
                return dist;
            }
        }
        return 0;
    }

    int actions(Square from, int direction) {
        if (from == sq('f',1)) {
            System.out.println("in action");
        }
        int dir = direction % 4;
        int action = 0;
        int dirC = Square.DIR[dir][0];
        int dirR = Square.DIR[dir][1];
        int fromR = from.row();
        int fromC = from.col();
        int forwardC, backC; int forwardR, backR;
        forwardC = backC = fromC; forwardR = backR = fromR;
        if (sq(fromC, fromR)._contains != EMP) {
            //System.out.println("action: self added.");
            action += 1;
        }
        while ((forwardC <= BOARD_SIZE - 1) && (forwardR <= BOARD_SIZE - 1)) {
            forwardC += dirC; forwardR += dirR;
            //if (sq(forwardC,forwardR) == null)
            if (forwardC < 0 || forwardC == BOARD_SIZE
                    || forwardR < 0 || forwardR == BOARD_SIZE) {
                break;
            }
            else if (sq(forwardC, forwardR)._contains != EMP) {
                //System.out.println("action: " + sq(forwardC,forwardR)._contains +" added");
                action += 1;
            }
        }
        while ((backC > 0) || (backR > 0)) {
            //FIXME does this give nullpoint ever?
            backC -= dirC; backR -= dirR;
            if (backC < 0 || backR < 0 || backC == BOARD_SIZE || backR == BOARD_SIZE) {
                break;
            }
             else if (sq(backC, backR)._contains != EMP) {
                action += 1;
            }
        }
        return action;
    }


    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    List<Move> legalMoves() {
        return null;  // FIXME
    }

    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            // FIXME
            //_winnerKnown = true;
            if (piecesContiguous(WP) || piecesContiguous(BP)) {
                _winnerKnown = true;
                _winner = _turn.opposite();
            }
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        return false; // FIXME I made my own block method.
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    int numContig(Square sq, boolean[][] visited, Piece p, int count) {
//        if (sq == null) {
//            return 0;
//        }
//        int c = sq.col();
//        int r = sq.row();
//        if (visited[c][r] == true) {
//            return 0;
//        }
//        if (p == EMP) {
//            visited[c][r] = true;
//            return 0;
//        }
//        visited[c][r] = true;
//        Square[] checkSq = new Square[8];
//        for (int i = 0; i < 8; i += 1) {
//            int cc = Square.DIR[i][0];
//            int cr = Square.DIR[i][1];
//            checkSq[i] = sq(c + cc, r + cr);
//        }
//        for (int j = 0; j < 8; j += 1) {
//            System.out.print(checkSq[j]);
//        }
//        System.out.println(" ");
//        for (int j = 0; j < 8; j += 1) {
//            count += numContig(checkSq[j], visited, p, count);
//            System.out.println(count);
//        }
        if (sq == null) {
            return 0;
        }
        int c = sq.col();
        int r = sq.row();
        if (visited[c][r] == true) {
            return 0;
        }
        visited[c][r] = true;
        if (sq._contains == p) {
            count += 1;
        }
        Square[] around = new Square[8];
        for (int i = 0; i < 8; i += 1) {
            int nextc = c + Square.DIR[i][0];
            int nextr = r + Square.DIR[i][1];
            if ((nextc > 7) || (nextr > 7) || (nextc < 0) || (nextr < 0)) {
                continue;
            } else if (sq(nextc, nextr)._contains == p) {
                around[i] = sq(nextc, nextr);
            }
        }
        int newcount = 0;
        for (int x = 0; x < 8; x += 1) {
            if (around[x] != null) {
                newcount += numContig(around[x], visited, p, 0);
            }
        }

        return count + newcount;  // FIXME
    }

    private int match(Square from, Square to) {
        return 0;
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        // FIXME
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int c = 0; c < BOARD_SIZE; c += 1) {
            for (int r = 0; r < BOARD_SIZE; r += 1) {
                Piece curr = sq(c,r)._contains;
                if (curr == EMP) {
                    visited[c][r] = true;
                } else if ((curr == WP || curr == BP) && !visited[c][r]) {
                    int count = numContig(sq(c,r), visited, curr, 0);
                    if (count > 0) {
                        if (curr == WP) {
                            _whiteRegionSizes.add(count);
                        } else {
                            _blackRegionSizes.add(count);
                        }
                    }
                }
            }
        }
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    // FIXME: Other methods, variables?
    /** Takes in index and returns coordinates */
    public int[] indexToRC(int i) {
        int[] ret = new int[2];
        ret[1] = i >> 3;
        ret[0] = i - ret[1] * 8;
        return ret;
    };

    /** Takes in RC coordinates and returns index. */
    public int rctoIndex(int r, int c) {
        return (c << 3) + r;
    };

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** The nonstandard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES2 = {
            { WP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
            { BP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };
    /** Current contents of the board.  Square S is at _board[S.index()]. */
    // FIXME change back to private.
    public final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();

    /** Pieces on board */
    private Piece[][] _pob;
}
