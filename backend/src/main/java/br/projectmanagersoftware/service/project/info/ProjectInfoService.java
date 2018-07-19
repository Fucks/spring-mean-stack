package br.projectmanagersoftware.service.project.info;

import br.projectmanagersoftware.application.SystemPermissions;
import br.projectmanagersoftware.entity.project.Project;
import br.projectmanagersoftware.entity.project.info.ProjectCharter;
import br.projectmanagersoftware.repository.project.info.IBenefitRepository;
import br.projectmanagersoftware.repository.project.info.IPremiseRepository;
import br.projectmanagersoftware.repository.project.info.IProjectCharterRepository;
import br.projectmanagersoftware.repository.project.info.IProjectRepository;
import br.projectmanagersoftware.repository.project.info.IRestrictionRepository;
import br.projectmanagersoftware.repository.project.planning.IRequirementRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@Service
public class ProjectInfoService {

    @Autowired
    public IProjectRepository projectRepository;

    @Autowired
    public IProjectCharterRepository projectCharterRepository;
    
    @Autowired
    private IBenefitRepository benefitRepository;
    
    @Autowired
    private IRequirementRepository requirementRepository;
    
    @Autowired
    private IPremiseRepository premiseRepository;
    
    @Autowired
    private IRestrictionRepository restrictionRepository;

    /**
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasPermission(#principal, '"+SystemPermissions.PROJECT_LIST+"')")
    public Optional<Project> findById(String id) {
        return this.projectRepository
                .findById(id);
    }

    /**
     * 
     * @param filter
     * @param pageable
     * @return 
     */
    @PreAuthorize("hasPermission(#principal, '"+SystemPermissions.PROJECT_LIST+"')")
    public Page<Project> listByFilter(String filter, Pageable pageable) {
        return this.projectRepository.findAll(pageable);
    }
    
    /**
     * 
     * @param project
     * @return 
     */
    @PreAuthorize("hasPermission(#principal, '"+SystemPermissions.PROJECT_INSERT+"')")
    public ProjectCharter save(ProjectCharter project){
       return this.projectCharterRepository.save(project);
    }
    
    /**
     * 
     * @param id 
     */
    @PreAuthorize("hasPermission(#principal, '"+SystemPermissions.PROJECT_DELETE+"')")
    public void deleteById(String id){
        this.projectRepository.deleteById(id);
    }
}
