package dev.lone.fastnbt.nms;

import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("SameParameterValue")
public abstract class Implementation
{
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface CyclicDependency
    {
        Class<?> type();
        Version version();
    }

    private static final String basePackageNonObf = Implementation.class.getPackageName();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T find(Class<T> type, Version Version)
    {
        Class implementationClass = null;
        for (Class clazz : listAllClasses())
        {
            if (!clazz.isAnnotationPresent(CyclicDependency.class))
                continue;

            CyclicDependency annotation = (CyclicDependency) clazz.getAnnotation(CyclicDependency.class);
            if (type.equals(annotation.type()) && annotation.version() == Version)
                implementationClass = clazz;
        }

        if(implementationClass == null)
        {
            throw new RuntimeException("This server is not compatible with this plugin.");
        }

        try
        {
            return (T) implementationClass.getDeclaredConstructor().newInstance();
        }
        catch (Throwable e)
        {
            Bukkit.getLogger().severe("FastNBT failed to instantiate nms wrapper");
            throw new RuntimeException(e);
        }
    }

    /**
     * Lists all classes in the current JAR.
     */
    private static List<Class<?>> listAllClasses()
    {
        return findClassesInPackage(null);
    }

    /**
     * Lists all classes in the current JAR which are in a particular package.
     */
    private static List<Class<?>> findClassesInPackage(@Nullable String packageName)
    {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Implementation.class.getClassLoader();
        CodeSource src = Implementation.class.getProtectionDomain().getCodeSource();
        if (src == null)
            return classes;
        URL jar = src.getLocation();
        ZipInputStream zip;
        try
        {
            zip = new ZipInputStream(jar.openStream());
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null)
            {
                String name = entry.getName();
                if (!name.endsWith(".class"))
                    continue;
                name = name
                        .replace("\\", ".")
                        .replace("/", ".")
                        .replace(".class", "");
                if(packageName == null || name.startsWith(packageName))
                {
                    if(name.startsWith(basePackageNonObf + "."))
                    {
                        try
                        {
                            classes.add(classLoader.loadClass(name));
                        }
                        catch (ClassNotFoundException ignored) {} // Skip if not a class.
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return classes;
    }
}