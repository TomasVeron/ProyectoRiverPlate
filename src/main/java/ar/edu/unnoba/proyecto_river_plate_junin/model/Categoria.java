package ar.edu.unnoba.proyecto_river_plate_junin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "edad_desde")
    private int edadDesde;

    @Column(name = "edad_hasta")
    private int edadHasta;

    @Column(name = "valor_cuota")
    private double valorCuota;


}
