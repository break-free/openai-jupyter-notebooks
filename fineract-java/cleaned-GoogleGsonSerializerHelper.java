
package org.apache.fineract.infrastructure.core.serialization;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.infrastructure.core.api.DateAdapter;
import org.apache.fineract.infrastructure.core.api.JodaDateTimeAdapter;
import org.apache.fineract.infrastructure.core.api.JodaMonthDayAdapter;
import org.apache.fineract.infrastructure.core.api.LocalDateAdapter;
import org.apache.fineract.infrastructure.core.api.LocalDateTimeAdapter;
import org.apache.fineract.infrastructure.core.api.LocalTimeAdapter;
import org.apache.fineract.infrastructure.core.api.OffsetDateTimeAdapter;
import org.apache.fineract.infrastructure.core.api.ParameterListExclusionStrategy;
import org.apache.fineract.infrastructure.core.api.ParameterListInclusionStrategy;
import org.apache.fineract.infrastructure.core.exception.UnsupportedParameterException;
import org.springframework.stereotype.Service;
@Service
public final class GoogleGsonSerializerHelper {
    public Gson createGsonBuilderForPartialResponseFiltering(final boolean prettyPrint, final Set<String> responseParameters) {
        final ExclusionStrategy strategy = new ParameterListInclusionStrategy(responseParameters);
        final GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(strategy);
        registerTypeAdapters(builder);
        if (prettyPrint) {
            builder.setPrettyPrinting();
        }
        return builder.create();
    }
    public Gson createGsonBuilderWithParameterExclusionSerializationStrategy(final Set<String> supportedParameters,
            final boolean prettyPrint, final Set<String> responseParameters) {
        final Set<String> parameterNamesToSkip = new HashSet<>();
        if (!responseParameters.isEmpty()) {
            final Set<String> differentParametersDetectedSet = new HashSet<>(responseParameters);
            differentParametersDetectedSet.removeAll(supportedParameters);
            if (!differentParametersDetectedSet.isEmpty()) {
                throw new UnsupportedParameterException(new ArrayList<>(differentParametersDetectedSet));
            }
            parameterNamesToSkip.addAll(supportedParameters);
            parameterNamesToSkip.removeAll(responseParameters);
        }
        final ExclusionStrategy strategy = new ParameterListExclusionStrategy(parameterNamesToSkip);
        final GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(strategy);
        registerTypeAdapters(builder);
        if (prettyPrint) {
            builder.setPrettyPrinting();
        }
        return builder.create();
    }
    public String serializedJsonFrom(final Gson serializer, final Object[] dataObjects) {
        return serializer.toJson(dataObjects);
    }
    public String serializedJsonFrom(final Gson serializer, final Object singleDataObject) {
        return serializer.toJson(singleDataObject);
    }
    public static GsonBuilder createGsonBuilder() {
        return createGsonBuilder(false);
    }
    public static GsonBuilder createGsonBuilder(final boolean prettyPrint) {
        final GsonBuilder builder = new GsonBuilder();
        registerTypeAdapters(builder);
        if (prettyPrint) {
            builder.setPrettyPrinting();
        }
        return builder;
    }
    public static void registerTypeAdapters(final GsonBuilder builder) {
        builder.registerTypeAdapter(java.util.Date.class, new DateAdapter());
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
        builder.registerTypeAdapter(ZonedDateTime.class, new JodaDateTimeAdapter());
        builder.registerTypeAdapter(MonthDay.class, new JodaMonthDayAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        builder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter());
    }
}
