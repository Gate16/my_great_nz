package org.govhack.gate16.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kevintr on 31/07/16.
 */
@RestController
@RequestMapping("/api/public")
public class WatsonResource {
    private final Logger logger = LoggerFactory.getLogger(WatsonResource.class);

    @RequestMapping(value = "/speak",
            method = RequestMethod.GET)
    @Timed
    public void getSpeak(HttpServletResponse response, String text) {
//        text = "Good morning everyone. Kevin had less sleep last night and successfully integrate with Watson. " +
//                "We could use this for result speaking out from the search. What should we do for our story ";

        InputStream in = null;
        OutputStream out = null;
        try {
            TextToSpeech textService = new TextToSpeech();
            textService.setUsernameAndPassword("a5d1f0fa-0701-4e69-a7fa-7950076a6893", "hTXrzxyiw3Xk");

            String voice = "en-US_LisaVoice";

            //String format = "audio/ogg; codecs=opus";
            in = textService.synthesize(text, new Voice(voice, null, null), AudioFormat.WAV).execute();

            out = response.getOutputStream();
            byte[] buffer = new byte[2048];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (Exception e) {
            // Log something and return an error message
            logger.error("Got error: " + e.getMessage(), e);
        } finally {
            close(in);
            close(out);
        }

    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

}
