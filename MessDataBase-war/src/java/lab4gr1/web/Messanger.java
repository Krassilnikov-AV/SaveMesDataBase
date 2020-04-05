package lab4gr1.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;     // 
import javax.ejb.EJB;
import javax.jms.Connection;        // активное подключением клиента к поставщику JMS
import javax.jms.JMSException;      // 
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lab4gr1.ejb.MessageBeanLocal;

/**
 *
 *
 */
@WebServlet(name = "Messanger", urlPatterns = {"/Messanger"})
public class Messanger extends HttpServlet {

    @EJB
    private MessageBeanLocal messageBean;

    @Resource(mappedName = "jms/textmessageFactory")
    private QueueConnectionFactory textFactory;
    @Resource(mappedName = "jms/textmessage")
    private Queue textQueue;

    @Resource(mappedName = "jms/nummessageFactory")  // название ресурса который создавали в GlassFish
    private QueueConnectionFactory numFactory;    // контейнер, который управляет ресурсом
    @Resource(mappedName = "jms/nummessage")
    private Queue numQueue;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// распознавание русского текста        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
// получение параметра с jsp файла, обобщенное (может быть и число и текст)               
        String info = request.getParameter("message");    // поле ввода 
        String choice = request.getParameter("choice");      // 
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Messanger</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Messanger at " + request.getContextPath() + "</h1>");
            out.println("<h1>Получена информация: " + info + "</h1>");
               request.setAttribute(info, info);
            Connection conn;
            Session session;
            if ("text".equals(choice)) {
                out.println("<h1>It must be a text</h1>");
// обработка текстового сообщения            
                try {
                    conn = textFactory.createConnection();
                    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);    // false - отсутствие транзакций, режим автоподтверждения
                    MessageProducer mp = (MessageProducer) session.createProducer(textQueue);
                    TextMessage tm = session.createTextMessage(info);      // передача текста из формы
                    mp.send(tm);
                    out.println("<h1>Your messager is entered</h1>");
                    mp.close();
                    session.close();
                    conn.close();
                } catch (JMSException ex) {
                    out.println("<h1>Problem sending the value</h1>");
                }
            } else {
                out.println("<h1>It must be a number</h1>");
// обработка числовых значений
                try {
                    conn = numFactory.createConnection();          // 
                    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer mp = (MessageProducer) session.createProducer(numQueue);
                    ObjectMessage tm = session.createObjectMessage(info);      // передача текста из формы
                    mp.send(tm);
// если не выкинет исключение, то...              
                    out.println("<h1> Your number is entered </h1>");
                    mp.close();
                    session.close();
                    conn.close();
                } catch (JMSException ex) {
                    out.println("<h1>Problem sending the value </h1>");
                }
            }
            out.println("<h1>Сообщения из базы: </h1>");
      String[] messages = messageBean.getMessageList();
      for(String m : messages) {
          out.println("<h2>" + m + "/<h2>");
                 }            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
