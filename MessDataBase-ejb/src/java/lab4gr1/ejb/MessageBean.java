package lab4gr1.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

//компонент работает с ДБ непосредственно
// Компонентно-определяющая аннотация для сессионного компонента без сохранения состояния.
@Stateless
public class MessageBean implements MessageBeanLocal {

    // сказать где БД
    @Resource(name = "jdbs/messDataBase")
    private DataSource ds;           //
    @PersistenceContext             //Выражает зависимость от управляемого контейнером EntityManager и связанного с ним контекста персистентности.
    private EntityManager em;       // Интерфейс, используемый для взаимодействия с контекстом постоянства.
 //   private EntityManager emn;     // 
// обращение к классу - таблице Smessage и извлечение списка из таблицы
    @Override
    public String[] getMessageList() {   
        System.out.println(" Вызван метод getMessageList()");
        List<Smessage> list;                      // создание списка 

        list = (List<Smessage>) em.createNamedQuery("Smessage.getAll").getResultList();
        String[] array = new String[list.size()];
        int i = 0;
        for (Smessage sm : list) {
            array[i++] = sm.getMessage();
        }
        System.out.println("метод getMessageList() завершился " + array);

        return array;
    }

    @Override
    public Integer[] getSumm() {
        System.out.println("Вызван метод long GetSumm()");

        List<Nmessage> nlist;
        
        nlist = (List<Nmessage>) em.createNamedQuery("Nmessage.getAll").getResultList();
        Integer[] numarr = new Integer[nlist.size()];
        int i=0;
        for(Nmessage nm : nlist) {
            numarr[i++] = nm.getMessage();
        }
         System.out.println("метод getSumm() завершился " + numarr);
        
        return numarr;
    }
// метод сохранения в БД строковых сообщений 
    
    @Override
    public void addSmessage(Smessage message) {
        em.persist(message);            // 
    }

    @Override
    public void addNmessage(Nmessage message) {
        em.persist(message);        // persist(Object entity) делает экземпляр управляемым и постоянным.  
    }
}
