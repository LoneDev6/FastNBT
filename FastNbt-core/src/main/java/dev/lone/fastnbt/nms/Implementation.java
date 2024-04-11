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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static <IType> IType find(Class<IType> type, Version version)
    {
        Map<Class<?>, CyclicDependency> cache = new HashMap<>();
        for (Class<?> clazz : listAllClasses())
        {
            if (!clazz.isAnnotationPresent(CyclicDependency.class))
                continue;

            CyclicDependency annotation = clazz.getAnnotation(CyclicDependency.class);
            cache.put(clazz, annotation);
        }

        Class<IType> implClass = findInCache(cache, type, version);
        if (implClass != null)
            return newInstance(implClass);

        // Try to find the closest compatible version.
        Version[] versions = Version.values();
        for (int i = version.ordinal(); i >= 0; i--)
        {
            implClass = findInCache(cache, type, versions[i]);
            if (implClass != null)
            {
                return newInstance(implClass);
            }
        }

        throw new RuntimeException("This server is not compatible with this plugin.");
    }

    private static <ImplType> ImplType newInstance(Class<ImplType> implClass)
    {
        try
        {
            return implClass.getDeclaredConstructor().newInstance();
        }
        catch (Throwable e)
        {
            Bukkit.getLogger().severe("FastNBT failed to instantiate nms wrapper");
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <ImplType, IType> Class<ImplType> findInCache(Map<Class<?>, CyclicDependency> cache, Class<IType> type, Version version)
    {
        Class<ImplType> implementationClass = null;
        for (Map.Entry<Class<?>, CyclicDependency> entry : cache.entrySet())
        {
            Class<?> clazz = entry.getKey();
            CyclicDependency annotation = entry.getValue();
            if (type.equals(annotation.type()) && annotation.version() == version)
                implementationClass = (Class<ImplType>) clazz;
        }
        return implementationClass;
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
                        catch (ClassNotFoundException | NoClassDefFoundError | UnsupportedClassVersionError ignored)
                        {
                            // ClassNotFoundException:
                            //      Skip if not a class for some reason.
                            // NoClassDefFoundError:
                            //      Happens if impl class extends an NMS class which is not available
                            //      in the current game version.
                            // UnsupportedClassVersionError:
                            //      Happens if class was compiled with a new Java version, unsupported by the server.
                        }
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