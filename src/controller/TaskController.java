package controller;

import model.Task;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject, name, description, notes, completed, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.getCompleted()); // poderia ser um task.isCompleted hm
            statement.setDate(6, java.sql.Date.valueOf(task.getDeadline().toLocalDate()));
            statement.setDate(7, java.sql.Date.valueOf(task.getCreatedAt().toLocalDate()));
            statement.setDate(8, java.sql.Date.valueOf(task.getUpdatedAt().toLocalDate()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }


    }

    public void update (Task task) {

        String sql = "UPDATE tasks SET " +
                "idProject = ?, " +
                "name = ?, " +
                "description = ?, " +
                "notes = ?, " +
                "completed = ?, " +
                "deadline = ?, " +
                "createdAt = ?, " +
                "updatedAt = ? " +
                "WHERE id = ? ";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.getCompleted());
            statement.setDate(6, java.sql.Date.valueOf(task.getDeadline().toLocalDate()));
            statement.setDate(7, java.sql.Date.valueOf(task.getCreatedAt().toLocalDate()));
            statement.setDate(8, java.sql.Date.valueOf(task.getUpdatedAt().toLocalDate()));
            statement.setInt(9, task.getId());

            //Executa a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) throws Exception {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, taskId);

            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<Task>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Setando um valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houver valores a serem percorridos no resultSet
            while(resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setCompleted(resultSet.getBoolean("completed"));
                java.sql.Timestamp timestampDeadline = resultSet.getTimestamp("deadline");
                if (timestampDeadline != null) {
                    task.setDeadline(timestampDeadline.toLocalDateTime());
                }
                //task.setDeadline(resultSet.getDate("deadline"));
                java.sql.Timestamp timestampCreatedAt = resultSet.getTimestamp("createdAt");
                if (timestampCreatedAt != null) {
                    task.setCreatedAt(timestampCreatedAt.toLocalDateTime());
                }
                //task.setCreatedAt(resultSet.getDate("createdAt"));
                java.sql.Timestamp timestampUpdatedAt = resultSet.getTimestamp("updatedAt");
                if (timestampUpdatedAt != null) {
                    task.setUpdatedAt(timestampUpdatedAt.toLocalDateTime());
                }
                //task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a lista " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
}
