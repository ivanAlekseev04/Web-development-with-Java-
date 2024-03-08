package bg.uni.fmi.lab02.streams.service;

public class OrderLine {
    private Item item;
    private int quantity;
    private boolean specialOffer;

    public OrderLine(Item item, int quantity, boolean specialOffer) {
        this.item = item;
        this.quantity = quantity;
        this.specialOffer = specialOffer;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isSpecialOffer() {
        return specialOffer;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSpecialOffer(boolean specialOffer) {
        this.specialOffer = specialOffer;
    }
}
