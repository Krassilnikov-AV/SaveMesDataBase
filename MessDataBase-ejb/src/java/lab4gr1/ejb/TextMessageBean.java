
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
 *
 * @author Alexk
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/textmessage")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TextMessageBean implements MessageListener {
    
    @EJB
    private MessageBeanLocal messageBean;
    
    public TextMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.out.println("Вызван метод onMessage()");
        TextMessage tm = (TextMessage)message;
        String text = "";
        try {
            text = tm.getText();
            System.out.println("Получено сообщение: " + text);
    //создание и отправление в БД объекта типа Entity
            Smessage sm = new Smessage();
            sm.setMessage(text);
            messageBean.addSmessage(sm);
        } catch (JMSException ex) {
            Logger.getLogger(TextMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }
