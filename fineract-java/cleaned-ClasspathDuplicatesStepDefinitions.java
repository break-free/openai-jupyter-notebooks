
package org.apache.fineract.infrastructure.classpath;
import io.cucumber.java8.En;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.opentest4j.AssertionFailedError;
public class ClasspathDuplicatesStepDefinitions implements En {
    private Map<String, List<String>> duplicates = new HashMap<>();
    private ClassGraph classGraph;
    public ClasspathDuplicatesStepDefinitions() {
        Given("A class graph", () -> {
            this.classGraph = new ClassGraph();
        });
        When("The user scans the class graph", () -> {
            try (ScanResult scanResult = this.classGraph.scan()) {
                for (Map.Entry<String, ResourceList> dupe : scanResult.getAllResources().findDuplicatePaths()) {
                    String resourceName = dupe.getKey();
                    if (!isHarmlessDuplicate(resourceName)) {
                        boolean addIt = true;
                        List<String> jars = dupe.getValue().stream().map(resource -> resource.getURL().toExternalForm())
                                .collect(Collectors.toList());
                        for (String jar : jars) {
                            if (skipJAR(jar)) {
                                addIt = false;
                                break;
                            }
                        }
                        if (addIt) {
                            duplicates.put(resourceName, jars);
                        }
                    }
                }
            }
        });
        Then("There should be no duplicates", () -> {
            if (!duplicates.isEmpty()) {
                throw new AssertionFailedError(duplicates.size() + " Classpath duplicates detected:\n" + duplicates);
            }
        });
    }
    private boolean skipJAR(String jarPath) {
        return jarPath.contains("/.gradle/wrapper/dists/") || jarPath.contains("/JetBrains/");
    }
    private boolean isHarmlessDuplicate(String resourcePath) {
        return resourcePath.equals("META-INF/MANIFEST.MF") || resourcePath.equals("META-INF/INDEX.LIST")
                || resourcePath.equals("META-INF/ORACLE_J.SF") || resourcePath.toUpperCase().startsWith("META-INF/ASL")
                || resourcePath.toUpperCase().startsWith("META-INF/NOTICE") || resourcePath.toUpperCase().startsWith("META-INF/LICENSE")
                || resourcePath.toUpperCase().startsWith("LICENSE") || resourcePath.toUpperCase().startsWith("LICENSE/NOTICE")
                || resourcePath.endsWith(".txt") || resourcePath.endsWith("LICENSE") || resourcePath.endsWith("license.html")
                || resourcePath.endsWith("about.html") || resourcePath.endsWith("readme.html")
                || resourcePath.startsWith("META-INF/services") || resourcePath.equals("META-INF/DEPENDENCIES")
                || resourcePath.equals("META-INF/git.properties") || resourcePath.equals("META-INF/io.netty.versions.properties")
                || resourcePath.equals("META-INF/jersey-module-version") || resourcePath.startsWith("OSGI-INF/blueprint/")
                || resourcePath.startsWith("org/opendaylight/blueprint/") || resourcePath.endsWith("reference.conf")
                || resourcePath.equals("draftv4/schema") || resourcePath.equals("draftv3/schema") 
                || resourcePath.equals("WEB-INF/web.xml") || resourcePath.equals("META-INF/web-fragment.xml")
                || resourcePath.equals("META-INF/eclipse.inf") || resourcePath.equals("META-INF/ECLIPSE_.SF")
                || resourcePath.equals("META-INF/ECLIPSE_.RSA") || resourcePath.equals("META-INF/BC2048KE.DSA")
                || resourcePath.equals("META-INF/BC1024KE.DSA") || resourcePath.equals("META-INF/BC2048KE.SF")
                || resourcePath.equals("META-INF/BC1024KE.SF") || resourcePath.equals("OSGI-INF/bundle.info")
                || resourcePath.startsWith("META-INF/spring") || resourcePath.startsWith("META-INF/additional-spring")
                || resourcePath.startsWith("META-INF/terracotta")
                || resourcePath.startsWith("META-INF/groovy")
                || resourcePath.startsWith("META-INF/maven/") || resourcePath.contains("surefire")
                || resourcePath.equals("org/slf4j/impl/StaticLoggerBinder.class")
                || resourcePath.contains("JavaLaunchHelper")
                || resourcePath.equals("javax/annotation/Resource$AuthenticationType.class")
                || resourcePath.startsWith("javax/inject/")
                || resourcePath.endsWith("module-info.class") || resourcePath.contains("findbugs")
                || resourcePath.equals("plugin.properties") || resourcePath.equals(".api_description")
                || resourcePath.startsWith("org/checkerframework/dataflow/qual/")
                || resourcePath.endsWith("overview.html") || resourcePath.endsWith("classic-engine.properties")
                || resourcePath.endsWith("loader.properties");
    }
}
