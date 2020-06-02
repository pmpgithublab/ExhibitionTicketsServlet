package ua.training.controller;

import org.apache.log4j.Logger;
import ua.training.controller.command.*;
import ua.training.model.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ua.training.Constants.*;

@WebServlet(urlPatterns = WELCOME_PATH)
public class Servlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(Servlet.class);
    private static final String SERVLET_DESTROY_METHOD_EXECUTING = "Servlet destroy method executing";
    private static final String SERVLET_INIT_METHOD_START = "Servlet init method start";
    private static final String SERVLET_INIT_METHOD_FINISH = "Servlet init method finish";

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() {
        log.info(SERVLET_INIT_METHOD_START);

        UserService userService = new UserService();
        HallService hallService = new HallService();
        ExhibitService exhibitService = new ExhibitService();
        CartService cartService = new CartService();
        PaymentService paymentService = new PaymentService();
        ReportService reportService = new ReportService();

        commands.put(WELCOME_PATH, new WelcomeCommand());
        commands.put(REGISTRATION_PATH, new RegistrationCommand(userService));
        commands.put(LOGIN_PATH, new LogInCommand(userService));
        commands.put(LOGOUT_PATH, new LogOutCommand());
        commands.put(ERROR_PATH, new ErrorCommand());

        HallCommand hallCommand = new HallCommand(hallService);
        commands.put(ADMIN_PATH + HALLS_LIST_PATH, hallCommand);
        commands.put(ADMIN_PATH + HALL_EDIT_PATH, hallCommand);

        ExhibitCommand exhibitCommand = new ExhibitCommand(hallService, exhibitService);
        commands.put(ADMIN_PATH + EXHIBITS_LIST_PATH, exhibitCommand);
        commands.put(ADMIN_PATH + EXHIBIT_EDIT_PATH, exhibitCommand);
        commands.put(ADMIN_PATH + REPORT_PATH, new AdminReportCommand(reportService));

        CartCommand cartCommand = new CartCommand(cartService);
        commands.put(TRADE_PATH + EXHIBITS_LIST_PATH, new TradeListCommand(exhibitService));
        commands.put(TRADE_PATH + TICKET_ADD_TO_CART_PATH, cartCommand);
        commands.put(TRADE_PATH + CART_PATH, cartCommand);
        commands.put(TRADE_PATH + TICKET_DELETE_FROM_CART_PATH, cartCommand);
        commands.put(TRADE_PATH + CLEAR_CART_PATH, cartCommand);
        commands.put(TRADE_PATH + PAYMENT_PATH, new PaymentCommand(paymentService));
        commands.put(TRADE_PATH + REPORT_PATH, new TradeReportCommand(reportService));

        log.info(SERVLET_INIT_METHOD_FINISH);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        Command command = commands.getOrDefault(path, (r) -> REDIRECT_STRING + WELCOME_PATH);
        String page = command.execute(request);
        if (page.startsWith(REDIRECT_STRING)) {
            response.sendRedirect(page.replace(REDIRECT_STRING, request.getContextPath()));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        log.info(SERVLET_DESTROY_METHOD_EXECUTING);
    }
}
