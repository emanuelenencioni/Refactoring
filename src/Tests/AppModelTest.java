package src.Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.applicationModel.*;

import java.util.*;

public class AppModelTest{

    @Test
    public void testEntity(){
        Entity e1 = new Entity("E1");
        Entity e2 = new Entity("E2");

        assertTrue(!e1.equals(e2));
        e1 = new Entity(e2);
        assertTrue(e1.equals(e2));
        e1 = new Entity("E2");
        assertTrue(e1.equals(e2));
    }
    @Test
    public void testCoupling(){
        Entity e1 = new Entity("E1");
        Entity e2 = new Entity("E2");
        Coupling c = new Coupling(e1, e2, Type.CC, .76f);
        Coupling c2 = new Coupling(new Entity("E3"), new Entity("E4"),Type.CC, .5f);
        assertEquals(false, c.equals(c2));
        c2 = new Coupling(e1, e2, Type.CQ, 0.76f);
        assertEquals(false, c.equals(c2));
        c2 = new Coupling(e1, e2, Type.CC, 0.2f);
        assertTrue(!c.equals(c2));
        assertTrue(c.equals(new Coupling(new Entity("E1"), new Entity("E2"), Type.CC, .76f)));
        c2 = new Coupling(e1, new Entity("E3"), Type.CC, .76f);
        assertTrue(!c.equals(c2));
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
        ArrayList<Coupling> cl2 = new ArrayList<Coupling>();
        ArrayList<Entity> el = new ArrayList<Entity>();
        for(int i=0;i<20;i++)
            el.add(new Entity("E"+i));
        
        for(int i=0;i<10;i++){
            cl.add(new Coupling(el.get(i), el.get(i+10), Type.CC, i));
            cl2.add(new Coupling(el.get(i), el.get(i+10), Type.CQ, i));
        }
        CoOccurrenceMatrix cm = new CoOccurrenceMatrix(Type.CC, cl);
        CoOccurrenceMatrix cm2 = new CoOccurrenceMatrix(Type.CC, cl);
        CoOccurrenceMatrix cm3 = new CoOccurrenceMatrix(Type.CQ, cl2);
        assertTrue(cm.equals(cm2));
        assertTrue(!cm.equals(cm3));
        assertTrue(!cm.equals(null));
        cm.addCoValue(new Coupling(el.get(3),el.get(10),Type.CC, 0.25f));
        assertTrue(!cm.equals(cm2));
        Float x =  cm.getValue(el.get(0), el.get(10));
        
        assertEquals(0, x.intValue());
        assertEquals(0, cm.getValue(el.get(0),new Entity("E10")), 0.00000001);

        Entity z = new Entity("E30");
        cm.addCoValue(Type.CC, el.get(0), z, 0.5f);
        
        float x1 = (float) cm.getValue(el.get(0), z);
        assertEquals(0.5,x1 , 0.00005f);

        Entity z1 = new Entity("E50");
        Entity y1 = new Entity("E60"); 
        cm.addCoValue(Type.CC, z1,y1,0.7f);
        assertEquals(0.7f,(float) cm.getValue(z1, y1), 0.00005);
        cm.addCoValue(Type.CC, y1,z1,0.9f);
        assertEquals(0.9f,(float) cm.getValue(y1, z1), 0.00005);



    }

    @Test
    public void testEndPoint(){
        ArrayList<Coupling> cl = new ArrayList<Coupling>();
        ArrayList<Entity> el = new ArrayList<Entity>();
        for(int i=0;i<20;i++)
            el.add(new Entity("E"+i));
        
        for(int i=0;i<10;i++){
            cl.add(new Coupling(el.get(i), el.get(i+10), Type.CC, i));
            cl.add(new Coupling(el.get(i+10), el.get(i), Type.CC, i+10));
        }
        cl.add(new Coupling(el.get(0), el.get(10), Type.CC,100));
        ApplicationAbstraction aa = new EndPoint(cl, "EP1", 0.5f);
        assertNull(aa.getMapper().get(Type.CC));
        cl.remove(cl.size()-1);
        aa = new EndPoint(cl, "EP1", 0.5f);

        assertEquals(0.5, aa.getFrequency(), 0.0005);
        assertEquals("EP1", aa.getID());
       
        assertEquals(5, (float) aa.getMapper().get(Type.CC).getValue(el.get(5), el.get(15)), 0.000005);
        assertEquals(15, (float) aa.getMapper().get(Type.CC).getValue(el.get(15), el.get(5)), 0.00005);
        assertEquals(0, (float) aa.getMapper().get(Type.CC).getValue(el.get(0), el.get(10)), 0.000005);
        assertEquals(10, (float) aa.getMapper().get(Type.CC).getValue(el.get(10), el.get(0)), 0.000005);

        ApplicationAbstraction aa2 = new EndPoint(cl, "EP1", 0.5f);
        assertTrue(aa.equals(aa2));
        assertTrue(!aa.equals(null));
    }

    @Test
    public void testAverage(){
        ArrayList<ApplicationAbstraction> epList = new ArrayList<>();
        ArrayList<Coupling> cl = new ArrayList<Coupling>();
        ArrayList<Entity> el = new ArrayList<Entity>();
        for(int i=0;i<20;i++)
                el.add(new Entity("E"+i));
        for(int i=0;i<10;i++){
            cl.add(new Coupling(el.get(i), el.get(i+10), Type.CC, i+1));
            cl.add(new Coupling(el.get(i+10), el.get(i),Type.CC, (i+1)*100));
        }
        
        epList.add(new EndPoint(cl, "EP1", 0.5f));
        epList.add(new EndPoint(cl, "EP2", 0.25f));

        epList.get(0).buildMatrices();
        epList.get(1).buildMatrices();

        BuildCoMatStrategy bs = new Average();
        ArrayList<CoOccurrenceMatrix> ms = bs.buildCoMat(epList);
        float value,value1;
        for(int i = 0; i<10; i++){
            value = (float) ((i+1)*0.5 + (i+1)*0.25)/2f;
            value1 = (float ) ((i+1)*0.5*100 + (i+1)*0.25*100)/2f;
            assertEquals(value, ms.get(0).getValue(el.get(i), el.get(i+10)), 0.00001f);
            assertEquals(value1, ms.get(0).getValue(el.get(i+10),el.get(i)),0.00001f);
        }
    }

    @Test
    public void testUseCase(){
        ArrayList<EndPoint> epList = new ArrayList<>();
        ArrayList<Coupling> cl = new ArrayList<Coupling>();
        ArrayList<Coupling> cl2 = new ArrayList<Coupling>();
        ArrayList<Entity> el = new ArrayList<Entity>();
        for(int i=0;i<20;i++)
                el.add(new Entity("E"+i));
        for(int i=0;i<10;i++){
            cl.add(new Coupling(el.get(i), el.get(i+10), Type.CC, i+1));
            cl.add(new Coupling(el.get(i+10), el.get(i),Type.CC, (i+1)*100));
            cl2.add(new Coupling(el.get(i), el.get(i+10), Type.CC, (i+1)*1000));
        }
        
        epList.add(new EndPoint(cl, "EP1", 0.5f));
        epList.add(new EndPoint(cl, "EP2", 0.25f));
        epList.add(new EndPoint(cl2, "EP2", 0.15f));
        epList.add(new EndPoint(cl2,"EP2",0.15f));
        for(int i = 0; i< epList.size(); i++)
            epList.get(i).buildMatrices();
        UseCase uc = new UseCase("UC1", 1, epList);
        uc.setStrategy(new Average());
        uc.buildMatrices();
        CoOccurrenceMatrix cocm = uc.getMapper().get(Type.CC);
        float value,value1;
        for(int i = 0; i<10; i++){
            value = (float) ((i+1)*0.5 + (i+1)*0.25 + (i+1)*1000f*0.15f)/3f;
            value1 = (float ) ((i+1)*0.5*100 + (i+1)*0.25*100)/3f;
            assertEquals(value, cocm.getValue(el.get(i), el.get(i+10)), 0.00001f);
            assertEquals(value1, cocm.getValue(el.get(i+10),el.get(i)),0.00001f);
        }
        UseCase uc2 = new UseCase("UC1", 1, epList);
        assertTrue(!uc.equals(uc2));
        assertTrue(!uc.equals(null));
        UseCase uc3 = new UseCase("UC1", 1, epList, new Average());
        assertTrue(uc.equals(uc3));
    }
}