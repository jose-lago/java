package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.PlayerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public boolean cadastrarPlayer(PlayerDTO player) {
        String sql = "INSERT INTO player(nome, nick, idade, timequejoga) VALUES (?,?,?,?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, player.getNome());
            ps.setString(2, player.getNick());
            ps.setInt(3, player.getIdade());
            ps.setString(4, player.getTimequejoga());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PlayerDTO> listarPlayers() {
        List<PlayerDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM player ORDER BY id";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PlayerDTO p = new PlayerDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setNick(rs.getString("nick"));
                p.setIdade(rs.getInt("idade"));
                p.setTimequejoga(rs.getString("timequejoga"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean editarPlayer(PlayerDTO player) {
        String sql = "UPDATE player SET nome=?, nick=?, idade=?, timequejoga=? WHERE id=?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, player.getNome());
            ps.setString(2, player.getNick());
            ps.setInt(3, player.getIdade());
            ps.setString(4, player.getTimequejoga());
            ps.setInt(5, player.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarPlayer(int id) {
        String sql = "DELETE FROM player WHERE id=?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}