package baiduLearning;

import java.net.InetAddress;
import java.util.Arrays;

import org.junit.Test;

public class TestClass {
	@Test
	public void testName() throws Exception {
		
		System.out.println(InetAddress.getByName("www.oracle.com"));
		System.out.println(InetAddress.getLocalHost().getHostName());
		System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		System.out.println(Arrays.toString(InetAddress.getLocalHost().getAddress()));
	}
}
