package ar.edu.unnoba.proyecto_river_plate_junin.model;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name="cuotas")
public class Cuota implements Serializable {
    private static final long serialVersionUID = 1L;  // 1 de tipo Long

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuota;

    @NotEmpty
    private String numero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fecha_caducidad")
    private Date fechaCaducidad;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fecha_creacion")
    private Date fechaCreacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_pago")
    private Date fechaPago;

    @Column(name = "forma_pago")
    private String formaPago;

    @Column(name = "detalle_pago")
    private String detallePago;
    
    @Column(name="monto")
    @NotNull
    private double importe;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "socio")
    @NotNull
    private Socio socio;

    @JoinColumn(name = "categoria", referencedColumnName = "id_categoria")
    @OneToOne
    private Categoria categoria;

}