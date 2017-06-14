package ua.nure.ipz.zoo.entity;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentInfo {
    private String cardType;
    private String cardNumber;
    private String cardCvv;

    public PaymentInfo() {
    }

    public PaymentInfo(String cardType, String cardNumber, String cardCvv) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardCvv = cardCvv;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }
}
