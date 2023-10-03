package toDoApp;

import controller.ProjectController;
import controller.TaskController;
import model.Project;
import model.Task;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProjectController projectController = new ProjectController();

//        Project project = new Project();
//        project.setName("Projeto teste");
//        project.setDescription("description");
//        project.setCreatedAt(LocalDateTime.now());
//        project.setUpdatedAt(LocalDateTime.now());
//        projectController.save(project);
//        project.setId(4);
//        project.setName("Novo nome do projeto");
//        projectController.update(project);

        List<Project> projects = projectController.getAll();
        System.out.println("Total de projetos: " + projects.size());

//        projectController.removeByID(4);


        TaskController taskController = new TaskController();
        Task task = new Task();
        task.setIdProject(2);
        task.setId(2);
        task.setName("tarefa teste teste");
        taskController.update(task);
//        task.setDescription("sem descrição");
//        task.setNotes("sem notas");
//        task.setCompleted(false);
//        task.setDeadline(LocalDateTime.of(2023, 10, 12, 12, 30));
//        task.setCreatedAt(LocalDateTime.now());
//        task.setUpdatedAt(LocalDateTime.now());
//        taskController.save(task);

    }
}
