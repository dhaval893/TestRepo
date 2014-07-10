package test.script;


import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Demo extends TestListenerAdapter {
  private int m_count = 0;
 
  public void onTestFailure(ITestResult tr) {
    log("F");
  }
 
  public void onTestSkipped(ITestResult tr) {
    log("S");
  }
 
  public void onTestSuccess(ITestResult tr) {
    log(".");
  }
 
  private void log(String string) {
    System.out.print(string);
    if (++m_count % 40 == 0) {
      System.out.println("");
    }
  }
} 