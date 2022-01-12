package ar.edu.unnoba.proyecto_river_plate_junin.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@Entity
@Table(name="socios")
public class Socio implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_socio")
    private Long id;
    
    @Column(name = "nombre")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String nombre;

    @Column(name = "apellido")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String apellido;

    @Column(name = "mail")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String email;

    @Column(name = "domicilio")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String domicilio;

    @Column(name = "dni")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String dni;

    @Column(name = "telefono")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String telefono;

    @Column(name = "codigo_socio")
    //@NotBlank(message = "El nombre no puede estar vacio")
    //@Size(min = 5, max = 16, message = "minimo 5 caracteres y maximo 16")
    private String codigo;
    
    @Column(name ="fecha_nacimiento")
    //@NotBlank( message = "la clave no puede estar vacia")
    //@Size(min = 8, message = "la contraseña debe ser de al menos 8 caracteres")
    private Date fechaNacimiento;

    @Column(name ="fecha_alta")
    //@NotBlank( message = "la clave no puede estar vacia")
    //@Size(min = 8, message = "la contraseña debe ser de al menos 8 caracteres")
    private Date fechaAlta;

    @NotNull
    @Column(name = "estado")
    private boolean estado;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_titular")
    private Socio socioTitular;

    @Transient
    private String codigoSocioTitular;


    @JoinColumn(name = "categoria", referencedColumnName = "id_categoria")
    @OneToOne
    private Categoria categoria;

    public Boolean getEstado () {
        return this.estado;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
}
