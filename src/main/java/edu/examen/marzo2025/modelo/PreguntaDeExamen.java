package edu.examen.marzo2025.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "preguntaDeExamen")
public class PreguntaDeExamen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_examen", nullable = false)
	private Examen examen;

	@ManyToOne
	@JoinColumn(name = "id_pregunta", nullable = false)
	private Pregunta pregunta;

	@Column(name = "posicion", nullable = false)
	private Integer posicion;

	public PreguntaDeExamen() {}

	public PreguntaDeExamen(Examen examen, Pregunta pregunta, int posicion) {
		this.examen = examen;
		this.pregunta = pregunta;
		this.posicion = posicion;
	}


	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
