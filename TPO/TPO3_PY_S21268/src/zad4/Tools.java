/**
 *
 *  @author Popichko Yehor S21268
 *
 */

package zad4;


import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class Tools {

    public static Options createOptionsFromYaml(String fileName) throws Exception {
        Yaml yaml = new Yaml();
        FileReader f = new FileReader(fileName);
        Map<String,Object> yamlToMap = yaml.load(f);
        String host = String.valueOf(yamlToMap.get("host"));
        int port = (int) yamlToMap.get("port");
        boolean concurMode = (boolean) yamlToMap.get("concurMode");
        boolean showSendRes = (boolean) yamlToMap.get("showSendRes");
        Map<String, List<String>> clientsMap = (Map<String, List<String>>) yamlToMap.get("clientsMap");
        return new Options(host,port,concurMode,showSendRes,clientsMap);
    }
}
