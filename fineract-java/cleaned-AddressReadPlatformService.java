
package org.apache.fineract.portfolio.address.service;
import java.util.Collection;
import org.apache.fineract.portfolio.address.data.AddressData;
public interface AddressReadPlatformService {
    Collection<AddressData> retrieveAddressFields(long clientid);
    Collection<AddressData> retrieveAllClientAddress(long clientid);
    Collection<AddressData> retrieveAddressbyType(long clientid, long typeid);
    Collection<AddressData> retrieveAddressbyTypeAndStatus(long clientid, long typeid, String status);
    Collection<AddressData> retrieveAddressbyStatus(long clientid, String status);
    AddressData retrieveTemplate();
}
