
package org.apache.fineract.dummy.service;
import org.apache.fineract.dummy.core.data.DummyMessage;
import org.apache.fineract.dummy.core.service.DummyService;
public class DummyServiceImpl implements DummyService {
    @Override
    public DummyMessage getMessage() {
        return new DummyMessage("Hello: DEFAULT DUMMY!");
    }
}
