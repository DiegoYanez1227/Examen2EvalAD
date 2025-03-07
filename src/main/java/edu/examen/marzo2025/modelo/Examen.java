package edu.examen.marzo2025.modelo;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "examen")
public class Examen {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "curso", nullable = false)
    private Integer curso;

    @Column(name = "grupo", length = 10)
    private String grupo;

    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaDeExamen> preguntasDeExamen;


    public Examen() {}

    public Examen(Date fecha, int curso, String grupo) {
        this.fecha = fecha;
        this.curso = curso;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}