package com.crud.tasks.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AdminConfigTest {

    @Test
    public void testGetAdminMail() {
        //Given
        AdminConfig adminConfig = new AdminConfig();

        //When
        String actualMail = adminConfig.getAdminMail();
        String expectedMail = "testmailforjava66@gmail.com";

        //Then
        assertEquals(expectedMail, actualMail);
    }
}
