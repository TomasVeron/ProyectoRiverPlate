package ar.edu.unnoba.proyecto_river_plate_junin.model;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.persistence.*;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity
@Table(name="usuarios")
public class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column(name = "id_usuario")
    private Long id;

    @NotEmpty
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty
    @Column(name ="clave")
    private String clave;

    @NotNull
    @Column(name = "rol")
    private boolean rol;


}
