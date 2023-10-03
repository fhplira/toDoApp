package controller;

import model.Project;
import util.ConnectionFactory;


import java.sql.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProjectController {

    public void save (Project project) {
        String sql = "INSERT INTO projects (name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(project.getCreatedAt().toLocalDate()));
            statement.setDate(4, java.sql.Date.valueOf(project.getUpdatedAt().toLocalDate()));

            statement.execute();

        } catch (SQLException ex){
            throw new RuntimeException("Erro ao salvar o projeto" + ex.getMessage(), ex);

        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }


    }

    public void update (Project project) {
        String sql = "UPDATE projects SET " +
                "name = ?, " +
                "description = ?, " +
                "createdAt = ?, " +
                "updatedAt = ? " +
                "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(project.getCreatedAt().toLocalDate()));
            statement.setDate(4, java.sql.Date.valueOf(project.getUpdatedAt().toLocalDate()));
            statement.setInt(5, project.getId());

            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                java.sql.Timestamp timestampCreatedAt = resultSet.getTimestamp("createdAt");
                if (timestampCreatedAt != null) {
                    project.setCreatedAt(timestampCreatedAt.toLocalDateTime());
                }
                //project.setCreatedAt(resultSet.getDate("createdAt"));
                java.sql.Timestamp timestampUpdatedAt = resultSet.getTimestamp("updatedAt");
                if (timestampUpdatedAt != null) {
                    project.setUpdatedAt(timestampUpdatedAt.toLocalDateTime());
                }
                //project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;

    }

    public void removeByID(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }


    }
}
