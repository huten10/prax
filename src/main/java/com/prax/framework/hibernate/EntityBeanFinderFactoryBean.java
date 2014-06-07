/**
 * 
 */
package com.prax.framework.hibernate;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author Alvin.Hu
 * 
 */
public class EntityBeanFinderFactoryBean implements ResourceLoaderAware, FactoryBean<Set<Class<?>>> {
    private ResourcePatternResolver resolver;

    private Set<Class<?>> annotatedEntityClasses = new HashSet<Class<?>>();

    private Set<String> searchPatterns = new HashSet<String>();

    private Set<String> qualifiedClassNamePatterns = new HashSet<String>();

    public void setResourceLoader(ResourceLoader resourceLoader) {
        resolver = (ResourcePatternResolver) resourceLoader;
    }

    public Set<Class<?>> getObject() throws Exception {
        if (annotatedEntityClasses.isEmpty())
            searchAnnotatedEntityClasses();

        return annotatedEntityClasses;
    }

    public Class<?> getObjectType() {
        return annotatedEntityClasses.getClass();
    }

    public boolean isSingleton() {
        return true;
    }

    public Set<String> getQualifiedClassNamePatterns() {
        return qualifiedClassNamePatterns;
    }

    public void setQualifiedClassNamePatterns(Set<String> qualifiedClassNamePatterns) {
        // Regular expression are sensitive with special characters.
        for (String pattern : qualifiedClassNamePatterns)
            this.qualifiedClassNamePatterns.add(pattern.replaceAll("[\t\n]", ""));
    }

    public Set<String> getSearchPatterns() {
        return searchPatterns;
    }

    public void setSearchPatterns(Set<String> searchPatterns) {
        // Regular expression are sensitive with special characters.
        for (String pattern : searchPatterns)
            this.searchPatterns.add(pattern.replaceAll("[\t\n]", ""));

    }

    protected void searchAnnotatedEntityClasses() {
        // Search resources by every search pattern.
        for (String searchPattern : searchPatterns) {
            try {
                Resource[] resources = resolver.getResources(searchPattern);
                if (resources != null) {
                    // Parse every resource.
                    for (Resource res : resources) {
                        String path = res.getURL().getPath();

                        // Path name string should not be empty.
                        if (!path.equals("")) {
                            if (path.endsWith(".class")) {
                                dealWithClasses(path);
                            } else if (path.endsWith(".jar")) {
                                dealWithJars(res);
                            }
                        }
                    }
                }
            } catch (Exception ignore) {
            }
        }
    }

    protected void dealWithClasses(String path) {
        Set<String> qClassNames = listAllPossibleQualifiedClassNames(path);

        for (String qName : qClassNames) {
            // Apply the qualified class name pattern to improve the searching
            // performance.
            if (matchQualifiedClassNamePatterns(qName))
                addPossibleClasses(qName);
        }
    }

    protected void addPossibleClasses(String qName) {
        Class<?> clazz;
        try {
            clazz = Class.forName(qName);

            // Add the class to the annotatedEntityClasses property.
            if (checkEntityAnnotation(clazz))
                annotatedEntityClasses.add(clazz);
        } catch (Exception ignore) {
        } catch (NoClassDefFoundError ignore) {
        }
    }

    protected void dealWithJars(Resource res) throws Exception {
        // Enumerate all entries in this JAR file.
        Enumeration<JarEntry> jarEntries = new JarFile(res.getFile()).entries();
        while (jarEntries.hasMoreElements()) {
            String name = jarEntries.nextElement().getName();

            // If the entry is a class, deal with it.
            if (name.endsWith(".class") && !name.equals("")) {
                // Format the path first.
                name = pathToQualifiedClassName(name);

                // Apply the qualified class name pattern to improve the
                // searching performance.
                if (matchQualifiedClassNamePatterns(name))
                    // This is the qualified class name, so add it.
                    addPossibleClasses(name);
            }
        }
    }

    protected Set<String> listAllPossibleQualifiedClassNames(String path) {
        Set<String> qualifiedClassNames = new HashSet<String>();

        // Format the path first.
        path = pathToQualifiedClassName(path);

        // Split the QName by the dot (i.e. '.') character.
        String[] pathParts = path.split("\\.");

        // Add the path parts one by one from the end of the array to the
        // beginning.
        StringBuffer qName = new StringBuffer();
        for (int i = pathParts.length - 1; i >= 0; i--) {
            qName.insert(0, pathParts[i]);
            qualifiedClassNames.add(qName.toString());
            qName.insert(0, ".");
        }

        return qualifiedClassNames;
    }

    protected String pathToQualifiedClassName(String path) {
        return path.replaceAll("/", ".").replaceAll("\\\\", ".").substring(0, path.length() - ".class".length());
    }

    protected boolean matchQualifiedClassNamePatterns(String path) {
        if (qualifiedClassNamePatterns.isEmpty())
            return true;

        for (String pattern : qualifiedClassNamePatterns) {
            if (path.matches(pattern))
                return true;
        }
        return false;
    }

    protected boolean checkEntityAnnotation(Class<?> clazz) {
        boolean hasEntityAnnotation = clazz.getAnnotation(javax.persistence.Entity.class) != null;
        boolean hasMappedSuperclassAnnotation = clazz.getAnnotation(javax.persistence.MappedSuperclass.class) != null;

        if (hasEntityAnnotation || hasMappedSuperclassAnnotation)
            return true;
        else
            return false;
    }
}
