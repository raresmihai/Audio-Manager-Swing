package model;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by rares on 20.03.2016.
 */
public class MetadataExtractor {

    public Metadata extract(String fileName) {
        File file = new File(fileName);

        //Parser method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            //System.out.println("File not found!");
        }
        ParseContext context = new ParseContext();

        try {
            if(inputstream != null) {
                parser.parse(inputstream, handler, metadata, context);
            }
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (SAXException e) {
            System.out.println("SAXException");
        } catch (TikaException e) {
            System.out.println("TikaException");
        }

        //getting the list of all meta data elements
        return metadata;
    }
}
