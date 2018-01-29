package controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import gdp.api.Application;
import gdp.api.controller.AnnonceController;
import gdp.api.entities.Annonce;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithMockUser(username="robertsross@sultraxin.com")
public class AnnonceControllerTest {

	@Autowired
	AnnonceController annonceController;
	@Autowired
	AnnonceRepository annonceRepository;
	@Autowired
	CollaborateurRepository collabRepository;

	private final String TEST_USER = "robertsross@sultraxin.com"; // should be the same as in the annotation @WithMockUser

	@Test
	public void testGetUserEmail() {
		String email = annonceController.GetUserEmail();
		assertThat(email).isEqualTo(TEST_USER);
	}
	
	@Test
	public void testFindAllAnnonces() {
		List<Annonce> annonces = annonceController.findAllAnnonces();
		assertThat(annonces.size()).isGreaterThan(0);
		assertThat(annonces).doesNotHaveDuplicates();
	}

	@Test
	public void testCreerAnnonce() {
		int mesAnnoncesSize1 = annonceRepository.findByAuteur(collabRepository.findByEmail(TEST_USER)).size();
		Annonce nouvAnnonce = new Annonce();
		annonceController.creerAnnonce(nouvAnnonce);
		int mesAnnoncesSize2 = annonceRepository.findByAuteur(collabRepository.findByEmail(TEST_USER)).size();
		assertThat(mesAnnoncesSize2).isEqualTo(mesAnnoncesSize1 + 1);
	}

	@Test
	public void testGetUserAnnonces() {
		List<Annonce> mesAnnonces = annonceController.getUserAnnonces();
		assertThat(mesAnnonces).doesNotHaveDuplicates();
		assertThat(mesAnnonces.stream().allMatch(a->a.getAuteur().getEmail().equals(TEST_USER))).isTrue();
	}

}
