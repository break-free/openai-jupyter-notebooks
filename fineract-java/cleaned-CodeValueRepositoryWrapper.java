
package org.apache.fineract.infrastructure.codes.domain;
import org.apache.fineract.infrastructure.codes.exception.CodeValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CodeValueRepositoryWrapper {
    private final CodeValueRepository repository;
    @Autowired
    public CodeValueRepositoryWrapper(final CodeValueRepository repository) {
        this.repository = repository;
    }
    public CodeValue findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new CodeValueNotFoundException(id));
    }
    public CodeValue findOneByCodeNameAndIdWithNotFoundDetection(final String codeName, final Long id) {
        final CodeValue codeValue = this.repository.findByCodeNameAndId(codeName, id);
        if (codeValue == null) {
            throw new CodeValueNotFoundException(codeName, id);
        }
        return codeValue;
    }
    public CodeValue findOneByCodeNameAndLabelWithNotFoundDetection(final String codeName, final String label) {
        final CodeValue codeValue = this.repository.findByCodeNameAndLabel(codeName, label);
        if (codeValue == null) {
            throw new CodeValueNotFoundException(codeName, label);
        }
        return codeValue;
    }
}
