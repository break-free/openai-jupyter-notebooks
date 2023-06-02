
package org.apache.fineract.infrastructure.codes.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class CodeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CodeNotFoundException(final String name) {
        super("error.msg.code.not.found", "Code with name `" + name + "` does not exist", name);
    }
    public CodeNotFoundException(final Long codeId) {
        super("error.msg.code.identifier.not.found", "Code with identifier `" + codeId + "` does not exist", codeId);
    }
    public CodeNotFoundException(final String name, EmptyResultDataAccessException e) {
        super("error.msg.code.not.found", "Code with name `" + name + "` does not exist", name, e);
    }
    public CodeNotFoundException(final Long codeId, EmptyResultDataAccessException e) {
        super("error.msg.code.identifier.not.found", "Code with identifier `" + codeId + "` does not exist", codeId, e);
    }
}
