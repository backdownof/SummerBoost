package com.backdownof.dao;

import com.backdownof.dto.PlayerFiler;
import com.backdownof.entity.Player;
import com.backdownof.exception.DaoException;
import com.backdownof.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDao implements Dao<Integer, Player> {
    private static final PlayerDao INSTANCE = new PlayerDao();
    private static final String DELETE_SQL = """
            DELETE FROM player WHERE id = ?;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO player (username, telegram_user_id, chat_id) VALUES (?, ?, ?);
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, username, telegram_user_id, chat_id FROM player
            """;
    private static final String UPDATE_SQL = """
            UPDATE player
            SET username = ?, telegram_user_id = ?, chat_id = ?
            WHERE id = ?
            """;
    private static final String FIND_BY_ID = """
            SELECT id, username, telegram_user_id, chat_id
            FROM player
            WHERE id = ?
            """;

    private static final String FIND_BY_USERNAME = """
            SELECT id, username, telegram_user_id, chat_id
            FROM player
            WHERE username = ?
            """;

    private PlayerDao() {
    }

    public static PlayerDao getInstance() {
        return INSTANCE;
    }


    public Optional<Player> findById(int id) {
        try (var connection = DBConnectionManager.get();) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Player> findById(int id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();

            Player player = null;
            if (resultSet.next()) {
                player = new Player(resultSet.getString("username"),
                        resultSet.getLong("telegram_user_id"),
                        resultSet.getLong("chat_id"));
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Player> getByUsername(String username) {
        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            var resultSet = preparedStatement.executeQuery();

            Player player = null;
            if (resultSet.next()) {
                player = new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getLong("telegram_user_id"),
                        resultSet.getLong("chat_id"));
            }
            return Optional.ofNullable(player);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Player save(Player player) {
        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, player.getNickname());
            preparedStatement.setLong(2, player.getTelegramUserId());
            preparedStatement.setLong(3, player.getChatId());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();

            while (generatedKeys.next()) {
                player.setId(generatedKeys.getInt("id"));
            }

            return player;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Player player) {
        try (var connection = DBConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, player.getNickname());
            prepareStatement.setLong(2, player.getTelegramUserId());
            prepareStatement.setLong(3, player.getChatId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Integer id) {
        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Player> findAll() {
        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(5);

            ArrayList<Player> playerList = new ArrayList<>();
            while (resultSet.next()) {
                playerList.add(buildPlayer(resultSet));
            }

            return playerList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Player> findAll(PlayerFiler filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String sql = FIND_ALL_SQL + """
                LIMIT ?
                OFFSET ?;
                """;
        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            List<Player> players = null;
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                players.add(buildPlayer(resultSet));
            }

            return players;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Player buildPlayer(ResultSet resultSet) throws SQLException {
        Player player = new Player(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getLong("telegram_user_id"),
                resultSet.getLong("chat_id")
        );
        return player;
    }


}
