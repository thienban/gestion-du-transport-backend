package controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import gdp.api.Application;
import gdp.api.controller.AnnonceController;
import gdp.api.controller.ReservCovoiturageController;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithMockUser(username = "robertsross@sultraxin.com")
public class ReservCovoiturageTest {

	@Autowired
	ReservCovoiturageController reservController;
	@Autowired
	AnnonceRepository annonceRepository;
	@Autowired
	CollaborateurRepository collabRepository;

	private final String TEST_USER = "robertsross@sultraxin.com"; // should be the same as in the annotation
																	// @WithMockUser

	@Test
	public void testMesReservations() {
	}

	@Test
	public void testReservationsDisponibles() {
	}

	@Test
	public void testCreerReservations() {
	}

}
