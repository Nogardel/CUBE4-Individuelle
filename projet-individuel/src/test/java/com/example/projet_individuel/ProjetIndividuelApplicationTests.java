package com.example.projet_individuel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.projet_individuel.service.EmployeApiService;
import com.example.projet_individuel.controller.EmployeController;

@SpringBootTest
class ProjetIndividuelApplicationTests {

	@Autowired
	private EmployeController employeController;

	@Autowired
	private EmployeApiService employeService;

	@Test
	void contextLoads() {
		assertNotNull(employeController, "Le controller devrait être injecté");
		assertNotNull(employeService, "Le service devrait être injecté");
	}

	@Test
	void applicationStartsWithoutError() {
		// Vérifie que les composants essentiels sont bien configurés
		assertNotNull(employeService.getAllEmployes(),
				"Le service devrait pouvoir accéder à la base de données");
	}

}
