package lab4gr1.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * Бин - взаимодействует с базой данных, из сервлета получает числовое сообщение
 * и вызывать MesageBean
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/nummessage")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NumMessageBean implements MessageListener {

    @EJB
    private MessageBeanLocal messageBean;

    public NumMessageBean() {
    }

    @Override                                 // переопределяется из интерфейса MessageListener
    public void onMessage(Message message) {
        System.out.println("the onMessage() method is called");   // оповещение вызова
// преобразование типа
 
ObjectMessage tm = (ObjectMessage) message;
        int number = 0;
        try {
            number = Integer.parseInt((String)tm.getObject());
            System.out.println("The received message: " + number);
//создание и отправление в БД объекта типа Entity
            Nmessage nm = new Nmessage();      // создается ссылочная переменая класса сущности Entity 
            nm.setMessage(number);             // переменная получает сообщение числовое
// добавление сообщения в БД
            messageBean.addNmessage(nm);        // переменная messageBean добавляет сообщение в метод addNmessage()
            //интерфейса MessageBeanLocal, который переопределяется в классе MessageBean и 
            // делает экземпляр управляемым и постоянным 
        } catch (JMSException ex) {
            System.out.println("Попал в исключение");
            Logger.getLogger(NumMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e){
System.out.println("Ошибка приведения в int");
    }
   }
}
