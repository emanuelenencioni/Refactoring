package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import applicationModel.*;
import java.util.*;

public class AppModelTest{

    @Test
    public void testEntity(){
        Entity e1 = new Entity("E1");
        Entity e2 = new Entity("E2");

        assertEquals(false,e1.equals(e2));
        e1 = new Entity(e2);
        assertEquals(true, e1.equals(e2));
    }
    @Test
    public void testCoupling(){
        Entity e1 = new Entity("E1");
        Entity e2 = new Entity("E2");
        Coupling c = new Coupling(e1, e2, Type.CC, .76f);
        Coupling c2 = new Coupling(new Entity("E3"), new Entity("E4"),Type.CC, .5f);
        assertEquals(false, c.equals(c2));
        assertEquals(Type.CC, c.getType());
        assertEquals(e1,c.getSrcEntity());
        assertEquals(e2, c.getDestEntity()); 
        Entity e3 = new Entity("E9");
        c.setSrcEntity(e3);
        assertEquals(e3, c.getSrcEntity());
    }
    @Test
    public void testCoOccurrenceMatrix(){
        ArrayList<Coupling> cl = new ArrayList<Coupling>();
        ArrayList<Entity> el = new ArrayList<Entity>();
        for(int i=0;i<20;i++){
            el.add(new Entity("E"+i));
        }
        for(int i=0;i<10;i++){
            cl.add(new Coupling(el.get(i), el.get(i+10), Type.CC, i));
            
        }
        CoOccurrenceMatrix cm = new CoOccurrenceMatrix(Type.CC, cl);
        Float x =  cm.getValue(el.get(0), el.get(10));
        
        assertEquals(0, x.intValue());
        assertNull(cm.getValue(el.get(0),new Entity("E10")));
    }
}