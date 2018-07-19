package br.projectmanagersoftware.application;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 17/11/2015
 */
public class SystemPermissions implements Serializable {

    /**
     * PERMISSÕES
     */
    //--- Usuários
    public static final String PROFILE_LIST = "profile.list";
    public static final String PROFILE_INSERT = "profile.insert";
    public static final String PROFILE_UPDATE = "profile.update";
    public static final String PROFILE_REMOVE = "profile.remove";

    //--- Usuários
    public static final String USUARIOS_LIST = "usuarios.list";
    public static final String USUARIOS_INSERT = "usuarios.insert";
    public static final String USUARIOS_UPDATE = "usuarios.update";
    public static final String USUARIOS_REMOVE = "usuarios.remove";
    
    //--- Projetos
    public static final String PROJECT_LIST = "projetos.list";
    public static final String PROJECT_INSERT = "projetos.insert";
    public static final String PROJECT_EDIT = "projetos.edit";
    public static final String PROJECT_DELETE = "projetos.delete";
    
    /**
     * 
     */
    public static final String[] ARRAY_PERMISSIONS = new String[]
    {
        PROFILE_LIST,
        PROFILE_INSERT,
        PROFILE_UPDATE,
        PROFILE_REMOVE,
        
        USUARIOS_LIST,
        USUARIOS_INSERT,
        USUARIOS_UPDATE,
        USUARIOS_REMOVE,
        
        PROJECT_LIST,
        PROJECT_INSERT,
        PROJECT_EDIT,
        PROJECT_DELETE
    };
 
     /**
     * 
     * @return 
     */
    public static List<String> getAllPermissions(){
        return Arrays.asList(ARRAY_PERMISSIONS);
    }

}
