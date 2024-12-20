package org.poo.cards;

import org.poo.utils.Utils;

public final class Card {
    private String status;
    private final String cardNumber;
    private final String type;


    private Card(final String cardNumber, final String type, final String status) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.status = status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getType() {
        return type;
    }

    public static class CardBuilder implements Builder {
        private String cardNumber;
        private String type = "classic";
        private String status = "active";

        /**
         * Set the card number
         * @return
         */
        @Override
        public Builder setCardNumber() {
            this.cardNumber = Utils.generateCardNumber();
            return this;
        }

        /**
         * Set the card type
         * @param type
         * @return
         */
        @Override
        public Builder setType(final String type) {
            this.type = type;
            return this;
        }

        /**
         * build the card
         * @return
         */
        @Override
        public Card build() {
            return new Card(cardNumber, type, status);
        }
    }
}
