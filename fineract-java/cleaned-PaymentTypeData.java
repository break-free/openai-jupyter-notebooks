
package org.apache.fineract.portfolio.paymenttype.data;
import java.io.Serializable;
import java.util.Objects;
public class PaymentTypeData implements Serializable {
    @SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    private String name;
    @SuppressWarnings("unused")
    private String description;
    @SuppressWarnings("unused")
    private Boolean isCashPayment;
    @SuppressWarnings("unused")
    private Long position;
    public PaymentTypeData(final Long id, final String name, final String description, final Boolean isCashPayment, final Long position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCashPayment = isCashPayment;
        this.position = position;
    }
    public static PaymentTypeData instance(final Long id, final String name, final String description, final Boolean isCashPayment,
            final Long position) {
        return new PaymentTypeData(id, name, description, isCashPayment, position);
    }
    public static PaymentTypeData instance(final Long id, final String name) {
        String description = null;
        Boolean isCashPayment = null;
        Long position = null;
        return new PaymentTypeData(id, name, description, isCashPayment, position);
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentTypeData)) {
            return false;
        }
        PaymentTypeData that = (PaymentTypeData) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(isCashPayment, that.isCashPayment) && Objects.equals(position, that.position);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isCashPayment, position);
    }
}
