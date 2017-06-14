package ua.nure.ipz.zoo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.service.AccountService;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.ProvisionService;
import ua.nure.ipz.zoo.service.RationService;
import ua.nure.ipz.zoo.service.TicketService;

@Component
public class ReportController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AviaryService aviaryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProvisionService provisionService;
    @Autowired
    private RationService rationService;
    @Autowired
    private TicketService ticketService;

    public void run() {
        ModelReporter modelReporter = new ModelReporter(
                System.out);

        modelReporter.reportAccounts(accountService);
        modelReporter.reportAnimals(animalService);
        modelReporter.reportAviaries(aviaryService);
        modelReporter.reportCarts(cartService);
        modelReporter.reportOrders(orderService);
        modelReporter.reportProducts(productService);
        modelReporter.reportRations(rationService);
        modelReporter.reportProvisions(provisionService);
        modelReporter.reportTickets(ticketService);
    }
}
