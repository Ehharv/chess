package service;

import chess.ChessGame;
import dataaccess.AuthDao;
import dataaccess.GameDao;
import model.GameData;
import service.exceptions.AlreadyTakenException;
import service.exceptions.BadRequestException;
import service.exceptions.UnauthorizedException;

public class GameService {

    private final AuthDao authDao;
    private final GameDao gameDao;

    public GameService(AuthDao authDao, GameDao gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    /**
     * Gets a list of all the games includes usernames of players and status of the game (including the entire board)
     *
     * @param authToken makes sure user is signed in
     * @return the array of all games in the database
     * @throws UnauthorizedException
     */
    public GameData[] listGames(String authToken) throws UnauthorizedException {
        if(authDao.getAuthByToken(authToken) !=  null){
            return gameDao.getAllGames();
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

    /**
     * Creates a new game with a name.
     *
     * @param authToken makes sure user is signed in
     * @param gameName what you would like to name the new game, does not need to be unique
     * @return the integer ID of the new game
     * @throws UnauthorizedException
     * @throws BadRequestException
     */
    public int addGame(String authToken, String gameName)
            throws UnauthorizedException, BadRequestException {
        if(authDao.getAuthByToken(authToken) !=  null) {
            if(gameName != null) {
                return gameDao.createGame(gameName);
            } else{
                throw new BadRequestException("Error: Invalid game name");
            }
        } else {
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

    /**
     * Find a game by its ID and join the game as black or white if available
     *
     * @param authToken makes sure user is signed in
     * @param color WHITE or BLACK to join the game as
     * @param gameId the game you want to join
     * @throws UnauthorizedException
     * @throws BadRequestException
     * @throws AlreadyTakenException
     */
    public void joinGame(String authToken, chess.ChessGame.TeamColor color, int gameId)
            throws UnauthorizedException, BadRequestException, AlreadyTakenException {

        if(authDao.getAuthByToken(authToken) !=  null) {
                GameData game = gameDao.getGame(gameId);
            if(game != null){
                if(colorAvailable(game,color)){
                    String username = authDao.getAuthByToken(authToken).username();
                    gameDao.joinGame(color, username, gameId);
                } else{
                    throw new AlreadyTakenException("Error: Already Taken");
                }
            } else{
                throw new BadRequestException("Error: Invalid game ID");
            }
        } else{
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }

    /**
     * checks if the color the user has selected to join as is available or taken by another user
     *
     * @param game the game to check the status of
     * @param color the color to check if taken
     * @return if the color is available or not
     */
    private boolean colorAvailable(GameData game, ChessGame.TeamColor color) {
        // make sure there isn't a username assigned already
        if(color == ChessGame.TeamColor.WHITE) {
            return (game.whiteUsername() == null);
        } else {
            return (game.blackUsername() == null);
        }

    }

}
