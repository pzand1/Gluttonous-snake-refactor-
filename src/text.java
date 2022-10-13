import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class text {
    @Test
    public void textDegree(){
        assertEquals(control.degree(-90), 270, 0.0001);
        assertEquals(control.degree(370), 10, 0.0001);
        assertEquals(control.degree(-270), 90, 0.0001);
        assertEquals(control.degree(90), 90, 0.0001);
        assertEquals(control.degree(359), 359, 0.0001);
    }
    @Test
    public void textTrun(){
        assertEquals(control.Trun(30, 209), 'r');
        assertEquals(control.Trun(30, 211), 'l');
        assertEquals(control.Trun(210, 31), 'l');
        assertEquals(control.Trun(210, 29), 'r');
//        assertEquals(control.(359), 359);
    }
}
