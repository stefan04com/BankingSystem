package org.poo.cards;

public interface Builder {
    /**
     *
     * @return
     */
    Builder setCardNumber();
    /**
     *
     * @param type
     * @return
     */
    Builder setType(String type);
    /**
     *
     * @return
     */
    Card build();
}
