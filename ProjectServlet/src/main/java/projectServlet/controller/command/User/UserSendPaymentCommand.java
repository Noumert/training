package projectServlet.controller.command.User;

import projectServlet.controller.command.Command;
import projectServlet.exceptions.BanException;
import projectServlet.exceptions.NotEnoughMoneyException;
import projectServlet.model.entity.Payment;
import projectServlet.model.service.PaymentProcessingService;
import projectServlet.model.service.PaymentProcessingServiceImpl;
import projectServlet.model.service.PaymentService;
import projectServlet.model.service.PaymentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

public class UserSendPaymentCommand implements Command {
    private final PaymentProcessingService paymentProcessingService = new PaymentProcessingServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> accPage = Optional.ofNullable(request.getParameter("accPage"));
        Optional<String> accSortBy = Optional.ofNullable(request.getParameter("accSortBy"));
        Optional<String> accAsc = Optional.ofNullable(request.getParameter("accAsc"));
        Optional<String> payPage = Optional.ofNullable(request.getParameter("payPage"));
        Optional<String> paySortBy = Optional.ofNullable(request.getParameter("paySortBy"));
        Optional<String> payAsc = Optional.ofNullable(request.getParameter("payAsc"));

        Long paymentId = Long.valueOf(request.getParameter("paymentId"));

        try {
            Payment payment = paymentService.findById(paymentId).orElseThrow(NotFoundException::new);
            if (payment.getAccount().isBan()) {
                throw new BanException("account was banned");
            }
            paymentProcessingService.sendPayment(payment);
            return "redirect:/user/profile" +
                    "?payPage=" + payPage.orElse("1") + "&paySortBy=" + paySortBy.orElse("payment_id") +
                    "&payAsc=" + payAsc.orElse("true") +
                    "&accPage=" + accPage.orElse("1") + "&accSortBy=" + accSortBy.orElse("account_id") +
                    "&accAsc=" + accAsc.orElse("true");
        } catch (NotEnoughMoneyException e) {
            return "redirect:/user/profile/sendResult?noMoneyError=true" +
                    "&payPage=" + payPage.orElse("1") + "&paySortBy=" + paySortBy.orElse("payment_id") +
                    "&payAsc=" + payAsc.orElse("true") +
                    "&accPage=" + accPage.orElse("1") + "&accSortBy=" + accSortBy.orElse("account_id") +
                    "&accAsc=" + accAsc.orElse("true");
        } catch (BanException e) {
            return "redirect:/user/profile/sendResult?banError=true" +
                    "&payPage=" + payPage.orElse("1") + "&paySortBy=" + paySortBy.orElse("payment_id") +
                    "&payAsc=" + payAsc.orElse("true") +
                    "&accPage=" + accPage.orElse("1") + "&accSortBy=" + accSortBy.orElse("account_id") +
                    "&accAsc=" + accAsc.orElse("true");
        } catch (RuntimeException e) {
//            System.err.println(e.getMessage());
            return "redirect:/user/profile/sendResult?error=true" +
                    "&payPage=" + payPage.orElse("1") + "&paySortBy=" + paySortBy.orElse("payment_id") +
                    "&payAsc=" + payAsc.orElse("true") +
                    "&accPage=" + accPage.orElse("1") + "&accSortBy=" + accSortBy.orElse("account_id") +
                    "&accAsc=" + accAsc.orElse("true");
        }
    }
}
