import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultSet;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "INSERT INTO produtos (id,nome, valor,status) VALUES (?,?,?,?)";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, produto.getId());
            prep.setString(2,produto.getNome());
            prep.setDouble(3, produto.getValor());
            prep.setString(4,produto.getStatus());
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o produto: " + e.getMessage());
        } finally {
            // Fechar conexão e recursos relacionados
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "SELECT * FROM produtos ORDER BY id DESC LIMIT 10";
            prep = conn.prepareStatement(sql);
            resultSet = prep.executeQuery();

            listagem.clear();

            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                
                listagem.add(produto);
            }           
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar os produtos: " + e.getMessage());
        } finally {
            // Fechar conexão e recursos relacionados
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }

        return listagem;
    }
}

