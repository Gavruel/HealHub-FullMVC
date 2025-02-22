package br.com.fiap.healhub.chat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {
    public static String chatGPT(String text) throws Exception {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer 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");

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        String botResponse = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
        System.out.println(botResponse);
        return botResponse;

    }
}
