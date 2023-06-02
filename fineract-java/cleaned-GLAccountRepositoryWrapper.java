
package org.apache.fineract.accounting.glaccount.domain;
import org.apache.fineract.accounting.glaccount.exception.GLAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GLAccountRepositoryWrapper {
    private final GLAccountRepository repository;
    @Autowired
    public GLAccountRepositoryWrapper(final GLAccountRepository repository) {
        this.repository = repository;
    }
    public GLAccount findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new GLAccountNotFoundException(id));
    }
    public GLAccount findOneByGlCodeWithNotFoundDetection(final String glCode) {
        return this.repository.findOneByGlCode(glCode).orElseThrow(() -> new GLAccountNotFoundException(glCode));
    }
}
