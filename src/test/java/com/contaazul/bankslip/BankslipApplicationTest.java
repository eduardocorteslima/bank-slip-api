package com.contaazul.bankslip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankslipApplication.class)
@ActiveProfiles(value = { "test" })
public class BankslipApplicationTest {

	@Test
	public void contextLoads() {
	}

}
