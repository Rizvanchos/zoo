package ua.nure.ipz.zoo.demo;

import ua.nure.ipz.zoo.dto.DomainEntityDto;
import ua.nure.ipz.zoo.service.AccountService;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.ProvisionService;
import ua.nure.ipz.zoo.service.RationService;
import ua.nure.ipz.zoo.service.Service;
import ua.nure.ipz.zoo.service.TicketService;

import java.io.PrintStream;
import java.util.UUID;

public class ModelReporter {
    private PrintStream output;

    public ModelReporter(PrintStream output) {
        this.output = output;
    }

    public void reportAccounts(AccountService accountService) {
        reportCollection("Accounts", accountService);
    }

    public void reportAnimals(AnimalService animalService) {
        reportCollection("Animals", animalService);
    }

    public void reportAviaries(AviaryService aviaryService) {
        reportCollection("Aviaries", aviaryService);
    }

    public void reportCarts(CartService cartService) {
        reportCollection("Carts", cartService);
    }

    public void reportOrders(OrderService orderService) {
        reportCollection("Orders", orderService);
    }

    public void reportProducts(ProductService productService) {
        reportCollection("Products", productService);
    }

    public void reportRations(RationService rationService) {
        reportCollection("Rations", rationService);
    }

    public void reportProvisions(ProvisionService provisionService) {
        reportCollection("Provisions", provisionService);
    }

    public void reportTickets(TicketService ticketService) {
        reportCollection("Tickets", ticketService);
    }

    private <T extends DomainEntityDto> void reportCollection(String title, Service<T> service) {
        output.format("==== %s ====\n\n", title);

        for (UUID itemUuid : service.viewAll()) {
            output.print(service.view(itemUuid));
            output.print("\n\n");
        }

        output.println();
    }
}
