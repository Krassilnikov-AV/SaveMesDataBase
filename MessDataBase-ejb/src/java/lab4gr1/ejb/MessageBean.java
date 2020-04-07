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
/*    метод getMessageList позволяет обратиться к этому классу-таблице и получить список
    всех записей этой таблицы и вернуть это список тому кто обратился к этому методу
     */
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

    /**
     * Нужно создать класс на подобии Smessage. В нем сделать именнованный
     * запрос в базу, который возвращает сразу сумму всех чисел. А в классе
     * MessageBean в методе getSum нужно вытаскивать эту сумму на подобии как в
     * методе getMessageList, но только использовать не getResultList, а
     * getSingleResult, так как нам нужно вытащить всего одну сумму, а не список
     */
    @Override
    public Integer[] getLitsSumm() {
        System.out.println("Вызван метод Integer[] getSumm()");
        List<Nmessage> nlist;
        nlist = (List<Nmessage>) em.createNamedQuery("Nmessage.getAll").getResultList();
        Integer[] numarr = new Integer[nlist.size()];
        int i = 0;
        for (Nmessage sm : nlist) {
            numarr[i++] = sm.getMessage();
        }
        System.out.println("метод getMessageList() завершился " + numarr);
        return numarr;
    }

    @Override
    public int getSumm() {
        System.out.println("Вызван метод int getSumm()");
        List<Nmessage> nlist;
        nlist = (List<Nmessage>) em.createNamedQuery("Nmessage.getAll").getResultList();
        int[] numarr = new int[nlist.size()];
        int i = 0;
        for (Nmessage sm : nlist) {
            numarr[i++] = sm.getMessage();
        }
        int summ = 0;
        for (int j = 0; j < numarr.length; j++) {
            summ += numarr[j];
        }
        return summ;
       }

// метод сохранения в БД строковых сообщений 
    @Override
    public void addSmessage(Smessage message) {
        em.persist(message);            // 
    }

    @Override
    public void addNmessage(Nmessage message) {
        em.persist(message);     // persist(Object entity) делает экземпляр управляемым и постоянным.  
    }
}
