
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class DatatableNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DatatableNotFoundException(final String datatable, final Long id) {
        super("error.msg.datatable.data.not.found", "Data not found for datatable: ", datatable + "  Id:" + id);
    }
    public DatatableNotFoundException(final String datatable) {
        super("error.msg.datatable.not.found", "Datatable not found.", datatable);
    }
}
