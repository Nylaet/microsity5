/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.pagesControllers;

import entitys.main.support.News;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Panker
 */
public class IndexControllerIT {
    
    public IndexControllerIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class IndexController.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        IndexController instance = new IndexController();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIconsList method, of class IndexController.
     */
    @Test
    public void testGetIconsList() {
        System.out.println("getIconsList");
        IndexController instance = new IndexController();
        List<String> expResult = null;
        List<String> result = instance.getIconsList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editNews method, of class IndexController.
     */
    @Test
    public void testEditNews() {
        System.out.println("editNews");
        News edit = null;
        IndexController instance = new IndexController();
        instance.editNews(edit);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNews method, of class IndexController.
     */
    @Test
    public void testCreateNews() {
        System.out.println("createNews");
        IndexController instance = new IndexController();
        instance.createNews();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteNews method, of class IndexController.
     */
    @Test
    public void testDeleteNews() {
        System.out.println("deleteNews");
        News delete = null;
        IndexController instance = new IndexController();
        instance.deleteNews(delete);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAddedNews method, of class IndexController.
     */
    @Test
    public void testGetAddedNews() {
        System.out.println("getAddedNews");
        IndexController instance = new IndexController();
        News expResult = null;
        News result = instance.getAddedNews();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAddedNews method, of class IndexController.
     */
    @Test
    public void testSetAddedNews() {
        System.out.println("setAddedNews");
        News addedNews = null;
        IndexController instance = new IndexController();
        instance.setAddedNews(addedNews);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNewses method, of class IndexController.
     */
    @Test
    public void testGetNewses() {
        System.out.println("getNewses");
        IndexController instance = new IndexController();
        List<News> expResult = null;
        List<News> result = instance.getNewses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNewses method, of class IndexController.
     */
    @Test
    public void testSetNewses() {
        System.out.println("setNewses");
        List<News> newses = null;
        IndexController instance = new IndexController();
        instance.setNewses(newses);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
