package zad1;

import java.util.HashSet;
import java.util.Set;

public class Programmer {
    private String name;
    private Set languages = new HashSet<String>();

    public Programmer(String name, Set<String> langs) {
        super();
        this.name = name;
        this.languages = langs;
    }

    public Integer getLangsSize(){
        return languages.size();
    }

    @Override
    public String toString() {
        return String.valueOf(languages);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getLanguages() {
        return languages;
    }

    public void setLanguages(Set languages) {
        this.languages = languages;
    }
}
