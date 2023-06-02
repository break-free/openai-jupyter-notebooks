
package org.apache.fineract.spm.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class LookupTableNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LookupTableNotFoundException(final String key) {
        super("error.msg.survey.lookuptable.notfound", "Lookup table with id " + key + " not found!", key);
    }
}
