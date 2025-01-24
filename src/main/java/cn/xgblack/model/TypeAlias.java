package cn.xgblack.model;


import org.apache.commons.text.StringEscapeUtils;


/**
 * 类型别名实体类
 * 
 * @author xg black
 */
public class TypeAlias extends DomainObject {

    public String title;

    public String description;

    public TypeAlias() {
    }

    public TypeAlias(String title, String description) {
        this.title = StringEscapeUtils.escapeJava(title);
        this.description = StringEscapeUtils.escapeJava(description);
    }

    public String getTitle() {
        return StringEscapeUtils.unescapeJava(title);
    }

    public void setTitle(String title) {
        this.title = StringEscapeUtils.escapeJava(title);
    }

    public String getDescription() {
        return StringEscapeUtils.unescapeJava(description);
    }

    public void setDescription(String description) {
        this.description = StringEscapeUtils.escapeJava(description);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.getTitle(), this.getDescription());
    }

}
