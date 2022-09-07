package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang<K, V> {

    private Map<K, V> langsMap;
    private Map<String, List<String>> progsMap;

    public ProgLang(String fname) throws SecurityException, IOException {
        langsMap = new LinkedHashMap<>();
        FileReader fileReader = new FileReader(fname);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null){
            String arr[] = line.split("\\t");
            List<String> list = new ArrayList<>();
            for (int i = 1; i < arr.length; i++) {
                if (!(list.contains(arr[i]))) {
                    list.add(arr[i]);
                }
            }
            langsMap.put((K)arr[0],(V)list);
        }
    }

    public <K2, V2> Map<K, V> getLangsMap() {
        return (Map<K, V>) langsMap;
    }

    public Map<String, List<String>> getProgsMap() {
        progsMap = new LinkedHashMap<String, List<String>>();
        for (K lang : langsMap.keySet()) {
            for (V programmers : (List<V>) langsMap.get(lang)) {
                if (progsMap.containsKey(programmers)) {
                    progsMap.get(programmers).add(String.valueOf(lang));
                } else {
                    List<String> langs = new ArrayList<>();
                    langs.add(String.valueOf(lang));
                    progsMap.put(String.valueOf(programmers), langs);
                }
            }
        }
        return progsMap;

    }
    public Map<K, V> getLangsMapSortedByNumOfProgs() {
        return langsMap.entrySet().stream().sorted((l1,l2)->{
            List<String> list = (List<String>) l1.getValue();
            List<String> list1 = (List<String>) l2.getValue();
            Integer listSize = list.size();
            Integer list1Size = list1.size();
            return list1Size.compareTo(listSize);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<K, V> getProgsMapSortedByNumOfLangs() {
        Map<K, V> map = (LinkedHashMap<K, V>) this.progsMap.entrySet()
                .stream()
                .sorted((e1,e2)->{
                    Integer integer = e1.getValue().size();
                    Integer integer1 = e2.getValue().size();
                    if(integer == integer1){
                        return e1.getKey().compareTo(e2.getKey());
                    }
                    return integer1.compareTo(integer);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return map;
    }
    public Map<K, V> getProgsMapForNumOfLangsGreaterThan(int i) {
        Map<K, V> map = (LinkedHashMap<K, V>) this.progsMap.entrySet()
                .stream()
                .filter(e -> e.getValue().size() > i)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return map;
    }

    public static <K, V> Map<K, V> sorted(Map<K, V> entry, Comparator<Map.Entry<K, V>> comparator) {
        Map<K, V> map = entry.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));
        return map;
    }

    public static <K, V> Map<K, V> filtered(Map<K, V> entry, Predicate<Map.Entry<K, V>> predicate) {
        Map<K, V> map = entry.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return map;
    }
}
