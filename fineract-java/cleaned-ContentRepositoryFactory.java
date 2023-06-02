
package org.apache.fineract.infrastructure.documentmanagement.contentrepository;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.configuration.data.S3CredentialsData;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.apache.fineract.infrastructure.configuration.service.ExternalServicesPropertiesReadPlatformService;
import org.apache.fineract.infrastructure.documentmanagement.domain.StorageType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ContentRepositoryFactory {
    private final ApplicationContext applicationContext;
    private final ExternalServicesPropertiesReadPlatformService externalServicesReadPlatformService;
    private final FileSystemContentPathSanitizer contentPathSanitizer;
    public ContentRepository getRepository() {
        final ConfigurationDomainService configurationDomainServiceJpa = this.applicationContext.getBean("configurationDomainServiceJpa",
                ConfigurationDomainService.class);
        if (configurationDomainServiceJpa.isAmazonS3Enabled()) {
            return createS3DocumentStore();
        }
        return new FileSystemContentRepository(contentPathSanitizer);
    }
    public ContentRepository getRepository(final StorageType documentStoreType) {
        if (documentStoreType == StorageType.FILE_SYSTEM) {
            return new FileSystemContentRepository(contentPathSanitizer);
        }
        return createS3DocumentStore();
    }
    private ContentRepository createS3DocumentStore() {
        final S3CredentialsData s3CredentialsData = this.externalServicesReadPlatformService.getS3Credentials();
        return new S3ContentRepository(s3CredentialsData.getBucketName(), s3CredentialsData.getSecretKey(),
                s3CredentialsData.getAccessKey());
    }
}
