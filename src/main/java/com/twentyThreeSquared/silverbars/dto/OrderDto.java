package com.twentyThreeSquared.silverbars.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.twentyThreeSquared.silverbars.persistence.entity.Order;

import static com.twentyThreeSquared.silverbars.persistence.entity.Order.OrderType.BUY;
import static com.twentyThreeSquared.silverbars.persistence.entity.Order.OrderType.SELL;

public class OrderDto implements Comparable<OrderDto> {

    private float quantity;
    private int price;
    private List<UUID> orderIds;
    private Order.OrderType orderType;

    private OrderDto() {}

    private OrderDto(float quantity, int price, List<UUID> orderIds, Order.OrderType orderType) {
        this.quantity = quantity;
        this.price = price;
        this.orderIds = orderIds;
        this.orderType = orderType;
    }

    public float getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public List<UUID> getOrderIds() {
        return orderIds;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(quantity, orderDto.quantity)
                .append(price, orderDto.price)
                .append(orderIds, orderDto.orderIds)
                .append(orderType, orderDto.orderType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(quantity)
                .append(price)
                .append(orderIds)
                .append(orderType)
                .toHashCode();
    }

    @Override
    public int compareTo(OrderDto o) {
        if(this.equals(o)) {
            return 0;
        } else if(this.orderType.equals(SELL) && o.getOrderType().equals(SELL)) {
            // Both SELL orders, order by price lowest to highest
            if(this.getPrice() > o.getPrice()) {
                return 1;
            } else {
                return -1;
            }
        } else if(this.orderType.equals(BUY) && o.getOrderType().equals(BUY)) {
            // Both BUY orders, order by price highest to lowest
            if(this.getPrice() > o.getPrice()) {
                return -1;
            } else {
                return 1;
            }
        } else if(this.orderType.equals(BUY) && o.getOrderType().equals(SELL)) {
            // SELL orders come first
            return 1;
        } else if(this.orderType.equals(SELL) && o.getOrderType().equals(BUY)) {
            // SELL orders come first
            return -1;
        }

        return 0;
    }

    public static class Builder {

        private int price;
        private float quantity;
        private List<UUID> orderIds = new ArrayList<>();
        private Order.OrderType orderType;

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder withOrderType(Order.OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder addQuantity(float quantity) {
            this.quantity += quantity;
            return this;
        }

        public Builder addOrderId(UUID orderId) {
            orderIds.add(orderId);
            return this;
        }

        public OrderDto build() {
            return new OrderDto(quantity, price, orderIds, orderType);
        }
    }
}
