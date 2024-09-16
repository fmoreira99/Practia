package com.banco.clientespersonas.entity;
import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false, unique = true)
    private String identificacion;

    private String direccion;
    private String telefono;
}