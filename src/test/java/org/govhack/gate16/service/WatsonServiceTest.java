package org.govhack.gate16.service;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;
import org.govhack.gate16.MyGreatNzApp;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    @Ignore
    @Test
    public void writeSoundFile(){
        TextToSpeech service = new TextToSpeech();
        service.setUsernameAndPassword("a5d1f0fa-0701-4e69-a7fa-7950076a6893", "hTXrzxyiw3Xk");

        try {
            String text = "this is text";
            InputStream stream = service.synthesize (text, Voice.EN_LISA, AudioFormat.WAV).execute();

            InputStream in = WaveUtils.reWriteWaveHeader(stream);
            OutputStream out = new FileOutputStream("/tmp/sound.wav");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
