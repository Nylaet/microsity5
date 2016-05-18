/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entitys.main.facility.SensorsData;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Panker
 */
@Stateless
public class SensorsDataFacade extends AbstractFacade<SensorsData> {

    @PersistenceContext(unitName = "MicroSityV5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SensorsDataFacade() {
        super(SensorsData.class);
    }
    
}
