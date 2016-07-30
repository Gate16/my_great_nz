package org.govhack.gate16.service;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import org.govhack.gate16.MyGreatNzApp;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyGreatNzApp.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class WatsonServiceTest {

    @Ignore("Need to config password to make connection test work")
    @Test
    public void testConnection() {
        TextToSpeech service = new TextToSpeech();
        service.setUsernameAndPassword("username", "password");

        List<Voice> voices = service.getVoices().execute();
        System.out.println(voices);
    }

}
