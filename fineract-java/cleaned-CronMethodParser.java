
package org.apache.fineract.infrastructure.jobs.annotation;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
public final class CronMethodParser {
    private CronMethodParser() {
    }
    public static class ClassMethodNamesPair {
        public String className;
        public String methodName;
    }
    private static final String SEARCH_PACKAGE = "org.apache.fineract.";
    private static final String CRON_ANNOTATION_ATTRIBUTE_NAME = "jobName";
    private static final String RESOURCE_PATTERN = "**
    private static void findAnnotationMethods(final Class<? extends Annotation> annotationClass, final String attributeName)
            throws IOException {
        final String basePackagePath = ClassUtils
                .convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(SEARCH_PACKAGE));
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + basePackagePath + "/" + RESOURCE_PATTERN;
        packageSearchPath = packageSearchPath.replace("
        final Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        for (final Resource resource : resources) {
            if (resource.isReadable()) {
                final MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                final Set<MethodMetadata> metadataSet = metadataReader.getAnnotationMetadata()
                        .getAnnotatedMethods(annotationClass.getName());
                if (metadataSet != null && metadataSet.size() > 0) {
                    for (final MethodMetadata metadata : metadataSet) {
                        final Map<String, Object> attributes = metadata.getAnnotationAttributes(annotationClass.getName());
                        final JobName attributeValue = (JobName) attributes.get(attributeName);
                        final String className = metadata.getDeclaringClassName();
                        final ClassMethodNamesPair pair = new ClassMethodNamesPair();
                        pair.className = className;
                        pair.methodName = metadata.getMethodName();
                        targetMethosMap.put(attributeValue.toString(), pair);
                    }
                }
            }
        }
    }
}
