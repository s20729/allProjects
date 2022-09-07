/**
 *
 *  @author Syniuhin Oleksandr S20729
 *
 */

package zad4;


import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Tools {

    static Options createOptionsFromYaml(String fileName) throws Exception{
        Yaml yaml = new Yaml(new Constructor());
        Map <String, Object> obj = yaml.load(new FileReader(fileName));
        String host = String.valueOf(obj.get("host"));
        int port = (int) obj.get("port");
        boolean concurMode = (boolean) obj.get("concurMode");
        boolean showSendRes = (boolean) obj.get("showSendRes");
        Map<String, List<String>> clientsMap = (Map<String, List<String>>) obj.get("clientsMap");
        return new Options(host,port,concurMode,showSendRes,clientsMap);
    }
}
