package bbro.tg_bot_testing.tgBot;

import bbro.tg_bot_testing.category.CategoryRepo;
import bbro.tg_bot_testing.client.ClientData;
import bbro.tg_bot_testing.client.ClientDataRepo;
import bbro.tg_bot_testing.serviceItem.ServiceItemRepo;
import bbro.tg_bot_testing.serviceType.ServiceTypeRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TgBotInlineKeyboardHandler {
    private Update update;
    private  String call_data;
    private int message_id;
    private long chat_id;
    private TgBotInlineKeyboardMenus tgBotInlineMenus;
    private ClientData currentClientData;


    private CategoryRepo categoryRepo;
    private ServiceTypeRepo serviceTypeRepo;
    private ServiceItemRepo serviceItemRepo;
    private ClientDataRepo clientDataRepo;

    public TgBotInlineKeyboardHandler(Update update) {
        tgBotInlineMenus = new TgBotInlineKeyboardMenus();
        this.update = update;
        call_data = update.getCallbackQuery().getData();
        message_id = update.getCallbackQuery().getMessage().getMessageId();
        chat_id = update.getCallbackQuery().getMessage().getChatId();

    }

    public EditMessageText userRequestHandler(){
        if (call_data.equals("categories")){
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "\uD83C\uDFE6",
                    message_id,
                    tgBotInlineMenus.categoriesMenu(categoryRepo.findAll())
            );
        }else if(call_data.equals("main_menu")){
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "\uD83C\uDFE6",
                    message_id,
                    tgBotInlineMenus.mainMenu()
            );
        }
        else if (isCategoryId(call_data)){
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "\uD83C\uDFE6",
                    message_id,
                    tgBotInlineMenus.servicesMenu(
                            serviceTypeRepo
                                    .findAllByCategoryCategoryId(
                                            findCategoryId(call_data)
                                    )
                    )
            );
        }else if(isServiceId(call_data)){
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "\uD83C\uDFE6",
                    message_id,
                    tgBotInlineMenus.serviceItemsMenu(
                            serviceItemRepo
                                    .findAllByServiceTypeServiceId(
                                    findServiceId(call_data)
                                    )
                    )
            );
        }else if(isServiceItemId(call_data)){

            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "serviceItemId#" + findServiceItemId(call_data),
                    message_id,
                    tgBotInlineMenus.orderMenu(serviceItemRepo.findByServiceItemId(findServiceItemId(call_data)))
            );
        }else if(isSubmitted(call_data)){
            currentClientData = new ClientData();
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "\uD83C\uDFE6",
                    message_id,
                    tgBotInlineMenus.mainMenu()
            );
        }
            //case of error
        else {
            return tgBotInlineMenus.sendMenu(
                    chat_id,
                    "Something wrong with request",
                    message_id,
                    tgBotInlineMenus.errorMenu()
            );
        }
    }

    public boolean isCategoryId(String callBack){
        return callBack.startsWith("categoryId");
    }
    public long findCategoryId(String callBack){
        String strId = callBack.replace("categoryId#","");
        return Long.valueOf(strId);
    }
    public boolean isServiceId(String callBack){
        return callBack.startsWith("serviceId");
    }
    public long findServiceId(String callBack){
        String strId = callBack.replace("serviceId#","");
        return Long.valueOf(strId);
    }
    public boolean isServiceItemId(String callBack){
        return callBack.startsWith("serviceItemId");
    }
    public long findServiceItemId(String callBack){
        String strId = callBack.replace("serviceItemId#","");
        return Long.valueOf(strId);
    }
    public boolean isSubmitted(String callBack){
        return callBack.startsWith("submit serviceItemId#");
    }
    public long findWorkerIdFromSubmission(String callBack){
        String strId = callBack.replace("submit serviceItemId#","");
        return Long.valueOf(strId);
    }
    public boolean isPhone(Update update){
        try {
            Integer.parseInt(update.getMessage().getText());
            return true;
        }catch (NumberFormatException | NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
