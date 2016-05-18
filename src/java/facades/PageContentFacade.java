/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entitys.main.support.PageContent;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Panker
 */
@Stateless
public class PageContentFacade extends AbstractFacade<PageContent> {

    @PersistenceContext(unitName = "MicroSityV5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PageContentFacade() {
        super(PageContent.class);
    }
    
}
