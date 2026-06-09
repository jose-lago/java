package com.template.dao;

import com.template.model.Conexao;
import com.template.model.PlayerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public void cadastrarPlayer(String nome,
                                String nick,
                                int idade,
                                String timequejoga) {

        String sql =
                "INSERT INTO player(nome,nick,idade,timequejoga) VALUES (?,?,?,?)";

        try (
                Connection c = new Conexao().conectaBD();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {

            ps.setString(1, nome);
            ps.setString(2, nick);
            ps.setInt(3, idade);
            ps.setString(4, timequejoga);

            ps.executeUpdate();

            System.out.println("Player cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerDTO> listarPlayers() {

        List<PlayerDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM player ORDER BY id";

        try (
                Connection c = new Conexao().conectaBD();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

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

    public void editarPlayer(int id,
                             String nome,
                             String nick,
                             int idade,
                             String timequejoga) {

        String sql =
                "UPDATE player SET nome=?, nick=?, idade=?, timequejoga=? WHERE id=?";

        try (
                Connection c = new Conexao().conectaBD();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {

            ps.setString(1, nome);
            ps.setString(2, nick);
            ps.setInt(3, idade);
            ps.setString(4, timequejoga);
            ps.setInt(5, id);

            ps.executeUpdate();

            System.out.println("Player atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPlayer(int id) {

        String sql = "DELETE FROM player WHERE id=?";

        try (
                Connection c = new Conexao().conectaBD();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Player removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}