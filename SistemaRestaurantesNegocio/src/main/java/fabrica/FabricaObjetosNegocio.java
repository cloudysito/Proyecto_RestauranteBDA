/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabrica;

import DAOs.ClientesFrecuentesDAO;
import DAOs.IngredientesDAO;
import DAOs.ProductosDAO;
import Interfaces.IClientesFrecuentesDAO;
import Interfaces.IIngredientesDAO;
import Interfaces.IProductosDAO;
import implementaciones.ClientesBO;
import implementaciones.IngredientesBO;
import implementaciones.ProductosBO;
import interfaces.IClientesBO;
import interfaces.IIngredientesBO;
import interfaces.IProductosBO;

/**
 *
 * @author garfi
 */
public class FabricaObjetosNegocio {
    
     public static IClientesBO crearClientesBO(){
        IClientesFrecuentesDAO clientesDAO = new ClientesFrecuentesDAO();
        IClientesBO clientesBO = new ClientesBO(clientesDAO);
        return clientesBO;
    }
     
     public static IIngredientesBO CrearIngredientesBO(){
        IIngredientesDAO ingredientesDAO = new IngredientesDAO();
        IIngredientesBO ingredientesBO = new IngredientesBO(ingredientesDAO);
        return ingredientesBO;
    }
    
     public static IProductosBO crearProductosBO(){
         IProductosDAO productosDAO = new ProductosDAO();
         IProductosBO productosBO = new ProductosBO(productosDAO);
         return productosBO;
     }
}
