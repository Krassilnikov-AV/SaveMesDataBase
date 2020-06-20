# SaveMesDataBase
Задача по курсу «Java EE. Разработка корпоративных приложений» 
  
Напишите web-приложение, которое реализует следующую функциональность: 

 • Пользователь посредством web-форм может послать на сервер сообщения двух 
типов: строку или целое число. 

 • Сервер сохраняет присланные сообщения в базе данных (база данных должна 
содержать две таблицы: одна для хранения строковых сообщений, вторая - для 
сохранения чисел). 

 • Пользователь в любое время может запросить на сервере список всех присланных 
им сообщений или сумму всех присланных им чисел. 

Приложение для обработки входящих сообщений должно использовать два MDB-бина 
- первый для обработки строк (он обрабатывает сообщения вида TextMessage), второй 
для обработки чисел (он обрабатывает сообщения вида ObjectMessage, в который 
упаковываются объекты типа Integer). Бин, который обрабатывает запросы на 
получение данных из базы данных, может реализовать следующий интерфейс: 
  
@Local 
public interface MessageBeanLocal { 
  
    String[] getMessageList() throws IOException;

    long getSum() throws IOException;
} 
  
Все бины должны работать с базой данных посредством JPA.  
