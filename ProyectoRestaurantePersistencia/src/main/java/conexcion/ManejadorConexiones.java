/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Laboratorios
 */
public class ManejadorConexiones {

    public static EntityManager getEntityManager() {
                EntityManagerFactory emFactory;
        emFactory = Persistence.createEntityManagerFactory("com.mycompany_SistemaRestaurantesPersistencia_jar_1.0PU");
        EntityManager entityManager = emFactory.createEntityManager();
        return entityManager;

    }
}

