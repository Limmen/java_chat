/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author kim
 */
public class RegularMessageTest {
    
    private static RegularMessage instance;
    public RegularMessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new RegularMessage("", "");
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
     * Test of setName method, of class RegularMessage.
     */
    @org.junit.Test
    public void testSetName() {
        System.out.println("RegularMessageTest : setName");
        instance.setName("testname");
        assertEquals("testname", instance.getName());        
    }

    /**
     * Test of setString method, of class RegularMessage.
     */
    @org.junit.Test
    public void testSetString() {
        System.out.println("RegularMessageTest : setString");
        instance.setString("teststring");
        assertEquals("teststring", instance.getString()); 
    }

    /**
     * Test of getString method, of class RegularMessage.
     */
    @org.junit.Test
    public void testGetString() {
        System.out.println("RegularMessageTest : getString");
        instance.setString("teststring");
        assertEquals("teststring", instance.getString());
    }

    /**
     * Test of getName method, of class RegularMessage.
     */
    @org.junit.Test
    public void testGetName() {
        System.out.println("RegularMessageTest : getName");
        instance.setName("testname");
        assertEquals("testname", instance.getName());
    }
    
}
