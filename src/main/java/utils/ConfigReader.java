package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static
    {
        try
        {
            FileInputStream file = new FileInputStream("src/resources/config.properties");
            properties=new Properties();
            properties.load(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config file!!!");
        }
    }

    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }



}
