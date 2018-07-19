package br.projectmanagersoftware.web.controller;

import br.projectmanagersoftware.entity.project.Project;
import br.projectmanagersoftware.entity.project.info.ProjectCharter;
import br.projectmanagersoftware.service.project.info.ProjectInfoService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@Controller
@RequestMapping("api/project/info")
public class ProjectInfoController {

    @Autowired
    protected ProjectInfoService projectInfoService;

    @GetMapping("")
    private ResponseEntity getProjects(String filter, Pageable page) {
        return ResponseEntity.ok(
                this.projectInfoService.listByFilter(filter, page));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/project-charter/{id}")
    private ResponseEntity getProjectCharter(@PathVariable("id") String id) {

        final Optional<Project> result = this.projectInfoService.findById(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(
                    this.projectInfoService
                            .findById(id));
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    /**
     *
     * @param projectCharter
     * @return
     */
    @PostMapping("/project-charter")
    private ResponseEntity postProjectChart(@RequestBody ProjectCharter projectCharter) {
        return ResponseEntity.ok(
                this.projectInfoService
                        .save(projectCharter));
    }

    /**
     *
     * @param id
     * @param projectCharter
     * @return
     */
    @PutMapping("/project-chart/{id}")
    private ResponseEntity putProjectChart(@PathVariable("id") String id, @RequestBody ProjectCharter projectCharter) {
        return ResponseEntity.ok(
                this.projectInfoService
                        .save(projectCharter));
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/project-chart/{id}")
    private ResponseEntity deleteProjectChart(@PathVariable("id") String id) {
        this.projectInfoService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
