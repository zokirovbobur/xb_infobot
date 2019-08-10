package bbro.tg_bot_testing.tgBot;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SendMessageToChat {
    public static void sendMessage(String message,long chatId,String token) {
        String urlString = "https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+message;
        sendRequest(urlString);
    }
    public static void deleteMessage(long messageId,long chatId,String token) {
        String urlString = "https://api.telegram.org/bot%s/deleteMessage?chat_id=%s&message_id=%s";
        urlString = String.format(urlString, token, chatId, messageId);

        sendRequest(urlString);
    }
    private static void sendRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}