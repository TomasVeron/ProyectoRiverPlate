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
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Column(name = "apellido")
    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @Column(name = "mail")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "dni")
    @NotBlank(message = "El DNI no puede estar vacio")
    @Size(min = 8, message = "El DNI debe ser de al menos 8 digitos")
    private String dni;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "codigo_socio")
    private String codigo;
    
    @Column(name ="fecha_nacimiento")
    @NotNull( message = "la fecha de nacimiento no puede estar vacia")
    private Date fechaNacimiento;

    @Column(name ="fecha_alta")
    private Date fechaAlta;

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

    public boolean isCategoriaGrupoFamiliar(){
        return getCategoria().isGrupoFamiliar();
    }

    public boolean isCategoriaIndividual(){
        return getCategoria().isIndividual();
    }

    public boolean isTitular(){
        return (isCategoriaGrupoFamiliar() && getSocioTitular()==null);
    }

    public boolean isDependiente(){
        return (getSocioTitular()!=null);
    }

    public boolean isIndividual(){
        return (isCategoriaIndividual() && getSocioTitular()==null);
    }
}
