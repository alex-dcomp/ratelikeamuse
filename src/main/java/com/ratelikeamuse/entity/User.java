package RateLikeAMuse.entity; //analisar esse package

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements UserDetails {

    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private Funcao funcao;

    public User() {
    }


    public User(Long id, String username, String email, String password, Funcao funcao) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.funcao = funcao;
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    @Override 
    public String getUsername() { 
        return this.username; 
    }

    public void setUsername(String username) {
         this.username = username; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    @Override 
    public String getPassword() { 
        return this.password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }

    public Funcao getFuncao() { 
        return funcao; 
    }

    public void setFuncao(Funcao funcao) { 
        this.funcao = funcao; 
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (funcao == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + funcao.name()));
    }

    @Override 
    @JsonIgnore 
    public boolean isAccountNonExpired() { 
        return true; 
    }

    @Override 
    @JsonIgnore 
    public boolean isAccountNonLocked() { 
        return true; 
    }

    @Override 
    @JsonIgnore 
    public boolean isCredentialsNonExpired() { 
        return true; 
    }

    @Override 
    @JsonIgnore 
    public boolean isEnabled() { 
        return true; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", funcao=" + funcao + '}';
    }
}
