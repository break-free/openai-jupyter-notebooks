
package org.apache.fineract.commands.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class ProcessingResultLookup {
    private final Long id;
    private final String processingResult;
}
