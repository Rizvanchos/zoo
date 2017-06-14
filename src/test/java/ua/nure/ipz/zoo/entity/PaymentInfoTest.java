package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PaymentInfoTest {

    private static final String CARD_TYPE = "MasterCard";
    private static final String CARD_NUMBER = "0000-0000-0000-000";
    private static final String CARD_CVV = "123";

    @Test
    public void shouldCreatePaymentInfoWithDefaultValues_whenCallDefaultConstructor(){
        PaymentInfo paymentInfo = new PaymentInfo();

        assertNull(paymentInfo.getCardType());
        assertNull(paymentInfo.getCardNumber());
        assertNull(paymentInfo.getCardCvv());
    }

    @Test
    public void shouldCreatePaymentInfoWithParameters_whenConstructorWithParameters() {
        PaymentInfo paymentInfo = new PaymentInfo(CARD_TYPE, CARD_NUMBER, CARD_CVV);

        assertEquals(CARD_TYPE, paymentInfo.getCardType());
        assertEquals(CARD_NUMBER, paymentInfo.getCardNumber());
        assertEquals(CARD_CVV, paymentInfo.getCardCvv());
    }

    @Test
    public void shouldSetNewPaymentInfoCardType_whenSetNewCardType(){
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardType(CARD_TYPE);

        assertEquals(CARD_TYPE, paymentInfo.getCardType());
    }

    @Test
    public void shouldSetNewPaymentInfoCardNumber_whenSetNewCardNumber(){
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(CARD_NUMBER);

        assertEquals(CARD_NUMBER, paymentInfo.getCardNumber());
    }

    @Test
    public void shouldSetNewPaymentInfoCvv_whenSetNewCvv(){
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardCvv(CARD_CVV);

        assertEquals(CARD_CVV, paymentInfo.getCardCvv());
    }
}
