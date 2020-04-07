/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import com.google.common.collect.Streams;

import java.util.ArrayList;


import static loa.Piece.*;

/** An automated Player.
 *  @author Cherish Truong
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** Returns Winning Value */
    public static int winningValue() {
        return WINNING_VALUE;
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;

        assert side() == getGame().getBoard().turn();
        int depth;
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        System.out.println("value :" +value);
        return _foundMove;
    }

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
//        int bestscore = 0;
//        ArrayList<Move> legalMoves = board.legalMoves();
//        for (Move m : legalMoves) {
//            Board copyBoard = new Board(board);
//            copyBoard.makeMove(m);
//            int score = copyBoard.score();
//            if (score > bestscore) {
//                bestscore = score;
//                _foundMove = m;
//            }
//        }
//        return bestscore;
        ArrayList<Move> legalMoves = board.legalMoves();
        _foundMove = legalMoves.get(0);
        int bestscore = 0;
        for (Move m: legalMoves) {
            Board copyBoard = new Board(board);
            copyBoard.makeMove(m);
            if (sense == 1) {
                int score = findMin(copyBoard, depth, alpha, beta);
                if (score > alpha) {
                    alpha = score;
                    _foundMove = m;
                    bestscore = score;
                }
            } else if (sense == -1) {
                int score = findMax(copyBoard, depth, alpha, beta);
                if (score < beta) {
                    beta = score;
                    _foundMove = m;
                    bestscore = score;
                }
            }
        }
        return bestscore;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 0;
    }

    private int findMax(Board board, int depth, int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return board.score();
        }
        ArrayList<Move> legalMoves = board.legalMoves();
        int bestsofar = -INFTY;
        for (Move m : legalMoves) {
            Board copyBoard = new Board(board);
            copyBoard.makeMove(m);
            int response = findMin(copyBoard, depth - 1, alpha, beta);
            if (response >= bestsofar) {
                alpha = Math.max(alpha, response);
                if (beta <= alpha) break;
            }
        }
        return bestsofar;
    }

    private int findMin(Board board, int depth, int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return board.score();
        }
        ArrayList<Move> legalMoves = board.legalMoves();
        int bestsofar = INFTY;
        for (Move m : legalMoves) {
            Board copyBoard = new Board(board);
            System.out.println(m);
            System.out.println(copyBoard);
            copyBoard.makeMove(m);
            int response = findMin(copyBoard, depth - 1, alpha, beta);
            if (response <= bestsofar) {
                bestsofar = response;
                beta = Math.min(beta, response);
                if (beta <= alpha) break;
            }
        }
        return bestsofar;
    }

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;
}
