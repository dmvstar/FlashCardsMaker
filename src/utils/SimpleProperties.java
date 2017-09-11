package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class SimpleProperties
{
    private String propertiesFilePath;
    private Properties properties;

    public SimpleProperties() throws InvalidPropertiesFormatException, IOException
    {
        propertiesFilePath = "configuration.xml";
        properties = new Properties();

        try
        {
            properties.loadFromXML(new FileInputStream(propertiesFilePath));
        } catch (InvalidPropertiesFormatException e)
        {

        }
    }

    public void put(String key, String value) throws FileNotFoundException, IOException
    {
        properties.setProperty(key, value);

        store();
    }

    public String get(String key)
    {
        return properties.getProperty(key);
    }

    private void store() throws FileNotFoundException, IOException
    {
        String commentText = "Program parameters";

        properties.storeToXML(new FileOutputStream(propertiesFilePath), commentText);
    }
}