package bbro.tg_bot_testing.tgBot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMessageThread implements Runnable{
    private int messageId;
    private long chatId;
    private String token;
    CyclicBarrier gate;
    @Override
    public void run() {
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        SendMessageToChat.deleteMessage(messageId,chatId,token);
    }
}
