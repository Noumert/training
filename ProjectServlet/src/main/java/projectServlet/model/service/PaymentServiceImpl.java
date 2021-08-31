package projectServlet.model.service;

import projectServlet.model.dao.DaoFactory;
import projectServlet.model.dao.PaymentDao;
import projectServlet.model.entity.Payment;
import projectServlet.model.entity.StatusType;

import java.util.List;
import java.util.Optional;


/**
 * Created by Noumert on 30.08.2021.
 */
public class PaymentServiceImpl implements  PaymentService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public List<Payment> findAll() {
        try (PaymentDao dao = daoFactory.createPaymentDao()) {
            return dao.findAll();
        }
    }

    @Override
    public void save(Payment payment) {
        try (PaymentDao dao = daoFactory.createPaymentDao()) {
            dao.save(payment);
        }
    }

//    @Override
//    public Page<Payment> findPaymentsByUserId(Long userId, Pageable pageable) {
//        return paymentRepository.findPaymentsByUserId(userId, pageable);
//    }

    @Override
    public List<Payment> findByUserId(Long userId) {
        try (PaymentDao dao = daoFactory.createPaymentDao()) {
            return dao.findByUserId(userId);
        }
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        try (PaymentDao dao = daoFactory.createPaymentDao()) {
            return dao.findById(paymentId);
        }
    }

    @Override
    public void setStatusByPayment(StatusType status, Payment payment) {
        payment.setStatus(status);
        save(payment);
    }
}
