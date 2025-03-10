package edu.examen.marzo2025.persistencia.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.examen.marzo2025.modelo.Examen;
import edu.examen.marzo2025.modelo.Pregunta;
import edu.examen.marzo2025.persistencia.ExamenesDAO;
import edu.examen.marzo2025.persistencia.ExamenesDAOImpl;

public class DaoTest {

	private static ExamenesDAO examenesDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		examenesDAO = new ExamenesDAOImpl();
	}

	@Test
	public void testRecuperarExamenes() {
		List<Examen> resultado = recuperarExamenes();
		assertTrue(resultado.isEmpty());
	}

	@Test
	public void testNumeroPreguntasDelExamen() {

		Examen examen=null;
		try {
			examen = new Examen(new SimpleDateFormat("dd/MM/yyyy").parse("15/11/2002"), 1, "A");
		} catch (ParseException e) {
			e.printStackTrace();
		} //TODO construye el examen con los datos  XX
		boolean resultadoAniadirExamen = examenesDAO.aniadirExamen(examen);
		assertTrue (resultadoAniadirExamen);
		List<Examen> listaExamenes = recuperarExamenes();
		assertFalse(listaExamenes.isEmpty());
		Pregunta pregunta = new Pregunta("Acceso a datos",4, 2); //TODO construye una pregunta	XX
		boolean resultadoAnidirPregunta = examenesDAO.aniadirPregunta(pregunta);
		assertTrue (resultadoAnidirPregunta);
		List<Pregunta> listaPreguntas = examenesDAO.recuperarPreguntas();
		assertNotNull(listaPreguntas);
		assertFalse(listaPreguntas.isEmpty());
		int idExamen = listaExamenes.get(0).getId();
		int idPregunta = listaPreguntas.get(0).getId();
		boolean resultadoAniadirPreguntaExamen = examenesDAO.aniadirPreguntaAExamen(idExamen, idPregunta);
		assertTrue (resultadoAniadirPreguntaExamen);
		int resultado = examenesDAO.numeroPreguntasDelExamen(idExamen);
		assertEquals(1,resultado);
		boolean resultadoElminarPregunta = examenesDAO.eliminarPregunta(idPregunta);
		assertTrue(resultadoElminarPregunta);
		boolean resultadoElminarExamen = examenesDAO.eliminarExamen(idExamen);
		assertTrue(resultadoElminarExamen);
	}

	private List<Examen> recuperarExamenes() {
		List<Examen> resultado = examenesDAO.recuperarExamenes();
		assertNotNull(resultado);
		return resultado;
	}
	
	
	@Test
	public void testCorrecto() {
		
	}

	
}
