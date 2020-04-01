/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4gr1.ejb;

import javax.ejb.Local;

/**
 *
 * @author Alexk
 */
@Local
public interface MessageBeanLocal {

    String[] getMessageList();

    long longGetSumm();

    void addSmessage(Smessage message);
    
}
