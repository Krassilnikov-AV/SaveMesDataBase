package lab4gr1.ejb;

import javax.ejb.Local;

/**
 *
 * @author Alexk
 */
@Local
public interface MessageBeanLocal {

   String[] getMessageList();
//
    Integer[] getSumm();

    void addSmessage(Smessage message);

    void addNmessage(Nmessage message);

}
