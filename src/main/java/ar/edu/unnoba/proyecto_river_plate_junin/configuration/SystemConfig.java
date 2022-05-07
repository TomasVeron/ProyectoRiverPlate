package ar.edu.unnoba.proyecto_river_plate_junin.configuration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "configuracion_sistema")
@Data
public class SystemConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConfig;

    @Column(name = "dia_generacion_cuota")
    private int diaGeneracionCuota;

    @Column(name = "cant_cuotas_generadas")
    private int cantDeCoutasGeneradas;

    @Column(name = "cant_socios_gf")
    private int cantSociosPorGF;

    @Column(name = "cant_meses_venc_couta")
    private int cantMesesVencimientoCuota;
    
}
