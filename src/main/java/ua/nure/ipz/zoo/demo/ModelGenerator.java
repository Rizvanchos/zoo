package ua.nure.ipz.zoo.demo;

import java.math.BigDecimal;
import java.util.UUID;

import ua.nure.ipz.zoo.exception.DomainLogicException;
import ua.nure.ipz.zoo.service.AccountService;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.ProvisionService;
import ua.nure.ipz.zoo.service.RationService;
import ua.nure.ipz.zoo.service.TicketService;

public class ModelGenerator {
    private UUID Lavretiy, Manager;
    private UUID provision;
    private UUID cart, order;
    private UUID Alex, Steve, Melman, Dave;
    private UUID Apple, Orange, Banana;
    private UUID FirstAviary, SecondAviary, ThirdAviary;
    private UUID firstRation, secondRation, thirdRation, fourthRation;
    private UUID ChildTicket, SchoolTicket, StudentTicket, StandardTicket;

    public void generateAnimals(AnimalService animalService) {
        Alex = animalService.create("Alex", "lion", "http://www.zoo.org.au/sites/default/files/styles/zv_carousel_large/public/lion-basking-620x380.jpg?itok=dt8-VNXO");
        Steve = animalService.create("Steve", "panda", "http://www.ctvnews.ca/polopoly_fs/1.1209642.1368651464!/httpImage/image.jpg_gen/derivatives/landscape_960/image.jpg");
        Melman = animalService.create("Melman", "giraffe", "http://news.nationalgeographic.com/content/dam/news/photos/000/765/76521.adapt.768.1.jpg");
        Dave = animalService.create("Dave", "bear", "http://zoo.sandiegozoo.org/sites/default/files/animal_hero/polr_bear_2.jpg");

        animalService.addDescription(Alex, "Lions have strong, compact bodies and powerful forelegs, teeth, and jaws for pulling down and killing prey. Their coats are yellow-gold. Adult males have shaggy manes that range in color from blond to reddish-brown to black and also vary in length. Without their coats, lion and tiger bodies are so similar that only experts can tell them apart.");
        animalService.addDescription(Steve, "Giant pandas live up to their name. They are 4 to 5 feet (1.2 to 1.5 meters) tall and weigh up to 300 lbs. (136 kilograms), according to the National Geographic, about the same as an American black bear. By comparison, their distant relatives, red pandas, are only 20 to 26 inches (50 to 65 cm) tall and weigh 12 to 20 lbs. (5.4 to 9 kg).");
        animalService.addDescription(Melman, "Giraffes are the tallest land animals. A giraffe could look into a second-story window without even having to stand on its tiptoes! A giraffe's 6-foot (1.8-meter) neck weighs about 600 pounds (272 kilograms). A giraffe's heart is 2 feet (0.6 meters) long and weighs about 25 pounds (11 kilograms), and its lungs can hold 12 gallons (55 liters) of air!");
        animalService.addDescription(Dave, "The focal point of the Conrad Prebys Polar Bear Plunge is, naturally, the pool. From the underwater viewing room you can see how agile and playful these Arctic bruins really are. In fact, they’re known to swim right up to the glass to check out all the humans on display.");
    }

    public void generateAviaries(AviaryService aviaryService) throws DomainLogicException {
        FirstAviary = aviaryService.create(1, 20, 60, true);
        aviaryService.addAnimal(FirstAviary, Alex);
        aviaryService.addAnimal(FirstAviary, Melman);

        SecondAviary = aviaryService.create(2, 13, 70, true);
        aviaryService.addAnimal(SecondAviary, Steve);

        ThirdAviary = aviaryService.create(1, 18, 65, false);
        aviaryService.addAnimal(ThirdAviary, Dave);

        aviaryService.addVisitTime(FirstAviary, "09:30 - 11:00");
        aviaryService.addVisitTime(FirstAviary, "10:30 - 11:00");
        aviaryService.addVisitTime(SecondAviary, "14:00 - 15:00");
    }

    public void generateTickets(TicketService ticketService) throws DomainLogicException {
        ChildTicket = ticketService.create("CHILD", BigDecimal.valueOf(10.00));
        SchoolTicket = ticketService.create("SCHOOL", BigDecimal.valueOf(10.00));
        StudentTicket = ticketService.create("STUDENT", BigDecimal.valueOf(15.00));
        StandardTicket = ticketService.create("STANDARD", BigDecimal.valueOf(30.00));
    }

    public void generateCarts(CartService cartService) throws DomainLogicException {
        cart = cartService.create();

        cartService.addItem(cart, ChildTicket, 10);
        cartService.addItem(cart, SchoolTicket, 5);
        cartService.addItem(cart, StudentTicket, 3);
        cartService.addItem(cart, StandardTicket, 1);

        cartService.lock(cart);
    }

    public void generateOrders(OrderService orderService) throws DomainLogicException {
        order = orderService.create("John Smith","abcde@mail.ru", "(050)123-45-67", "Test", cart);

        orderService.setDiscount(order, "CHILD", 5, 0.6f);
        orderService.setPaymentInfo(order, "visa", "4111111111111111", "123");
    }

    public void generateProducts(ProductService productService) throws DomainLogicException {
        Apple = productService.create("apple", BigDecimal.valueOf(10.00));
        Orange = productService.create("orange", BigDecimal.valueOf(25.00));
        Banana = productService.create("banana", BigDecimal.valueOf(45.00));
    }

    public void generateRations(RationService rationService) throws DomainLogicException {
        firstRation = rationService.create(Alex);
        rationService.addItem(firstRation, Apple, 5f);

        secondRation = rationService.create(Steve);
        rationService.addItem(secondRation, Apple, 1f);
        rationService.addItem(secondRation, Orange, 3f);

        thirdRation = rationService.create(Melman);
        rationService.addItem(thirdRation, Apple, 10f);
        rationService.addItem(thirdRation, Banana, 4.7f);

        fourthRation = rationService.create(Dave);
        rationService.addItem(fourthRation, Apple, 40f);
    }

    public void generateProvisions(ProvisionService provisionService) throws DomainLogicException {
        provision = provisionService.create();

        provisionService.addRation(provision, firstRation);
        provisionService.addRation(provision, secondRation);
        provisionService.addRation(provision, thirdRation);
        provisionService.addRation(provision, fourthRation);
    }

    public void generateAccounts(AccountService accountService) throws DomainLogicException {
        Lavretiy = accountService.createOperator("Lavretiy", "lavretiy@mail.ru", "abcde");

        Manager = accountService.createManager("Boss", "boss@mail.ru", "boss");
        accountService.assignProvision(Manager, provision);
    }
}
