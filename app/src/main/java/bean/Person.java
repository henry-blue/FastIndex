package bean;


import util.languageUtil;

public class Person implements Comparable<Person> {
    private String name;
    private String pinyin;

    public Person(String name) {
        this.name = name;
        this.pinyin = languageUtil.getLanguageString(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Person another) {
        return this.pinyin.compareTo(another.getPinyin());
    }
}
