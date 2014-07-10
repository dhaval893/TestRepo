 import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.TestNG;

public class SampleTest {
	
   String message = "Hello World";
  

   @Test
   public void testPrintMessage() {
        Assert.assertEquals(message, "Hello World");
   }
}
