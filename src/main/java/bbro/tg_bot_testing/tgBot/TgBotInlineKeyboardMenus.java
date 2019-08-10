package bbro.tg_bot_testing.tgBot;

import bbro.tg_bot_testing.category.Category;
import bbro.tg_bot_testing.serviceType.ServiceType;
import bbro.tg_bot_testing.serviceItem.ServiceItem;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class TgBotInlineKeyboardMenus {


    public SendMessage initialMenu(long chatId, String s, InlineKeyboardMarkup markupKeyboard){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(markupKeyboard);
        return sendMessage;
    }

    public EditMessageText sendMenu(
            long chatId,
            String text,
            int messageId,
            InlineKeyboardMarkup markupKeyboard
    ){
        EditMessageText new_message = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(toIntExact(messageId))
                .setText(text)
                .setReplyMarkup(markupKeyboard);
      return new_message;
    }

    public InlineKeyboardMarkup categoriesMenu(List<Category> categories) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> bt_home = new ArrayList<>();
        List<InlineKeyboardButton> bt_categories = new ArrayList<>();
        categories.forEach(category -> {
            bt_categories.add(
                    new InlineKeyboardButton().
                            setText(category.getCategoryName())
                            .setCallbackData(
                                    "categoryId#"+category.getCategoryId()
                            )
            );
        });
        buttons.add(bt_categories);
        buttons.add(bt_home);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;
    }
    public InlineKeyboardMarkup servicesMenu(List<ServiceType> serviceTypes) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> bt_back = new ArrayList<>();
        List<InlineKeyboardButton> bt_services = new ArrayList<>();
        bt_back.add(new InlineKeyboardButton().setText("<==").
                setCallbackData("categories"));
        serviceTypes.forEach(serviceType -> {
            bt_services.add(
                    new InlineKeyboardButton().
                            setText(
                                    serviceType.getServiceType()
                            )
                            .setCallbackData(
                                    "serviceId#"+serviceType.getServiceId()
                            )
            );
        });
        buttons.add(bt_services);
        buttons.add(bt_back);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;
    }
    public InlineKeyboardMarkup serviceItemsMenu(List<ServiceItem> serviceItems) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> bt_back = new ArrayList<>();
        bt_back.add(new InlineKeyboardButton().setText("<==").
                setCallbackData("categoryId#"+ serviceItems.get(0).getServiceType().getCategory().getCategoryId()));
        serviceItems.forEach(serviceItem -> {
                    List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
                    inlineKeyboardButtons.add(new InlineKeyboardButton().
                            setText(serviceItem.getServiceItemName())
                            .setCallbackData(
                                    "serviceItemId#" + serviceItem.getServiceItemId()
                            ));
                    buttons.add(inlineKeyboardButtons);
                });

        buttons.add(bt_back);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;

    }
    public InlineKeyboardMarkup orderMenu(ServiceItem serviceItem) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> bt_back = new ArrayList<>();
        List<InlineKeyboardButton> bt_submit = new ArrayList<>();
        bt_back.add(new InlineKeyboardButton().setText("<==").
                setCallbackData("categoryId#"+ serviceItem.getServiceType().getCategory().getCategoryId()));
        bt_submit.add(new InlineKeyboardButton().setText("Yes")
                .setCallbackData("submit serviceItemId#"+ serviceItem.getServiceItemId())
        );
        buttons.add(bt_submit);
        buttons.add(bt_back);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;

    }

    public InlineKeyboardMarkup errorMenu() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Main Menu").
                setCallbackData("main_menu"));
        buttons.add(buttons1);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;

    }
    public InlineKeyboardMarkup mainMenu() {


        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Categories").
                setCallbackData("categories"));


        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return  markupKeyboard;

    }

}
