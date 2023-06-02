
package com.acmecorp.fineract.foo.service;
import org.apache.fineract.dummy.core.data.DummyMessage;
import org.apache.fineract.dummy.core.service.DummyService;
import org.springframework.stereotype.Service;
@Service
public class FooDummyServiceImpl implements DummyService {
    @Override
    public DummyMessage getMessage() {
        return new DummyMessage("Hello: FOO!");
    }
}
