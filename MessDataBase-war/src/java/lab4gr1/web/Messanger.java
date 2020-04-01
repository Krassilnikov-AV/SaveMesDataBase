
package lab4gr1.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
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
 * @author Alexk
 */
@WebServlet(name = "Messanger", urlPatterns = {"/Messanger"})
public class Messanger extends HttpServlet {

    @EJB
    private MessageBeanLocal messageBean;

  @Resource(mappedName="jms/textmessageFactory")
  private QueueConnectionFactory textFactory;
  @Resource(mappedName="jms/textmessage")
   private Queue textQueue;
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");       // распознавание русского текста
        response.setContentType("text/html;charset=UTF-8");
        String info = request.getParameter("message");       //
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
            out.println("<h1>ПОлучена информация" + info + "</h1>");
            if("text".equals(choice)) {
                out.println("<h1>Это должен быть текст</h1>");
           
                Connection conn;
                Session session;
                 try {
                conn = textFactory.createConnection();
                session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);    // false - отсутствие транзакций, режим автоподтверждения
                 MessageProducer mp = (MessageProducer)session.createProducer(textQueue);
                 TextMessage tm = session.createTextMessage(info);      // передача текста из формы
                 mp.send(tm);
                     out.println("<h1>Сообщение отправлено</h1>");
                     mp.close();
                     session.close(); 
                     conn.close();
                 } catch (JMSException ex) {
                out.println("<h1>Проблема с отправкой сообщения</h1>");                 
            }
            } else {
                out.println("<h1>Это должно быть число</h1>");
            }
             out.println("<h1>Сообщения из базы:</h1>");
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
