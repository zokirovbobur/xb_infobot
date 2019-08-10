package bbro.tg_bot_testing.tgBot;

import bbro.tg_bot_testing.category.CategoryRepo;
import bbro.tg_bot_testing.client.ClientData;
import bbro.tg_bot_testing.client.ClientDataRepo;
import bbro.tg_bot_testing.serviceItem.ServiceItem;
import bbro.tg_bot_testing.serviceType.ServiceTypeRepo;
import bbro.tg_bot_testing.serviceItem.ServiceItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


@Component
public class TgBot extends TelegramLongPollingBot {
    private TgBotInlineKeyboardMenus tgBotInlineMenus;
    private TgBotInlineKeyboardHandler requestHandler;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ServiceTypeRepo serviceTypeRepo;
    @Autowired
    private ServiceItemRepo serviceItemRepo;
    @Autowired
    private ClientDataRepo clientDataRepo;

    private ClientData currentClientData;

    private Update update;
    private Map<Integer,Long> activeChatIdMessageIdMap =new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        this.update = update;
        tgBotInlineMenus = new TgBotInlineKeyboardMenus();
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                startInlineKeyboardMenus(update);
            }
        }else if (update.hasCallbackQuery()) {
            handleUsersByInlineKeyboardMenus(update);
        }

    }

    public void startReplyKeyboardMenus(Update update){

    }
    public void continueReplyKeyboardMenus(Update update){

    }

    public void startInlineKeyboardMenus(Update update){
        long chat_id = update.getMessage().getChatId();
        try {
            SendMessage sendMessage = tgBotInlineMenus.initialMenu(
                    chat_id,"\uD83C\uDFE6",
                    tgBotInlineMenus.categoriesMenu(categoryRepo.findAll())
            );
            SendMessageToChat.deleteMessage(
                    update.getMessage().getMessageId(),
                    update.getMessage().getChatId(),
                    getBotToken()
            );
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void handleUsersByInlineKeyboardMenus(Update update){
        requestHandler = new TgBotInlineKeyboardHandler(update);
        requestHandler.setCategoryRepo(categoryRepo);
        requestHandler.setServiceTypeRepo(serviceTypeRepo);
        requestHandler.setServiceItemRepo(serviceItemRepo);
        requestHandler.setClientDataRepo(clientDataRepo);
        requestHandler.setCurrentClientData(currentClientData);
        continueInlineKeyboardMenus();

    }
    public void continueInlineKeyboardMenus(){
        EditMessageText editMessageText = requestHandler.userRequestHandler();
        if (editMessageText.getText().startsWith("serviceItemId")){
            long serviceItemId = findServiceItemId(editMessageText.getText());
            SendMessageToChat.deleteMessage(
                    update.getCallbackQuery().getMessage().getMessageId(),
                    update.getCallbackQuery().getMessage().getChatId(),
                    getBotToken()
            );
            ServiceItem serviceItem = serviceItemRepo.findByServiceItemId(serviceItemId);
            String text = serviceItem.getServiceItemDetail() + "%0A" +
                    serviceItem.getServiceItemUrl();
            SendMessageToChat.sendMessage(
                    text,
                    update.getCallbackQuery().getMessage().getChatId(),
                    getBotToken());
            SendMessageToChat.sendMessage(
                    "/start",
                    update.getCallbackQuery().getMessage().getChatId(),
                    getBotToken());
        }else {
            editTextExecute(editMessageText);
        }
    }

    public void editTextExecute(EditMessageText editMessageText){
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public long findServiceItemId(String callBack){
        String strId = callBack.replace("serviceItemId#","");
        return Long.valueOf(strId);
    }

    public boolean deleteChatHistoryUsingThreads(int messageId, long chatId){
        new Thread(()->{
            List<Exception> exceptions = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();
            final CyclicBarrier gate = new CyclicBarrier(messageId);
            for (int i = messageId; i > 0; i--) {
                try {
                    Runnable target = new DeleteMessageThread(i,chatId,getBotToken(),gate);
                    String name = " thread_" + i;
                    Thread t = new Thread(target, name);
                    t.start();
                    threads.add(t);
                }catch (Exception e){
                    exceptions.add(e);
                }
            }

            try {
                gate.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            //System.out.println(exceptions);
        }).start();

        return true;
    }

    @Override
    public String getBotUsername() {
        return "tesafbfndjksjnkfvdsbot";
    }
    @Override
    public String getBotToken() {
        return "712444289:AAGEHKqRVapP8TtUTCaCHTpuuALswIogp64";
    }
}
