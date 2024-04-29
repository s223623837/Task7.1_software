package sit707_week7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class BodyTemperatureMonitorTest {
	private BodyTemperatureMonitor bodyTemperatureMonitor;
    private TemperatureSensor temperatureSensorMock;
    private CloudService cloudServiceMock;
    private NotificationSender notificationSenderMock;

    @Before
    public void setUp() {
        temperatureSensorMock = mock(TemperatureSensor.class);
        cloudServiceMock = mock(CloudService.class);
        notificationSenderMock = mock(NotificationSender.class);

        bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensorMock, cloudServiceMock, notificationSenderMock);
    }
    
	@Test
	public void testStudentIdentity() {
		String studentId = "223623837";
		Assert.assertNotNull("Student ID is ", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Bidhan Babu Gupta";
		Assert.assertNotNull("Student name is", studentName);
	}
	
	@Test
    public void testReadTemperatureNegative() {
		// Stub temperatureSensor to return a negative temperature reading
        when(temperatureSensorMock.readTemperatureValue()).thenReturn(-1.0);

        // Call the method under test
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verify that temperatureSensor.readTemperatureValue() was called
        verify(temperatureSensorMock).readTemperatureValue();

        // Assert the behavior for a negative temperature reading
        Assert.assertTrue("Temperature should be negative", temperature < 0);
    }

    @Test
    public void testReadTemperatureZero() {
    	 // Stub temperatureSensor to return zero temperature reading
        when(temperatureSensorMock.readTemperatureValue()).thenReturn(0.0);

        // Call the method under test
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verify that temperatureSensor.readTemperatureValue() was called
        verify(temperatureSensorMock).readTemperatureValue();

        // Assert the behavior for a zero temperature reading
        Assert.assertEquals("Temperature should be zero", 0.0, temperature, 0.01);
    }

    @Test
    public void testReadTemperatureNormal() {
    	 // Stub temperatureSensor to return a normal temperature reading
        when(temperatureSensorMock.readTemperatureValue()).thenReturn(36.5);

        // Call the method under test
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verify that temperatureSensor.readTemperatureValue() was called
        verify(temperatureSensorMock).readTemperatureValue();

        // Assert the returned temperature
        Assert.assertEquals(36.5, temperature, 0.01);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
    	// Stub temperatureSensor to return an abnormally high temperature reading
        when(temperatureSensorMock.readTemperatureValue()).thenReturn(45.0); // Assuming abnormally high temperature

        // Call the method under test
        double temperature = bodyTemperatureMonitor.readTemperature();

        // Verify that temperatureSensor.readTemperatureValue() was called
        verify(temperatureSensorMock).readTemperatureValue();

        // Assert the behavior for an abnormally high temperature reading
        Assert.assertTrue("Temperature should be abnormally high", temperature > 40.0); // Assuming threshold for high temperature
 
    }
    @Test
    public void testReportTemperatureReadingToCloud() {
        // Call the method under test
        TemperatureReading temperatureReading = new TemperatureReading();
        bodyTemperatureMonitor.reportTemperatureReadingToCloud(temperatureReading);

        // Verify that cloudService.sendTemperatureToCloud() was called with the correct parameter
        verify(cloudServiceMock).sendTemperatureToCloud(temperatureReading);
    }

    @Test
    public void testInquireBodyStatus_Normal() {
        // Stub cloudService to return "NORMAL" status
        when(cloudServiceMock.queryCustomerBodyStatus(any(Customer.class))).thenReturn("NORMAL");

        // Call the method under test
        bodyTemperatureMonitor.inquireBodyStatus();

        // Verify that notificationSender.sendEmailNotification() was called with the correct parameter
        verify(notificationSenderMock).sendEmailNotification(any(Customer.class), eq("Thumbs Up!"));
    }

    @Test
    public void testInquireBodyStatus_Abnormal() {
        // Stub cloudService to return "ABNORMAL" status
        when(cloudServiceMock.queryCustomerBodyStatus(any(Customer.class))).thenReturn("ABNORMAL");

        // Call the method under test
        bodyTemperatureMonitor.inquireBodyStatus();

        // Verify that notificationSender.sendEmailNotification() was called with the correct parameter
        verify(notificationSenderMock).sendEmailNotification(any(FamilyDoctor.class), eq("Emergency!"));
    }



	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testReportTemperatureReadingToCloud() {
//		// Mock reportTemperatureReadingToCloud() calls cloudService.sendTemperatureToCloud()
//		
//		Assert.assertTrue("Not tested", false);
//	}
	
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testInquireBodyStatusNormalNotification() {
//		Assert.assertTrue("Not tested", false);
//	}
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */
//	@Test
//	public void testInquireBodyStatusAbnormalNotification() {
//		Assert.assertTrue("Not tested", false);
//	}
}
