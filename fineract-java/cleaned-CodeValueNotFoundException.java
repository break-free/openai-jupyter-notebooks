
package org.apache.fineract.infrastructure.codes.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class CodeValueNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CodeValueNotFoundException(final Long id) {
        super("error.msg.codevalue.id.invalid", "Code value with identifier " + id + " does not exist", id);
    }
    public CodeValueNotFoundException(final String codeName, final Long id) {
        super("error.msg.codevalue.codename.id.combination.invalid",
                "Code value with identifier " + id + " does not exist for a code with name " + codeName, id, codeName);
    }
    public CodeValueNotFoundException(final String codeName, final String label) {
        super("error.msg.codevalue.codename.id.combination.invalid",
                "Code value with label " + label + " does not exist for a code with name " + codeName, label, codeName);
    }
    public CodeValueNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.codevalue.id.invalid", "Code value with identifier " + id + " does not exist", id, e);
    }
}
