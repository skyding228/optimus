package com.netfinworks.optimus.entity;

public class DictEntity extends BaseEntity {
    /** 键 */
    private String id;

    /** 值 */
    private String value;

    /** 类型;CHAN:渠道,AUTH:授权信息 */
    private String type;

    /** 描述信息 */
    private String description;

    /**
     * 键
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 键
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 值
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * 值
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 类型;CHAN:渠道,AUTH:授权信息
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 类型;CHAN:渠道,AUTH:授权信息
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 描述信息
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述信息
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}