
package lab4gr1.ejb;


import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


@Stateless
public class MessageBean implements MessageBeanLocal {

    // сказать где БД
    @Resource(name="jdbs/messDataBase") 
    private DataSource ds;
    @PersistenceContext
    private EntityManager em;
    

    @Override
    public String[] getMessageList() {
       System.out.println(" Вызван метод getMessageList()");
       List<Smessage> list;
       
       list = (List<Smessage>)em.createNamedQuery("Smessage.getAll").getResultList();
       String[] array = new String[list.size()];
       int i = 0;
       for(Smessage sm : list) {
           array[i++] = sm.getMessage();
    }
    System.out.println("метод getMessageList() завершился " + array);
      
    return array;
}
    
    @Override
    public long longGetSumm() {
        return 0L;
    }
// такой же и на числовые
    @Override
    public void addSmessage(Smessage message) {
       em.persist(message);
    }
    
    
}
