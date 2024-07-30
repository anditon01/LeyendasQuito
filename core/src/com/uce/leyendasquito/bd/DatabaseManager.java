package com.uce.leyendasquito.bd;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.uce.leyendasquito.estados.AlmeidaState;
import com.uce.leyendasquito.estados.CantuniaState;
import com.uce.leyendasquito.estados.GameState;
import com.uce.leyendasquito.estados.PlazaState;
import com.uce.leyendasquito.ventanas.MyGame;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        // Establecer la conexión a la base de datos
        try {
        	
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Partidas.db");
            //String pathDB = getDatabasePath(connection).toString();
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS game_state (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "level TEXT," +
                         "pos_x REAL," +
                         "pos_y REAL)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveGameState(String level, float x, float y) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO game_state (level, pos_x, pos_y) VALUES (?, ?, ?)")) {
            pstmt.setString(1, level);
            pstmt.setFloat(2, x);
            pstmt.setFloat(3, y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GameState loadGameState(MyGame game) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM game_state ORDER BY id DESC LIMIT 1")) {
            if (rs.next()) {
                String level = rs.getString("level");
                float x = rs.getFloat("pos_x");
                float y = rs.getFloat("pos_y");

                GameState gameState = createStateFromLevel(level, game);
                if (gameState != null) {
                    gameState.setPlayerPosition(x, y);
                }
                return gameState;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GameState createStateFromLevel(String level, MyGame game) {
        switch (level) {
            case "Plaza":
                return new PlazaState(game);
            case "Cantunia":
                return new CantuniaState(game);
            case "Almeida":
                return new AlmeidaState(game);
            default:
                return null;
        }
    }

    public String getSavedGameInfo() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM game_state ORDER BY id DESC LIMIT 1")) {
            if (rs.next()) {
                String level = rs.getString("level");
                float x = rs.getFloat("pos_x");
                float y = rs.getFloat("pos_y");
                return "Nivel: " + level + "\nPosición: (" + x + ", " + y + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No hay partida guardada.";
    }
}
