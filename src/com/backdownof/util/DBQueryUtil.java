package com.backdownof.util;

import java.sql.SQLException;

public final class DBQueryUtil {
    DBQueryUtil() {
    }

    static {
        createPlayerTable();
    }

    public static void createPlayerTable() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS player (
                        id SERIAL PRIMARY KEY ,
                        username VARCHAR(128) UNIQUE NOT NULL,
                        chat_id BIGINT UNIQUE NOT NULL
                    );
                """;

        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropPlayerTable() {
        String sql = """
                    DROP TABLE player;
                """;

        try (var connection = DBConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
