package lab4gr1.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Бин для обработки входящих строковых сообщений
 *
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/textmessage")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TextMessageBean implements MessageListener {

    @EJB                // 
    private MessageBeanLocal messageBean;

    public TextMessageBean() {
    }

    @Override                // переопределяется из интерфейса MessageListener
    public void onMessage(Message message) {
        System.out.println("Вызван метод onMessage()");    // оповещение вызова
        TextMessage tm = (TextMessage) message;   // создается ссылочная переменная интерфейса TextMessage
        String text = "";                    // создается строковая ссылочная переменная 
        try {
            text = tm.getText();              //  Получает строку, содержащую данные сообщения.
            System.out.println("Получено сообщение: " + text);    // выводится сообщение
            //создание и отправление в БД объекта типа Entity
            Smessage sm = new Smessage();    // создается ссылочная переменая класса сущности Entity 
            sm.setMessage(text);             // переменная получает сообщение 
            messageBean.addSmessage(sm);     // переменная messageBean добавляет сообщение в метод addSmessage() 
            //интерфейса MessageBeanLocal, который переопределяется в классе MessageBean и 
            // делает экземпляр управляемым и постоянным
        } catch (JMSException ex) {
            Logger.getLogger(TextMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
