
package org.apache.fineract.portfolio.address.data;
public final class ClientAddressData {
    private final long clientAddressID;
    private final long clientID;
    private final long addressID;
    private final long addressTypeID;
    private final boolean isActive;
    private ClientAddressData(final long clientAddressID, final long client_id, final long address_id, final long address_type_id,
            final boolean isActive) {
        this.clientAddressID = clientAddressID;
        this.clientID = client_id;
        this.addressID = address_id;
        this.addressTypeID = address_type_id;
        this.isActive = isActive;
    }
    public static ClientAddressData instance(final long clientAddressID, final long client_id, final long address_id,
            final long address_type_id, final boolean isActive) {
        return new ClientAddressData(clientAddressID, client_id, address_id, address_type_id, isActive);
    }
}
