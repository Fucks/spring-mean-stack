package br.projectmanagersoftware.entity.project.info;

import br.projectmanagersoftware.entity.account.User;
import br.projectmanagersoftware.entity.project.AbstractProjectEntity;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * O Termo de Abertura é o documento que formaliza o início do projeto e dá a
 * autoridade necessária ao gerente de projetos.
 *
 * Ele deve ser desenvolvido preferencialmente pelo próprio gerente do projeto e
 * é composto por diversas atividades de outras áreas de conhecimento como:
 *
 * Identificar as entregas chaves; 
 * Identificar riscos de alto nível, premissas e restrições;
 * Analisar benefícios e alinhá-los com as partes interessadas e com o negócio;
 * Levantar de forma macro o cronograma e o orçamento do projeto de modo a avaliar a viabilidade do projeto; 
 * 
 * Entre outras atividades. O termo de abertura contém:
 *
 * Gerente de Projeto e sua equipe; 
 * Data de início do projeto e suas dependências; 
 * Requisitos que satisfazem as necessidades do cliente;
 * Justificativa do projeto; 
 * WBS; 
 * Cronograma e Orçamento resumido.
 *
 * ROI = (Ganho obtido – Investimento) / Investimento
 * 
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 19/09/2017
 */
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProjectCharter extends AbstractProjectEntity {

    /**
     * Caso seja do tipo BUSSINESS então pode-se fazer uma projeção de valor
     * do projeto, assim podemos calcular o ROI.
     */
    @Getter
    @Setter
    @Field()
    private ProjectType type;
    
    @Getter
    @Setter
    @Field
    private DateTime startAt;
    
    @Getter
    @Setter
    @Field
    private DateTime endAt;
    
    @Getter
    @Setter
    @DBRef
    private User projectManager;
    
    @Getter
    @Setter
    private String justification;
    
    @Getter
    @Setter
    private String resume;

    @Getter
    @Setter
    private List<Premise> premises;
    
    @Getter
    @Setter
    private List<Restriction> restrictions;
    
    public ProjectCharter() {
        super();
    }
    
}
