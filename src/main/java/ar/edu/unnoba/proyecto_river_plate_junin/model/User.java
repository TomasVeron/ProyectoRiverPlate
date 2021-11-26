package ar.edu.unnoba.proyecto_river_plate_junin.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.Arrays;
import java.util.Collection;

import lombok.Data;

@Data
@Entity
@Table(name="usuarios")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    
    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Column(name = "apellido")
    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @Column(name= "email")
    @NotBlank(message= "El email no puede estar vacio")
    private String email;

    @Column(name ="clave")
    @NotBlank( message = "la clave no puede estar vacia")
    @Size(min = 8, message = "la contraseña debe ser de al menos 8 caracteres")
    private String clave;

    @Transient
    @NotBlank( message = "la clave no puede estar vacia")
    @Size(min = 8, message = "la contraseña debe ser de al menos 8 caracteres")
    private String confirmarPassword;

    @NotNull
    @Column(name = "rol")
    private boolean rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.getClave();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.getNombre();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}