package model.returnobjects;

import chess.ChessGame;

public record JoinGameRequest(int gameId, ChessGame.TeamColor color) {
}
