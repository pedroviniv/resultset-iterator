/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Product {
    
    private Long id;
    private String name;
    private BigDecimal price;

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal price;

        private Builder() {
        }

        public Builder id(final Long value) {
            this.id = value;
            return this;
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder price(final BigDecimal value) {
            this.price = value;
            return this;
        }

        public Product build() {
            return new io.github.kieckegard.resultset.iterator.Product(id, name, price);
        }
    }

    public static Product.Builder builder() {
        return new Product.Builder();
    }

    private Product(final Long id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.price);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + '}';
    }
}
