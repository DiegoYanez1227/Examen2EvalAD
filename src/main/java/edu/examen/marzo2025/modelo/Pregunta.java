package edu.examen.marzo2025.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "num_opciones", nullable = true)
    private Integer numOpciones;

    @Column(name = "respuesta_correcta", nullable = true)
    private Integer respuestaCorrecta;

    @JsonIgnore
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaDeExamen> preguntasDeExamen;

    public Pregunta() {}

    public Pregunta(String nombre, Integer numOpciones, Integer respuestaCorrecta) {
        this.nombre = nombre;
        this.numOpciones = numOpciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumOpciones() {
        return numOpciones;
    }

    public void setNumOpciones(Integer numOpciones) {
        this.numOpciones = numOpciones;
    }

    public Integer getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(Integer respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}