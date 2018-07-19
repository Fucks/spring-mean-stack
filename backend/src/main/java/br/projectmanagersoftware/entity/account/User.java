package br.projectmanagersoftware.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.projectmanagersoftware.entity.AbstractEntity;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity implements UserDetails {

    @Getter
    @Setter
    @Field
    @NotNull
    @NotEmpty
    @Indexed
    private String fullName;

    @Getter
    @Setter
    @Field
    @NotNull
    @NotEmpty
    @Email
    @Indexed(unique = true)
    private String email;

    @Setter
    @Field
    @NotNull
    @NotEmpty
    private String password;

    @Setter
    @Field
    @NotEmpty
    @Indexed(unique = true)
    private String username;

    @Getter
    @Setter
    @DBRef(lazy = true)
    private Profile profile;

    @Setter
    @Field
    private Boolean enabled;

    @Getter
    @Setter
    @Field
    private Boolean emailActivated;

    @Getter
    @Setter
    @Field
    @JsonIgnore
    private String tokenResetPassword;

    @Getter
    @Setter
    @Field
    @JsonIgnore
    private Date tokenResetPasswordDate;

    /**
     *
     */
    public User() {
        this.emailActivated = false;
    }

    /**
     *
     */
    public User(String id) {
        super(id);
    }

    /**
     *
     * @param fullName
     * @param email
     * @param password
     * @param username
     * @param profile
     * @param enabled
     * @param emailActivated
     * @param tokenResetPassword
     * @param tokenResetPasswordDate
     */
    public User(String fullName, String email, String password, String username, Profile profile, Boolean enabled, Boolean emailActivated, String tokenResetPassword, Date tokenResetPasswordDate) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile = profile;
        this.enabled = enabled;
        this.emailActivated = emailActivated;
        this.tokenResetPassword = tokenResetPassword;
        this.tokenResetPasswordDate = tokenResetPasswordDate;
    }

    /**
     *
     * @param fullName
     * @param email
     * @param password
     * @param username
     * @param profile
     * @param enabled
     * @param emailActivated
     * @param tokenResetPassword
     * @param tokenResetPasswordDate
     * @param id
     */
    public User(String fullName, String email, String password, String username, Profile profile, Boolean enabled, Boolean emailActivated, String tokenResetPassword, Date tokenResetPasswordDate, String id) {
        super(id);
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile = profile;
        this.enabled = enabled;
        this.emailActivated = emailActivated;
        this.tokenResetPassword = tokenResetPassword;
        this.tokenResetPasswordDate = tokenResetPasswordDate;
    }

    /**
     *
     * @param id
     * @param fullName
     * @param email
     * @param username
     * @param enabled
     */
    public User(String id, String fullName, String email, String username, Boolean enabled) {
        super(id);
        this.enabled = enabled;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }

    /*
     USER DETAILS METODOS
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profile.getPermissoes()
                .stream()
                .map((String e) -> (GrantedAuthority) () -> e)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<String> getPermissoes() {
        return this.profile.getPermissoes().stream().collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    public boolean hasPermission(String permissao) {
        return this.profile.getPermissoes()
                .stream()
                .anyMatch((perm) -> {
                    return perm.equals(permissao);
                });
    }

    /**
     * Retorna o usuário logado no sistema no momento.
     *
     * @return
     */
    @JsonIgnore
    public static User getAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return new User();
    }

}
